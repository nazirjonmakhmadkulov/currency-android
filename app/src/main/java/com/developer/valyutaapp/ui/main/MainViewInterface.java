package com.developer.valyutaapp.ui.main;

import com.developer.valyutaapp.model.Valute;

import java.util.List;

public interface MainViewInterface {
    void showToast(String s);
    void showProgressBar();
    void hideProgressBar();
    void displayValutes(List<Valute> valutes);
    void displayError(String s);
}