package com.exigency.exigencycare.fragment_slider;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.exigency.exigencycare.exigencycareapp.R;

public class ProductFragment extends Fragment {
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private Toolbar toolbar;
    private WebView webview;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //txtDone.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
    }// end onResume()-------------


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_fragment, container,
                false);

        initializeView(rootView);

        return rootView;
    }// end onCreateView()-------------------


    /**
     * Find widgets
     *
     * @param view
     */

    private void initializeView(View view) {
        webview = (WebView) view.findViewById(R.id.wvProduct);
        webview.setWebViewClient(new MyWebViewClient());
        openURL();
    }

    /**
     * Opens the URL in a browser
     */
    private void openURL() {
        webview.loadUrl("http://www.exigencycare.com/");
        webview.requestFocus();
    }


}