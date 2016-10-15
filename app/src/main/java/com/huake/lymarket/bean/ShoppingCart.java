package com.huake.lymarket.bean;

/*
 * @创建者     兰昱
 * @创建时间  2016/10/15 15:57
 * @描述	      装饰着模式，增加购物车新需要的两个属性
 */

import java.io.Serializable;

public class ShoppingCart extends HotBean.ListBean implements Serializable {
    private int count;
    private boolean isChecked=true;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
