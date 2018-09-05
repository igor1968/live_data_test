package com.igordanilchik.live_data_test.repo

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


/**
 * @author Igor Danilchik
 */
data class SchedulersSet(
        val ioScheduler: Scheduler,
        val computationScheduler: Scheduler,
        val uiScheduler: Scheduler,
        val immediateScheduler: Scheduler
) {

    companion object {

        @JvmStatic
        fun test(): SchedulersSet = from(Schedulers.trampoline())

        fun from(scheduler: Scheduler): SchedulersSet =
                SchedulersSet(
                        scheduler,
                        scheduler,
                        scheduler,
                        scheduler
                )

    }

}