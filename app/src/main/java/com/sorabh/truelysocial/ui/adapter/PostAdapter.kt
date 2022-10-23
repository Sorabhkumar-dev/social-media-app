package com.sorabh.truelysocial.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sorabh.truelysocial.R
import com.sorabh.truelysocial.data.models.Post
import com.sorabh.truelysocial.databinding.ErrorLayoutBinding
import com.sorabh.truelysocial.databinding.PostLayoutBinding
import com.sorabh.truelysocial.databinding.ProgressLayoutBinding
import com.sorabh.truelysocial.utils.Constants
import javax.inject.Inject

class PostAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var clickListener: View.OnClickListener? = null
    var onCommentClicked:OnCommentClicked? = null
    private val posts: MutableList<Post> = mutableListOf()
    private var showProgress = false
    private var showError = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Constants.POST_VIEW -> PostViewHolder(
                PostLayoutBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            Constants.PROGRESSBAR_VIEW -> ProgressBarViewHolder(
                ProgressLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> ErrorViewHolder(
                ErrorLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PostViewHolder) {
            holder.binding.txvUserId.apply {
                text = context.getString(R.string.user_id,posts[position].userId)
            }
            holder.binding.txvTitle.apply {
                text = context.getString(R.string.title,posts[position].title)
            }
            holder.binding.txvBody.text = posts[position].body
            holder.binding.btnComment.setOnClickListener{
                onCommentClicked?.onCommentClicked(posts[position].id)
            }
        } else if (holder is ErrorViewHolder) {
            holder.binding.btnRetry.setOnClickListener(clickListener)
        }
    }

    override fun getItemCount() = posts.size + if (showProgress) 1 else 0 + if (showError) 1 else 0

    override fun getItemViewType(position: Int): Int {
        return if (showProgress && itemCount - 1 == position) Constants.PROGRESSBAR_VIEW
        else if (showError && itemCount - 1 == position) Constants.ERROR_VIEW
        else Constants.POST_VIEW
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

    fun updatePosts(newPosts:List<Post>){
        posts.clear()
        posts.addAll(newPosts)
        notifyDataSetChanged()
    }

   inner class PostViewHolder(val binding: PostLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}
interface OnCommentClicked{
    fun onCommentClicked(postId:Int)
}