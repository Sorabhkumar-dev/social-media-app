package com.sorabh.truelysocial.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sorabh.truelysocial.data.models.Follower
import com.sorabh.truelysocial.databinding.ErrorLayoutBinding
import com.sorabh.truelysocial.databinding.FollowerLayoutBinding
import com.sorabh.truelysocial.databinding.ProgressLayoutBinding
import com.sorabh.truelysocial.utils.Constants
import javax.inject.Inject

class FollowerAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var showProgress = false
    private var showError = false
    private val followers: MutableList<Follower> = mutableListOf()
    var onRetryClicked: View.OnClickListener? = null
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
            else -> FollowerViewHolder(
                FollowerLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FollowerViewHolder) {
            holder.binding.txvName.text = followers[position].name
            holder.binding.txvUserEmail.text = followers[position].email
            holder.binding.txvWeb.text = followers[position].website
            holder.binding.txvUserId.text = followers[position].username
        } else if (holder is ErrorViewHolder)
            holder.binding.btnRetry.setOnClickListener(onRetryClicked)
    }

    override fun getItemCount() =
        followers.size + if (showProgress) 1 else 0 + if (showError) 1 else 0

    override fun getItemViewType(position: Int) =
        if (showProgress && position == itemCount - 1) Constants.PROGRESSBAR_VIEW
        else if (showError && position == itemCount - 1) Constants.ERROR_VIEW
        else Constants.FOLLOWER_VIEW

    fun updateFollowers(newFollowers: List<Follower>) {
        followers.clear()
        followers.addAll(newFollowers)
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
    inner class FollowerViewHolder(val binding: FollowerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}