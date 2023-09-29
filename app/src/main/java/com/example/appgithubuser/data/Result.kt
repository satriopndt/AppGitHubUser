package com.example.appgithubuser.data

import kotlin.Result

sealed class Result <out R> private constructor(){
    data class Success<out T>(val data: T): com.example.appgithubuser.data.Result<T>()
    data class Error(val error: String): com.example.appgithubuser.data.Result<Nothing>()
    object Loading : com.example.appgithubuser.data.Result<Nothing>()
}