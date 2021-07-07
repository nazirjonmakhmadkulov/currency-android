package com.developer.valyutaapp.ui.sort;

import com.developer.valyutaapp.model.Valute;

import java.util.List;

public interface SortViewInterface {
    void showToast(String s);
    void showProgressBar();
    void hideProgressBar();
    void displayValutes(List<Valute> valute);
    void displayError(String s);
}
