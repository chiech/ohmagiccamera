package com.irisstudio.demo;

import android.os.AsyncTask;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpRequestTask extends AsyncTask<String, String, Void> {
    private RequestListener mRequestListener;
    String response = null;

    public interface RequestListener {
        void onError();

        void onResponse(String str);
    }

    public HttpRequestTask(RequestListener requestListener) {
        this.mRequestListener = requestListener;
    }

    protected Void doInBackground(String... params) {
        try {
            String url = params[0];
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("application-id", "");
            httpGet.addHeader("secret-key", "");
            httpGet.addHeader("application-type", "REST");
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                this.response = EntityUtils.toString(httpResponse.getEntity());
                Log.i("Interstitial response", this.response);
            } else {
                Log.i("Interstitial response", "Failed to get response");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (this.response == null) {
            this.mRequestListener.onError();
        } else {
            this.mRequestListener.onResponse(this.response);
        }
    }
}
