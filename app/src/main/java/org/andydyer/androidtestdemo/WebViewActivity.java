package org.andydyer.androidtestdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by andy on 8/23/14.
 */
public class WebViewActivity extends Activity {

    public static final String EXTRA_URL = "url";
    public static final String EXTRA_TITLE = "title";

    @InjectView(R.id.webview) WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        setProgressBarIndeterminate(true);
        ButterKnife.inject(this);
        getActionBar().setTitle(getIntent().getStringExtra(EXTRA_TITLE));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        loadUrl();
    }

    private void loadUrl() {
        String url = getIntent().getStringExtra(EXTRA_URL);
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                setProgressBarIndeterminateVisibility(false);
                return false;
            }
        });
        setProgressBarIndeterminateVisibility(true);
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
