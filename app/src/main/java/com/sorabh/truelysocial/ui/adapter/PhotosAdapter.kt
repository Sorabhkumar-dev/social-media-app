package com.sorabh.truelysocial.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sorabh.truelysocial.R
import com.sorabh.truelysocial.data.models.Photo
import com.sorabh.truelysocial.databinding.ErrorLayoutBinding
import com.sorabh.truelysocial.databinding.PhotosLayoutBinding
import com.sorabh.truelysocial.databinding.ProgressLayoutBinding
import com.sorabh.truelysocial.utils.Constants
import javax.inject.Inject

class PhotosAdapter @Inject constructor(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val photos: MutableList<Photo> = mutableListOf()
    var onRetryButtonClicked:View.OnClickListener? = null
    private var showProgress = false
    private var showError = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
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
            else -> PhotoViewHolder(
                PhotosLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PhotoViewHolder){
            holder.binding.imgPhoto.apply {
                Glide.with(context)
                    .load(photos[position].thumbnailUrl)
                    .error(R.drawable.ic_big_logo)
                    .into(this)
            }
            holder.binding.txvTitle.text = photos[position].title
        }else if (holder is ErrorViewHolder){
            holder.binding.btnRetry.setOnClickListener(onRetryButtonClicked)
        }
    }

    override fun getItemCount() = photos.size + if (showError) 1 else 0 + if (showProgress) 1 else 0

    override fun getItemViewType(position: Int): Int {
        return if (showProgress && itemCount - 1 == position) Constants.PROGRESSBAR_VIEW
        else if (showError && itemCount - 1 == position) Constants.ERROR_VIEW
        else Constants.PHOTO_VIEW
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

    fun updatePhotos(newPhotos: List<Photo>) {
        photos.clear()
        photos.addAll(newPhotos)
        notifyDataSetChanged()
    }

    inner class PhotoViewHolder(val binding: PhotosLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}