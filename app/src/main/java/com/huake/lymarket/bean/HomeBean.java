package com.huake.lymarket.bean;

/*
 * @创建者     兰昱
 * @创建时间  2016/10/12 17:42
 * @描述	      
 */

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

public class HomeBean implements MultiItemEntity,Serializable {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    /**
     * id : 1
     * <p>
     * title : 超值购
     * cpOne : {"id":17,"title":"手机专享","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/555c6c90Ncb4fe515.jpg"}
     * cpTwo : {"id":15,"title":"闪购","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/560a26d2N78974496.jpg"}
     * cpThree : {"id":11,"title":"团购","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/560be0c3N9e77a22a.jpg"}
     */

    private int id;
    private String title;
    /**
     * id : 17
     * title : 手机专享
     * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/555c6c90Ncb4fe515.jpg
     */

    private Compaign cpOne;
    /**
     * id : 15
     * title : 闪购
     * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/560a26d2N78974496.jpg
     */

    private Compaign cpTwo;
    /**
     * id : 11
     * title : 团购
     * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/560be0c3N9e77a22a.jpg
     */

    private Compaign cpThree;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }


    public Compaign getCpTwo() {
        return cpTwo;
    }


    public Compaign getCpThree() {
        return cpThree;
    }


    public Compaign getCpOne() {
        return cpOne;
    }


    private int itemType = LEFT;

    public HomeBean(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }


    public class Compaign {
        private int id;
        private String title;
        private String imgUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }

}
