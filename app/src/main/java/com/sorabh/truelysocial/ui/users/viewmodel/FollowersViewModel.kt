package com.sorabh.truelysocial.ui.users.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sorabh.truelysocial.data.models.Follower
import com.sorabh.truelysocial.data.other.Result
import com.sorabh.truelysocial.data.other.Status
import com.sorabh.truelysocial.domain.usecase.GetFollowerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowersViewModel @Inject constructor(private val getFollowerUseCase: GetFollowerUseCase) :
    ViewModel() {
    private val _followersLiveData: MutableLiveData<List<Follower>> = MutableLiveData()
    val followersLiveData: LiveData<List<Follower>> = _followersLiveData

    private val _followerStatusLiveData: MutableLiveData<Status> = MutableLiveData()
    val followerStatusLiveData: LiveData<Status> = _followerStatusLiveData

    init {
        getFollowers()
    }

    fun getFollowers() {
        viewModelScope.launch {
            getFollowerUseCase(null).collect {
                when (it) {
                    is Result.Error -> _followerStatusLiveData.postValue(Status.ERROR)
                    is Result.Loading -> _followerStatusLiveData.postValue(Status.LOADING)
                    is Result.Success -> {
                        _followerStatusLiveData.postValue(Status.SUCCESS)
                        it.body?.let { followers ->
                            _followersLiveData.postValue(followers)
                        }
                    }
                }
            }
        }
    }
}