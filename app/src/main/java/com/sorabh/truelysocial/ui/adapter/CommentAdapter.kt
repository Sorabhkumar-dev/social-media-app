package com.sorabh.truelysocial.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sorabh.truelysocial.data.models.Comment
import com.sorabh.truelysocial.databinding.CommentLayoutBinding
import com.sorabh.truelysocial.databinding.ErrorLayoutBinding
import com.sorabh.truelysocial.databinding.ProgressLayoutBinding
import com.sorabh.truelysocial.utils.Constants
import javax.inject.Inject

class CommentAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var onRetryButtonClicked: View.OnClickListener? = null
    private val comments: MutableList<Comment> = mutableListOf()
    private var showProgress = false
    private var showError = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            Constants.PROGRESSBAR_VIEW -> ProgressBarViewHolder(
                ProgressLayoutBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            Constants.ERROR_VIEW -> ErrorViewHolder(
                ErrorLayoutBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> CommentViewHolder(
                CommentLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CommentViewHolder){
            holder.binding.txvEmail.text = comments[position].email
            holder.binding.txvName.text = comments[position].name
            holder.binding.txvCommentBody.text = comments[position].body
        }else if (holder is ErrorViewHolder)holder.binding.btnRetry.setOnClickListener(onRetryButtonClicked)
    }

    override fun getItemCount() =
        comments.size + if (showError) 1 else 0 + if (showProgress) 1 else 0

    override fun getItemViewType(position: Int) =
        if (showError && position == itemCount - 1) Constants.ERROR_VIEW
        else if (showProgress && position == itemCount - 1) Constants.PROGRESSBAR_VIEW
        else Constants.COMMENT_VIEW


    fun updateComment(newComments: List<Comment>) {
        comments.clear()
        comments.addAll(newComments)
        notifyDataSetChanged()
    }

    fun showProgress(isShow: Boolean) {
        if (showProgress == isShow) return
        val previousState = showProgress
        showProgress = isShow
        if (previousState)
            notifyItemRemoved(itemCount - 1)
        else
            notifyItemInserted(itemCount)
    }

    fun showError(isError: Boolean) {
        if (showError == isError) return
        val previousState = showError
        showError = isError
        if (previousState)
            notifyItemRemoved(itemCount - 1)
        else
            notifyItemInserted(itemCount)
    }

    inner class CommentViewHolder(val binding: CommentLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}