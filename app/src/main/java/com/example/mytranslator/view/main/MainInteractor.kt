package com.example.mytranslator.view.main

import com.example.mytranslator.model.data.Appstate
import com.example.mytranslator.model.data.DataModel
import com.example.mytranslator.model.repository.Repository
import com.example.mytranslator.precenter.Interactor
import io.reactivex.Observable

class MainInteractor(
    // Снабжаем интерактор репозиторием для получения локальных или внешних данных


    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: Repository<List<DataModel>>
) : Interactor<Appstate> {
    // Интерактор лишь запрашивает у репозитория данные, детали имплементации интерактору неизвестны
    override fun getData(word: String, fromRemoteSource: Boolean): Observable<Appstate> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { Appstate.Success(it) }
        } else {
            localRepository.getData(word).map { Appstate.Success(it) }
        }
    }
}