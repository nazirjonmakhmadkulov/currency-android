package com.developer.valyutaapp.ui.main;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.developer.valyutaapp.data.local_data_source.repository.ValuteRespository;
import com.developer.valyutaapp.data.remote_data_source.NetworkClient;
import com.developer.valyutaapp.data.remote_data_source.NetworkInterfaces;
import com.developer.valyutaapp.model.ValCurs;
import com.developer.valyutaapp.model.Valute;
import com.developer.valyutaapp.utils.Utils;

import java.util.List;

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

public class MainPresenter implements MainPresenterInterface {

    MainViewInterface mvi;
    Context context;
    private String TAG = "MainPresenter";
    private ValuteRespository valuteRespository;
    private CompositeDisposable compositeDisposable;
    private int countVal = 0;

    public MainPresenter(Context context, MainViewInterface mvi, ValuteRespository valuteRespository) {
        this.context = context;
        this.mvi = mvi;
        this.valuteRespository = valuteRespository;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void getValutesRemote() {
        mvi.showProgressBar();
        loadValuteCount();
    }

    @Override
    public void getValutesLocal() {
        loadData();
    }

    public Observable<ValCurs> getObservable(){
        return NetworkClient.getRetrofit().create(NetworkInterfaces.class)
                .getValutes(Utils.getDate(),"xmlout")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<ValCurs> getObserver(int valCount){
        return new DisposableObserver<ValCurs>() {

            @Override
            public void onNext(@NonNull ValCurs valCurs) {
                Log.d(TAG,"OnNext"+valCurs.getValute().toString());
                if (valCount > 0){
                    updateData(valCurs);
                }else {
                    insertData(valCurs);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error"+e);
                e.printStackTrace();
                mvi.displayError("Error fetching valutes Data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
                mvi.hideProgressBar();
            }
        };
    }

    public void insertData(ValCurs valCurs){
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) {
                Log.d(TAG,"subscribe = " + valCurs.valute.size());
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
                        loadData();
                    }
                });
    }


    public void updateData(ValCurs valCurs){
        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) {
                Log.d(TAG,"subscribe = " + valCurs.valute.size());
                valuteRespository.updateValute(valCurs.valute);
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
                        loadData();
                    }
                });
    }


    private int loadValuteCount() {
        Disposable disposable = valuteRespository.getValuteCount()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>(){
                    @Override
                    public void accept(Integer count) throws Exception {
                        getObservable().subscribeWith(getObserver(count));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception{
                        Toast.makeText(context, ""+ throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
        return countVal;
    }

    private void loadData() {
        Disposable disposable = valuteRespository.getAllValutes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Valute>>(){
                    @Override
                    public void accept(List<Valute> valutes) throws Exception {
                        Log.d(TAG,"Completed = " + valutes.size());
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
