package com.cyc.markdemo.mvp;

/**
 * Created by cyc20 on 2017/12/27.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
}
