package com.sorabh.truelysocial.ui.post.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.sorabh.truelysocial.R
import com.sorabh.truelysocial.data.other.Status
import com.sorabh.truelysocial.databinding.CommentsFragmentBinding
import com.sorabh.truelysocial.ui.adapter.CommentAdapter
import com.sorabh.truelysocial.ui.post.viewmodel.CommentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CommentsFragment : Fragment(R.layout.comments_fragment) {
    private lateinit var binding: CommentsFragmentBinding
    private  val args: CommentsFragmentArgs by navArgs()
    private val viewModel:CommentViewModel by viewModels()

    @Inject
    lateinit var commentAdapter: CommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initializeField()
        setOnClickListener()
        setupObserver()
        return binding.root
    }

    private fun setOnClickListener() {
        commentAdapter.onRetryButtonClicked = View.OnClickListener {
            viewModel.getComments(args.postId)
        }
    }

    private fun initializeField() {
        binding = CommentsFragmentBinding.inflate(layoutInflater)
        binding.rvComment.adapter = commentAdapter
        viewModel.getComments(args.postId)
    }

    private fun setupObserver() {
        viewModel.commentLiveData.observe(viewLifecycleOwner) {
            commentAdapter.updateComment(it)
        }
        viewModel.commentStatusLiveData.observe(viewLifecycleOwner) {
            when (it) {
                Status.LOADING -> {
                    commentAdapter.showProgress(true)
                    commentAdapter.showError(false)
                }
                Status.SUCCESS -> {
                    commentAdapter.showProgress(false)
                    commentAdapter.showError(false)
                }
                Status.ERROR -> {
                    commentAdapter.showProgress(false)
                    commentAdapter.showError(true)
                }
                else -> {}
            }

        }
    }
}