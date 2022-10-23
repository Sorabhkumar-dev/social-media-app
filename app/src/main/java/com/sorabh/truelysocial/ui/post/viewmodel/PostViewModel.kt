package com.sorabh.truelysocial.ui.post.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sorabh.truelysocial.data.models.Post
import com.sorabh.truelysocial.data.other.Result
import com.sorabh.truelysocial.data.other.Status
import com.sorabh.truelysocial.domain.usecase.GetPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val getPostUseCase: GetPostUseCase) : ViewModel() {
    private val _postStatus: MutableLiveData<Status> = MutableLiveData()
    val postStatus: LiveData<Status> = _postStatus

    private val _postLiveData: MutableLiveData<List<Post>> = MutableLiveData()
    val postLiveData: LiveData<List<Post>> = _postLiveData

    init {
        getPost()
    }

    fun getPost() {
        viewModelScope.launch(Dispatchers.IO) {
            getPostUseCase(null).collect {
                when (it) {
                    is Result.Error -> {
                        _postStatus.postValue(Status.ERROR)
                    }
                    is Result.Loading -> {
                        _postStatus.postValue(Status.LOADING)
                    }
                    is Result.Success -> {
                        _postStatus.postValue(Status.SUCCESS)
                        it.body?.let { posts ->
                            _postLiveData.postValue(posts)
                        }
                    }
                }
            }
        }
    }
}