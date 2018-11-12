package com.beibei.drawlayout;

/**
 * author: anbeibei
 * <p>
 * date: 2018/11/12
 * <p>
 * desc:
 */
public class ThreeBean {
    private String title;
    private String url;
    private String icon;

    public ThreeBean(String title, String url, String icon) {
        this.title = title;
        this.url = url;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "ThreeBean{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
