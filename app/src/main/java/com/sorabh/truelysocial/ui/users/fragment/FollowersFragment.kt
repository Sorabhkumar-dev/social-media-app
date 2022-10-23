package com.sorabh.truelysocial.ui.users.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sorabh.truelysocial.R
import com.sorabh.truelysocial.data.other.Status
import com.sorabh.truelysocial.databinding.UsersFragmentBinding
import com.sorabh.truelysocial.ui.adapter.FollowerAdapter
import com.sorabh.truelysocial.ui.users.viewmodel.FollowersViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class FollowersFragment : Fragment(R.layout.users_fragment) {
    private lateinit var binding: UsersFragmentBinding
    private val viewModel:FollowersViewModel by viewModels()
    @Inject
    lateinit var followerAdapter: FollowerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        initializeField()
        setupObserver()
        setOnClickListener()
        return binding.root
    }

    private fun setOnClickListener() {
        followerAdapter.onRetryClicked = View.OnClickListener {
            viewModel.getFollowers()
        }
    }

    private fun initializeField() {
        binding = UsersFragmentBinding.inflate(layoutInflater)
        binding.rvFollowers.adapter = followerAdapter
    }

    private fun setupObserver() {
        viewModel.followersLiveData.observe(viewLifecycleOwner) {
            followerAdapter.updateFollowers(it)
        }
        viewModel.followerStatusLiveData.observe(viewLifecycleOwner) {
            when (it) {
                Status.LOADING -> {
                    followerAdapter.showProgress(true)
                    followerAdapter.showError(false)
                }
                Status.SUCCESS -> {
                    followerAdapter.showProgress(false)
                    followerAdapter.showError(false)
                }
                Status.ERROR -> {
                    followerAdapter.showProgress(false)
                    followerAdapter.showError(true)
                }
                else ->{}
            }
        }
    }
}