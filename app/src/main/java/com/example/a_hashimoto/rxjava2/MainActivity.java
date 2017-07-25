package com.example.a_hashimoto.rxjava2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RxJava
//        Observable : back pressure なし

        //----------------------> 時間の流れ
        //------o----o--o--o--o-->
        // ---o---x
        // ------o---o--o-|

        // o : onNext()
        // x : onError()
        // | : onConmplete()

        // ---1---|

//        Maybe.just(1)
//                .subscribe(new DisposableMaybeObserver<Integer>() {
//                    @Override
//                    public void onSuccess(@NonNull Integer integer) {
//                        Log.d("RxSample", "onSuccess : " + integer);
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.d("RxSample", "onError : " + e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d("RxSample", "onComplete : ");
//                    }
//                });
//        Maybe.empty()
//                .subscribe(new DisposableMaybeObserver<Object>() {
//                    @Override
//                    public void onSuccess(@NonNull Object integer) {
//                        Log.d("RxSample", "onSuccess : " + integer);
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.d("RxSample", "onError : " + e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d("RxSample", "onComplete : ");
//                    }
//                });
//
//        Completable.complete()
//                .subscribe(new DisposableCompletableObserver() {
//                    @Override
//                    public void onComplete() {
//                        Log.d("RxSample", "onComplete : ");
//
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.d("RxSample", "onError : " + e);
//                    }
//                });

//        Observable.just(1, 2, 3, 4)
//        int[] array = {1, 2, 3, 4, 5};
        // -1-2-3-4-5

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
//        Observable.range(0,10)
//                .map(new Function<Integer, Integer>() {
//                    @Override
//                    public Integer apply(@NonNull Integer integer) throws Exception {
//                        return integer * 10;
//                    }
//                })
//        Observable.fromIterable(list)
//        Observable.intervalRange(0, 5, 0, 1, TimeUnit.SECONDS)
//
//        Observable.just(1,2,3)
//                .filter(new Predicate<Integer>() {
//                    @Override
//                    public boolean test(@NonNull Integer integer) throws Exception {
//                        return integer % 2 == 0;
//                    }
//                })
        Observable.combineLatest(
                Observable.intervalRange(10, 3, 0, 1000, TimeUnit.MICROSECONDS),
                Observable.intervalRange(20, 4, 500, 1000, TimeUnit.MICROSECONDS),
                new BiFunction<Long, Long, String>() {
                    @Override
                    public String apply(@NonNull Long integer, @NonNull Long s) throws Exception {
                        return integer + " : " + s;
                    }
                }
        )
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(@NonNull String integer) {
                        Log.d("RxSample", "onNext : " + integer);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("RxSample", "onError : " + e);

                    }

                    @Override
                    public void onComplete() {
                        Log.d("RxSample", "onComplete : ");

                    }
//                }) {
//                    @Override
//                    public void onNext(@NonNull Integer integer) {
//                        Log.d("RxSample", "onNext : " + integer);
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.d("RxSample", "onError : " + e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d("RxSample", "onComplete : ");
//                    }
                });

//        Single.just(1)
//                .subscribe(new DisposableSingleObserver<Integer>() {
//                    @Override
//                    public void onSuccess(@NonNull Integer integer) {
//                        Log.d("RxSample", "onSuccess : " + integer);
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.d("RxSample", "onError : " + e);
//                    }
//                });

//        Single.error(new IllegalStateException())
//                .subscribe(new BiConsumer<Object, Throwable>() {
//                    @Override
//                    public void accept(Object integer, Throwable throwable) throws Exception {
//                        Log.d("RxSample", integer + " : " + throwable);
//                    }
//                });
//        Observable.empty()
//                .subscribe()
//
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object integer) throws Exception {
//                        Log.d("RxSample", integer + "");
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Log.d("RxSamle", throwable.toString());
//                    }
//                }, new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        Log.d("RxSample", "run()called");
//                    }
//                });

        // --1--2--3--4--|
        Observable.just(1, 2, 3, 4);

//        int[] array = {1, 2, 3, 4, 5};
        // -1-2-3-4-5
//        Observable.fromArray(array);
//
//        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        Observable.fromIterable(list);

        //1-2-3-4-5--...10-|
        Observable.range(0, 10);


        Observable.just(1, 2, 3)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer) throws Exception {
                        return integer * 10;
                    }
                });

        Observable.just(1, 2, 3)
                // 1-
                .concatMap(new Function<Integer, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Integer integer) throws Exception {
                        // integer から初めて２個uえみtる。emitの間空くは１びょう
                        return Observable.intervalRange(integer, 2, 0, 1, TimeUnit.SECONDS);
                    }
                });

        Observable.just(1, 2, 3)
                // -2-|
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        return integer % 2 == 0;
                    }
                });

        //Conbinelatest api 組み合わせ

        Observable.combineLatest(
                Observable.just(1, 2, 3),
                Observable.just("A", "B", "C", "D"),
                new BiFunction<Integer, String, String>() {

                    @Override
                    public String apply(@NonNull Integer integer, @NonNull String s) throws Exception {
                        return integer + s;
                    }
                }
        );

//        Observable.zip(
//                Observable.just(1, 2, 3),
//                Observable.just("A", "B", "C", "D"),
//                Observable.just("A", "B", "C", "D"),
//                new Function3<Integer, String, String>() {
//                    @Override
//                    public String apply(@NonNull Integer integer, @NonNull String s) throws Exception {
//                        return integer + s;
//                    }
//                }
//        );

        // 1-10-2-20-3-30
//        Observable.merge(
//                Observable.just(1,2,3),
//                Observable.just(10,20,30)
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//
//                    }
//                })
//        );
//
//        Observable.merge(
//                Observable.just(1,2,3),
//
//        )


    }
}
