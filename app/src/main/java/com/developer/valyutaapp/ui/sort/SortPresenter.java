package com.developer.valyutaapp.ui.sort;

import android.content.Context;
import android.widget.Toast;

import com.developer.valyutaapp.data.local_data_source.database.ValuteDataSource;
import com.developer.valyutaapp.data.local_data_source.database.ValuteDatabase;
import com.developer.valyutaapp.data.local_data_source.repository.ValuteRespository;
import com.developer.valyutaapp.model.Valute;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SortPresenter implements SortPresenterInterface {

    SortViewInterface sortViewInterface;
    Context context;
    private String TAG = "SortPresenter";
    private ValuteRespository valuteRespository;
    private CompositeDisposable compositeDisposable;

    public SortPresenter(Context context, SortViewInterface sortViewInterface) {
        this.context = context;
        this.sortViewInterface = sortViewInterface;
        ValuteDatabase valuteDatabase = ValuteDatabase.getInstance(context);
        valuteRespository = ValuteRespository.getInstance(ValuteDataSource.getInstance(valuteDatabase.valuteDAO()));
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void updateValute(Valute valute) {
        updateData(valute);
    }

    @Override
    public void getValutes() {
        loadData();
    }

    private void updateData(Valute valute) {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws  Exception {
                valuteRespository.updateValuteStatus(valute);
                e.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                         Toast.makeText(context, "Valute updated!", Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(context, ""+ throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Action(){
                    @Override
                    public void run() throws Exception {
                        //loadData();
                    }
                });

        compositeDisposable.add(disposable);
    }


    private void loadData() {
        Disposable disposable = valuteRespository.getAllValutes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Valute>>(){
                    @Override
                    public void accept(List<Valute> valutes) throws Exception {
                        sortViewInterface.displayValutes(valutes);
                        sortViewInterface.hideProgressBar();
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
