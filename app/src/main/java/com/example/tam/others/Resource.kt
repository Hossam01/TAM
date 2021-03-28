package com.example.tam.others

data class Resource<out T>(
    val code: Status,
    val item: T?,
    val msg:String?
){
    companion object{

        fun <T> success(data:T?): Resource<T>{
            return Resource(Status.OK, data, null)
        }

        fun <T> error(msg:String, data:T?): Resource<T>{
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data:T?): Resource<T>{
            return Resource(Status.LOAD, data, null)
        }

    }
}
