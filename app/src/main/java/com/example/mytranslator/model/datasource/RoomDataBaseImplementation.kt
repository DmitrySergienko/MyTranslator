package com.example.mytranslator.model.datasource


import com.example.mytranslator.model.data.DataModel
import com.example.mytranslator.model.data.DataSource
import io.reactivex.Observable

class RoomDataBaseImplementation : DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
 