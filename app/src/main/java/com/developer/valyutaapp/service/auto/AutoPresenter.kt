package com.developer.valyutaapp.service.auto

import android.content.Context

class AutoPresenter(var context: Context) : AutoPresenterInterface {
    private val TAG = "AutoPresenter"
    //private val valuteRespository: ValuteRespository
    //private val compositeDisposable: CompositeDisposable

    override fun valutesRemote() {
        //observable.subscribeWith(observer)
    }

//    private val observable: Observable<ValCurs>
//        get() = NetworkClient.getRetrofit().create(
//            NetworkInterfaces::class.java
//        )
//            .getValutes(Utils.date, "xmlout")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//    private val observer: DisposableObserver<ValCurs?>
//        get() = object : DisposableObserver<ValCurs?>() {
//            override fun onNext(valCurs: ValCurs) {
//                //Log.d(TAG, "OnNext" + valCurs.getValute().toString())
//                insertData(valCurs)
//            }
//
//            override fun onError(e: Throwable) {
//                Log.d(TAG, "Error$e")
//                e.printStackTrace()
//            }
//
//            override fun onComplete() {
//                Log.d(TAG, "Completed")
//            }
//        }
//
//    fun insertData(valCurs: ValCurs) {
//        val disposable = Observable.create<Any> { e ->
//            valuteRespository.insertValute(valCurs.valute)
//            e.onComplete()
//        }
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .subscribe(
//                { Toast.makeText(context, "Valute added!", Toast.LENGTH_SHORT).show() },
//                { throwable ->
//                    Toast.makeText(context, "" + throwable.message, Toast.LENGTH_SHORT).show()
//                }) { }
//    }
//
//    init {
//        val valuteDatabase = ValuteDatabase.getInstance(context)
//        valuteRespository =
//            ValuteRespository.getInstance(ValuteDataSource.getInstance(valuteDatabase.valuteDAO()))
//        compositeDisposable = CompositeDisposable()
//    }
}