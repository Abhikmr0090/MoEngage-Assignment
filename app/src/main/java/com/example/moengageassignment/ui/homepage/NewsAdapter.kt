package com.example.moengageassignment.ui.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.moengageassignment.R
import com.example.moengageassignment.databinding.NewsItemLayoutBinding
import com.example.moengageassignment.ui.homepage.models.NewsArticlesModel

class NewsAdapter(
    private val newsList: List<NewsArticlesModel>,
    private val onItemClick: (String) -> Unit
) : Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(val binding: NewsItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.news_item_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsData = newsList[position]
        holder.binding.newsModel = newsData

        holder.binding.root.setOnClickListener {
            onItemClick(newsData.url ?: "")
        }
    }
}