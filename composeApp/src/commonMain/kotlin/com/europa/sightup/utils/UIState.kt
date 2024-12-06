package com.europa.sightup.utils

sealed class UIState<Type : Any> {
    class InitialState<Type : Any> : UIState<Type>()
    class Loading<Type : Any> : UIState<Type>()
    data class Success<Type : Any>(val data: Type) : UIState<Type>()
    data class Error<Type : Any>(val message: String) : UIState<Type>()
}