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

    public static String getDomain(String url) {
        String domain = "";
        if (!TextUtils.isEmpty(url) && url.startsWith("http")) {
            domain = Uri.parse(url).getHost();
        }
        return domain;
    }
    public static String getHtml(String pathUrl){
        try{
            URL url = new URL(pathUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();//通过输入流获取html数据
            byte[] data = readInputStream(inStream);//得到html的二进制数据
            return new String(data, StandardCharsets.UTF_8);
        }catch (Exception e){
            return "";
        }
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

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

    public static void getHtmlBeanByUrl(Context context, String url, DownHtmlListener listener){
       new Thread(new Runnable() {
           @Override
           public void run() {
                   Document document = Jsoup.parse(getHtml(url));
                   listener.success(analysisHtml(document,url));



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
