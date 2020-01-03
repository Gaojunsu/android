package com.keylinks.android.rxjava;
import android.util.Log;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class RxJavaManager {


    Disposable subscribe = null;


    public static void filter(final String conditionA) {


        Disposable subscribe = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

                e.onNext(conditionA);


            }
        })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        if (s.equals("大佬")) return true;
                        Log.w("Rxjava", "非大佬");
                        return false;
                    }
                })

                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.w("Rxjava", s);
                    }
                });


    }
    public static void interval() {

        Observable.interval(1, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                            Log.w("rxjava",String.valueOf(aLong));
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.w("rxjava",String.valueOf(aLong));
                    }
                });
    }
    public static void zip() {

        Observable observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                    e.onNext("无敌");
            }
        }).subscribeOn(Schedulers.io());

        Observable observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("天下");
            }
        }).subscribeOn(Schedulers.io());
        Observable.zip(observable1, observable2, new BiFunction<String, String, String>() {
            @Override
            public String apply(String o, String o2) throws Exception {
                return o+o2;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String o) throws Exception {
                Log.w("rxjava",o);
            }
        });
    }
    public void destroy() {

        if (subscribe != null) {
            subscribe.dispose();
        }

    }
    public static void contains(){
        Observable.just("web","html","css","english")
                .contains("js")
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.w("rxjava",aBoolean.toString());
                    }
                });
    }
    public static  void startWhith(){


        Observable<String> stringObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                            e.onNext("牛逼");
                            e.onComplete();
            }
        });


         Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("沙雕");
                e.onComplete();
            }
        }).startWith(stringObservable).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.w("rxjava",s);

            }
        });





    }
}
