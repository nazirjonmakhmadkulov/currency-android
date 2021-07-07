package com.developer.valyutaapp.ui.widget;


import com.developer.valyutaapp.model.Valute;

import java.util.List;

public interface WidgetViewInterface {
    void showToast(String s);
    void showProgressBar();
    void hideProgressBar();
    void displayValuteWithId(Valute valute);
    void displayValutes(List<Valute> valute);
    void displayError(String s);
}
