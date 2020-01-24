package com.google.common.eventbus;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

@Beta
public class AsyncEventBus extends EventBus {
    private final ConcurrentLinkedQueue<EventWithSubscriber> eventsToDispatch = new ConcurrentLinkedQueue<>();
    private final Executor executor;

    public AsyncEventBus(String identifier, Executor executor2) {
        super(identifier);
        this.executor = (Executor) Preconditions.checkNotNull(executor2);
    }

    public AsyncEventBus(Executor executor2, SubscriberExceptionHandler subscriberExceptionHandler) {
        super(subscriberExceptionHandler);
        this.executor = (Executor) Preconditions.checkNotNull(executor2);
    }

    public AsyncEventBus(Executor executor2) {
        super("default");
        this.executor = (Executor) Preconditions.checkNotNull(executor2);
    }

    /* access modifiers changed from: 0000 */
    public void enqueueEvent(Object event, EventSubscriber subscriber) {
        this.eventsToDispatch.offer(new EventWithSubscriber(event, subscriber));
    }

    /* access modifiers changed from: protected */
    public void dispatchQueuedEvents() {
        while (true) {
            EventWithSubscriber eventWithSubscriber = (EventWithSubscriber) this.eventsToDispatch.poll();
            if (eventWithSubscriber != null) {
                dispatch(eventWithSubscriber.event, eventWithSubscriber.subscriber);
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatch(final Object event, final EventSubscriber subscriber) {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(subscriber);
        this.executor.execute(new Runnable() {
            public void run() {
                AsyncEventBus.super.dispatch(event, subscriber);
            }
        });
    }
}
