package com.developer.valyutaapp.data.local_data_source.repository;


import com.developer.valyutaapp.model.Valute;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class ValuteRespository implements IValuteLocalDataSource {

    private static IValuteLocalDataSource mLocalDataSource;
    private static ValuteRespository mInstance;

    public ValuteRespository(IValuteLocalDataSource mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public static ValuteRespository getInstance(IValuteLocalDataSource mLocalDataSourc){
        if (mInstance == null){
            mInstance = new ValuteRespository(mLocalDataSourc);
        }
        return mInstance;
    }

    @Override
    public Flowable<Valute> getValuteById(int userId) {
        return mLocalDataSource.getValuteById(userId);
    }

    @Override
    public Single<Integer> getValuteCount() {
        return mLocalDataSource.getValuteCount();
    }

    @Override
    public Flowable<List<Valute>> getAllValutes() {
        return mLocalDataSource.getAllValutes();
    }

    @Override
    public void insertValute(List<Valute> valutes) {
        mLocalDataSource.insertValute(valutes);
    }

    @Override
    public void updateValute(List<Valute> valutes) {
        mLocalDataSource.updateValute(valutes);
    }

    @Override
    public void updateValuteStatus(Valute valute) {
        mLocalDataSource.updateValuteStatus(valute);
    }

    @Override
    public void deleteValute(Valute... valutes) {
        mLocalDataSource.deleteValute(valutes);
    }

    @Override
    public void deleteAllValutes() {
        mLocalDataSource.deleteAllValutes();

    }
}
