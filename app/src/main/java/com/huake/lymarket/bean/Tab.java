package com.huake.lymarket.bean;

/**
 * Created by Ivan on 15/9/25.
 */
public class Tab {

    private  int title;//标题也定义成int值，是因为我放入的是资源id
    private  int icon;
    private Class fragment;

    public Tab(Class fragment, int title, int icon) {
        this.title = title;
        this.icon = icon;
        this.fragment = fragment;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }
}
