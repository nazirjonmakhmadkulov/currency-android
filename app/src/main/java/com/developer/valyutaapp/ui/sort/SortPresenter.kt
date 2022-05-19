package com.developer.valyutaapp.ui.sort

import android.content.Context
import com.developer.valyutaapp.ui.sort.SortViewInterface
import com.developer.valyutaapp.ui.sort.SortPresenterInterface
import com.developer.valyutaapp.data.local_data_source.repository.ValuteRespository
import io.reactivex.disposables.CompositeDisposable
import com.developer.valyutaapp.model.Valute
import io.reactivex.ObservableOnSubscribe
import kotlin.Throws
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.widget.Toast
import com.developer.valyutaapp.data.local_data_source.database.ValuteDatabase
import com.developer.valyutaapp.data.local_data_source.database.ValuteDataSource
import io.reactivex.Observable

class SortPresenter(var context: Context, var sortViewInterface: SortViewInterface) :
    SortPresenterInterface {
    private val TAG = "SortPresenter"
    private val valuteRespository: ValuteRespository
    private val compositeDisposable: CompositeDisposable
    override fun updateValute(valute: Valute) {
        updateData(valute)
    }

    override fun valutes() {
        loadData()
    }

    private fun updateData(valute: Valute) {
        val disposable = Observable.create<Any> { e ->
            valuteRespository.updateValuteStatus(valute)
            e.onComplete()
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { Toast.makeText(context, "Valute updated!", Toast.LENGTH_SHORT).show() },
                { throwable ->
                    Toast.makeText(context, "" + throwable.message, Toast.LENGTH_SHORT).show()
                }) {
                //loadData();
            }
        compositeDisposable.add(disposable)
    }

    private fun loadData() {
        val disposable = valuteRespository.allValutes
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ valutes ->
                sortViewInterface.displayValutes(valutes)
                sortViewInterface.hideProgressBar()
            }) { throwable ->
                Toast.makeText(context, "" + throwable.message, Toast.LENGTH_SHORT).show()
            }
        compositeDisposable.add(disposable)
    }

    init {
        val valuteDatabase = ValuteDatabase.getInstance(context)
        valuteRespository =
            ValuteRespository.getInstance(ValuteDataSource.getInstance(valuteDatabase.valuteDAO()))
        compositeDisposable = CompositeDisposable()
    }
}