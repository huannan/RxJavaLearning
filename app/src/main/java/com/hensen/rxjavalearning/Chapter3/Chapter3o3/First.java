package com.hensen.rxjavalearning.Chapter3.Chapter3o3;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by handsomexu on 2018/4/11.
 */

public class First {

    public static void main(String[] args) {
        Observable.just(1, 2, 3, 4, 1, 2, 3)
                .first(7)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("onNext=" + integer);
                    }
                });

        // 7当数据源不发射数据的时候，发射默认值
        Integer[] arrays = new Integer[0];
        Observable.fromArray(arrays)
                .first(7)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("onNext=" + integer);
                    }
                });

    }
}
