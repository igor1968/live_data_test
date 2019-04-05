package com.igordanilchik.livedatatest.common.di.feature.timestamp

import com.igordanilchik.livedatatest.common.di.common.Private
import com.igordanilchik.livedatatest.data.timestamp.local.TimeStampLocalStorage
import dagger.Subcomponent

/**
 * @author Igor Danilchik
 */
@Subcomponent(modules = [DefaultTimeStampModule::class])
interface TimeStampComponent {

    @Private
    fun storage(): TimeStampLocalStorage

    @Subcomponent.Builder
    interface Builder {
        fun build(): TimeStampComponent
    }
}