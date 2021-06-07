package com.developer.valyutaapp;

/**
 * Created by User on 16.08.2018.
 */

public class ModelDate {

    int _idd;
    String _dates;

    public ModelDate(int _idd, String _dates) {
        this._idd = _idd;
        this._dates = _dates;
    }

    public int get_idd() {
        return _idd;
    }

    public void set_idd(int _idd) {
        this._idd = _idd;
    }

    public String get_dates() {
        return _dates;
    }

    public void set_dates(String _dates) {
        this._dates = _dates;
    }
}
