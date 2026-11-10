package com.webhtml.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;

public class KeepAliveWebView extends WebView {

    public KeepAliveWebView(Context context) {
        super(context);
    }

    public KeepAliveWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeepAliveWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        // Namerno ignorišemo poziv sistema i uvek mu prosleđujemo da je vidljiv (View.VISIBLE)
        super.onWindowVisibilityChanged(View.VISIBLE);
    }

    @Override
    public boolean isShown() {
        // Lažiramo sistem da je WebView uvek nacrtan i prisutan na ekranu
        return true;
    }
}
