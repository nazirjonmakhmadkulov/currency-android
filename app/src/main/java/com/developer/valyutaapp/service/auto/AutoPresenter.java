package com.developer.valyutaapp.service.auto;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.developer.valyutaapp.data.local_data_source.database.ValuteDataSource;
import com.developer.valyutaapp.data.local_data_source.database.ValuteDatabase;
import com.developer.valyutaapp.data.local_data_source.repository.ValuteRespository;
import com.developer.valyutaapp.data.remote_data_source.NetworkClient;
import com.developer.valyutaapp.data.remote_data_source.NetworkInterfaces;
import com.developer.valyutaapp.model.ValCurs;
import com.developer.valyutaapp.utils.Utils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class AutoPresenter implements AutoPresenterInterface {

    Context context;
    private String TAG = "AutoPresenter";
    private ValuteRespository valuteRespository;
    private CompositeDisposable compositeDisposable;

    public AutoPresenter(Context context) {
        this.context = context;
        ValuteDatabase valuteDatabase = ValuteDatabase.getInstance(context);
        valuteRespository = ValuteRespository.getInstance(ValuteDataSource.getInstance(valuteDatabase.valuteDAO()));
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getValutesRemote() {
        getObservable().subscribeWith(getObserver());
    }

    public Observable<ValCurs> getObservable(){
        return NetworkClient.getRetrofit().create(NetworkInterfaces.class)
                .getValutes(Utils.getDate(),"xmlout")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<ValCurs> getObserver(){
        return new DisposableObserver<ValCurs>() {

            @Override
            public void onNext(@NonNull ValCurs valCurs) {
                Log.d(TAG,"OnNext"+valCurs.getValute().toString());
                insertData(valCurs);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error"+e);
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
            }
        };
    }


    public void insertData(final ValCurs valCurs){
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) {
                valuteRespository.insertValute(valCurs.valute);
                e.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) {
                        Toast.makeText(context, "Valute added!", Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Toast.makeText(context, ""+ throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }, new Action(){
                    @Override
                    public void run() throws Exception {
                    }
                });
    }
}
