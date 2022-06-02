package com.example.mytranslator.precenter

import com.example.mytranslator.model.data.Appstate
import com.example.mytranslator.view.base.View


interface Presenter<T : Appstate, V : View> {

    fun attachView(view: V)

    fun detachView(view: V)

    fun getData(word: String, isOnline: Boolean)
}