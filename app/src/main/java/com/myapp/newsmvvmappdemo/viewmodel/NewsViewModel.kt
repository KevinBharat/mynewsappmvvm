package com.myapp.newsmvvmappdemo.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myapp.newsmvvmappdemo.network.ActionResult
import com.myapp.newsmvvmappdemo.network.Articles
import com.myapp.newsmvvmappdemo.network.NewsResponse
import com.myapp.newsmvvmappdemo.repository.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class NewsViewModel @Inject constructor(
    @ApplicationContext val mContext: Context,
    private val remoteRepository: RemoteRepository) : BaseViewModel() {

    private val _news = MutableLiveData<List<Articles>>()
    val news: LiveData<List<Articles>>
        get() = _news

    fun getNews() = processAsync()
    {
        when (val result = remoteRepository.getNews()) {
            is ActionResult.Success<NewsResponse> -> onSuccess(result.data)
            is ActionResult.Error -> onFailure(result.exception)
            is ActionResult.Warning -> Toast.makeText(mContext, result.message, Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun onFailure(exception: ResponseBody?) {

        Toast.makeText(mContext, exception?.string(), Toast.LENGTH_LONG).show()
    }

    private fun onSuccess(data: NewsResponse) {
        _news.value = data.articles
    }
}