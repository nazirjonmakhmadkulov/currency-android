package com.developer.valyutaapp.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import io.reactivex.annotations.NonNull;


@Entity(tableName = "valute")
@Root(name="Valute")
public class Valute {

    @NonNull
    @Attribute(name = "ID")
    @PrimaryKey(autoGenerate = false)
    private int id;

    @Element(name = "CharCode")
    public String charCode;

    @Element(name = "Nominal")
    public int nominal;

    @Element(name = "Name")
    public String name;

    @Element(name = "Value")
    public String value;

    public int sortValute;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
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

    public int getSortValute() {
        return sortValute;
    }

    public void setSortValute(int sortValute) {
        this.sortValute = sortValute;
    }
}
