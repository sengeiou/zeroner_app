package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.ImmutableSetMultimap.Builder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.Monitor.Guard;
import com.google.common.util.concurrent.Service.State;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

@Beta
public final class ServiceManager {
    /* access modifiers changed from: private */
    public static final Callback<Listener> HEALTHY_CALLBACK = new Callback<Listener>("healthy()") {
        /* access modifiers changed from: 0000 */
        public void call(Listener listener) {
            listener.healthy();
        }
    };
    /* access modifiers changed from: private */
    public static final Callback<Listener> STOPPED_CALLBACK = new Callback<Listener>("stopped()") {
        /* access modifiers changed from: 0000 */
        public void call(Listener listener) {
            listener.stopped();
        }
    };
    /* access modifiers changed from: private */
    public static final Logger logger = Logger.getLogger(ServiceManager.class.getName());
    private final ImmutableList<Service> services;
    private final ServiceManagerState state;

    private static final class EmptyServiceManagerWarning extends Throwable {
        private EmptyServiceManagerWarning() {
        }
    }

    @Beta
    public static abstract class Listener {
        public void healthy() {
        }

        public void stopped() {
        }

        public void failure(Service service) {
        }
    }

    private static final class NoOpService extends AbstractService {
        private NoOpService() {
        }

        /* access modifiers changed from: protected */
        public void doStart() {
            notifyStarted();
        }

        /* access modifiers changed from: protected */
        public void doStop() {
            notifyStopped();
        }
    }

    private static final class ServiceListener extends com.google.common.util.concurrent.Service.Listener {
        final Service service;
        final WeakReference<ServiceManagerState> state;

        ServiceListener(Service service2, WeakReference<ServiceManagerState> state2) {
            this.service = service2;
            this.state = state2;
        }

        public void starting() {
            ServiceManagerState state2 = (ServiceManagerState) this.state.get();
            if (state2 != null) {
                state2.transitionService(this.service, State.NEW, State.STARTING);
                if (!(this.service instanceof NoOpService)) {
                    ServiceManager.logger.log(Level.FINE, "Starting {0}.", this.service);
                }
            }
        }

        public void running() {
            ServiceManagerState state2 = (ServiceManagerState) this.state.get();
            if (state2 != null) {
                state2.transitionService(this.service, State.STARTING, State.RUNNING);
            }
        }

        public void stopping(State from) {
            ServiceManagerState state2 = (ServiceManagerState) this.state.get();
            if (state2 != null) {
                state2.transitionService(this.service, from, State.STOPPING);
            }
        }

        public void terminated(State from) {
            ServiceManagerState state2 = (ServiceManagerState) this.state.get();
            if (state2 != null) {
                if (!(this.service instanceof NoOpService)) {
                    ServiceManager.logger.log(Level.FINE, "Service {0} has terminated. Previous state was: {1}", new Object[]{this.service, from});
                }
                state2.transitionService(this.service, from, State.TERMINATED);
            }
        }

        public void failed(State from, Throwable failure) {
            ServiceManagerState state2 = (ServiceManagerState) this.state.get();
            if (state2 != null) {
                if (!(this.service instanceof NoOpService)) {
                    ServiceManager.logger.log(Level.SEVERE, "Service " + this.service + " has failed in the " + from + " state.", failure);
                }
                state2.transitionService(this.service, from, State.FAILED);
            }
        }
    }

    private static final class ServiceManagerState {
        final Guard awaitHealthGuard = new Guard(this.monitor) {
            public boolean isSatisfied() {
                return ServiceManagerState.this.states.count(State.RUNNING) == ServiceManagerState.this.numberOfServices || ServiceManagerState.this.states.contains(State.STOPPING) || ServiceManagerState.this.states.contains(State.TERMINATED) || ServiceManagerState.this.states.contains(State.FAILED);
            }
        };
        @GuardedBy("monitor")
        final List<ListenerCallQueue<Listener>> listeners = Collections.synchronizedList(new ArrayList());
        final Monitor monitor = new Monitor();
        final int numberOfServices;
        @GuardedBy("monitor")
        boolean ready;
        @GuardedBy("monitor")
        final SetMultimap<State, Service> servicesByState = Multimaps.newSetMultimap(new EnumMap(State.class), new Supplier<Set<Service>>() {
            public Set<Service> get() {
                return Sets.newLinkedHashSet();
            }
        });
        @GuardedBy("monitor")
        final Map<Service, Stopwatch> startupTimers = Maps.newIdentityHashMap();
        @GuardedBy("monitor")
        final Multiset<State> states = this.servicesByState.keys();
        final Guard stoppedGuard = new Guard(this.monitor) {
            public boolean isSatisfied() {
                return ServiceManagerState.this.states.count(State.TERMINATED) + ServiceManagerState.this.states.count(State.FAILED) == ServiceManagerState.this.numberOfServices;
            }
        };
        @GuardedBy("monitor")
        boolean transitioned;

        ServiceManagerState(ImmutableCollection<Service> services) {
            this.numberOfServices = services.size();
            this.servicesByState.putAll(State.NEW, services);
            Iterator i$ = services.iterator();
            while (i$.hasNext()) {
                this.startupTimers.put((Service) i$.next(), Stopwatch.createUnstarted());
            }
        }

        /* access modifiers changed from: 0000 */
        public void markReady() {
            this.monitor.enter();
            try {
                if (!this.transitioned) {
                    this.ready = true;
                    return;
                }
                List<Service> servicesInBadStates = Lists.newArrayList();
                Iterator i$ = servicesByState().values().iterator();
                while (i$.hasNext()) {
                    Service service = (Service) i$.next();
                    if (service.state() != State.NEW) {
                        servicesInBadStates.add(service);
                    }
                }
                throw new IllegalArgumentException("Services started transitioning asynchronously before the ServiceManager was constructed: " + servicesInBadStates);
            } finally {
                this.monitor.leave();
            }
        }

        /* access modifiers changed from: 0000 */
        public void addListener(Listener listener, Executor executor) {
            Preconditions.checkNotNull(listener, "listener");
            Preconditions.checkNotNull(executor, "executor");
            this.monitor.enter();
            try {
                if (!this.stoppedGuard.isSatisfied()) {
                    this.listeners.add(new ListenerCallQueue(listener, executor));
                }
            } finally {
                this.monitor.leave();
            }
        }

        /* access modifiers changed from: 0000 */
        public void awaitHealthy() {
            this.monitor.enterWhenUninterruptibly(this.awaitHealthGuard);
            try {
                checkHealthy();
            } finally {
                this.monitor.leave();
            }
        }

        /* access modifiers changed from: 0000 */
        public void awaitHealthy(long timeout, TimeUnit unit) throws TimeoutException {
            this.monitor.enter();
            try {
                if (!this.monitor.waitForUninterruptibly(this.awaitHealthGuard, timeout, unit)) {
                    throw new TimeoutException("Timeout waiting for the services to become healthy. The following services have not started: " + Multimaps.filterKeys(this.servicesByState, Predicates.in(ImmutableSet.of(State.NEW, State.STARTING))));
                }
                checkHealthy();
            } finally {
                this.monitor.leave();
            }
        }

        /* access modifiers changed from: 0000 */
        public void awaitStopped() {
            this.monitor.enterWhenUninterruptibly(this.stoppedGuard);
            this.monitor.leave();
        }

        /* access modifiers changed from: 0000 */
        public void awaitStopped(long timeout, TimeUnit unit) throws TimeoutException {
            this.monitor.enter();
            try {
                if (!this.monitor.waitForUninterruptibly(this.stoppedGuard, timeout, unit)) {
                    throw new TimeoutException("Timeout waiting for the services to stop. The following services have not stopped: " + Multimaps.filterKeys(this.servicesByState, Predicates.not(Predicates.in(ImmutableSet.of(State.TERMINATED, State.FAILED)))));
                }
            } finally {
                this.monitor.leave();
            }
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: 0000 */
        public ImmutableMultimap<State, Service> servicesByState() {
            Builder<State, Service> builder = ImmutableSetMultimap.builder();
            this.monitor.enter();
            try {
                for (Entry<State, Service> entry : this.servicesByState.entries()) {
                    if (!(entry.getValue() instanceof NoOpService)) {
                        builder.put(entry.getKey(), entry.getValue());
                    }
                }
                this.monitor.leave();
                return builder.build();
            } catch (Throwable th) {
                this.monitor.leave();
                throw th;
            }
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: 0000 */
        public ImmutableMap<Service, Long> startupTimes() {
            this.monitor.enter();
            try {
                List<Entry<Service, Long>> loadTimes = Lists.newArrayListWithCapacity((this.states.size() - this.states.count(State.NEW)) + this.states.count(State.STARTING));
                for (Entry<Service, Stopwatch> entry : this.startupTimers.entrySet()) {
                    Service service = (Service) entry.getKey();
                    Stopwatch stopWatch = (Stopwatch) entry.getValue();
                    if (!stopWatch.isRunning() && !this.servicesByState.containsEntry(State.NEW, service) && !(service instanceof NoOpService)) {
                        loadTimes.add(Maps.immutableEntry(service, Long.valueOf(stopWatch.elapsed(TimeUnit.MILLISECONDS))));
                    }
                }
                this.monitor.leave();
                Collections.sort(loadTimes, Ordering.natural().onResultOf(new Function<Entry<Service, Long>, Long>() {
                    public Long apply(Entry<Service, Long> input) {
                        return (Long) input.getValue();
                    }
                }));
                ImmutableMap.Builder<Service, Long> builder = ImmutableMap.builder();
                for (Entry<Service, Long> entry2 : loadTimes) {
                    builder.put(entry2);
                }
                return builder.build();
            } catch (Throwable th) {
                this.monitor.leave();
                throw th;
            }
        }

        /* access modifiers changed from: 0000 */
        public void transitionService(Service service, State from, State to) {
            boolean z = true;
            Preconditions.checkNotNull(service);
            if (from == to) {
                z = false;
            }
            Preconditions.checkArgument(z);
            this.monitor.enter();
            try {
                this.transitioned = true;
                if (this.ready) {
                    Preconditions.checkState(this.servicesByState.remove(from, service), "Service %s not at the expected location in the state map %s", service, from);
                    Preconditions.checkState(this.servicesByState.put(to, service), "Service %s in the state map unexpectedly at %s", service, to);
                    Stopwatch stopwatch = (Stopwatch) this.startupTimers.get(service);
                    if (from == State.NEW) {
                        stopwatch.start();
                    }
                    if (to.compareTo(State.RUNNING) >= 0 && stopwatch.isRunning()) {
                        stopwatch.stop();
                        if (!(service instanceof NoOpService)) {
                            ServiceManager.logger.log(Level.FINE, "Started {0} in {1}.", new Object[]{service, stopwatch});
                        }
                    }
                    if (to == State.FAILED) {
                        fireFailedListeners(service);
                    }
                    if (this.states.count(State.RUNNING) == this.numberOfServices) {
                        fireHealthyListeners();
                    } else if (this.states.count(State.TERMINATED) + this.states.count(State.FAILED) == this.numberOfServices) {
                        fireStoppedListeners();
                    }
                    this.monitor.leave();
                    executeListeners();
                }
            } finally {
                this.monitor.leave();
                executeListeners();
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("monitor")
        public void fireStoppedListeners() {
            ServiceManager.STOPPED_CALLBACK.enqueueOn(this.listeners);
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("monitor")
        public void fireHealthyListeners() {
            ServiceManager.HEALTHY_CALLBACK.enqueueOn(this.listeners);
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("monitor")
        public void fireFailedListeners(final Service service) {
            new Callback<Listener>("failed({service=" + service + "})") {
                /* access modifiers changed from: 0000 */
                public void call(Listener listener) {
                    listener.failure(service);
                }
            }.enqueueOn(this.listeners);
        }

        /* access modifiers changed from: 0000 */
        public void executeListeners() {
            Preconditions.checkState(!this.monitor.isOccupiedByCurrentThread(), "It is incorrect to execute listeners with the monitor held.");
            for (int i = 0; i < this.listeners.size(); i++) {
                ((ListenerCallQueue) this.listeners.get(i)).execute();
            }
        }

        /* access modifiers changed from: 0000 */
        @GuardedBy("monitor")
        public void checkHealthy() {
            if (this.states.count(State.RUNNING) != this.numberOfServices) {
                throw new IllegalStateException("Expected to be healthy after starting. The following services are not running: " + Multimaps.filterKeys(this.servicesByState, Predicates.not(Predicates.equalTo(State.RUNNING))));
            }
        }
    }

    public ServiceManager(Iterable<? extends Service> services2) {
        ImmutableList<Service> copy = ImmutableList.copyOf(services2);
        if (copy.isEmpty()) {
            logger.log(Level.WARNING, "ServiceManager configured with no services.  Is your application configured properly?", new EmptyServiceManagerWarning());
            copy = ImmutableList.of(new NoOpService());
        }
        this.state = new ServiceManagerState(copy);
        this.services = copy;
        WeakReference<ServiceManagerState> stateReference = new WeakReference<>(this.state);
        Executor sameThreadExecutor = MoreExecutors.sameThreadExecutor();
        Iterator i$ = copy.iterator();
        while (i$.hasNext()) {
            Service service = (Service) i$.next();
            service.addListener(new ServiceListener(service, stateReference), sameThreadExecutor);
            Preconditions.checkArgument(service.state() == State.NEW, "Can only manage NEW services, %s", service);
        }
        this.state.markReady();
    }

    public void addListener(Listener listener, Executor executor) {
        this.state.addListener(listener, executor);
    }

    public void addListener(Listener listener) {
        this.state.addListener(listener, MoreExecutors.sameThreadExecutor());
    }

    public ServiceManager startAsync() {
        Iterator i$ = this.services.iterator();
        while (i$.hasNext()) {
            Service service = (Service) i$.next();
            State state2 = service.state();
            Preconditions.checkState(state2 == State.NEW, "Service %s is %s, cannot start it.", service, state2);
        }
        Iterator i$2 = this.services.iterator();
        while (i$2.hasNext()) {
            Service service2 = (Service) i$2.next();
            try {
                service2.startAsync();
            } catch (IllegalStateException e) {
                logger.log(Level.WARNING, "Unable to start Service " + service2, e);
            }
        }
        return this;
    }

    public void awaitHealthy() {
        this.state.awaitHealthy();
    }

    public void awaitHealthy(long timeout, TimeUnit unit) throws TimeoutException {
        this.state.awaitHealthy(timeout, unit);
    }

    public ServiceManager stopAsync() {
        Iterator i$ = this.services.iterator();
        while (i$.hasNext()) {
            ((Service) i$.next()).stopAsync();
        }
        return this;
    }

    public void awaitStopped() {
        this.state.awaitStopped();
    }

    public void awaitStopped(long timeout, TimeUnit unit) throws TimeoutException {
        this.state.awaitStopped(timeout, unit);
    }

    public boolean isHealthy() {
        Iterator i$ = this.services.iterator();
        while (i$.hasNext()) {
            if (!((Service) i$.next()).isRunning()) {
                return false;
            }
        }
        return true;
    }

    public ImmutableMultimap<State, Service> servicesByState() {
        return this.state.servicesByState();
    }

    public ImmutableMap<Service, Long> startupTimes() {
        return this.state.startupTimes();
    }

    public String toString() {
        return Objects.toStringHelper(ServiceManager.class).add("services", (Object) Collections2.filter(this.services, Predicates.not(Predicates.instanceOf(NoOpService.class)))).toString();
    }
}
