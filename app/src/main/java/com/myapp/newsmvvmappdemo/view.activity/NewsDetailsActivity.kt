package com.myapp.newsmvvmappdemo.view.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.myapp.newsmvvmappdemo.R
import com.myapp.newsmvvmappdemo.databinding.ActivityNewsDetailsBinding
import com.myapp.newsmvvmappdemo.network.Articles
import com.myapp.newsmvvmappdemo.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsDetailsBinding
    private lateinit var mContext: Context
    private var news: Articles? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_details)
        mContext = this
        news = intent.getSerializableExtra(Utils.NEWS) as Articles?
        initView()
        registerListener()
    }

    private fun initView() {
        binding.toolbar.imgBack.visibility = View.VISIBLE
        binding.toolbar.tvToolbarTitle.text = mContext.resources.getString(R.string.news_details)
        Glide.with(mContext).load(news?.urlToImage)
            .error(ContextCompat.getDrawable(mContext, R.drawable.ic_no_image))
            .diskCacheStrategy(DiskCacheStrategy.DATA).into(binding.img)
        binding.tvTitle.text = news?.title
        binding.tvDescription.text = news?.description
        binding.tvContent.text = news?.content
    }

    private fun registerListener() {
       binding.toolbar.imgBack.setOnClickListener {
            finish()
        }
    }
}