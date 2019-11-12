package mobo.andro.apps.ohmagiccamera;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
//import com.google.android.gms.ads.AdRequest;


public class CustomAds
{
//    public static com.google.android.gms.ads.InterstitialAd mInterstitialAd;
    public static InterstitialAd interstitialAdFacebook;
    public static void facebookAdBanner(final Context context, final LinearLayout adContainer) {
        AudienceNetworkAds.initialize(context);
        AudienceNetworkAds.isInAdsProcess(context);
        AdSettings.addTestDevice("94cfae3d-9ab8-4df2-8c6e-9db88b11e42c");
        AdView adView = new AdView(context, context.getString(R.string.id_b), AdSize.BANNER_HEIGHT_50);
        adContainer.addView(adView);
//        adView.setAdListener(new AdListener() {
//            @Override
//            public void onError(Ad ad, AdError adError) {
//                googleAdBanner(context,adContainer);
//            }
//            @Override
//            public void onAdLoaded(Ad ad) { }
//
//            @Override
//            public void onAdClicked(Ad ad) { }
//
//            @Override
//            public void onLoggingImpression(Ad ad) { }
//        });
        adView.loadAd();
    }
    public static void facebookAdInterstitial(final Context context) {
        AudienceNetworkAds.initialize(context);
        AudienceNetworkAds.isInAdsProcess(context);
        AdSettings.addTestDevice("94cfae3d-9ab8-4df2-8c6e-9db88b11e42c");

        interstitialAdFacebook = new InterstitialAd(context, context.getString(R.string.id_i));
        interstitialAdFacebook.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) { }
            @Override
            public void onInterstitialDismissed(Ad ad) { }
            @Override
            public void onAdLoaded(Ad ad) {
                interstitialAdFacebook.show();
            }
            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("Errorfacebook", " : "+adError.getErrorMessage());
            }

            @Override
            public void onAdClicked(Ad ad) { }
            @Override
            public void onLoggingImpression(Ad ad) { }
        });
        interstitialAdFacebook.loadAd();
    }
//    public static void googleAdBanner(Context context, LinearLayout adContainer){
//        com.google.android.gms.ads.AdView mAdView = new com.google.android.gms.ads.AdView(context);
//        mAdView.setAdSize(com.google.android.gms.ads.AdSize.SMART_BANNER);
//        mAdView.setAdUnitId(context.getString(R.string.id_b_g));
//        adContainer.addView(mAdView);
//        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
//        mAdView.setAdListener(new com.google.android.gms.ads.AdListener() {
//            @Override
//            public void onAdLoaded() { }
//            @Override
//            public void onAdClosed() { }
//            @Override
//            public void onAdFailedToLoad(int errorCode) { }
//            @Override
//            public void onAdLeftApplication() { }
//            @Override
//            public void onAdOpened() { super.onAdOpened(); }
//        });
//        mAdView.loadAd(adRequest);
//    }
    public static void dismissInterstitialFacebook(Context context){
        if(interstitialAdFacebook!=null)
        {
            interstitialAdFacebook.destroy();
        }
    }

    public static void dismissInterstitialGoogle(Context mainActivity) {
    }
}
