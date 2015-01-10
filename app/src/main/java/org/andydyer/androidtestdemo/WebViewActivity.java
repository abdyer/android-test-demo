package org.andydyer.androidtestdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by andy on 8/23/14.
 */
public class WebViewActivity extends ActionBarActivity {

    public static final String EXTRA_URL = "url";
    public static final String EXTRA_TITLE = "title";

    @InjectView(R.id.webview) WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.inject(this);
        getSupportActionBar().setTitle(getIntent().getStringExtra(EXTRA_TITLE));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadUrl();
    }

    private void loadUrl() {
        String url = getIntent().getStringExtra(EXTRA_URL);
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        webview.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
