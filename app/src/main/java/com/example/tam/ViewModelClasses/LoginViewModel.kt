package com.example.tam.ViewModelClasses

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tam.repositry.Repository
import com.example.tam.others.Resource
import com.example.tam.models.login
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val mainRepository: Repository
): ViewModel() {


    private val _res = MutableStateFlow<Resource<login>>(Resource.loading(null))


     fun getData(email:String,password:String)  = viewModelScope.launch {
        var hashMap:HashMap<String,String> = HashMap<String,String>()
        hashMap.put("email",email)
        hashMap.put("password",password)
        hashMap.put("fcmToken","123")
        _res.emit(Resource.loading(null))
        mainRepository.login(hashMap).let {
            if (it.isSuccessful){
                _res.emit(Resource.success(it.body()))
            }else{
                _res.emit(Resource.error(it.errorBody().toString(), null))
            }
        }

    }

    @ExperimentalCoroutinesApi
    fun getUsers(): StateFlow<Resource<login>> {
        return _res
    }


}