package com.github.gasblg.videoapp.presentation.ui.fragments.videos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.github.gasblg.videoapp.domain.models.Video

import com.github.gasblg.videoapp.presentation.databinding.ItemVideosBinding


class VideosAdapter :
    PagingDataAdapter<Video, VideosAdapter.VideosHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Video>() {
            override fun areItemsTheSame(
                oldVideo: Video,
                newVideo: Video
            ) =
                oldVideo.tag == newVideo.tag

            override fun areContentsTheSame(
                oldVideo: Video,
                newVideo: Video
            ) =
                oldVideo == newVideo
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VideosHolder {
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

    private lateinit var onItemClickListener: ((item: Video) -> Unit)

    fun setOnItemClickListener(onItemClickListener: (item: Video) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    inner class VideosHolder(private val binding: ItemVideosBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Video?) {
            binding.apply {
                item?.let { videoItem ->
                    date.text = videoItem.date
                    title.text = videoItem.title
                    description.text = videoItem.description
                    Glide.with(this.root)
                        .load(videoItem.imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .dontAnimate()
                        .into(logo)
                    this.root.setOnClickListener {
                        onItemClickListener.invoke(item)
                    }
                }
            }
        }
    }
}






