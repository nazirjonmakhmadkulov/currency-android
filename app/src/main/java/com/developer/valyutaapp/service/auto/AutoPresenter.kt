package com.developer.valyutaapp.service.auto

import android.content.Context
import android.util.Log
import com.developer.valyutaapp.data.local_data_source.repository.ValuteRespository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import com.developer.valyutaapp.model.ValCurs
import com.developer.valyutaapp.data.remote_data_source.NetworkInterfaces
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import android.widget.Toast
import com.developer.valyutaapp.data.local_data_source.database.ValuteDatabase
import com.developer.valyutaapp.data.local_data_source.database.ValuteDataSource
import com.developer.valyutaapp.data.remote_data_source.NetworkClient
import com.developer.valyutaapp.utils.Utils
import io.reactivex.Observable

class AutoPresenter(var context: Context) : AutoPresenterInterface {
    private val TAG = "AutoPresenter"
    private val valuteRespository: ValuteRespository
    private val compositeDisposable: CompositeDisposable

    override fun valutesRemote() {
        observable.subscribeWith(observer)
    }

    val observable: Observable<ValCurs>
        get() = NetworkClient.getRetrofit().create(
            NetworkInterfaces::class.java
        )
            .getValutes(Utils.date, "xmlout")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    val observer: DisposableObserver<ValCurs?>
        get() = object : DisposableObserver<ValCurs?>() {
            override fun onNext(valCurs: ValCurs) {
                //Log.d(TAG, "OnNext" + valCurs.getValute().toString())
                insertData(valCurs)
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "Error$e")
                e.printStackTrace()
            }

            override fun onComplete() {
                Log.d(TAG, "Completed")
            }
        }

    fun insertData(valCurs: ValCurs) {
        val disposable = Observable.create<Any> { e ->
            valuteRespository.insertValute(valCurs.valute)
            e.onComplete()
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { Toast.makeText(context, "Valute added!", Toast.LENGTH_SHORT).show() },
                { throwable ->
                    Toast.makeText(context, "" + throwable.message, Toast.LENGTH_SHORT).show()
                }) { }
    }

    init {
        val valuteDatabase = ValuteDatabase.getInstance(context)
        valuteRespository =
            ValuteRespository.getInstance(ValuteDataSource.getInstance(valuteDatabase.valuteDAO()))
        compositeDisposable = CompositeDisposable()
    }


}