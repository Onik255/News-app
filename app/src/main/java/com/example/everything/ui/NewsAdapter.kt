package com.example.everything.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.everything.R
import com.example.everything.data.News
import com.example.everything.databinding.ItemNewsBinding

class NewsAdapter(
    private val isForFavorites: Boolean,
    private val onItemClick: (String) -> Unit,
    private val changeFavorite: (News) -> Unit
) : ListAdapter<News, NewsAdapter.NewsViewHolder>(ItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) =
        holder.bind(currentList[position], isForFavorites, onItemClick, changeFavorite)

    class NewsViewHolder(private val itemBinding: ItemNewsBinding) :
        ViewHolder(itemBinding.root) {

        fun bind(
            item: News,
            isForFavorites: Boolean,
            onItemClick: (String) -> Unit,
            changeFavorite: (News) -> Unit
        ) {
            itemBinding.titleTextView.text = item.title
            itemBinding.authorTextView.text = item.author
            itemBinding.publishedAtTextView.text = item.publishedAt
            itemBinding.descriptionTextView.text = item.description
            itemBinding.imageView.load(item.urlToImage)

            itemBinding.root.setOnClickListener { onItemClick(item.url) }
            itemBinding.favoriteImageView.setOnClickListener { changeFavorite(item) }
            if (isForFavorites) {
                itemBinding.favoriteImageView.setImageResource(R.drawable.ic_remove_fav)
            } else {
                itemBinding.favoriteImageView.setImageResource(R.drawable.ic_add_fav)
            }
        }
    }

    private class ItemDiffCallBack : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: News, newItem: News) =
            oldItem == newItem
    }
}