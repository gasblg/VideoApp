package com.github.gasblg.videoapp.presentation.ui.fragments.video

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.github.gasblg.videoapp.presentation.databinding.FragmentVideoBinding
import com.github.gasblg.videoapp.presentation.ui.viewmodel.ViewModelFactory
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class VideoFragment : DaggerFragment() {

    companion object {
        const val YOUTUBE_VIDEO_ID = "youtube_video_id"
    }

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
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.video.collectLatest {
                    binding?.apply {
                        date.text = it.date
                        title.text = it.title
                        description.text = it.description
                        showVideo(it.videoId ?: "")
                    }
                }
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