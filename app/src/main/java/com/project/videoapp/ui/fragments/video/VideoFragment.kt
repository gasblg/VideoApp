package com.project.videoapp.ui.fragments.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.project.videoapp.core.DateManager
import com.project.videoapp.databinding.FragmentVideoBinding
import com.project.videoapp.ui.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class VideoFragment : DaggerFragment() {

    companion object {
        fun newInstance() = VideoFragment()
        const val YOUTUBE_VIDEO_ID = "youtube_video_id"
    }

    @Inject
    lateinit var dateManager: DateManager

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: VideoViewModel by viewModels {
        viewModelFactory
    }
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
            viewModel.getVideoInfo(videoId)
        }
        showDescription()
    }


    private fun showDescription() {
        viewModel.video.observe(viewLifecycleOwner) {
            binding?.apply {
                tvDate.text = dateManager.getRuDateFormat(it.date)
                tvTitle.text = it.title
                tvDescription.text = it.description
                showVideo(it.videoId ?: "")
            }
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

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}