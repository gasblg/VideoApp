package com.project.videoapp.data.source

import androidx.paging.*
import androidx.room.withTransaction
import com.bumptech.glide.load.HttpException
import com.project.videoapp.data.database.db.AppDataBase
import com.project.videoapp.data.database.entities.RemoteKey
import com.project.videoapp.data.database.entities.Video
import com.project.videoapp.data.mappers.VideoMapper
import com.project.videoapp.data.models.InfoModel
import com.project.videoapp.net.Operation
import com.project.videoapp.net.Result
import com.project.videoapp.net.api.ApiVideos
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class RemoteVideosMediator(
    private val label: String,
    private val database: AppDataBase,
    private val api: ApiVideos,
    private val apiKey: String,
    private val infoModel: InfoModel,
    private val videoMapper: VideoMapper
) : RemoteMediator<Int, Video>() {

    override suspend fun initialize(): InitializeAction {
        // Require that remote REFRESH is launched on initial load and succeeds before launching
        // remote PREPEND / APPEND.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Video>
    ): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        database.remoteKeysDao.remoteKeyByQuery(label)
                    }

                    if (remoteKey.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.nextKey
                }
            }
            when (val result = Operation.of {
                api.getVideos(
                    apiKey,
                    infoModel.part,
                    infoModel.channelId,
                    infoModel.maxResult,
                    infoModel.order,
                    pageToken = page
                )
            }) {
                is Result.Error -> {
                    return MediatorResult.Error(result.error)
                }
                is Result.Data -> {
                    database.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            database.remoteKeysDao.remoteKeyByQuery(label)
                            database.videosDao.deleteAll()
                        }
                    }

                    val data = result.data
                    val items = data.items.map { videoMapper.mapFrom(it) }
                    val nextKey = if (data.items.isEmpty()) null else data.nextPageToken
                    val prevKey = data.prevPageToken

                    database.remoteKeysDao.insertOrReplace(
                        RemoteKey(
                            label = label,
                            prevKey = prevKey,
                            nextKey = nextKey
                        )
                    )
                    database.videosDao.insertMultipleVideos(items)
                    return MediatorResult.Success(endOfPaginationReached = items.isEmpty())
                }
            }

        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

}