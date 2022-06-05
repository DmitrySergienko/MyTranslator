package com.example.mytranslator.model.datasource


import com.example.mytranslator.model.data.DataModel
import com.example.mytranslator.model.data.DataSource
import io.reactivex.Observable

class DataSourceLocal(private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}
