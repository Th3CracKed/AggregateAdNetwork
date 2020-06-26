package com.network.ads.library.admob

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.network.ads.library.common.LibAdListener
import com.network.ads.library.common.InterstitialHelper
import com.google.android.gms.ads.AdRequest


class ADMOBInterstitialAdapter(private val interstitialAd: InterstitialAd) : InterstitialHelper {
    private var isLoading = false;

    override fun init(context: Context) {
        MobileAds.initialize(context)
    }

    override fun loadAd() {
        if (!isLoading && !interstitialAd.isLoaded) {
            interstitialAd.loadAd(AdRequest.Builder().build())
            isLoading = true
        }
    }

    override fun show(): Boolean {
        interstitialAd.show()
        return true
    }

    override fun setAdListener(adListener: LibAdListener) {
        interstitialAd.adListener =  object: AdListener() {
            override fun onAdClosed() {
                adListener.onAdClosed()
            }

            override fun onAdFailedToLoad(err: Int) {
                Log.d("ADMOBInterstitial", err.toString())
                adListener.onAdFailedToLoad()
                isLoading = false
            }

            override fun onAdLeftApplication() {
                adListener.onAdLeftApplication()
            }

            override fun onAdOpened() {
                adListener.onAdOpened()
            }

            override fun onAdLoaded() {
                adListener.onAdLoaded()
                isLoading = false
            }

            override fun onAdClicked() {
                adListener.onAdClicked()
            }

            override fun onAdImpression() {
                adListener.onAdImpression()
            }
        }
    }

    override fun isLoaded(): Boolean {
        return interstitialAd.isLoaded
    }

    override fun destroy() {
    }

}