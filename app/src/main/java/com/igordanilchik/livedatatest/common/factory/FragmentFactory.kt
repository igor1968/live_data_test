package com.igordanilchik.livedatatest.common.factory

import android.os.Bundle
import com.igordanilchik.livedatatest.flows.catalogue.view.CatalogueFragment
import com.igordanilchik.livedatatest.flows.location.view.LocationFragment
import com.igordanilchik.livedatatest.flows.offer.view.OfferFragment
import com.igordanilchik.livedatatest.flows.offers.view.OffersFragment

/**
 * @author Igor Danilchik
 */
object FragmentFactory {

    fun location(): LocationFragment = LocationFragment()

    fun categories(): CatalogueFragment =  CatalogueFragment()

    fun offers(bundle: Bundle): OffersFragment = OffersFragment().apply { arguments = bundle }

    fun offer(bundle: Bundle): OfferFragment = OfferFragment().apply { arguments = bundle }

}