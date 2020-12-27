package com.gu.androidhtmlanalysis;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gu.htmlanalysis.UrlUtils;
import com.gu.htmlanalysis.bean.HtmlContentBean;
import com.gu.htmlanalysis.listener.DownHtmlListener;

public class MainActivity extends AppCompatActivity {


    private TextView tvTitle, tvContent;
    private SimpleDraweeView sdvThumb;
    private EditText edUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        sdvThumb = findViewById(R.id.thumb);
        edUrl = findViewById(R.id.ed_url);

        String localUrl = "https://club.huawei.com/thread-24913493-1-1.html";

        findViewById(R.id.tv_action).setOnClickListener(v -> {
            String urlEdit = edUrl.getText().toString();
            String url = !TextUtils.isEmpty(urlEdit) ? urlEdit : localUrl;
            edUrl.setText("");
            UrlUtils.getHtmlBeanByUrl(this, url, new DownHtmlListener() {
                @Override
                public void success(HtmlContentBean bean) {
                    setData(bean);
                }

                @Override
                public void error() {

                }
            });
        });
    }

    private void setData(HtmlContentBean data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvTitle.setText(data.title);
                tvContent.setText(UrlUtils.getContent(data));
                sdvThumb.setImageURI(Uri.parse(data.getImageUrl()));
            }
        });
    }
}