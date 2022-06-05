package com.example.mytranslator.view.base

import com.example.mytranslator.model.data.Appstate

interface View {
    // View имеет только один метод, в который приходит некое состояние приложения
    fun renderData(appstate: Appstate)
}