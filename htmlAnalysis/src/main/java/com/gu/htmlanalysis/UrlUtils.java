package com.gu.htmlanalysis;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.gu.htmlanalysis.bean.HtmlContentBean;
import com.gu.htmlanalysis.listener.DownHtmlListener;
import com.gu.htmlanalysis.platform.PlatformUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UrlUtils {
    public static String getContent(HtmlContentBean htmlBean){
        if(htmlBean == null){
            return "";
        }
        if(!TextUtils.isEmpty(htmlBean.getDescription())){
            return htmlBean.getDescription();
        }
        if(!TextUtils.isEmpty(htmlBean.getAuthor())){
            return "作者："+htmlBean.getAuthor();
        }
        if(!TextUtils.isEmpty(htmlBean.url)){
            return htmlBean.url;
        }
        return "";
    }

    public static String getDomain(String url) {
        String domain = "";
        if (!TextUtils.isEmpty(url) && url.startsWith("http")) {
            domain = Uri.parse(url).getHost();
        }
        return domain;
    }

    public static void getHtmlBeanByUrl(Context context, String url, DownHtmlListener listener){
       new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   Document document = Jsoup.connect(url).get();
                   listener.success(analysisHtml(document,url));

               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }).start();
    }

    private static HtmlContentBean analysisHtml(Document document,String url){
        if(document == null){
            return null;
        }
        String type = getDomain(document.location());

        if(type.equals(UrlTypeConstants.WX_GZH)){
            return PlatformUtils.getWXImageThumb(document);
        }
        if(type.equals(UrlTypeConstants.Tencent_News)){
            return PlatformUtils.getTencentNews(document);
        }

        return PlatformUtils.getSimpleThumb(document,url);
    }
}
