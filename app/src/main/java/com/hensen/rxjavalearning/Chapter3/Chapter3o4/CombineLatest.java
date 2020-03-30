package com.hensen.rxjavalearning.Chapter3.Chapter3o4;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by handsomexu on 2018/4/11.
 */

public class CombineLatest {

    public static String[] str = {"A", "B", "C", "D", "E"};

    public static void main(String[] args) {
        Observable<Integer> just1 = Observable.just(1, 2, 3);
        Observable<String> just2 = Observable.intervalRange(0, 5, 0, 1, TimeUnit.SECONDS).map(new Function<Long, String>() {
            @Override
            public String apply(Long index) throws Exception {
                return str[index.intValue()];
            }
        });
        Observable.combineLatest(just1, just2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + " " + s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("onNext=" + s);
            }
        });

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
