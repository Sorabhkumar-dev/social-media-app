package com.sorabh.truelysocial.ui.post.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sorabh.truelysocial.data.other.Status
import com.sorabh.truelysocial.databinding.DraftsFragmentBinding
import com.sorabh.truelysocial.ui.adapter.DraftAdapter
import com.sorabh.truelysocial.ui.post.viewmodel.DraftsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DraftsFragment : Fragment(){
    private val viewModel:DraftsViewModel by viewModels()
    @Inject
    lateinit var draftAdapter: DraftAdapter
    private lateinit var binding:DraftsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        initializeField(inflater)
        setupObserver()
        setOnClickListener()
        return binding.root
    }

    private fun initializeField(inflater: LayoutInflater) {
        binding = DraftsFragmentBinding.inflate(inflater)
        binding.rvDraft.adapter = draftAdapter
    }

    private fun setOnClickListener() {
        draftAdapter.retryButtonClicked = View.OnClickListener {
            viewModel.getDrafts()
        }
    }

    private fun setupObserver() {
        viewModel.draftLiveData.observe(viewLifecycleOwner) {
            draftAdapter.updateDrafts(it)
        }
        viewModel.draftStatusLiveData.observe(viewLifecycleOwner) {
            when (it) {
                Status.LOADING -> {
                    draftAdapter.showProgress(true)
                    draftAdapter.showError(false)
                }
                Status.SUCCESS -> {
                    draftAdapter.showProgress(false)
                    draftAdapter.showError(false)
                }
                Status.ERROR -> {
                    draftAdapter.showProgress(false)
                    draftAdapter.showError(true)
                }
                else -> {}
            }
        }
    }
}