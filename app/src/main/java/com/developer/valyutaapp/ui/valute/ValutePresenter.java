package com.developer.valyutaapp.ui.valute;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.developer.valyutaapp.data.local_data_source.database.ValuteDataSource;
import com.developer.valyutaapp.data.local_data_source.database.ValuteDatabase;
import com.developer.valyutaapp.data.local_data_source.repository.ValuteRespository;
import com.developer.valyutaapp.model.Valute;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ValutePresenter implements ValutePresenterInterface {

    ValuteViewInterface mvi;
    Context context;
    private String TAG = "ValutePresenter";
    private ValuteRespository valuteRespository;
    private CompositeDisposable compositeDisposable;

    public ValutePresenter(Context context, ValuteViewInterface mvi) {
        this.context = context;
        this.mvi = mvi;
        ValuteDatabase valuteDatabase = ValuteDatabase.getInstance(context);
        valuteRespository = ValuteRespository.getInstance(ValuteDataSource.getInstance(valuteDatabase.valuteDAO()));
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getValuteById(int id) {
        loadValuteById(id);
    }

    @Override
    public void getValutes() {
        loadValutes();
    }

    private void loadValuteById(int id) {
        Disposable disposable = valuteRespository.getValuteById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Valute>(){
                    @Override
                    public void accept(Valute valute) throws Exception {
                        Log.d(TAG , " = " + valute.getName());
                        mvi.displayValuteWithId(valute);
                        mvi.hideProgressBar();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception{
                        Toast.makeText(context, ""+ throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
    }


    private void loadValutes() {
        Disposable disposable = valuteRespository.getAllValutes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Valute>>(){
                    @Override
                    public void accept(List<Valute> valutes) throws Exception {
                        Log.d(TAG , " = " + valutes.toString());
                        mvi.displayValutes(valutes);
                        mvi.hideProgressBar();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception{
                        Toast.makeText(context, ""+ throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
    }
}
