package com.example.dell.ozbrowser;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.net.PortUnreachableException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button forward,backward,refresh,clear,go;
    EditText txt_url;
    ProgressBar progressBar;
    WebView brow;
    String url ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar = (ProgressBar) findViewById(R.id.progress_url);
        txt_url = (EditText) findViewById(R.id.edit_url);
        forward = (Button) findViewById(R.id.btn_forward);
        forward.setOnClickListener(this);
        backward = (Button) findViewById(R.id.btn_backward);
        backward.setOnClickListener(this);
        refresh = (Button) findViewById(R.id.btn_refresh);
        refresh.setOnClickListener(this);
        clear = (Button) findViewById(R.id.btn_clear);
        clear.setOnClickListener(this);
        go = (Button) findViewById(R.id.btn_go);
        go.setOnClickListener(this);
        brow = (WebView) findViewById(R.id.web_view);

        brow.setWebViewClient(new OurViewClient());
        brow.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
               progressBar.setProgress(newProgress);
                if(newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                }
                else{
                 progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        brow.loadUrl("http://www.google.com");
        WebSettings webSettings = brow.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onClick(View view) {

        if(view.equals(go)){
            String edit_url = txt_url.getText().toString().trim();

            if(TextUtils.isEmpty(edit_url)){
                return;
            }
            if(!edit_url.startsWith("http://")){
                edit_url = "http://"+edit_url;
            }
            url = edit_url;
            brow.loadUrl(url);
            //to hide the soft keyboard
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(txt_url.getWindowToken(), 0);
            }
        }
        else if(view.equals(forward)){
            if(brow.canGoForward())
                brow.goForward();

        }

        else if(view.equals(backward)){
            if(brow.canGoBack())
                brow.goBack();
        }

        else if(view.equals(refresh)){
            brow.reload();
        }

        else if(view.equals(clear)){
            brow.clearHistory();
        }

    }
}
