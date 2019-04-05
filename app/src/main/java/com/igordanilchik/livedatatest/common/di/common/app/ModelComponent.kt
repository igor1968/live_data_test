package com.igordanilchik.livedatatest.common.di.common.app

import dagger.Subcomponent

/**
 * @author Igor Danilchik
 */
@Subcomponent(modules = [ModelModule::class])
interface ModelComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): ModelComponent
    }

}