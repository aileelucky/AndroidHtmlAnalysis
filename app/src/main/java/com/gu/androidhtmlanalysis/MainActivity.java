package com.gu.androidhtmlanalysis;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gu.htmlanalysis.UrlContentCatch;
import com.gu.htmlanalysis.bean.HtmlContentBean;
import com.gu.htmlanalysis.listener.DownHtmlListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private TextView tvTitle, tvContent;
    private SimpleDraweeView sdvThumb;
    private EditText edUrl;
    private String currentContent = "";

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
            UrlContentCatch.getHtmlBeanByUrl(this, url, new DownHtmlListener() {
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
                tvContent.setText(UrlContentCatch.getContent(data));
                sdvThumb.setImageURI(Uri.parse(data.getImageUrl()));
            }
        });
    }

    @Override
    protected void onResume() {
        //获取系统剪贴板服务
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               getClipData();
           }
       },1000);
        super.onResume();
    }

    private void getClipData(){
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        if (null != clipboardManager) {
            // 获取剪贴板的剪贴数据集
            ClipData clipData = clipboardManager.getPrimaryClip();
            if (null != clipData && clipData.getItemCount() > 0) {
                // 从数据集中获取（粘贴）第一条文本数据
                ClipData.Item item = clipData.getItemAt(0);
                if (null != item) {
                    String content = item.getText().toString();
                    if(content.equals(currentContent)){
                        return;
                    }
                    currentContent = content;
                    List<String> strings = UrlContentCatch.getUrlFromString(content);
                    if(strings.size()> 0){
                        edUrl.setText(strings.get(0));
                    }
                }
            }
        }
    }
}