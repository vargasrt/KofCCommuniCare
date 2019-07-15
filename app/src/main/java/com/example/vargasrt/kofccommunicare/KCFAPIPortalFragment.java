package com.example.vargasrt.kofccommunicare;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Window;

import static android.view.View.GONE;

public class KCFAPIPortalFragment extends Fragment {

    private WebView wvPortalPage;
    String kcfapiportalurl = "https://www.kcfapionline.org/";
    //String kcfapiportalurl = "http://www.kcfapionline.org/";
    //String kcfapiportalurl = "https://www.google.com";
    public TextView textPortalnointernet;
    WebView webView;

    public static KCFAPIPortalFragment newInstance() {
        return new KCFAPIPortalFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kcfapiportal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //FragmentTransaction ft = getFragmentManager().beginTransaction();
        //ft.detach(this).attach(this).commit();

        /*
        URL url = new URL("https://wikipedia.org");
        URLConnection urlConnection = url.openConnection();
        InputStream in = urlConnection.getInputStream();
        copyInputStreamToOutputStream(in, System.out);
        */

        ConnectivityManager cm = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        wvPortalPage = (WebView) view.findViewById(R.id.wvPortalPage);
        WebSettings settings = wvPortalPage.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        wvPortalPage.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wvPortalPage.setWebViewClient(new MyWebViewClient());
        textPortalnointernet = (TextView) view.findViewById(R.id.portalnointernet);

        if (isConnected) {
            //Toast.makeText(getContext(), "Loading website", Toast.LENGTH_LONG).show();
            textPortalnointernet.setVisibility(GONE);
            wvPortalPage.loadUrl(kcfapiportalurl);
        } else {
            textPortalnointernet.setVisibility(View.VISIBLE);
        }
        /*
        wvPortalPage = (WebView) view.findViewById(R.id.wvPortalPage);
        wvPortalPage.setWebViewClient(new WebViewClient());
        WebSettings settings = wvPortalPage.getSettings();
        settings.setJavaScriptEnabled(true);

        textPortalnointernet = (TextView) view.findViewById(R.id.portalnointernet);

        if (isConnected) {
            //Toast.makeText(getContext(), "Loading website", Toast.LENGTH_LONG).show();
            textPortalnointernet.setVisibility(GONE);
            wvPortalPage.loadUrl(kcfapiportalurl);
        } else {
            textPortalnointernet.setVisibility(View.VISIBLE);
        }
        */
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            wvPortalPage.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            handler.proceed();
        }
    }
}
