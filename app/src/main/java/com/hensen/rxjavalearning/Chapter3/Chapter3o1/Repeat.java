package com.hensen.rxjavalearning.Chapter3.Chapter3o1;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by handsomexu on 2018/4/11.
 */

public class Repeat {

    public static void main(String[] args) {
        Observable.just(1).repeat(5)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("onNext=" + integer);
                    }
                });
    }
}
