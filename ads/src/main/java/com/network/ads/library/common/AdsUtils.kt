package com.network.ads.library.common

import android.content.Context
import com.google.android.gms.ads.InterstitialAd
import com.network.ads.library.admob.ADMOBInterstitialAdapter
import com.network.ads.library.fb.FbInterstitialAdapter


object AdsUtils {
    private lateinit var mainInterstitialAd: InterstitialHelper
    private lateinit var secondaryInterstitialAd: InterstitialHelper

    fun initAdNetworks(context: Context, adUnitADMOB: String, adUnitFb: String, priority: Int) {
        val interstitialADMOB = InterstitialAd(context)
        interstitialADMOB.adUnitId = adUnitADMOB
        when (priority) {
            0 -> {
                mainInterstitialAd = ADMOBInterstitialAdapter(interstitialADMOB)
                secondaryInterstitialAd = FbInterstitialAdapter(context, adUnitFb)
            }
            1 -> {
                mainInterstitialAd = FbInterstitialAdapter(context, adUnitFb)
                secondaryInterstitialAd = ADMOBInterstitialAdapter(interstitialADMOB)
            }
            else -> {
                mainInterstitialAd = ADMOBInterstitialAdapter(interstitialADMOB)
                secondaryInterstitialAd = FbInterstitialAdapter(context, adUnitFb)
            }
        }
        loadInterstitial()
    }

    fun showInterstitial(isRunning: Boolean, adListener: LibAdListener) {
        val secondAdListener = object : LibAdListener() {
            override fun onAdClosed() {
                loadInterstitial()
            }
        }
        adListener.secondAdListener = secondAdListener
        mainInterstitialAd.setAdListener(adListener)
        secondaryInterstitialAd.setAdListener(adListener)
        if (isRunning && mainInterstitialAd.isLoaded()) {
            mainInterstitialAd.show()
        } else if (isRunning && secondaryInterstitialAd.isLoaded()) {
            secondaryInterstitialAd.show()
        } else {
            adListener.onAdClosed()
        }
    }

    fun destroy() {
        mainInterstitialAd.destroy()
        secondaryInterstitialAd.destroy()
    }

    private fun loadInterstitial() {
        resetListeners()
        mainInterstitialAd.setAdListener(object : LibAdListener() {
            override fun onAdLoaded() {
                secondaryInterstitialAd.loadAd()
            }

            override fun onAdFailedToLoad() {
                switchNetwork()
                loadInterstitial()
            }
        })
        mainInterstitialAd.loadAd()
    }

    private fun resetListeners() {
        val adListener: LibAdListener = object: LibAdListener(){}
        mainInterstitialAd.setAdListener(adListener)
        secondaryInterstitialAd.setAdListener(adListener)
    }

    private fun switchNetwork() {
        val tmpInterstitial = mainInterstitialAd
        mainInterstitialAd = secondaryInterstitialAd
        secondaryInterstitialAd = tmpInterstitial
    }

}