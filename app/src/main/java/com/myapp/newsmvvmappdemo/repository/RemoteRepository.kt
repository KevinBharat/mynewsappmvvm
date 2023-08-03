package com.myapp.newsmvvmappdemo.repository

import android.content.Context
import com.myapp.newsmvvmappdemo.R
import com.myapp.newsmvvmappdemo.network.ActionResult
import com.myapp.newsmvvmappdemo.network.NewsResponse
import com.myapp.newsmvvmappdemo.network.WebApiClient
import com.myapp.newsmvvmappdemo.utils.Utils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

interface IRemoteRepository {

    suspend fun getNews(): ActionResult<NewsResponse>
}


@Singleton
class RemoteRepository @Inject constructor(
    @ApplicationContext val mContext: Context
) : IRemoteRepository {
    override suspend fun getNews(): ActionResult<NewsResponse> {
        return withTimeout(Utils.TIMEOUT) {
            try {
                val response = WebApiClient.instance.getNews()
                if (response.status == "ok") {
                    ActionResult.Success(response)
                } else {
                    ActionResult.Warning(mContext.resources.getString(R.string.something_went_wrong))
                }
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        ActionResult.Error(throwable.response()?.errorBody())
                    }
                    else -> {
                        ActionResult.Warning(mContext.resources.getString(R.string.something_went_wrong))
                    }
                }
            }
        }
    }
}