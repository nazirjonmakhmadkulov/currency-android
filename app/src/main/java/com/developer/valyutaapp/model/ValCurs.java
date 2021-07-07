package com.developer.valyutaapp.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "ValCurs", strict = false)
public class ValCurs {

    @ElementList(inline = true, required = true)
    public List<Valute> valute;

    @Attribute(name = "Date")
    public String date;

    @Attribute(name = "name")
    public String name;

    public List<Valute> getValute() {
        return valute;
    }

    public void setValute(List<Valute> valute) {
        this.valute = valute;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

