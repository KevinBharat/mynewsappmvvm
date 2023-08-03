package com.myapp.newsmvvmappdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.myapp.newsmvvmappdemo.R
import com.myapp.newsmvvmappdemo.databinding.ItemNewsBinding
import com.myapp.newsmvvmappdemo.interfaces.ItemClickListener
import com.myapp.newsmvvmappdemo.network.Articles

class NewsAdapter(
    private val mContext: Context,
    private val arrayListNews: ArrayList<Articles>,
    private val itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        //bind layout for databinding
        return MyViewHolder(
            ItemNewsBinding.bind(
                LayoutInflater.from(mContext).inflate(
                    R.layout.item_news,
                    parent,
                    false
                )
            )
        )

    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {

        holder.bind(
            arrayListNews[position],
            position,
            itemClickListener,
            mContext
        )
    }

    override fun getItemCount(): Int {
        return arrayListNews.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class MyViewHolder(private var itemBinding: ItemNewsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        //method to set data
        fun bind(
            news: Articles,
            position: Int,
            itemClickListener: ItemClickListener,
            context: Context
        ) {
            itemBinding.tvTitle.text = news.title
            itemBinding.tvDescription.text = news.description
            Glide.with(context).load(news.urlToImage)
                .error(ContextCompat.getDrawable(context, R.drawable.ic_no_image))
                .diskCacheStrategy(DiskCacheStrategy.DATA).into(itemBinding.img)


            itemBinding.root.setOnClickListener {
                itemClickListener.onItemClick(position)
            }
        }
    }
}