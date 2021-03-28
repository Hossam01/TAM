package com.example.tam.ViewModelClasses

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tam.models.login
import com.example.tam.models.response
import com.example.tam.others.Resource
import com.example.tam.repositry.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel@ViewModelInject constructor(
    private val mainRepository: Repository
): ViewModel()  {



    private val _res = MutableStateFlow<Resource<response>>(Resource.loading(null))
    init {
        getData()
    }

    fun getData()  = viewModelScope.launch {
        _res.emit(Resource.loading(null))
        mainRepository.data().let {
            if (it.isSuccessful){
                _res.emit(Resource.success(it.body()))
            }else{
                _res.emit(Resource.error(it.errorBody().toString(), null))
            }
        }

    }

    @ExperimentalCoroutinesApi
    fun getUsers(): StateFlow<Resource<response>> {
        return _res
    }

}