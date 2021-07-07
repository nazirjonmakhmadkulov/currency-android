package com.developer.valyutaapp.ui.widget;

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

public class WidgetPresenter implements WidgetPresenterInterface {

    WidgetViewInterface widgetViewInterface;
    Context context;
    private String TAG = "WidgetViewInterface";
    private ValuteRespository valuteRespository;
    private CompositeDisposable compositeDisposable;

    public WidgetPresenter(Context context, WidgetViewInterface widgetViewInterface) {
        this.context = context;
        this.widgetViewInterface = widgetViewInterface;
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
                        widgetViewInterface.displayValuteWithId(valute);
                        widgetViewInterface.hideProgressBar();
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
                        widgetViewInterface.displayValutes(valutes);
                        widgetViewInterface.hideProgressBar();
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
