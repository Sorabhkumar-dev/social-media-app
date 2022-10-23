package com.sorabh.truelysocial.ui.post.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sorabh.truelysocial.data.models.Comment
import com.sorabh.truelysocial.data.other.Result
import com.sorabh.truelysocial.data.other.Status
import com.sorabh.truelysocial.domain.usecase.GetCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(private val getCommentUseCase: GetCommentUseCase) :
    ViewModel() {

    private val _commentLiveData: MutableLiveData<List<Comment>> = MutableLiveData()
    val commentLiveData: LiveData<List<Comment>> = _commentLiveData

    private val _commentStatusLiveData: MutableLiveData<Status> = MutableLiveData()
    val commentStatusLiveData: LiveData<Status> = _commentStatusLiveData

    fun getComments(postId: Int) {
        viewModelScope.launch {
            getCommentUseCase(postId).collect {
                when (it) {
                    is Result.Error -> _commentStatusLiveData.postValue(Status.ERROR)
                    is Result.Loading -> _commentStatusLiveData.postValue(Status.LOADING)
                    is Result.Success -> {
                        _commentStatusLiveData.postValue(Status.SUCCESS)
                        it.body?.let { comments ->
                            _commentLiveData.postValue(comments)
                        }
                    }
                }
            }
        }
    }
}