package com.project.videoapp.ui.fragments.videos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.project.videoapp.core.DateManager
import com.project.videoapp.databinding.ItemVideosBinding
import com.project.videoapp.net.responses.Item


class VideosAdapter(val dateManager: DateManager) :
    PagingDataAdapter<Item, VideosAdapter.VideosHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(
                oldConcert: Item,
                newConcert: Item
            ) =
                oldConcert == newConcert

            override fun areContentsTheSame(
                oldConcert: Item,
                newConcert: Item
            ) =
                oldConcert == newConcert
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VideosAdapter.VideosHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVideosBinding.inflate(
            inflater,
            parent,
            false
        )
        return VideosHolder(binding)
    }

    override fun onBindViewHolder(holder: VideosHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private lateinit var onItemClickListener: ((item: Item) -> Unit)

    fun setOnItemClickListener(onItemClickListener: (item: Item) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    inner class VideosHolder(private val binding: ItemVideosBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item?) {
            binding.apply {
                item?.let { videoItem ->
                    tvDate.text = dateManager.getRuDateFormat(videoItem.snippet.publishTime)
                    tvTitle.text = videoItem.snippet.title
                    tvDescription.text = videoItem.snippet.description
                    Glide.with(this.root)
                        .load(videoItem.snippet.thumbnails.medium.url)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .dontAnimate()
                        .into(ivLogo)
                    this.root.setOnClickListener {
                        onItemClickListener.invoke(item)
                    }
                }
            }
        }
    }


}






