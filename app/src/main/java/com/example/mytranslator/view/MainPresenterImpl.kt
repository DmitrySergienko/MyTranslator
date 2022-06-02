package com.example.mytranslator.view

import com.example.mytranslator.model.data.Appstate
import com.example.mytranslator.model.datasource.DataSourceLocal
import com.example.mytranslator.model.datasource.DataSourceRemote
import com.example.mytranslator.model.repository.RepositoryImplementation
import com.example.mytranslator.precenter.Presenter
import com.example.mytranslator.rx.SchedulerProvider
import com.example.mytranslator.view.base.View
import com.example.mytranslator.view.main.MainInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver


class MainPresenterImpl<T : Appstate, V : View>(

    private val interactor: MainInteractor = MainInteractor(
        RepositoryImplementation(DataSourceRemote()),
        RepositoryImplementation(DataSourceLocal())
    ),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
   protected val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : Presenter<T, V> {
    // Ссылка на View, никакого контекста
    private var currentView: V? = null
    // Как только появилась View, сохраняем ссылку на неё для дальнейшей работы
    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }
    // View скоро будет уничтожена: прерываем все загрузки и обнуляем ссылку
    override fun detachView(view: V) {
        compositeDisposable.clear()
        if (view == currentView) {
            currentView = null
        }
    }
    // Стандартный код RxJava
    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                // Как только начинается загрузка, передаём во View модель данных для
                // отображения экрана загрузки
                .doOnSubscribe { currentView?.renderData(Appstate.Loading(null)) }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<Appstate> {
        return object : DisposableObserver<Appstate>() {

            override fun onNext(appState: Appstate) {
                // Если загрузка окончилась успешно, передаем модель с данными
                // для отображения
                currentView?.renderData(appState)
            }

            override fun onError(e: Throwable) {
                // Если произошла ошибка, передаем модель с ошибкой
                currentView?.renderData(Appstate.Error(e))
            }

            override fun onComplete() {
            }
        }
    }
}
