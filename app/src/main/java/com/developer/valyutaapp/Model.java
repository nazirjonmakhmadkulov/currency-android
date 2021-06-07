package com.developer.valyutaapp;

/**
 * Created by User on 13.06.2018.
 */

public class Model {

    long _id;
    int id_val;
    String charcode;
    int nominal;
    String name;
    String value;
    String date;
    int selected;

    public Model(long _id, int id_val, String charcode, int nominal, String name, String value, String date, int selected) {

        this._id = _id;
        this.date = date;
        this.id_val = id_val;
        this.charcode = charcode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
        this.selected = selected;

    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public int getId_val() {
        return id_val;
    }

    public void setId_val(int id_val) {
        this.id_val = id_val;
    }

    public String getCharcode() {
        return charcode;
    }

    public void setCharcode(String charcode) {
        this.charcode = charcode;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}