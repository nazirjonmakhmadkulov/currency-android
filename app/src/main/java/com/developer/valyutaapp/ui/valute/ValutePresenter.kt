package com.developer.valyutaapp.ui.valute

import android.content.Context
import android.util.Log
import com.developer.valyutaapp.data.local_data_source.repository.ValuteRespository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.widget.Toast
import com.developer.valyutaapp.data.local_data_source.database.ValuteDatabase
import com.developer.valyutaapp.data.local_data_source.database.ValuteDataSource

class ValutePresenter(var context: Context, var mvi: ValuteViewInterface) :
    ValutePresenterInterface {
    private val TAG = "ValutePresenter"
    private val valuteRespository: ValuteRespository
    private val compositeDisposable: CompositeDisposable
    override fun getValuteById(id: Int) {
        loadValuteById(id)
    }

    override fun valutes() {
        loadValutes()
    }

    private fun loadValuteById(id: Int) {
        val disposable = valuteRespository.getValuteById(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ valute ->
                Log.d(TAG, " = " + valute.name)
                mvi.displayValuteWithId(valute)
                mvi.hideProgressBar()
            }) { throwable ->
                Toast.makeText(context, "" + throwable.message, Toast.LENGTH_SHORT).show()
            }
        compositeDisposable.add(disposable)
    }

    private fun loadValutes() {
        val disposable = valuteRespository.allValutes
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ valutes ->
                Log.d(TAG, " = $valutes")
                mvi.displayValutes(valutes)
                mvi.hideProgressBar()
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