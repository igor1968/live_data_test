package com.igordanilchik.livedatatest.common.mvvm.view_model

import androidx.annotation.IntDef
import androidx.lifecycle.ViewModel
import com.igordanilchik.livedatatest.common.mvvm.SchedulersSet
import com.igordanilchik.livedatatest.common.mvvm.view_model.BaseViewModel.ExecuteOn.Companion.COMPUTATION
import com.igordanilchik.livedatatest.common.mvvm.view_model.BaseViewModel.ExecuteOn.Companion.COMPUTATION_DELAY_ERROR
import com.igordanilchik.livedatatest.common.mvvm.view_model.BaseViewModel.ExecuteOn.Companion.IMMEDIATE
import com.igordanilchik.livedatatest.common.mvvm.view_model.BaseViewModel.ExecuteOn.Companion.IMMEDIATE_DELAY_ERROR
import com.igordanilchik.livedatatest.common.mvvm.view_model.BaseViewModel.ExecuteOn.Companion.IO
import com.igordanilchik.livedatatest.common.mvvm.view_model.BaseViewModel.ExecuteOn.Companion.IO_DELAY_ERROR
import com.igordanilchik.livedatatest.data.common.logger.CapLogger
import io.reactivex.Completable
import io.reactivex.CompletableTransformer
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import timber.log.Timber

/**
 * @author Igor Danilchik
 */
open class BaseViewModel(
    private val schedulersSet: SchedulersSet
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    @IntDef(
        IO,
        IO_DELAY_ERROR,
        COMPUTATION,
        COMPUTATION_DELAY_ERROR,
        IMMEDIATE,
        IMMEDIATE_DELAY_ERROR
    )
    @Retention(AnnotationRetention.SOURCE)
    protected annotation class ExecuteOn {
        companion object {

            /**
             * <pre>
             * ``
             * subscribeOn(Schedulers.ioScheduler())
             * observeOn(Schedulers.uiScheduler())
             * subscribe(... , delayError = false)
             * ``
             *
             * `` compositeDisposable.clear() `` @ onCleared
            </pre> *
             */
            const val IO = 1

            /**
             * <pre>
             * ``
             * subscribeOn(Schedulers.ioScheduler())
             * observeOn(Schedulers.uiScheduler())
             * subscribe(... , delayError = true)
             * ``
             *
             * `` compositeDisposable.clear() `` @ onCleared
            </pre> *
             */
            const val IO_DELAY_ERROR = 2

            /**
             * <pre>
             * ``
             * subscribeOn(Schedulers.computationScheduler())
             * observeOn(Schedulers.uiScheduler())
             * subscribe(... , delayError = false)
             * ``
             *
             * `` compositeDisposable.clear() `` @ onCleared
            </pre> *
             */
            const val COMPUTATION = 3

            /**
             * <pre>
             * ``
             * subscribeOn(Schedulers.computationScheduler())
             * observeOn(Schedulers.uiScheduler())
             * subscribe(... , delayError = true)
             * ``
             *
             * `` compositeDisposable.clear() `` @ onCleared
            </pre> *
             */
            const val COMPUTATION_DELAY_ERROR = 4

            /**
             * <pre>
             * ``
             * subscribeOn(Schedulers.immediateScheduler())
             * observeOn(Schedulers.immediateScheduler())
             * subscribe(... , delayError = false)
             * ``
             *
             * `` compositeDisposable.clear() `` @ onCleared
            </pre> *
             */
            const val IMMEDIATE = 5

            /**
             * <pre>
             * ``
             * subscribeOn(Schedulers.immediateScheduler())
             * observeOn(Schedulers.immediateScheduler())
             * subscribe(... , delayError = true)
             * ``
             *
             * `` compositeDisposable.clear() `` @ onCleared
            </pre> *
             */
            const val IMMEDIATE_DELAY_ERROR = 6
        }
    }

    protected fun <RESULT> executeOn(
        @ExecuteOn executeOn: Int,
        observable: Observable<RESULT>,
        onNextAction: Consumer<RESULT>,
        onErrorAction: Consumer<Throwable>,
        onCompleteAction: Action,
        onSchedulersApplied: ObservableTransformer<RESULT, RESULT>
    ): Disposable {
        return execute(
            executeOn,
            observable,
            onNextAction,
            onErrorAction,
            onCompleteAction,
            onSchedulersApplied
        )
    }

    protected fun <RESULT> executeOn(
        @ExecuteOn executeOn: Int,
        observable: Observable<RESULT>,
        onNextAction: Consumer<RESULT>,
        onErrorAction: Consumer<Throwable>,
        onCompleteAction: Action
    ): Disposable {
        return execute(
            executeOn,
            observable,
            onNextAction,
            onErrorAction,
            onCompleteAction,
            ObservableTransformer { o -> o }
        )
    }

    protected fun <RESULT> executeOn(
        @ExecuteOn executeOn: Int,
        observable: Observable<RESULT>,
        onNextAction: Consumer<RESULT>,
        onErrorAction: Consumer<Throwable>
    ): Disposable {
        return executeOn(
            executeOn,
            observable,
            onNextAction,
            onErrorAction,
            Action { },
            ObservableTransformer { o -> o }
        )
    }

    protected fun <RESULT> executeOn(
        @ExecuteOn executeOn: Int,
        observable: Observable<RESULT>,
        onNextAction: Consumer<RESULT>
    ): Disposable {
        return executeOn(
            executeOn,
            observable,
            onNextAction,
            Consumer { t -> },
            Action { },
            ObservableTransformer { o -> o }
        )
    }

    protected fun <RESULT> executeOn(
        @ExecuteOn executeOn: Int,
        observable: Observable<RESULT>,
        onNextAction: Consumer<RESULT>,
        onErrorAction: Consumer<Throwable>,
        onSchedulersApplied: ObservableTransformer<RESULT, RESULT>
    ): Disposable {

        return executeOn(
            executeOn,
            observable,
            onNextAction,
            onErrorAction,
            Action { },
            onSchedulersApplied
        )
    }

    protected fun executeOn(
        @ExecuteOn executeOn: Int,
        completable: Completable,
        onErrorAction: Consumer<Throwable>,
        onCompleteAction: Action,
        onSchedulersApplied: CompletableTransformer
    ): Disposable {

        val disposable = completable
            .compose(applyCompletableSchedulers(executeOn))
            .compose(onSchedulersApplied)
            .subscribe(
                onCompleteAction,
                Consumer { throwable ->
                    CapLogger.e(throwable)
                    onErrorAction.accept(throwable)
                }
            )

        addSubscription(disposable)

        return disposable
    }

    protected fun executeOn(
        @ExecuteOn executeOn: Int,
        completable: Completable,
        onErrorAction: Consumer<Throwable>,
        onCompleteAction: Action
    ): Disposable {

        return executeOn(
            executeOn,
            completable,
            onErrorAction,
            onCompleteAction,
            CompletableTransformer { c -> c }
        )
    }

    protected fun executeOn(
        @ExecuteOn executeOn: Int,
        completable: Completable,
        onCompleteAction: Action
    ): Disposable {

        return executeOn(
            executeOn,
            completable,
            Consumer { t -> },
            onCompleteAction,
            CompletableTransformer { c -> c }
        )
    }

    protected fun executeOn(
        @ExecuteOn executeOn: Int,
        completable: Completable,
        onErrorAction: Consumer<Throwable>
    ): Disposable {

        return executeOn(
            executeOn,
            completable,
            onErrorAction,
            Action { },
            CompletableTransformer { c -> c }
        )
    }

    protected fun executeOn(
        @ExecuteOn executeOn: Int,
        completable: Completable
    ): Disposable {

        return executeOn(
            executeOn,
            completable,
            Consumer { t -> },
            Action { },
            CompletableTransformer { c -> c }
        )
    }

    protected fun <RESULT> executeOn(
        @ExecuteOn executeOn: Int,
        single: Single<RESULT>,
        onSuccess: Consumer<RESULT>,
        onErrorAction: Consumer<Throwable>,
        onSchedulersApplied: SingleTransformer<RESULT, RESULT>
    ): Disposable {

        val disposable = single
            .compose(applySingleSchedulers(executeOn))
            .compose(onSchedulersApplied)
            .subscribe(
                onSuccess,
                Consumer { throwable ->
                    CapLogger.e(throwable)
                    onErrorAction.accept(throwable)
                }
            )

        addSubscription(disposable)

        return disposable
    }

    protected fun <RESULT> executeOn(
        @ExecuteOn executeOn: Int,
        single: Single<RESULT>,
        onSuccess: Consumer<RESULT>,
        onErrorAction: Consumer<Throwable>
    ): Disposable {

        return executeOn<RESULT>(
            executeOn,
            single,
            onSuccess,
            onErrorAction,
            SingleTransformer { s -> s }
        )
    }

    protected fun <RESULT> executeOn(
        @ExecuteOn executeOn: Int,
        single: Single<RESULT>,
        onSuccess: Consumer<RESULT>
    ): Disposable {

        return executeOn<RESULT>(
            executeOn,
            single,
            onSuccess,
            Consumer { t -> },
            SingleTransformer { s -> s }
        )
    }

    private fun <RESULT> execute(
        @ExecuteOn executeOn: Int,
        observable: Observable<RESULT>,
        onNextAction: Consumer<RESULT>,
        onErrorAction: Consumer<Throwable>,
        onCompleteAction: Action,
        onSchedulersApplied: ObservableTransformer<RESULT, RESULT>
    ): Disposable {

        val disposable = observable
            .compose(applyObservableSchedulers(executeOn))
            .compose(onSchedulersApplied)
            .subscribe(
                onNextAction,
                Consumer { throwable ->
                    Timber.e(throwable)
                    onErrorAction.accept(throwable)
                },
                onCompleteAction
            )

        addSubscription(disposable)

        return disposable
    }

    private fun getSubscribeScheduler(@ExecuteOn executeOn: Int): Scheduler =
        when (executeOn) {
            IO,
            IO_DELAY_ERROR -> schedulersSet.ioScheduler
            COMPUTATION,
            COMPUTATION_DELAY_ERROR -> schedulersSet.computationScheduler
            IMMEDIATE,
            IMMEDIATE_DELAY_ERROR -> schedulersSet.immediateScheduler
            else -> schedulersSet.immediateScheduler
        }

    private fun getObserveScheduler(@ExecuteOn executeOn: Int): Scheduler =
        when (executeOn) {
            IMMEDIATE,
            IMMEDIATE_DELAY_ERROR -> schedulersSet.immediateScheduler
            else -> schedulersSet.uiScheduler
        }

    private fun isDelayError(@ExecuteOn executeOn: Int): Boolean =
        when (executeOn) {
            IO_DELAY_ERROR,
            COMPUTATION_DELAY_ERROR,
            IMMEDIATE_DELAY_ERROR -> true
            else -> false
        }

    private fun <T> applyObservableSchedulers(@ExecuteOn executeOn: Int): ObservableTransformer<T, T> =
        ObservableTransformer { observable ->
            observable.subscribeOn(getSubscribeScheduler(executeOn))
                .observeOn(getObserveScheduler(executeOn), isDelayError(executeOn))
        }

    private fun <T> applySingleSchedulers(@ExecuteOn executeOn: Int): SingleTransformer<T, T> =
        SingleTransformer { single ->
            single.subscribeOn(getSubscribeScheduler(executeOn))
                .observeOn(getObserveScheduler(executeOn))
        }

    private fun applyCompletableSchedulers(@ExecuteOn executeOn: Int): CompletableTransformer =
        CompletableTransformer { completable ->
            completable.subscribeOn(getSubscribeScheduler(executeOn))
                .observeOn(getObserveScheduler(executeOn))
        }
}