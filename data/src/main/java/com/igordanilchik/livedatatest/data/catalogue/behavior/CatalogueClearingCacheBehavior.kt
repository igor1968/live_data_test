package com.igordanilchik.livedatatest.data.catalogue.behavior

import com.igordanilchik.livedatatest.data.catalogue.local.CatalogueLocalStorage
import com.igordanilchik.livedatatest.data.common.Constants.TimeStampType
import com.igordanilchik.livedatatest.data.timestamp.local.TimeStampLocalStorage

/**
 * @author Igor Danilchik
 */
class CatalogueClearingCacheBehavior(
    private val catalogueLocalStorage: CatalogueLocalStorage,
    private val timeStampLocalStorage: TimeStampLocalStorage
) : CatalogueClearingBehavior {

    override fun clear() {
        catalogueLocalStorage.clear()
        timeStampLocalStorage.removeAllKeys(TimeStampType.CATLOGUE)
    }
}