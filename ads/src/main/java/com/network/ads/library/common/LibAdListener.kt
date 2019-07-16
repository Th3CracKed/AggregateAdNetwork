package com.network.ads.library.common

interface LibAdListener {

    var adListener: LibAdListener

    fun onAdClosed() {
        adListener.onAdClosed()
    }

    fun onAdFailedToLoad() {
        adListener.onAdFailedToLoad()
    }

    fun onAdLeftApplication() {
        adListener.onAdLeftApplication()
    }

    fun onAdOpened() {
        adListener.onAdOpened()
    }

    fun onAdLoaded() {
        adListener.onAdLoaded()
    }

    fun onAdClicked() {
        adListener.onAdClicked()
    }

    fun onAdImpression() {
        adListener.onAdImpression()
    }

}