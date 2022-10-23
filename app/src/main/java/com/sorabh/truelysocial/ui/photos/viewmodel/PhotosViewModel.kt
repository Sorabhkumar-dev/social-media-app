package com.sorabh.truelysocial.ui.photos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sorabh.truelysocial.data.models.Photo
import com.sorabh.truelysocial.data.other.Result
import com.sorabh.truelysocial.data.other.Status
import com.sorabh.truelysocial.domain.usecase.GetPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(private val getPhotoUseCase: GetPhotoUseCase) :
    ViewModel() {

    private val _photosLiveData: MutableLiveData<List<Photo>> = MutableLiveData()
    val photosLiveData: LiveData<List<Photo>> = _photosLiveData

    private val _photosStatusLiveData: MutableLiveData<Status> = MutableLiveData()
    val photosStatusLiveData: LiveData<Status> = _photosStatusLiveData
    init {
        getPhotos()
    }
    fun getPhotos() {
        viewModelScope.launch {
            getPhotoUseCase(null).collect {
                when (it) {
                    is Result.Error -> _photosStatusLiveData.postValue(Status.ERROR)
                    is Result.Loading -> _photosStatusLiveData.postValue(Status.LOADING)
                    is Result.Success -> {
                        _photosStatusLiveData.postValue(Status.SUCCESS)
                        it.body?.let { photos -> _photosLiveData.postValue(photos) }
                    }
                }
            }
        }
    }

}