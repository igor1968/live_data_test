package com.igordanilchik.live_data_test.common.mvp.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.MvpPresenter;
import com.igordanilchik.live_data_test.common.mvp.view.AppBaseView;
import com.igordanilchik.live_data_test.repo.SchedulersSet;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.CompletableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

import static com.igordanilchik.live_data_test.common.mvp.presenter.AppBasePresenter.ExecuteOn.COMPUTATION_DESTROY;
import static com.igordanilchik.live_data_test.common.mvp.presenter.AppBasePresenter.ExecuteOn.COMPUTATION_DESTROY_DELAY_ERROR;
import static com.igordanilchik.live_data_test.common.mvp.presenter.AppBasePresenter.ExecuteOn.COMPUTATION_DETACH;
import static com.igordanilchik.live_data_test.common.mvp.presenter.AppBasePresenter.ExecuteOn.COMPUTATION_DETACH_DELAY_ERROR;
import static com.igordanilchik.live_data_test.common.mvp.presenter.AppBasePresenter.ExecuteOn.IMMEDIATE_DESTROY;
import static com.igordanilchik.live_data_test.common.mvp.presenter.AppBasePresenter.ExecuteOn.IMMEDIATE_DESTROY_DELAY_ERROR;
import static com.igordanilchik.live_data_test.common.mvp.presenter.AppBasePresenter.ExecuteOn.IMMEDIATE_DETACH;
import static com.igordanilchik.live_data_test.common.mvp.presenter.AppBasePresenter.ExecuteOn.IMMEDIATE_DETACH_DELAY_ERROR;
import static com.igordanilchik.live_data_test.common.mvp.presenter.AppBasePresenter.ExecuteOn.IO_DESTROY;
import static com.igordanilchik.live_data_test.common.mvp.presenter.AppBasePresenter.ExecuteOn.IO_DESTROY_DELAY_ERROR;
import static com.igordanilchik.live_data_test.common.mvp.presenter.AppBasePresenter.ExecuteOn.IO_DETACH;
import static com.igordanilchik.live_data_test.common.mvp.presenter.AppBasePresenter.ExecuteOn.IO_DETACH_DELAY_ERROR;

/**
 * @author Igor Danilchik
 */
public class AppBasePresenter<View extends AppBaseView> extends MvpPresenter<View> {

    private final SchedulersSet schedulersSet;

    private final CompositeDisposable compositeDisposableDetach = new CompositeDisposable();
    private final CompositeDisposable compositeDisposableDestroy = new CompositeDisposable();

    protected AppBasePresenter(@NonNull final SchedulersSet schedulersSet) {
        this.schedulersSet = schedulersSet;
    }

    private void addSubscription(@NonNull final Disposable disposable, final boolean onDestroy) {
        if (onDestroy) {
            compositeDisposableDestroy.add(disposable);
        } else {
            compositeDisposableDetach.add(disposable);
        }
    }

    @Override
    public void detachView(final View view) {
        super.detachView(view);
        compositeDisposableDetach.clear();
    }

    @Override
    public void destroyView(final View view) {
        super.destroyView(view);
        compositeDisposableDetach.clear();
        compositeDisposableDestroy.clear();
    }

    protected void clearSubscription() {
        compositeDisposableDetach.clear();
        compositeDisposableDestroy.clear();
    }

    @IntDef({
            IO_DETACH,
            IO_DETACH_DELAY_ERROR,
            IO_DESTROY,
            IO_DESTROY_DELAY_ERROR,
            COMPUTATION_DETACH,
            COMPUTATION_DETACH_DELAY_ERROR,
            COMPUTATION_DESTROY,
            COMPUTATION_DESTROY_DELAY_ERROR,
            IMMEDIATE_DETACH,
            IMMEDIATE_DETACH_DELAY_ERROR,
            IMMEDIATE_DESTROY,
            IMMEDIATE_DESTROY_DELAY_ERROR
    })
    @Retention(RetentionPolicy.SOURCE)
    protected @interface ExecuteOn {

        /**
         * <pre>
         *  {@code}
         *  subscribeOn(Schedulers.ioScheduler())
         *  observeOn(Schedulers.uiScheduler())
         *  subscribe(... , delayError = false)
         *  {@code}
         *
         *  {@code} compositeDisposable.clear() {@code} @ DetachView
         *  </pre>
         **/
        int IO_DETACH = 1;

        /**
         * <pre>
         *  {@code}
         *  subscribeOn(Schedulers.ioScheduler())
         *  observeOn(Schedulers.uiScheduler())
         *  subscribe(... , delayError = true)
         *  {@code}
         *
         *  {@code} compositeDisposable.clear() {@code} @ DetachView
         *  </pre>
         **/
        int IO_DETACH_DELAY_ERROR = 2;

        /**
         * <pre>
         *  {@code}
         *  subscribeOn(Schedulers.ioScheduler())
         *  observeOn(Schedulers.uiScheduler())
         *  subscribe(... , delayError = false)
         *  {@code}
         *
         *  {@code} compositeDisposable.clear() {@code} @ DestroyView
         *  </pre>
         **/
        int IO_DESTROY = 3;

        /**
         * <pre>
         *  {@code}
         *  subscribeOn(Schedulers.ioScheduler())
         *  observeOn(Schedulers.uiScheduler())
         *  subscribe(... , delayError = true)
         *  {@code}
         *
         *  {@code} compositeDisposable.clear() {@code} @ DestroyView
         *  </pre>
         **/
        int IO_DESTROY_DELAY_ERROR = 4;

        /**
         * <pre>
         *  {@code}
         *  subscribeOn(Schedulers.computationScheduler())
         *  observeOn(Schedulers.uiScheduler())
         *  subscribe(... , delayError = false)
         *  {@code}
         *
         *  {@code} compositeDisposable.clear() {@code} @ DetachView
         *  </pre>
         **/
        int COMPUTATION_DETACH = 5;

        /**
         * <pre>
         *  {@code}
         *  subscribeOn(Schedulers.computationScheduler())
         *  observeOn(Schedulers.uiScheduler())
         *  subscribe(... , delayError = true)
         *  {@code}
         *
         *  {@code} compositeDisposable.clear() {@code} @ DetachView
         *  </pre>
         **/
        int COMPUTATION_DETACH_DELAY_ERROR = 6;

        /**
         * <pre>
         *  {@code}
         *  subscribeOn(Schedulers.computationScheduler())
         *  observeOn(Schedulers.uiScheduler())
         *  subscribe(... , delayError = false)
         *  {@code}
         *
         *  {@code} compositeDisposable.clear() {@code} @ DestroyView
         *  </pre>
         **/
        int COMPUTATION_DESTROY = 7;

        /**
         * <pre>
         *  {@code}
         *  subscribeOn(Schedulers.computationScheduler())
         *  observeOn(Schedulers.uiScheduler())
         *  subscribe(... , delayError = true)
         *  {@code}
         *
         *  {@code} compositeDisposable.clear() {@code} @ DestroyView
         *  </pre>
         **/
        int COMPUTATION_DESTROY_DELAY_ERROR = 8;

        /**
         * <pre>
         *  {@code}
         *  subscribeOn(Schedulers.immediateScheduler())
         *  observeOn(Schedulers.immediateScheduler())
         *  subscribe(... , delayError = false)
         *  {@code}
         *
         *  {@code} compositeDisposable.clear() {@code} @ DetachView
         *  </pre>
         **/
        int IMMEDIATE_DETACH = 9;

        /**
         * <pre>
         *  {@code}
         *  subscribeOn(Schedulers.immediateScheduler())
         *  observeOn(Schedulers.immediateScheduler())
         *  subscribe(... , delayError = true)
         *  {@code}
         *
         *  {@code} compositeDisposable.clear() {@code} @ DetachView
         *  </pre>
         **/
        int IMMEDIATE_DETACH_DELAY_ERROR = 10;

        /**
         * <pre>
         *  {@code}
         *  subscribeOn(Schedulers.immediateScheduler())
         *  observeOn(Schedulers.immediateScheduler())
         *  subscribe(... , delayError = false)
         *  {@code}
         *
         *  {@code} compositeDisposable.clear() {@code} @ DestroyView
         *  </pre>
         **/
        int IMMEDIATE_DESTROY = 11;

        /**
         * <pre>
         *  {@code
         *  subscribeOn(Schedulers.immediateScheduler())
         *  observeOn(Schedulers.immediateScheduler())
         *  subscribe(... , delayError = true)
         *  }
         *
         *  {@code compositeDisposable.clear() } @ DestroyView
         *  </pre>
         **/
        int IMMEDIATE_DESTROY_DELAY_ERROR = 12;
    }

    protected <RESULT> Disposable executeOn(
            @ExecuteOn final int executeOn,
            @NonNull final Observable<RESULT> observable,
            @NonNull final Consumer<RESULT> onNextAction,
            @NonNull final Consumer<Throwable> onErrorAction,
            @NonNull final Action onCompleteAction,
            @NonNull final ObservableTransformer<RESULT, RESULT> onSchedulersApplied
    ) {
        return execute(
                executeOn,
                observable,
                onNextAction,
                onErrorAction,
                onCompleteAction,
                onSchedulersApplied
        );
    }

    protected <RESULT> Disposable executeOn(
            @ExecuteOn final int executeOn,
            @NonNull final Observable<RESULT> observable,
            @NonNull final Consumer<RESULT> onNextAction,
            @NonNull final Consumer<Throwable> onErrorAction,
            @NonNull final Action onCompleteAction
    ) {
        return execute(
                executeOn,
                observable,
                onNextAction,
                onErrorAction,
                onCompleteAction,
                o -> o
        );
    }

    protected <RESULT> Disposable executeOn(
            @ExecuteOn final int executeOn,
            @NonNull final Observable<RESULT> observable,
            @NonNull final Consumer<RESULT> onNextAction,
            @NonNull final Consumer<Throwable> onErrorAction
    ) {
        return executeOn(
                executeOn,
                observable,
                onNextAction,
                onErrorAction,
                () -> {
                },
                (o) -> o
        );
    }

    protected <RESULT> Disposable executeOn(
            @ExecuteOn final int executeOn,
            @NonNull final Observable<RESULT> observable,
            @NonNull final Consumer<RESULT> onNextAction
    ) {
        return executeOn(
                executeOn,
                observable,
                onNextAction,
                (t) -> {
                },
                () -> {
                },
                (o) -> o
        );
    }

    protected <RESULT> Disposable executeOn(
            @ExecuteOn final int executeOn,
            @NonNull final Observable<RESULT> observable,
            @NonNull final Consumer<RESULT> onNextAction,
            @NonNull final Consumer<Throwable> onErrorAction,
            @NonNull final ObservableTransformer<RESULT, RESULT> onSchedulersApplied
    ) {

        return executeOn(
                executeOn,
                observable,
                onNextAction,
                onErrorAction,
                () -> {
                },
                onSchedulersApplied
        );
    }

    protected Disposable executeOn(
            @ExecuteOn final int executeOn,
            @NonNull final Completable completable,
            @NonNull final Consumer<Throwable> onErrorAction,
            @NonNull final Action onCompleteAction,
            @NonNull final CompletableTransformer onSchedulersApplied
    ) {

        final boolean clearOnDestroy = isClearCompositeSubscriptionOnDestroy(executeOn);

        final Disposable disposable = completable
                .compose(applyCompletableSchedulers(executeOn))
                .compose(onSchedulersApplied)
                .subscribe(
                        onCompleteAction,
                        throwable -> {
                            Timber.e(throwable);
                            onErrorAction.accept(throwable);
                        }
                );

        addSubscription(disposable, clearOnDestroy);

        return disposable;
    }

    protected Disposable executeOn(
            @ExecuteOn final int executeOn,
            @NonNull final Completable completable,
            @NonNull final Consumer<Throwable> onErrorAction,
            @NonNull final Action onCompleteAction
    ) {

        return executeOn(
                executeOn,
                completable,
                onErrorAction,
                onCompleteAction,
                c -> c
        );
    }

    protected Disposable executeOn(
            @ExecuteOn final int executeOn,
            @NonNull final Completable completable,
            @NonNull final Action onCompleteAction
    ) {

        return executeOn(
                executeOn,
                completable,
                t -> {
                },
                onCompleteAction,
                c -> c
        );
    }

    protected Disposable executeOn(
            @ExecuteOn final int executeOn,
            @NonNull final Completable completable,
            @NonNull final Consumer<Throwable> onErrorAction
    ) {

        return executeOn(
                executeOn,
                completable,
                onErrorAction,
                () -> {
                },
                c -> c
        );
    }

    protected Disposable executeOn(
            @ExecuteOn final int executeOn,
            @NonNull final Completable completable
    ) {

        return executeOn(
                executeOn,
                completable,
                t -> {
                },
                () -> {
                },
                c -> c
        );
    }

    protected <RESULT> Disposable executeOn(
            @ExecuteOn final int executeOn,
            @NonNull final Single<RESULT> single,
            @NonNull final Consumer<RESULT> onSuccess,
            @NonNull final Consumer<Throwable> onErrorAction,
            @NonNull final SingleTransformer<RESULT, RESULT> onSchedulersApplied
    ) {

        final boolean clearOnDestroy = isClearCompositeSubscriptionOnDestroy(executeOn);

        final Disposable disposable = single
                .compose(applySingleSchedulers(executeOn))
                .compose(onSchedulersApplied)
                .subscribe(
                        onSuccess,
                        throwable -> {
                            Timber.e(throwable);
                            onErrorAction.accept(throwable);
                        }
                );

        addSubscription(disposable, clearOnDestroy);

        return disposable;
    }

    protected <RESULT> Disposable executeOn(
            @ExecuteOn final int executeOn,
            @NonNull final Single<RESULT> single,
            @NonNull final Consumer<RESULT> onSuccess,
            @NonNull final Consumer<Throwable> onErrorAction
    ) {

        return executeOn(
                executeOn,
                single,
                onSuccess,
                onErrorAction,
                s -> s
        );
    }

    protected <RESULT> Disposable executeOn(
            @ExecuteOn final int executeOn,
            @NonNull final Single<RESULT> single,
            @NonNull final Consumer<RESULT> onSuccess
    ) {

        return executeOn(
                executeOn,
                single,
                onSuccess,
                t -> {
                },
                s -> s
        );
    }

    private <RESULT> Disposable execute(
            @ExecuteOn final int executeOn,
            @NonNull final Observable<RESULT> observable,
            @NonNull final Consumer<RESULT> onNextAction,
            @NonNull final Consumer<Throwable> onErrorAction,
            @NonNull final Action onCompleteAction,
            @NonNull final ObservableTransformer<RESULT, RESULT> onSchedulersApplied
    ) {

        final boolean clearOnDestroy = isClearCompositeSubscriptionOnDestroy(executeOn);

        final Disposable disposable = observable
                .compose(applyObservableSchedulers(executeOn))
                .compose(onSchedulersApplied)
                .subscribe(
                        onNextAction,
                        throwable -> {
                            Timber.e(throwable);
                            onErrorAction.accept(throwable);
                        },
                        onCompleteAction
                );

        addSubscription(disposable, clearOnDestroy);

        return disposable;
    }

    private Scheduler getSubscribeScheduler(@ExecuteOn final int executeOn) {
        final Scheduler scheduler;

        switch (executeOn) {
            case IO_DETACH:
            case IO_DETACH_DELAY_ERROR:
            case IO_DESTROY:
            case IO_DESTROY_DELAY_ERROR:
                scheduler = schedulersSet.getIoScheduler();
                break;
            case COMPUTATION_DETACH:
            case COMPUTATION_DETACH_DELAY_ERROR:
            case COMPUTATION_DESTROY:
            case COMPUTATION_DESTROY_DELAY_ERROR:
                scheduler = schedulersSet.getComputationScheduler();
                break;
            case IMMEDIATE_DETACH:
            case IMMEDIATE_DETACH_DELAY_ERROR:
            case IMMEDIATE_DESTROY:
            case IMMEDIATE_DESTROY_DELAY_ERROR:
            default:
                scheduler = schedulersSet.getImmediateScheduler();
                break;
        }
        return scheduler;
    }

    @SuppressLint("SwitchIntDef")
    private Scheduler getObserveScheduler(@ExecuteOn final int executeOn) {
        final Scheduler scheduler;

        switch (executeOn) {
            case IMMEDIATE_DETACH:
            case IMMEDIATE_DETACH_DELAY_ERROR:
            case IMMEDIATE_DESTROY:
            case IMMEDIATE_DESTROY_DELAY_ERROR:
                scheduler = schedulersSet.getImmediateScheduler();
                break;
            default:
                scheduler = schedulersSet.getUiScheduler();
                break;
        }
        return scheduler;
    }

    @SuppressLint("SwitchIntDef")
    private boolean isDelayError(@ExecuteOn final int executeOn) {
        final boolean delayError;

        switch (executeOn) {
            case IO_DETACH_DELAY_ERROR:
            case IO_DESTROY_DELAY_ERROR:
            case COMPUTATION_DETACH_DELAY_ERROR:
            case COMPUTATION_DESTROY_DELAY_ERROR:
            case IMMEDIATE_DETACH_DELAY_ERROR:
            case IMMEDIATE_DESTROY_DELAY_ERROR:
                delayError = true;
                break;
            default:
                delayError = false;
                break;
        }
        return delayError;
    }

    @SuppressLint("SwitchIntDef")
    private boolean isClearCompositeSubscriptionOnDestroy(@ExecuteOn final int executeOn) {
        final boolean clearOnDestroy;

        switch (executeOn) {
            case IO_DESTROY:
            case IO_DESTROY_DELAY_ERROR:
            case COMPUTATION_DESTROY:
            case COMPUTATION_DESTROY_DELAY_ERROR:
            case IMMEDIATE_DESTROY:
            case IMMEDIATE_DESTROY_DELAY_ERROR:
                clearOnDestroy = true;
                break;
            default:
                clearOnDestroy = false;
                break;
        }
        return clearOnDestroy;
    }

    private <T> ObservableTransformer<T, T> applyObservableSchedulers(@ExecuteOn final int executeOn) {
        return observable -> observable.subscribeOn(getSubscribeScheduler(executeOn))
                .observeOn(getObserveScheduler(executeOn), isDelayError(executeOn));
    }

    private <T> SingleTransformer<T, T> applySingleSchedulers(@ExecuteOn final int executeOn) {
        return single -> single.subscribeOn(getSubscribeScheduler(executeOn))
                .observeOn(getObserveScheduler(executeOn));
    }

    private CompletableTransformer applyCompletableSchedulers(@ExecuteOn final int executeOn) {
        return completable -> completable.subscribeOn(getSubscribeScheduler(executeOn))
                .observeOn(getObserveScheduler(executeOn));
    }
}
