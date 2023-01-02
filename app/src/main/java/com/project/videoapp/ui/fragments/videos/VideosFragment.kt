package com.project.videoapp.ui.fragments.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.videoapp.R
import com.project.videoapp.core.DateManager
import com.project.videoapp.data.database.entities.Video
import com.project.videoapp.databinding.FragmentVideosBinding
import com.project.videoapp.ui.fragments.video.VideoFragment.Companion.YOUTUBE_VIDEO_ID
import com.project.videoapp.ui.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.*
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
        bindClicks()
        bindRefresh()
        observeData()
    }

    private fun bindAdapter() {
        adapter = VideosAdapter(dateManager)
        adapter.apply {
            setOnItemClickListener {
                openVideo(it)
            }
        }
    }

    private fun openVideo(item: Video) {
        val bundle = Bundle()
        bundle.putString(YOUTUBE_VIDEO_ID, item.tag)

        val navController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.action_videosFragment_to_videoFragment, bundle)
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.videoData.collectLatest {
                adapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest {
                showState(it)
            }
        }
    }

    private fun showState(loadState: CombinedLoadStates) {
        binding!!.apply {
            // show empty list.
            val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            tvEmpty.isVisible = isListEmpty
            // only show the list if refresh succeeds.
            rvVideos.isVisible = !isListEmpty
            // show progress bar during initial load or refresh.
            swipe.isRefreshing = loadState.mediator?.refresh is LoadState.Loading
            // show progress when error and list is empty
            btnUpdate.isVisible =
                loadState.mediator?.refresh is LoadState.Error && adapter.itemCount == 0
        }
    }

    private fun bindRecycler() {
        binding!!.apply {
            rvVideos.layoutManager = LinearLayoutManager(context)
            rvVideos.isNestedScrollingEnabled = true
            rvVideos.adapter = adapter
        }
    }

    private fun bindClicks() {
        binding?.btnUpdate?.setOnClickListener {
            adapter.retry()
        }
    }

    private fun bindRefresh() {
        binding?.swipe?.setOnRefreshListener { adapter.refresh() }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}