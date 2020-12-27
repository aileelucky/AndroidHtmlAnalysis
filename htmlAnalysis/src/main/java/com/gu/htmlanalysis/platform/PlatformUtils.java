package com.gu.htmlanalysis.platform;

import android.util.Log;

import com.gu.htmlanalysis.bean.HtmlContentBean;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by 顾佳佳 on 2020/12/27.
 */
public class PlatformUtils {

    //获取微信公众号
    public static HtmlContentBean getWXImageThumb(Document document) {
        HtmlContentBean htmlBean = new HtmlContentBean();
        htmlBean.url = document.location();
        Elements elements = document.getElementsByTag("meta");
        for(Element element : elements){
            String name = element.attr("property");
            if(name.equals("og:title")){
                htmlBean.title = element.attr("content");
                continue;
            }
            if(name.equals("og:image")){
                htmlBean.setImage(element.attr("content"));
                continue;
            }
            if(name.equals("og:description")){
                htmlBean.setDescription(element.attr("content"));
                continue;
            }
            if(name.equals("og:article:author")){
                htmlBean.setAuthor(element.attr("content"));
            }
        }
        return htmlBean;
    }

    //获取腾讯新闻内容
    public static HtmlContentBean getTencentNews(Document document){
        HtmlContentBean htmlBean = new HtmlContentBean();
        htmlBean.title = document.head().getElementsByTag("title").text();
        htmlBean.url = document.location();
        Elements imgs = document.getElementsByTag("img");
        if(imgs != null && imgs.size()>0){
            htmlBean.setImage(imgs.get(0).attr("abs:src"));
        }
        Elements meta = document.head().getElementsByTag("meta");
        for(Element element : meta){
            if(element.attr("name").equals("description")){
                htmlBean.setDescription(element.attr("content"));
                break;
            }
        }
        return htmlBean;
    }

    //通用网页
    public static HtmlContentBean getSimpleThumb(Document document,String url){
        HtmlContentBean htmlBean = new HtmlContentBean();

       Elements elements = document.getElementsByTag("script");


        for(Element element : elements){
            String title = element.data();
            String[] elScriptList = title.split("var");
            Log.i("ailee",""+ elScriptList);
        }
        htmlBean.title = document.getElementsByTag("title").text();
        htmlBean.url = document.location();
        Elements imgs = document.getElementsByTag("img");
        if(imgs != null && imgs.size()>0){
            htmlBean.setImage(imgs.get(0).attr("abs:src"));
        }

        String description = null;
        Elements meta = document.getElementsByTag("meta");
        for(Element element : meta){
            if(hasDescription(element.attr("name"))){
                description = element.attr("content");
                break;
            }
        }
        htmlBean.setDescription(description);
        return htmlBean;
    }

    //判断是否含描述字段
    private static boolean hasDescription(String attributeKey){
        if(attributeKey.contains("description") || attributeKey.contains("desc")){
            return true;
        }
        return false;
    }

}

