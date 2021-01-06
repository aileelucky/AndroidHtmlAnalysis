# AndroidHtmlAnalysis
android  通过url自动获取标题、描述、图片

## 使用方式

<code>
 UrlContentCatch.getHtmlBeanByUrl(this, url, new DownHtmlListener() {
                @Override
                public void success(HtmlContentBean bean) {
 
                  // do you can do
                }

                @Override
                public void error() {

                }
            });
</code>
