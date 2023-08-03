package com.myapp.newsmvvmappdemo.view.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.newsmvvmappdemo.viewmodel.NewsViewModel
import com.myapp.newsmvvmappdemo.R
import com.myapp.newsmvvmappdemo.adapter.NewsAdapter
import com.myapp.newsmvvmappdemo.databinding.ActivityNewsBinding
import com.myapp.newsmvvmappdemo.interfaces.ItemClickListener
import com.myapp.newsmvvmappdemo.network.Articles
import com.myapp.newsmvvmappdemo.utils.ConnectionManager
import com.myapp.newsmvvmappdemo.utils.ProgressDialoger
import com.myapp.newsmvvmappdemo.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppCompatActivity(), ItemClickListener {
    private lateinit var binding: ActivityNewsBinding
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var mContext: Context
    private lateinit var arrayListNews: ArrayList<Articles>
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        mContext = this
        initView()
        setupViewModel()
        if (!ConnectionManager.userOnline(mContext)) {
            Toast.makeText(
                mContext,
                mContext.resources.getString(R.string.please_check_your_internet_connection),
                Toast.LENGTH_LONG
            ).show()
        } else {
            viewModel.getNews()
        }
    }

    private fun initView() {
        arrayListNews = ArrayList()
        binding.rvNews.layoutManager = LinearLayoutManager(mContext)
        newsAdapter = NewsAdapter(mContext, arrayListNews, this)
        binding.rvNews.adapter = newsAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupViewModel() {
        viewModel.processing.observe(this) {
            it?.let { loading ->
                if (loading)
                    ProgressDialoger.initialize(this)
                else
                    ProgressDialoger.dismiss()
            }
        }
        viewModel.news.observe(this) { data ->
            arrayListNews.addAll(data)
            newsAdapter.notifyDataSetChanged()
        }
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(mContext, NewsDetailsActivity::class.java)
        intent.putExtra(Utils.NEWS, arrayListNews[position])
        startActivity(intent)
    }
}