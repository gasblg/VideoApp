package com.project.videoapp.ui.fragments.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.videoapp.R
import com.project.videoapp.core.DateManager
import com.project.videoapp.databinding.FragmentVideosBinding
import com.project.videoapp.net.responses.Item
import com.project.videoapp.ui.fragments.video.VideoFragment.Companion.DATE
import com.project.videoapp.ui.fragments.video.VideoFragment.Companion.DESCRIPTION
import com.project.videoapp.ui.fragments.video.VideoFragment.Companion.TITLE
import com.project.videoapp.ui.fragments.video.VideoFragment.Companion.YOUTUBE_VIDEO_ID
import com.project.videoapp.ui.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class VideosFragment : DaggerFragment() {

    companion object {
        fun newInstance() = VideosFragment()
    }


    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: VideosViewModel by viewModels {
        viewModelFactory
    }

    @Inject
    lateinit var dateManager: DateManager

    private lateinit var adapter: VideosAdapter
    private var binding: FragmentVideosBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentVideosBinding =
            FragmentVideosBinding.inflate(inflater, container, false)
        this.binding = fragmentVideosBinding
        return fragmentVideosBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindAdapter()
        bindRecycler()
        observeData()
        bindClicks()
    }


    private fun bindAdapter() {
        adapter = VideosAdapter(dateManager)
        adapter.apply {
            addLoadStateListener {
                when {
                    it.refresh is LoadState.Loading -> showLoading()
                    it.refresh is LoadState.Error -> showError()
                    it.prepend is LoadState.NotLoading -> showDataOrEmpty(itemCount)
                }
            }
            setOnItemClickListener {
                openVideo(it)
            }
        }
    }

    private fun showLoading() {
        binding?.rvVideos?.isVisible = false
        binding?.progressBar?.isVisible = true
        binding?.btnUpdate?.isVisible = false
        binding?.tvEmpty?.isVisible = false
    }

    private fun showError() {
        binding?.rvVideos?.isVisible = false
        binding?.progressBar?.isVisible = false
        binding?.btnUpdate?.isVisible = true
        binding?.tvEmpty?.isVisible = false
    }

    private fun showDataOrEmpty(itemCount: Int) {
        binding?.rvVideos?.isVisible = itemCount >= 1
        binding?.progressBar?.isVisible = false
        binding?.btnUpdate?.isVisible = false
        binding?.tvEmpty?.isVisible = itemCount < 1
    }

    private fun openVideo(item: Item) {
        val bundle = Bundle()
        bundle.putString(YOUTUBE_VIDEO_ID, item.id.videoId)
        bundle.putString(DATE, item.snippet.publishTime)
        bundle.putString(TITLE, item.snippet.title)
        bundle.putString(DESCRIPTION, item.snippet.description)

        val navController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.action_videosFragment_to_videoFragment, bundle)
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.load().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun bindRecycler() {
        binding?.rvVideos?.layoutManager = LinearLayoutManager(context)
        binding?.rvVideos?.isNestedScrollingEnabled = true
        binding?.rvVideos?.adapter = adapter
    }

    private fun bindClicks() {
        binding?.btnUpdate?.setOnClickListener {
            adapter.retry()
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}