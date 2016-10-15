package com.huake.lymarket.utils;

/*
 * @创建者     兰昱
 * @创建时间  2016/10/15 15:53
 * @描述	     购物车类，专门用来储存买的商品，和我想的很不一样，没有用的数据库，就是用了sharedpreference储存json数据
 * 中间用sparsearray作桥接
 */

import android.content.Context;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.huake.lymarket.bean.HotBean;
import com.huake.lymarket.bean.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

public class CarProvider {
    public static final String CART_JSON = "cart_json";
    private Context mContext;
    private SparseArray<ShoppingCart> mdatas;
    private Gson mGson;
    public CarProvider(Context context) {
        mContext = context;
        mdatas = new SparseArray<>(10);
        mGson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();//这种带时间的写法多巧妙
    }


    //转换类型
    public ShoppingCart convertToShoppingCart(HotBean.ListBean hotBean) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(hotBean.getId());
        shoppingCart.setDescription(hotBean.getDescription());
        shoppingCart.setImgUrl(hotBean.getImgUrl());
        shoppingCart.setName(hotBean.getName());
        shoppingCart.setPrice(hotBean.getPrice());
        return shoppingCart;
    }

    //从本地读取数据
    public List<ShoppingCart> getDataFromLocal() {

        String string = PreferencesUtils.getString(mContext, CART_JSON);
        List<ShoppingCart> shoppingCarts = null;
        if (string != null) {
            shoppingCarts = mGson.fromJson(string, new TypeToken<List<ShoppingCart>>() {
            }.getType());
        }
        return shoppingCarts;
    }

    //数组转换成稀疏数组
    private void listToSparse() {

        List<ShoppingCart> dataFromLocal = getDataFromLocal();
        if (dataFromLocal != null && dataFromLocal.size() > 0) {
            for (ShoppingCart shoppingCart : dataFromLocal) {
                mdatas.put(shoppingCart.getId(),shoppingCart);
            }
        }
    }
    //加入购物车
    public void put(ShoppingCart cart) {

        //先看有没有设置一下数量
        ShoppingCart shoppingCart = mdatas.get(cart.getId());
        if (shoppingCart != null) {
            shoppingCart.setCount(shoppingCart.getCount()+1);
        }else {
            shoppingCart=cart;
            shoppingCart.setCount(1);
        }
        mdatas.put(shoppingCart.getId(),shoppingCart);
        //储存到本地
        commit();
    }
    //当时如果把数据就写好就不用转换了呀
    public void put(HotBean.ListBean wares) {

        ShoppingCart cart = convertToShoppingCart(wares);
        put(cart);
    }
    //储存到本地 很奇怪，储存还要在跟array数组转换一层，为什么？
    public void commit() {
        List<ShoppingCart> shoppingCarts = sparseToList();
        String toJson = mGson.toJson(shoppingCarts);
        PreferencesUtils.putString(mContext,CART_JSON,toJson);
    }

    //稀疏数组转换成正常数组
    private List<ShoppingCart> sparseToList() {

        ArrayList<ShoppingCart> shoppingCarts = new ArrayList<>();
        //稀疏数组很奇怪，没有给予什么遍历方法
        int size=mdatas.size();
        for (int i = 0; i < size; i++) {
            shoppingCarts.add(mdatas.valueAt(i));
        }
        return shoppingCarts;
    }

    //更新就是覆盖一下，呵呵。。。
    public void update(ShoppingCart cart) {

        mdatas.put(cart.getId(), cart);
        commit();
    }
    //删除
    public void delete(ShoppingCart cart) {
        mdatas.delete(cart.getId());
        commit();
    }

    public List<ShoppingCart> getAll() {

        return getDataFromLocal();
    }
}
