package com.example.a_hashimoto.rxjava2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

public class MainActivity extends AppCompatActivity {

    public static final String ANDROID_PERMISSION_RECEIVE_BOOT_COMPLETED = "android.permission.RECEIVE_BOOT_COMPLETED";
    private TextView textView;
    private ProgressBar progressDialog;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("intent", "intent");

        textView = (TextView) findViewById(R.id.text);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        Intent intent = new Intent();

    }

    private void subject() {

        final ReplaySubject<String> replaySubject = ReplaySubject.create();

        Observable.just("A","B","C")
                .subscribe(replaySubject);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                replaySubject.subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(@NonNull String s) {
                        Log.d("RxSample", "onNext : " + s);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("RxSample", "onError : " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("RxSample", "onComplete");
                    }
                });
            }
        }, 450);

    }

//        final BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();
//        final BehaviorSubject<String> behaviorSubjectDefault = BehaviorSubject.createDefault("1");

//        final PublishSubject<String> publishSubject = PublishSubject.create();
//        ReplaySubject<Object> replaySubject = ReplaySubject.create();
//        replaySubject.
//
//
//        Observable.just("A", "B", "C")
//                .subscribe(publishSubject);


//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                publishSubject.subscribe(new DisposableObserver<String>() {
//                    @Override
//                    public void onNext(@NonNull String s) {
//                        Log.d("RxSample", "onNext : " + s);
//
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.d("RxSample", "onError : " + e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d("RxSample", "onComplete");
//                    }
//                });
//            }
//        },1000);


    private void doOnTerminate() {
        Observable
                .just(1)
//                .error(new RuntimeException())
                .doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("RxSample", "doOnTerminate");
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {

                    }
                });
    }

    private void parse() {
        Completable.complete()
                .toObservable()
                .subscribe(new DisposableObserver() {
                    @Override
                    public void onNext(@NonNull Object o) {
                        Log.d("parse", "onNext" + o);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("parse", "onError" + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("parse", "onComplete");

                    }
                });
    }

    private void load() {

        getUserName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        progressDialog.setVisibility(View.GONE);
                    }
                })
                .doOnEvent(new BiConsumer<String, Throwable>() {
                    @Override
                    public void accept(String s, Throwable throwable) throws Exception {
                        progressDialog.setVisibility(View.VISIBLE);
                    }
                })
                .doOnSuccess(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("rxSample", "doOnSuccess : " + Thread.currentThread().getName());
                    }
                })
                .subscribe(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(@NonNull String s) {
                        textView.setText(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private Single<String> getUserName() {
        return Single.just("Android")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String integer) throws Exception {
                        //ネットワーク通信の代わり
                        Thread.sleep(2000);
                        return integer;
                    }
                });

//    private Disposable disposable = Disposables.empty();

//        private void scheduler () {
//
//            Observable.error(new IllegalStateException())
//                .flatMap(new Function<Integer, ObservableSource<Integer>>() {
//                    @Override
//                    public ObservableSource<Integer> apply(@NonNull Integer integer) throws Exception {
//                        Log.d("RxSample", "apply : " + integer + " : " + Thread.currentThread().getName());
//                        return Observable.range(integer * 10, 3)
//                                .subscribeOn(Schedulers.newThread())
//                                .doOnNext(new Consumer<Integer>() {
//                                    @Override
//                                    public void accept(Integer integer) throws Exception {
//                                        Log.d("RxSample", "doOnNext : " + integer + " : " + Thread.currentThread().getName());
//                                    }
//                                });
//                    }
//                })
//
//                    .doOnError(new Consumer<Throwable>() {
//                        @Override
//                        public void accept(Throwable throwable) throws Exception {
//                            Log.d("RxSample", "doOnError : ");
//                        }
//                    })
//                    .doOnNext(new Consumer<Object>() {
//                        @Override
//                        public void accept(Object integer) throws Exception {
//                            Log.d("RxSample", "doOnNext : " + integer);
//                        }
//                    })
//                    .doOnComplete(new Action() {
//                        @Override
//                        public void run() throws Exception {
//                            Log.d("RxSample", "doOnComplete : ");
//
//                        }
//                    })
//                    .doOnSubscribe(new Consumer<Disposable>() {
//                        @Override
//                        public void accept(Disposable disposable) throws Exception {
//                            Log.d("RxSample", "doOnSubscribe : ");
//                        }
//                    })
//                    .doOnTerminate(new Action() { // comp and error do
//                        @Override
//                        public void run() throws Exception {
//                            Log.d("RxSample", "doOnTerminate : ");
//
//                        }
//                    })
//                    .doOnLifecycle(new Consumer<Disposable>() {
//                        @Override
//                        public void accept(Disposable disposable) throws Exception {
//                            Log.d("RxSample", "doOnLifecycle : Consumer");
//
//                        }
//                    }, new Action() {
//                        // doOnSubscribe
//                        @Override
//                        public void run() throws Exception {
//                            Log.d("RxSample", "doOnLifecycle : Action");
//
//                        }
//                    })
//                    .doOnEach(new Consumer<Notification<Object>>() {
//                        // onNext
//                        // onError
//                        // onComplete
//                        @Override
//                        public void accept(Notification<Object> objectNotification) throws Exception {
//                            Log.d("RxSample", "doOnEach : Consumer" + objectNotification);
//                        }
//                    })
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<Object>() {
//                        @Override
//                        public void accept(Object integer) throws Exception {
//                            Log.d("RxSample", "accept : " + integer + " : " + Thread.currentThread().getName());
////                        disposable.dispose();
//                        }
//                    });
//
//
//        Observable.just(10)
//                .map(new Function<Integer, String>() {
//                    @Override
//                    public String apply(@NonNull Integer integer) throws Exception {
//                        //io thread にしたい
//                        Thread.sleep(6000);
//                        Log.d("RxSample", "map : " + Thread.currentThread().getName());
//                        return String.valueOf(integer);
//                    }
//                })
//                .observeOn(Schedulers.computation())
//                .map(new Function<String, String>() {
//                    //computation thread にしたい
//                    @Override
//                    public String apply(@NonNull String s) throws Exception {
//                        Log.d("RxSample", "map2 : " + Thread.currentThread().getName());
//                        return s + " : A";
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    // main thread にしたい
//                    @Override
//                    public void accept(String s) throws Exception {
//                        Log.d("RxSample", "accept : " + Thread.currentThread().getName());
//                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
//                    }
//                });

        // オブザーバブルに寄ってどのスレッドを用いるのかみんな書いてある
        // schedulerを見れば書いてある


    }

    private void zip() {
        Observable.zip(
                Observable.just(1, 2, 3)
                , Observable.just(10, 20, 30, 40)
                , new BiFunction<Integer, Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                        Log.d("RxSample", " zip" + integer + " : " + integer2);
                        return integer + " : " + integer2;
                    }
                }
        )
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(@NonNull String s) {
                        Log.d("RxSample", " zip onNext" + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("RxSample", " zip onError" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("RxSample", " zip onComplete");
                    }
                });
    }

    private void combineLatest() {
        Observable.combineLatest(
                Observable
                        .just(1, 2, 3)
                        .subscribeOn(Schedulers.newThread())
                        .doOnNext(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Log.d("RxSample", "just1 : " + integer + " " + Thread.currentThread().getName());
                            }
                        })
                , Observable
                        .just(10, 20, 30, 40)
                        .subscribeOn(Schedulers.newThread())
                        .doOnNext(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Log.d("RxSample", "just2 : " + integer + " " + Thread.currentThread().getName());
                            }
                        })
                , new BiFunction<Integer, Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                        return integer + " : " + integer2;
                    }
                }
        )
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(@NonNull String s) {
                        Log.d("RxSample", " combineLatest onNext" + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("RxSample", " combineLatest onError" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("RxSample", " combineLatest onComplete");
                    }
                });
    }

    private void merge() {
        Observable.merge(
                Observable
                        .just(1, 2, 3, 4)
                        .subscribeOn(Schedulers.newThread())
                        .doOnNext(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Log.d("RxSample", "just1 : " + integer + " " + Thread.currentThread().getName());
                            }
                        })
                , Observable
                        .just(10, 20, 30, 40)
                        .subscribeOn(Schedulers.newThread())
                        .doOnNext(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Log.d("RxSample", "just2 : " + integer + " " + Thread.currentThread().getName());
                            }
                        })
        )
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("RxSample", "marge : " + integer);
                    }
                });
    }


//        Single.just(1)
//                .subscribe(new SingleObserver<Integer>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(@NonNull Integer integer) {
//
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//                });
//        Single.just(1)
//                .subscribeWith(new DisposableSingleObserver<Integer>() {
//                    @Override
//                    public void onSuccess(@NonNull Integer integer) {
//
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//                });

//        Single.create(new SingleOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(@NonNull SingleEmitter<Integer> e) throws Exception {
//                e.onSuccess(1);
//                e.onSuccess(2);
//            }
//        }).

    // RxJava
//        Observable : back pressure なし

    //----------------------> 時間の流れ
    //------o----o--o--o--o-->
    // ---o---x
    // ------o---o--o-|

    // o : onNext()
    // x : onError()
    // | : onComplete()

    // ---1---|

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

//        List<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
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


//        Observable.merge(
//                Observable.intervalRange(10, 3, 0, 1000, TimeUnit.MILLISECONDS),
//                Observable.intervalRange(20, 4, 500, 1000, TimeUnit.MILLISECONDS))
//                .subscribe(new DisposableObserver<Long>() {
//                    @Override
//                    public void onNext(@NonNull Long aLong) {
//                        Log.d("RxSample", "onNext : " + aLong);
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


//        Observable.zip(
//                Observable.intervalRange(10, 3, 0, 1000, TimeUnit.MICROSECONDS),
//                Observable.intervalRange(20, 4, 500, 1000, TimeUnit.MICROSECONDS),
//                new BiFunction<Long, Long, String>() {
//                    @Override
//                    public String apply(@NonNull Long integer, @NonNull Long s) throws Exception {
//                        return integer + " : " + s;
//                    }
//                }
//        )
//                .subscribe(new DisposableObserver<String>() {
//                    @Override
//                    public void onNext(@NonNull String integer) {
//                        Log.d("RxSample", "onNext : " + integer);
//
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.d("RxSample", "onError : " + e);
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d("RxSample", "onComplete : ");
//
//                    }
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
//                });

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
//                        Log.d("RxSample", throwable.toString());
//                    }
//                }, new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        Log.d("RxSample", "run()called");
//                    }
//                });

//        // --1--2--3--4--|
//        Observable.just(1, 2, 3, 4);

//        int[] array = {1, 2, 3, 4, 5};
    // -1-2-3-4-5
//        Observable.fromArray(array);
//
//        List<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//
//        Observable.fromIterable(list);
//
//        //1-2-3-4-5--...10-|
//        Observable.range(0, 10);
//
//
//        Observable.just(1, 2, 3)
//                .map(new Function<Integer, Integer>() {
//                    @Override
//                    public Integer apply(@NonNull Integer integer) throws Exception {
//                        return integer * 10;
//                    }
//                });
//
//        Observable.just(1, 2, 3)
//                // 1-
//                .concatMap(new Function<Integer, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(@NonNull Integer integer) throws Exception {
//                        // integer から初めて２個uえみtる。emitの間空くは１びょう
//                        return Observable.intervalRange(integer, 2, 0, 1, TimeUnit.SECONDS);
//                    }
//                });
//
//        Observable.just(1, 2, 3)
//                // -2-|
//                .filter(new Predicate<Integer>() {
//                    @Override
//                    public boolean test(@NonNull Integer integer) throws Exception {
//                        return integer % 2 == 0;
//                    }
//                });
//
//        //Conbinelatest api 組み合わせ
//
//        Observable.combineLatest(
//                Observable.just(1, 2, 3),
//                Observable.just("A", "B", "C", "D"),
//                new BiFunction<Integer, String, String>() {
//
//                    @Override
//                    public String apply(@NonNull Integer integer, @NonNull String s) throws Exception {
//                        return integer + s;
//                    }
//                }
//        );

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
