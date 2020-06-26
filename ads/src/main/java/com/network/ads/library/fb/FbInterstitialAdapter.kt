package com.network.ads.library.fb

import android.content.Context
import android.util.Log
import com.facebook.ads.AudienceNetworkAds
import com.facebook.ads.InterstitialAd
import com.network.ads.library.common.InterstitialHelper
import com.network.ads.library.common.LibAdListener
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.InterstitialAdListener





class FbInterstitialAdapter(private val context: Context, private val interstitialAdUnit: String) : InterstitialHelper {

    private lateinit var interstitialAd: InterstitialAd
    private lateinit var  fbAdListener: LibAdListener
    private var isLoading = false

    override fun init(context: Context) {
        if(AudienceNetworkAds.isInAdsProcess(context) || AudienceNetworkAds.isInitialized(context)) {
            return;
        }
        AudienceNetworkAds.initialize(context);
    }

    override fun loadAd() {
        if( !isLoading && !isLoaded() ) {
            destroy();
            initInterstitial();
            setupListener();
            interstitialAd.loadAd()
            isLoading = true;
        }
    }

    override fun show(): Boolean {
        return interstitialAd.show();
    }

    override fun setAdListener(adListener: LibAdListener) {
        this.fbAdListener = adListener;
        setupListener();
    }

    override fun isLoaded(): Boolean {
        return interstitialAd.isAdLoaded && !interstitialAd.isAdInvalidated();
    }

    override fun destroy() {
        interstitialAd.destroy();
    }

    private fun setupListener() {
        interstitialAd.setAdListener(object : InterstitialAdListener {
            override fun onInterstitialDisplayed(ad: Ad) {
                fbAdListener.onAdOpened()
            }

            override fun onInterstitialDismissed(ad: Ad) {
                fbAdListener.onAdClosed()
            }

            override fun onError(ad: Ad, adError: AdError) {
                Log.d(
                    "FBInterstitial",
                    "Ad toString = " + ad.toString() + " \n AdError getErrorMessage = " + adError.errorMessage
                )
                fbAdListener.onAdFailedToLoad()
                isLoading = false
            }

            override fun onAdLoaded(ad: Ad) {
                fbAdListener.onAdLoaded()
                isLoading = false
            }

            override fun onAdClicked(ad: Ad) {
                fbAdListener.onAdClicked()
            }

            override fun onLoggingImpression(ad: Ad) {
                fbAdListener.onAdImpression()
            }
        })
    }

    private fun initInterstitial() {
        this.interstitialAd = InterstitialAd(context, interstitialAdUnit)
    }
}