package com.developer.valyutaapp.ui.valute;
import com.developer.valyutaapp.model.Valute;

import java.util.List;


public interface ValuteViewInterface {
    void showToast(String s);
    void showProgressBar();
    void hideProgressBar();
    void displayValuteWithId(Valute valute);
    void displayValutes(List<Valute> valute);
    void displayError(String s);
}
