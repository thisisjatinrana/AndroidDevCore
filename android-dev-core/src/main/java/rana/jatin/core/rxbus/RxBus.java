package rana.jatin.core.rxbus;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

@SuppressWarnings("unused")
public class RxBus {
    public static final String LOG_BUS = "RXBUS_LOG";
    private static volatile RxBus defaultInstance;
    private final Subject<Object> bus;
    private Map<Class, List<Disposable>> subscriptionsByEventType = new HashMap<>();
    private Map<Object, List<Class>> eventTypesBySubscriber = new HashMap<>();
    private Map<Class, List<SubscriberMethod>> subscriberMethodByEventType = new HashMap<>();

    private RxBus() {
        this.bus = PublishSubject.create().toSerialized();
    }

    public static RxBus get() {
        RxBus rxBus = defaultInstance;
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                rxBus = defaultInstance;
                if (defaultInstance == null) {
                    rxBus = new RxBus();
                    defaultInstance = rxBus;
                }
            }
        }
        return rxBus;
    }

    /**
     * According to the passed eventType Type returns a specific type(eventType)Of Observer
     *
     * @param eventType Event type
     * @return return
     */
    private <T> Flowable<T> toObservable(Class<T> eventType) {
        return bus.toFlowable(BackpressureStrategy.BUFFER).ofType(eventType);
    }

    /**
     * Return the observer of the specific type (eventType) according to the passed code and eventType types
     *
     * @param code      Event code
     * @param eventType Event type
     */
    private <T> Flowable<T> toObservable(final int code, final Class<T> eventType) {
        return bus.toFlowable(BackpressureStrategy.BUFFER).ofType(Message.class)
                .filter(new Predicate<Message>() {
                    @Override
                    public boolean test(Message o) {
                        return o.getCode() == code && eventType.isInstance(o.getObject());
                    }
                }).map(new Function<Message, Object>() {
                    @Override
                    public Object apply(Message o) {
                        return o.getObject();
                    }
                }).cast(eventType);
    }

    /**
     * Registered
     *
     * @param subscriber Subscriber
     */
    public void register(Object subscriber) {
        Class<?> subClass = subscriber.getClass();
        Method[] methods = subClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Subscribe.class)) {
                //Get parameter type
                Class[] parameterType = method.getParameterTypes();
                //The parameter is not empty And the number of parameters is 1
                if (parameterType != null && parameterType.length == 1) {

                    Class eventType = parameterType[0];

                    addEventTypeToMap(subscriber, eventType);
                    Subscribe sub = method.getAnnotation(Subscribe.class);
                    int code = sub.code();
                    ThreadMode threadMode = sub.threadMode();

                    SubscriberMethod subscriberMethod = new SubscriberMethod(subscriber, method, eventType, code, threadMode);
                    addSubscriberToMap(eventType, subscriberMethod);

                    addSubscriber(subscriberMethod);
                } else if (parameterType == null || parameterType.length == 0) {

                    Class eventType = BusData.class;

                    addEventTypeToMap(subscriber, eventType);
                    Subscribe sub = method.getAnnotation(Subscribe.class);
                    int code = sub.code();
                    ThreadMode threadMode = sub.threadMode();

                    SubscriberMethod subscriberMethod = new SubscriberMethod(subscriber, method, eventType, code, threadMode);
                    addSubscriberToMap(eventType, subscriberMethod);

                    addSubscriber(subscriberMethod);

                }
            }
        }
    }


    /**
     * Save the event type to the map with the subscriber in the subscription as the key
     *
     * @param subscriber Subscriber
     * @param eventType  Event type
     */
    private void addEventTypeToMap(Object subscriber, Class eventType) {
        List<Class> eventTypes = eventTypesBySubscriber.get(subscriber);
        if (eventTypes == null) {
            eventTypes = new ArrayList<>();
            eventTypesBySubscriber.put(subscriber, eventTypes);
        }

        if (!eventTypes.contains(eventType)) {
            eventTypes.add(eventType);
        }
    }

    /**
     * Save annotation method information to map with event type as key
     *
     * @param eventType        Event type
     * @param subscriberMethod Annotation method information
     */
    private void addSubscriberToMap(Class eventType, SubscriberMethod subscriberMethod) {
        List<SubscriberMethod> subscriberMethods = subscriberMethodByEventType.get(eventType);
        if (subscriberMethods == null) {
            subscriberMethods = new ArrayList<>();
            subscriberMethodByEventType.put(eventType, subscriberMethods);
        }

        if (!subscriberMethods.contains(subscriberMethod)) {
            subscriberMethods.add(subscriberMethod);
        }
    }

    /**
     * Save the subscription event to the map with the event type as the key, used to cancel the subscription
     *
     * @param eventType  Event type
     * @param disposable Subscription event
     */
    private void addSubscriptionToMap(Class eventType, Disposable disposable) {
        List<Disposable> disposables = subscriptionsByEventType.get(eventType);
        if (disposables == null) {
            disposables = new ArrayList<>();
            subscriptionsByEventType.put(eventType, disposables);
        }

        if (!disposables.contains(disposable)) {
            disposables.add(disposable);
        }
    }

    /**
     * Add Subscribers with RxJava
     *
     * @param subscriberMethod d
     */
    @SuppressWarnings("unchecked")
    private void addSubscriber(final SubscriberMethod subscriberMethod) {
        Flowable flowable;
        if (subscriberMethod.code == -1) {
            flowable = toObservable(subscriberMethod.eventType);
        } else {
            flowable = toObservable(subscriberMethod.code, subscriberMethod.eventType);
        }
        Disposable subscription = postToObservable(flowable, subscriberMethod)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) {
                        callEvent(subscriberMethod, o);
                    }
                });

        addSubscriptionToMap(subscriberMethod.subscriber.getClass(), subscription);
    }

    /**
     * Used to process subscription events executed in that thread
     *
     * @param observable       d
     * @param subscriberMethod d
     * @return Observable
     */
    private Flowable postToObservable(Flowable observable, SubscriberMethod subscriberMethod) {
        Scheduler scheduler;
        switch (subscriberMethod.threadMode) {
            case MAIN_THREAD:
                scheduler = AndroidSchedulers.mainThread();
                break;

            case NEW_THREAD:
                scheduler = Schedulers.newThread();
                break;

            case IO_THREAD:
                scheduler = Schedulers.io();
                break;

            case COMPUTATION_THREAD:
                scheduler = Schedulers.computation();
                break;

            case CURRENT_THREAD:
                scheduler = Schedulers.trampoline();
                break;
            default:
                throw new IllegalStateException("Unknown thread mode: " + subscriberMethod.threadMode);
        }
        return observable.observeOn(scheduler);
    }

    /**
     * Callback to subscribers
     *
     * @param method code
     * @param object obj
     */
    private void callEvent(SubscriberMethod method, Object object) {
        Class eventClass = object.getClass();
        List<SubscriberMethod> methods = subscriberMethodByEventType.get(eventClass);
        if (methods != null && methods.size() > 0) {
            for (SubscriberMethod subscriberMethod : methods) {
                Subscribe sub = subscriberMethod.method.getAnnotation(Subscribe.class);
                int c = sub.code();
                if (c == method.code && method.subscriber.equals(subscriberMethod.subscriber) && method.method.equals(subscriberMethod.method)) {
                    subscriberMethod.invoke(object);
                }

            }
        }
    }

    /**
     * Unregister
     *
     * @param subscriber object
     */
    public void unRegister(Object subscriber) {
        List<Class> subscribedTypes = eventTypesBySubscriber.get(subscriber);
        if (subscribedTypes != null) {
            for (Class<?> eventType : subscribedTypes) {
                unSubscribeByEventType(subscriber.getClass());
                unSubscribeMethodByEventType(subscriber, eventType);
            }
            eventTypesBySubscriber.remove(subscriber);
        }
    }

    /**
     * subscriptions unsubscribe
     *
     * @param eventType eventType
     */
    private void unSubscribeByEventType(Class eventType) {
        List<Disposable> disposables = subscriptionsByEventType.get(eventType);
        if (disposables != null) {
            Iterator<Disposable> iterator = disposables.iterator();
            while (iterator.hasNext()) {
                Disposable disposable = iterator.next();
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                    iterator.remove();
                }
            }
        }
    }

    /**
     * Remove the subscriberMethods that correspond to subscribers
     *
     * @param subscriber subscriber
     * @param eventType  eventType
     */
    private void unSubscribeMethodByEventType(Object subscriber, Class eventType) {
        List<SubscriberMethod> subscriberMethods = subscriberMethodByEventType.get(eventType);
        if (subscriberMethods != null) {
            Iterator<SubscriberMethod> iterator = subscriberMethods.iterator();
            while (iterator.hasNext()) {
                SubscriberMethod subscriberMethod = iterator.next();
                if (subscriberMethod.subscriber.equals(subscriber)) {
                    iterator.remove();
                }
            }
        }
    }

    public void send(int code, Object o) {
        bus.onNext(new Message(code, o));
    }

    public void send(Object o) {
        bus.onNext(o);
    }

    public void send(int code) {
        bus.onNext(new Message(code, new BusData()));
    }

    private class Message {
        private int code;
        private Object object;

        public Message() {
        }

        private Message(int code, Object o) {
            this.code = code;
            this.object = o;
        }

        private int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        private Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }
    }
}
