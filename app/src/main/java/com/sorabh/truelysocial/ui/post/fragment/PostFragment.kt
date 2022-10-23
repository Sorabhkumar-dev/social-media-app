package com.sorabh.truelysocial.ui.post.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.sorabh.truelysocial.data.other.Status
import com.sorabh.truelysocial.databinding.PostFragmentBinding
import com.sorabh.truelysocial.ui.adapter.OnCommentClicked
import com.sorabh.truelysocial.ui.adapter.PostAdapter
import com.sorabh.truelysocial.ui.post.activity.RootActivity
import com.sorabh.truelysocial.ui.post.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PostFragment : Fragment() {

    @Inject
    lateinit var postAdapter: PostAdapter

    private lateinit var binding: PostFragmentBinding
    private lateinit var navController: NavController

    private val viewModel: PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        initializeField(inflater)
        setupObserver()
        setOnClickListener()
        return binding.root
    }

    private fun initializeField(inflater: LayoutInflater) {
        binding = PostFragmentBinding.inflate(inflater)
        navController = findNavController()
        binding.rvPosts.adapter = postAdapter
        (activity as RootActivity).bottomNavigationView.visibility = View.VISIBLE
    }

    private fun setupObserver() {
        viewModel.postLiveData.observe(viewLifecycleOwner) {
            postAdapter.updatePosts(it)
        }
        viewModel.postStatus.observe(viewLifecycleOwner) {
            when (it) {
                Status.LOADING -> {
                    postAdapter.showProgress(true)
                    postAdapter.showError(false)
                }
                Status.SUCCESS -> {
                    postAdapter.showProgress(false)
                    postAdapter.showError(false)
                }
                Status.ERROR -> {
                    postAdapter.showProgress(false)
                    postAdapter.showError(true)
                }
                else -> {}
            }
        }
    }

    private fun setOnClickListener() {
        binding.btnDrafts.setOnClickListener {
            navController.navigate(PostFragmentDirections.actionPostFragmentToDraftsFragment())
        }
        postAdapter.clickListener = View.OnClickListener { viewModel.getPost() }
        postAdapter.onCommentClicked = object : OnCommentClicked {
            override fun onCommentClicked(postId: Int) {
                navController.navigate(
                    PostFragmentDirections.actionPostFragmentToCommentsFragment(
                        postId
                    )
                )
            }
        }
    }

}