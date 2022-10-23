package com.sorabh.truelysocial.ui.post.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sorabh.truelysocial.data.models.Draft
import com.sorabh.truelysocial.data.other.Result
import com.sorabh.truelysocial.data.other.Status
import com.sorabh.truelysocial.domain.usecase.GetDraftsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DraftsViewModel @Inject constructor(private val getDraftsUseCase: GetDraftsUseCase) :
    ViewModel() {
        private val _draftLiveData:MutableLiveData<List<Draft>> = MutableLiveData()
    val draftLiveData: LiveData<List<Draft>>  = _draftLiveData

    private val _draftStatusLiveData:MutableLiveData<Status> = MutableLiveData()
    val draftStatusLiveData:LiveData<Status> = _draftStatusLiveData
    init {
        getDrafts()
    }

    fun getDrafts() {
        viewModelScope.launch {
            getDraftsUseCase(null).collect{
                when(it){
                    is Result.Error -> _draftStatusLiveData.postValue(Status.ERROR)
                    is Result.Loading -> _draftStatusLiveData.postValue(Status.LOADING)
                    is Result.Success -> {
                        _draftStatusLiveData.postValue(Status.SUCCESS)
                        it.body?.let {
                            _draftLiveData.postValue(it)
                        }
                    }
                }
            }
        }
    }
}