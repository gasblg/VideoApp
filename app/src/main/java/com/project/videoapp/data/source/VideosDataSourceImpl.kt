package com.project.videoapp.data.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.project.videoapp.net.Operation
import com.project.videoapp.net.Result
import com.project.videoapp.net.api.ApiVideos
import com.project.videoapp.net.responses.Item
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class VideosDataSourceImpl @Inject constructor(
    private val api: ApiVideos
) : VideosDataSource {

    companion object {
        const val PAGE_SIZE = 20
    }

    override suspend fun getVideos(
        part: String,
        channelId: String,
        maxResult: String,
        apiKey: String,
        date: String
    ) = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
        ),
        pagingSourceFactory = { LoadData(part, channelId, maxResult, apiKey, date) }
    ).flow


    inner class LoadData(
        private val part: String,
        private val channelId: String,
        private val maxResult: String,
        private val apiKey: String,
        private val date: String
    ) : PagingSource<String, Item>() {

        override fun getRefreshKey(state: PagingState<String, Item>): String? {
            return null
        }

        override suspend fun load(params: LoadParams<String>): LoadResult<String, Item> {
            val pageToken = params.key
            try {
                return when (val result =
                    Operation.of {
                        api.getVideos(
                            part,
                            channelId,
                            maxResult,
                            apiKey,
                            date,
                            pageToken
                        )
                    }) {
                    is Result.Error -> {
                        LoadResult.Error(result.error)
                    }
                    is Result.Data -> {
                        val data = result.data
                        val nextKey = if (data.items.isEmpty()) null else data.nextPageToken
                        val prevKey = if (pageToken == null) null else data.prevPageToken

                        LoadResult.Page(
                            data = data.items,
                            prevKey = prevKey,
                            nextKey = nextKey
                        )
                    }
                }
            } catch (e: IOException) {
                return LoadResult.Error(e)
            } catch (exception: HttpException) {
                return LoadResult.Error(exception)
            }
        }
    }
}