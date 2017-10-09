package rana.jatin.androiddevessentials.RxEventBus;

import android.nfc.Tag;
import android.support.annotation.IntDef;
import android.util.Log;
import android.util.SparseArray;

import java.lang.annotation.Retention;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public final class RxBus<T> {

    private String TAG=RxBus.class.getName();
    private static RxBus rxBus;
    private Map<String,PublishSubject<Object>> sSubjectMap = new HashMap<>();
    private Map<Object, CompositeDisposable> sSubscriptionsMap = new HashMap<>();

    private RxBus() {
        // hidden constructor
    }

    public static RxBus getInstance() {
        if (rxBus == null)
            rxBus = new RxBus();
        return rxBus;
    }

    /**
     * Get the subject or create it if it's not already in memory.
     */
    @NonNull
    private PublishSubject<Object> getSubject(String subjectCode) {
        PublishSubject<Object> subject = sSubjectMap.get(subjectCode);
        if (subject == null) {
            subject = PublishSubject.create();
            subject.subscribeOn(AndroidSchedulers.mainThread());
            subject.observeOn(AndroidSchedulers.mainThread());
            sSubjectMap.put(subjectCode, subject);
        }

        return subject;
    }

    /**
     * Get the CompositeSubscription or create it if it's not already in memory.
     */
    @NonNull
    private CompositeDisposable getCompositeSubscription(@NonNull Object object) {
        CompositeDisposable compositeSubscription = sSubscriptionsMap.get(object);
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeDisposable();
            sSubscriptionsMap.put(object, compositeSubscription);
        }

        return compositeSubscription;
    }

    /**
     * Subscribe to the specified subject and listen for updates on that subject. Pass in an object to associate
     * your registration with, so that you can unsubscribe later.
     * <br/><br/>
     * <b>Note:</b> Make sure to call {@link RxBus#unregister(Object)} to avoid memory leaks.
     */
    public <T> Disposable subscribe(String subject, @NonNull Object lifecycle,Class<T> classType, @NonNull Consumer<? super T> action){
        Disposable disposable=getSubject(subject).ofType(classType).subscribe(action);
        getCompositeSubscription(lifecycle).add(disposable);
        return disposable;
    }

    /**
     * Unregisters this object from the bus, removing all subscriptions.
     * This should be called when the object is going to go out of memory.
     */
    public void unregister(@NonNull Object lifecycle) {
        //We have to remove the composition from the map, because once you unsubscribe it can't be used anymore
        CompositeDisposable compositeSubscription = sSubscriptionsMap.remove(lifecycle);
        if (compositeSubscription != null) {
            compositeSubscription.clear();
        }
    }

    /**
     * Publish an object to the specified subject for all subscribers of that subject.
     */
    public void publish(String subject, @NonNull Object message) {
        getSubject(subject).onNext(message);
        Log.d(TAG,"publish: "+"subject: "+String.valueOf(subject)+" message:"+message);
    }

}
