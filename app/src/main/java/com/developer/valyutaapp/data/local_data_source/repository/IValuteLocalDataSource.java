package com.developer.valyutaapp.data.local_data_source.repository;


import com.developer.valyutaapp.model.Valute;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface IValuteLocalDataSource {

    Flowable<Valute> getValuteById(int userId);
    Single<Integer> getValuteCount();
    Flowable<List<Valute>> getAllValutes();
    void insertValute(List<Valute> valutes);
    void updateValute(List<Valute> valutes);
    void updateValuteStatus(Valute valute);
    void deleteValute(Valute... valutes);
    void deleteAllValutes();

}
