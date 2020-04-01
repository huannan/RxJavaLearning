package com.hensen.rxjavalearning.Chapter7.Chapter7o1;

import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class StringToInteger implements ObservableOperator<Integer,String> {
    @Override
    public Observer<? super String> apply(final Observer<? super Integer> observer) throws Exception {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                observer.onSubscribe(d);
            }

            @Override
            public void onNext(String s) {
                observer.onNext(Integer.parseInt(s));
            }

            @Override
            public void onError(Throwable e) {
                observer.onError(e);
            }

            @Override
            public void onComplete() {
                observer.onComplete();
            }
        };
    }
}
