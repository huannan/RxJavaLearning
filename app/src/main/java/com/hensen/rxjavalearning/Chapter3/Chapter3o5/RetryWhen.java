package com.hensen.rxjavalearning.Chapter3.Chapter3o5;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by handsomexu on 2018/4/9.
 */

public class RetryWhen {

    private static int retryCount = 0;
    private static int maxRetries = 2;

    public static void main(String[] args) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                for (int i = 1; i < 5; i++) {
                    if (i == 4) {
                        e.onError(new Exception("onError crash"));
                    }
                    e.onNext(i);
                }
            }
        })
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
//                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
//                            @Override
//                            public ObservableSource<?> apply(Throwable throwable) throws Exception {
//                                if (++retryCount <= maxRetries) {
//                                    // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
//                                    System.out.println("get error, it will try after " + 1 + " seconds, retry count " + retryCount);
//                                    return Observable.timer(1, TimeUnit.SECONDS);
//                                }
//                                return Observable.error(throwable);
//                            }
//                        });

                        // 参数Observable<Throwable>中的泛型 = 上游操作符抛出的异常，可通过该条件来判断异常的类型
                        // 返回Observable<?> = 新的被观察者 Observable（任意类型）
                        // 此处有两种情况：
                        // 1. 若 新的被观察者 Observable发送的事件 = Error事件，那么 原始Observable则不重新发送事件：
                        // 2. 若 新的被观察者 Observable发送的事件 = Next事件 ，那么原始的Observable则重新发送事件：
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {

                                // 1. 若返回的Observable发送的事件 = Error事件，则原始的Observable不重新发送事件
                                // 该异常错误信息可在观察者中的onError（）中获得
                                // return Observable.error(new Throwable("retryWhen终止啦"));

                                // 2. 若返回的Observable发送的事件 = Next事件，则原始的Observable重新发送事件（若持续遇到错误，则持续重试）
                                return Observable.just(-2);
                            }
                        });
                    }
                })
//                .onErrorReturn(new Function<Throwable, Integer>() {
//                    @Override
//                    public Integer apply(Throwable throwable) throws Exception {
//                        return -1;
//                    }
//                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("onNext=" + integer);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println("onError");
                    }
                }, new Action() {

                    @Override
                    public void run() throws Exception {
                        System.out.println("onComplete");
                    }
                });
    }
}
