package com.developer.valyutaapp.data.local_data_source.database;

import com.developer.valyutaapp.data.local_data_source.repository.IValuteLocalDataSource;
import com.developer.valyutaapp.model.Valute;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class ValuteDataSource implements IValuteLocalDataSource {

    private ValuteDAO valuteDAO;
    private static ValuteDataSource mInstance;

    public ValuteDataSource(ValuteDAO valuteDAO){
        this.valuteDAO = valuteDAO;
    }

    public static ValuteDataSource getInstance(ValuteDAO userDAO){
        if (mInstance == null){
            mInstance = new ValuteDataSource(userDAO);
        }
        return mInstance;
    }


    @Override
    public Flowable<Valute> getValuteById(int userId) {
        return valuteDAO.getValuteById(userId);
    }

    @Override
    public Single<Integer> getValuteCount() {
        return valuteDAO.getValuteCount();
    }

    @Override
    public Flowable<List<Valute>> getAllValutes() {
        return valuteDAO.getAllValutes();
    }

    @Override
    public void insertValute(List<Valute> valutes) {
        valuteDAO.insertValute(valutes);
    }

    @Override
    public void updateValute(List<Valute> valutes) {
        valuteDAO.updateValute(valutes);
    }

    @Override
    public void updateValuteStatus(Valute valute) {
        valuteDAO.updateValuteStatus(valute);
    }

    @Override
    public void deleteValute(Valute... valutes) {
        valuteDAO.deleteValute(valutes);
    }

    @Override
    public void deleteAllValutes() {
        valuteDAO.deleteAllValutes();
    }
}
