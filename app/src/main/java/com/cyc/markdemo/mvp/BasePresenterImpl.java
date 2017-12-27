package com.cyc.markdemo.mvp;

import io.reactivex.disposables.CompositeDisposable;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by cyc20 on 2017/12/27.
 */

public class BasePresenterImpl implements BasePresenter {

    private CompositeDisposable mCompositeDisposable;

    @Override
    public void subscribe() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void unSubscribe() {
        mCompositeDisposable.clear();
    }
}
