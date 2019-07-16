package com.network.ads.library.common

import android.content.Context

interface InterstitialHelper {

    fun init(context: Context)

    fun loadAd()

    fun show(): Boolean

    fun setAdListener(adListener: LibAdListener)

    fun isLoaded(): Boolean

    fun destroy()
}