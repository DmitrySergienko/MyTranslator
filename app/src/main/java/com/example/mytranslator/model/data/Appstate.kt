package com.example.mytranslator.model.data

sealed class Appstate {
    data class Success(val data: List<DataModel>?) : Appstate()
    data class Error(val error: Throwable) : Appstate()
    data class Loading(val progress: Int?) : Appstate()
}