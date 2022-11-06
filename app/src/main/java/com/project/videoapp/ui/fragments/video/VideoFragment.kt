package com.project.videoapp.ui.fragments.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.project.videoapp.core.DateManager
import com.project.videoapp.databinding.FragmentVideoBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class VideoFragment :DaggerFragment() {

    companion object {
        fun newInstance() = VideoFragment()
        const val YOUTUBE_VIDEO_ID = "youtube_video_id"
        const val DATE = "date"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
    }

    @Inject
    lateinit var dateManager: DateManager

    private var binding: FragmentVideoBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentVideoBinding = FragmentVideoBinding.inflate(inflater, container, false)
        this.binding = fragmentVideoBinding
        return fragmentVideoBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getInfoData()
    }

    private fun getInfoData() {
        arguments?.let { args ->
            val videoId = args.getString(YOUTUBE_VIDEO_ID) as String
            val date = args.getString(DATE) as String
            val title = args.getString(TITLE) as String
            val description = args.getString(DESCRIPTION) as String

            showVideo(videoId)
            showDescription(date, title, description)
        }
    }

    private fun showVideo(videoId: String) {
        lifecycle.addObserver(binding!!.playerView)
        binding!!.playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }

    private fun showDescription(date: String, title: String, description: String) {
        binding?.apply {
            tvDate.text = dateManager.getRuDateFormat(date)
            tvTitle.text = title
            tvDescription.text = description
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}