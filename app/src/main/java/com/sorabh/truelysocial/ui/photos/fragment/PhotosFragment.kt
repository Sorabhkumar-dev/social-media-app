package com.sorabh.truelysocial.ui.photos.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sorabh.truelysocial.data.other.Status
import com.sorabh.truelysocial.databinding.PhotosFragmentBinding
import com.sorabh.truelysocial.ui.adapter.PhotosAdapter
import com.sorabh.truelysocial.ui.photos.viewmodel.PhotosViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PhotosFragment : Fragment() {
    private lateinit var binding: PhotosFragmentBinding
    private val viewModel : PhotosViewModel by viewModels()
    @Inject
    lateinit var photosAdapter: PhotosAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initializeFields()
        setupObserver()
        setOnclickListener()
        return binding.root
    }

    private fun setOnclickListener() {
        photosAdapter.onRetryButtonClicked = View.OnClickListener {
            viewModel.getPhotos()
        }
    }

    private fun initializeFields() {
        binding = PhotosFragmentBinding.inflate(layoutInflater)
        binding.rvPhotos.adapter = photosAdapter
    }

    private fun setupObserver() {
        viewModel.photosLiveData.observe(viewLifecycleOwner) {
            photosAdapter.updatePhotos(it)
        }
        viewModel.photosStatusLiveData.observe(viewLifecycleOwner) {
            when (it) {
                Status.LOADING -> {
                    photosAdapter.showProgress(true)
                    photosAdapter.showError(false)
                }
                Status.SUCCESS -> {
                    photosAdapter.showProgress(false)
                    photosAdapter.showError(false)
                }
                Status.ERROR -> {
                    photosAdapter.showProgress(false)
                    photosAdapter.showError(true)
                }
                else -> {}
            }
        }
    }

}