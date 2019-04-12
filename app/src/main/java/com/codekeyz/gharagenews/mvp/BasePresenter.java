package com.codekeyz.gharagenews.mvp;

public abstract class BasePresenter<V extends BaseView> {

    private V view;

    public V getView() {
        return view;
    }

    public void takeView(V view) {
        this.view = view;
    }

    public void destroyView() {
        this.view = null;
    }

    public boolean isViewAvailable() {
        return this.view != null;
    }
}
