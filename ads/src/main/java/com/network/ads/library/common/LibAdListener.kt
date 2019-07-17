package com.network.ads.library.common

abstract class LibAdListener {

    var secondAdListener: LibAdListener? = null

    open fun onAdClosed() {
        secondAdListener?.onAdClosed()
    }

    open fun onAdFailedToLoad() {
        secondAdListener?.onAdFailedToLoad()
    }

    open fun onAdLeftApplication() {
        secondAdListener?.onAdLeftApplication()
    }

    open fun onAdOpened() {
        secondAdListener?.onAdOpened()
    }

    open fun onAdLoaded() {
        secondAdListener?.onAdLoaded()
    }

    open fun onAdClicked() {
        secondAdListener?.onAdClicked()
    }

    open fun onAdImpression() {
        secondAdListener?.onAdImpression()
    }

}