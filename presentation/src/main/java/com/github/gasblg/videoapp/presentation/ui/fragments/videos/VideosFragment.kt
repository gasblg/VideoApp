package com.github.gasblg.videoapp.presentation.ui.fragments.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.Navigation
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.gasblg.videoapp.domain.models.Video
import com.github.gasblg.videoapp.presentation.R
import com.github.gasblg.videoapp.presentation.databinding.FragmentVideosBinding
import com.github.gasblg.videoapp.presentation.ui.fragments.video.VideoFragment.Companion.YOUTUBE_VIDEO_ID
import com.github.gasblg.videoapp.presentation.ui.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class VideosFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: VideosViewModel by viewModels {
        viewModelFactory
    }

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
        adapter = VideosAdapter()
        adapter.apply {
            setOnItemClickListener {
                openVideo(it)
            }
        }
    }

    private fun openVideo(item: Video) {
        val bundle = Bundle()
        bundle.putString(YOUTUBE_VIDEO_ID, item.videoId)

        val navController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.action_videosFragment_to_videoFragment, bundle)
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.videoData.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collectLatest {
                    showState(it)
                }
            }
        }
    }

    private fun showState(loadState: CombinedLoadStates) {
        binding!!.apply {
            // show empty list.
            val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            empty.isVisible = isListEmpty
            // only show the list if refresh succeeds.
            rv.isVisible = !isListEmpty
            // show progress bar during initial load or refresh.
            swipe.isRefreshing = loadState.mediator?.refresh is LoadState.Loading
            // show progress when error and list is empty
            update.isVisible =
                loadState.mediator?.refresh is LoadState.Error && adapter.itemCount == 0
        }
    }

    private fun bindRecycler() {
        binding!!.apply {
            rv.layoutManager = LinearLayoutManager(context)
            rv.isNestedScrollingEnabled = true
            rv.adapter = adapter
        }
    }

    private fun bindClicks() {
        binding?.update?.setOnClickListener {
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