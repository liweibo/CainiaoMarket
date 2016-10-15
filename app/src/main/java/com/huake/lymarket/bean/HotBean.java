package com.huake.lymarket.bean;

/*
 * @创建者     兰昱
 * @创建时间  2016/10/13 15:19
 * @描述	      
 */

import java.io.Serializable;
import java.util.List;

public class HotBean implements Serializable {


    /**
     * totalCount : 28
     * currentPage : 3
     * totalPage : 3
     * pageSize : 10
     * list : [{"id":21,"name":"三星（SAMSUNG）455R4J-X01 14英寸超薄本（四核A4-6210 4G 500G 2G独显 WIN8.1 蓝牙4.0)神秘银","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_rmd558b9a9bN25c3b9a7.jpg","description":null,"price":2759,"sale":5696},{"id":22,"name":"旅武极i5 4590/8G/GTX750Ti双风扇/四核独显台式组装游戏主机/DIY组装机","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_rmd55b841c5Nc9ac1f5c.jpg","description":null,"price":2899,"sale":2849},{"id":23,"name":"微星（MSI）GE62 2QC-264XCN 15.6英寸游戏笔记本电脑（i5-4210H 8G 1T GTX960MG DDR5 2G 背光键盘）黑色","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_rmd55c31763N94b2245a.jpg","description":null,"price":5899,"sale":7157},{"id":24,"name":"武极 i3 4170/8G/B85/128G台式组装办公家用电脑主机/DIY组装机","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_rmd55cc3a3dNd4c467ee.jpg","description":null,"price":1658,"sale":7238},{"id":25,"name":"EiT i5 4590/4G/SSD/华硕B85/四核游戏办公台式电脑主机/DIY组装机","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_rmd55d56f8fN26c378d5.jpg","description":null,"price":1999,"sale":4718},{"id":26,"name":"戴尔（DELL）Inspiron 3452-R1348B 23.8英寸一体机电脑 （奔腾N3700 4G 500G DVD WIFI 蓝牙 Win8.1）黑","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_rmd55d580baN2e5bfab6.jpg","description":null,"price":3499,"sale":1875},{"id":27,"name":"极途（Gimit）i3 4170/1T/GTX750Ti台式游戏电脑主机/DIY组装机","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_rmd55ebf745N51a3be79.jpg","description":null,"price":2659,"sale":5221},{"id":28,"name":"雷诺塔 i5 4590/8G/GTX950/技嘉B85 四核游戏电脑主机/DIY组装机","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/s_rmd55f69cc4N1571707b.jpg","description":null,"price":2449,"sale":480}]
     */

    private int totalCount;
    private int currentPage;
    private int totalPage;
    private int pageSize;
    /**
     * id : 21
     * name : 三星（SAMSUNG）455R4J-X01 14英寸超薄本（四核A4-6210 4G 500G 2G独显 WIN8.1 蓝牙4.0)神秘银
     * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/s_rmd558b9a9bN25c3b9a7.jpg
     * description : null
     * price : 2759.0
     * sale : 5696
     */

    private List<ListBean> list;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        private int id;
        private String name;
        private String imgUrl;
        private Object description;
        private double price;
        private int sale;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getSale() {
            return sale;
        }

        public void setSale(int sale) {
            this.sale = sale;
        }
    }
}
