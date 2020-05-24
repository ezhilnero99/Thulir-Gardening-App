package in.edu.ssn.insta.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import in.edu.ssn.insta.R;

public class web extends AppCompatActivity {

    WebView shop;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_web);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        shop = (WebView)findViewById(R.id.shop_web);
        title = (TextView)findViewById(R.id.title_textv);
        String url = getIntent().getExtras().getString("url");
        String title_in = getIntent().getExtras().getString("title");

        title.setText(title_in);
        shop.setWebChromeClient(new WebChromeClient());
        shop.setWebViewClient(new WebViewClient());
        shop.getSettings().setAllowFileAccessFromFileURLs(true);
        shop.getSettings().setAllowUniversalAccessFromFileURLs(true);
        shop.clearCache(true);
        shop.clearHistory();
        shop.getSettings().setAllowContentAccess(true);
        shop.getSettings().setDomStorageEnabled(true);
        shop.getSettings().setJavaScriptEnabled(true); // enable javascript
        shop.getSettings().setBuiltInZoomControls(true);
        shop.getSettings().setSupportZoom(true);
        shop.getSettings().setLoadWithOverviewMode(true);
        shop.getSettings().setUseWideViewPort(true);

        shop.getSettings().setBuiltInZoomControls(true);
        shop.getSettings().setDisplayZoomControls(false);

        shop.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        shop.setScrollbarFadingEnabled(false);


        shop.loadUrl(url);
        shop.setWebViewClient(new WebViewClient());
        shop.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });

    }
}
