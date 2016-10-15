package com.huake.lymarket.activity;

/*
 * @创建者     兰昱
 * @创建时间  2016/10/15 13:51
 * @描述	      
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.huake.lymarket.R;
import com.huake.lymarket.bean.HotBean;
import com.huake.lymarket.config.Contants;
import com.huake.lymarket.utils.CarProvider;
import com.huake.lymarket.views.MyToolbar;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WareDetailActivity extends AppCompatActivity  {

    @InjectView(R.id.toolbar_detail_activity)
    MyToolbar mToolbarDetailActivity;
    @InjectView(R.id.wb_detail_activity)
    WebView mWbDetailActivity;
    private CarProvider mCarProvider;
    private HotBean.ListBean mListBean;
    private WebAppInterface mJavascriptInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_detail);
        ButterKnife.inject(this);
        mCarProvider = new CarProvider(this);
        initToolBar();
        initData();
        initWebView();
    }

    private void initWebView() {
        WebSettings settings = mWbDetailActivity.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setAppCacheEnabled(true);
        mWbDetailActivity.loadUrl(Contants.API.WARES_DETAIL);
        //跟javascript交互
        mJavascriptInterface = new WebAppInterface(this);
        mWbDetailActivity.addJavascriptInterface(mJavascriptInterface,"appInterface");
        mWbDetailActivity.setWebViewClient(new MyWebClient());
    }

    //相当于页面的监听
    class MyWebClient extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mJavascriptInterface.showDetail();
        }
    }
    private void initData() {
        Serializable serializableExtra = getIntent().getSerializableExtra(Contants.WARE);
        mListBean = (HotBean.ListBean) serializableExtra;
    }

    private void initToolBar(){


        mToolbarDetailActivity.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WareDetailActivity.this.finish();
            }
        });
        mToolbarDetailActivity.setRightButtonText("分享");
        mToolbarDetailActivity.setTextTitle("商品详情");

    }

    //专门用来给网页调用java的接口及方法
    class WebAppInterface {

        private Context mContext;

        public WebAppInterface(Context context) {
            mContext = context;
        }
        @JavascriptInterface
        public void showDetail(){
            //如果调用网页方法似乎必须写到线程里
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mWbDetailActivity.loadUrl("javascript:showDetail("+mListBean.getId()+")");
                }
            });
        }

        @JavascriptInterface
        public void addToCart(long id){
            mCarProvider.put(mListBean);
            Toast.makeText(mContext, "已添加到购物车", Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void buy(){

        }

    }

}
