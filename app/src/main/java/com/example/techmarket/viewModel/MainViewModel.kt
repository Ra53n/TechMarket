package com.example.techmarket.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.techmarket.model.AppState
import com.example.techmarket.model.Repository
import com.example.techmarket.model.RepositoryImpl

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
) : ViewModel() {
    fun getData() = liveDataToObserve

    fun getItemsFromServer(){
        liveDataToObserve.postValue(AppState.Loading)
        liveDataToObserve.postValue(AppState.Success(repository.getMenuItems()))
    }

}