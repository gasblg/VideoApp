package com.github.gasblg.videoapp.data.source

import androidx.paging.*
import androidx.room.withTransaction
import com.github.gasblg.videoapp.data.DeveloperData
import com.github.gasblg.videoapp.data.database.db.AppDataBase
import com.github.gasblg.videoapp.data.database.entities.RemoteKeyEntity
import com.github.gasblg.videoapp.data.database.entities.VideoEntity
import com.github.gasblg.videoapp.data.mappers.VideoResponseMapper
import com.github.gasblg.videoapp.data.net.Operation
import com.github.gasblg.videoapp.domain.models.Params
import com.github.gasblg.videoapp.data.net.api.ApiVideos
import retrofit2.HttpException
import java.io.IOException
import com.github.gasblg.videoapp.data.net.Result

@OptIn(ExperimentalPagingApi::class)
class RemoteVideosMediator(
    private val label: String,
    private val database: AppDataBase,
    private val api: ApiVideos,
    private val params: Params,
    private val videoMapper: VideoResponseMapper
) : RemoteMediator<Int, VideoEntity>() {

    override suspend fun initialize(): InitializeAction {
        // Require that remote REFRESH is launched on initial load and succeeds before launching
        // remote PREPEND / APPEND.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, VideoEntity>
    ): MediatorResult {
        try {

            val page = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        database.remoteKeysDao.getRemoteKeyByLabel(label)
                    }

                    if (remoteKey.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.nextKey
                }
            }
            when (val result = Operation.of {
                api.getVideos(
                    DeveloperData.DEVELOPER_KEY,
                    params.part,
                    params.channelId,
                    params.maxResult,
                    params.order,
                    pageToken = page
                )
            }) {
                is Result.Error -> {
                    return MediatorResult.Error(result.error)
                }

                is Result.Data -> {
                    database.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            database.remoteKeysDao.getRemoteKeyByLabel(label)
                            database.videosDao.deleteAll()
                        }
                    }

                    val data = result.data
                    val items = data.items.map { videoMapper.mapFrom(it) }
                    val nextKey = if (data.items.isEmpty()) null else data.nextPageToken
                    val prevKey = data.prevPageToken
                    database.remoteKeysDao.insertRemoteKey(
                        RemoteKeyEntity(
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