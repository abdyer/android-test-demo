package org.andydyer.androidtestdemo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.andydyer.androidtestdemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by andy on 8/23/14.
 */
public class WebViewActivity extends ActionBarActivity {

    private static final String EXTRA_URL = "url";
    private static final String EXTRA_TITLE = "title";

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

    public static IntentBuilder intent(Context context) {
        return new IntentBuilder(context);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Setter
    @Accessors(chain = true, fluent = true)
    public static final class IntentBuilder {

        private final Context context;
        private String url;
        private String title;

        public void start() {
            if (url == null || title == null) {
                throw new IllegalStateException("Url and Title must not be null.");
            }
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra(EXTRA_URL, url);
            intent.putExtra(EXTRA_TITLE, title);
            context.startActivity(intent);
        }
    }
}
