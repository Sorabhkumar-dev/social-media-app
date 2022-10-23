package com.sorabh.truelysocial.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sorabh.truelysocial.R
import com.sorabh.truelysocial.data.models.Draft
import com.sorabh.truelysocial.databinding.DraftLayoutBinding
import com.sorabh.truelysocial.databinding.ErrorLayoutBinding
import com.sorabh.truelysocial.databinding.ProgressLayoutBinding
import com.sorabh.truelysocial.utils.Constants
import javax.inject.Inject

class DraftAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val drafts: MutableList<Draft> = mutableListOf()
    private var showProgress = false
    private var showError = false
    var retryButtonClicked: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
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
        else -> DraftViewHolder(
            DraftLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DraftViewHolder) {
            holder.binding.txvTitle.text = drafts[position].title
            holder.binding.txvEdit.apply {

                text = if (drafts[position].completed)
                    context.getString(R.string.editable, "Yes")
                else context.getString(R.string.editable, "No")
            }
        }else if (holder is ErrorViewHolder)
            holder.binding.btnRetry.setOnClickListener(retryButtonClicked)
    }

    override fun getItemCount() =
        drafts.size + if (showProgress) 1 else 0 + if (showError) 1 else 0

    override fun getItemViewType(position: Int): Int {
        return if (showError && position == itemCount - 1) Constants.ERROR_VIEW
        else if (showProgress && position == itemCount - 1) Constants.PROGRESSBAR_VIEW
        else Constants.DRAFT_VIEW
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


    fun updateDrafts(newDrafts:List<Draft>){
        drafts.clear()
        drafts.addAll(newDrafts)
        notifyDataSetChanged()
    }

    inner class DraftViewHolder(val binding: DraftLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}