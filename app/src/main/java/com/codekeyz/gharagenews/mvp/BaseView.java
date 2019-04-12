package com.codekeyz.gharagenews.mvp;

public interface BaseView {
    void showLoadingIndicator();

    void hideLoadingIndicator();

    void showMessage(String message);
}
