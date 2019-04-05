package com.igordanilchik.livedatatest.data.common

import com.igordanilchik.livedatatest.data.common.annotations.CapByteDef
import com.igordanilchik.livedatatest.data.common.annotations.CapIntDef
import com.igordanilchik.livedatatest.data.common.annotations.CapStringDef
import java.util.Calendar

/**
 * @author Igor Danilchik
 */
interface Constants {

    @CapIntDef(
        CatalogueLoadingBehaviorType.LOCAL,
        CatalogueLoadingBehaviorType.THROTTLING,
        CatalogueLoadingBehaviorType.FORCE_REFRESH
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class CatalogueLoadingBehaviorType {
        companion object {

            const val LOCAL = 1
            const val THROTTLING = 2
            const val FORCE_REFRESH = 3
        }
    }

    @CapStringDef(
        TimeStampType.CATLOGUE
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class TimeStampType {
        companion object {

            const val CATLOGUE = "catalogue_timestamp"
        }
    }

    @CapByteDef(
        ValidationTimeType.VALIDATION_TIME_HOURS,
        ValidationTimeType.VALIDATION_TIME_MINUTES,
        ValidationTimeType.VALIDATION_TIME_SECONDS
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class ValidationTimeType {
        companion object {

            const val VALIDATION_TIME_HOURS = Calendar.HOUR_OF_DAY.toByte()
            const val VALIDATION_TIME_MINUTES = Calendar.MINUTE.toByte()
            const val VALIDATION_TIME_SECONDS = Calendar.SECOND.toByte()
        }
    }

    interface RequestThrottling {
        companion object {

            const val DEFAULT_CATALOGUE_THROTTLING_TIME_IN_SEC = 30
        }
    }
}
