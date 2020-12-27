package com.gu.htmlanalysis.listener;

import com.gu.htmlanalysis.bean.HtmlContentBean;

/**
 * Created by 顾佳佳 on 2020/12/27.
 */
public interface DownHtmlListener {
    void success(HtmlContentBean bean);
    void error();
}
