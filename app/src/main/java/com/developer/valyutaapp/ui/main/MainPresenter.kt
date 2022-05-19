//package com.developer.valyutaapp.ui.main
//
//import android.content.Context
//import android.util.Log
//import io.reactivex.disposables.CompositeDisposable
//import com.developer.valyutaapp.domain.entities.ValCurs
//import io.reactivex.schedulers.Schedulers
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.observers.DisposableObserver
//import android.widget.Toast
//import com.developer.valyutaapp.utils.Utils
//import io.reactivex.Observable
//
//class MainPresenter(
//    var context: Context,
//    var mvi: MainViewInterface,
//    private val valuteRespository: ValuteRespository
//) : MainPresenterInterface {
//
//    private val TAG = "MainPresenter"
//    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
//    private val countVal = 0
//    override fun valutesRemote() {
//        mvi.showProgressBar()
//        loadValuteCount()
//    }
//
//    override fun valutesLocal() {
//        loadData()
//    }
//
//    val observable: Observable<ValCurs>
//        get() = NetworkClient.getRetrofit().create(
//            NetworkInterfaces::class.java
//        )
//            .getValutes(Utils.date, "xmlout")
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//
//    fun getObserver(valCount: Int): DisposableObserver<ValCurs?> {
//        return object : DisposableObserver<ValCurs?>() {
//            override fun onNext(valCurs: ValCurs) {
//                //Log.d(TAG, "OnNext" + valCurs.getValute().toString())
//                if (valCount > 0) {
//                    updateData(valCurs)
//                } else {
//                    insertData(valCurs)
//                }
//            }
//            override fun onError(e: Throwable) {
//                Log.d(TAG, "Error$e")
//                e.printStackTrace()
//                mvi.displayError("Error fetching valutes Data")
//            }
//
//            override fun onComplete() {
//                Log.d(TAG, "Completed")
//                mvi.hideProgressBar()
//            }
//        }
//    }
//
//    fun insertData(valCurs: ValCurs) {
//        val disposable = Observable.create<Any> { e ->
//            Log.d(TAG, "subscribe = " + valCurs.valute!!.size)
//            valuteRespository.insertValute(valCurs.valute)
//            e.onComplete()
//        }
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .subscribe(
//                { Toast.makeText(context, "Valute added!", Toast.LENGTH_SHORT).show() },
//                { throwable ->
//                    Toast.makeText(context, "" + throwable.message, Toast.LENGTH_SHORT).show()
//                }) { loadData() }
//    }
//
//    fun updateData(valCurs: ValCurs) {
//        val disposable = Observable.create<Any> { e ->
//            Log.d(TAG, "subscribe = " + valCurs.valute!!.size)
//            valuteRespository.updateValute(valCurs.valute)
//            e.onComplete()
//        }
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .subscribe(
//                { Toast.makeText(context, "Valute added!", Toast.LENGTH_SHORT).show() },
//                { throwable ->
//                    Toast.makeText(context, "" + throwable.message, Toast.LENGTH_SHORT).show()
//                }) { loadData() }
//    }
//
//    private fun loadValuteCount(): Int {
//        val disposable = valuteRespository.valuteCount
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .subscribe({ count -> observable.subscribeWith(getObserver(count)) }) { throwable ->
//                Toast.makeText(
//                    context,
//                    "" + throwable.message,
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        compositeDisposable.add(disposable)
//        return countVal
//    }
//
//    private fun loadData() {
//        val disposable = valuteRespository.allValutes
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .subscribe({ valutes ->
//                Log.d(TAG, "Completed = " + valutes.size)
//                mvi.displayValutes(valutes)
//                mvi.hideProgressBar()
//            }) { throwable ->
//                Toast.makeText(context, "" + throwable.message, Toast.LENGTH_SHORT).show()
//            }
//        compositeDisposable.add(disposable)
//    }
//
//}