package com.huake.lymarket.views;

/*
 * @创建者     兰昱
 * @创建时间  2016/10/9 13:44
 * @描述	      
 */


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.huake.lymarket.R;

public class MyToolbar extends Toolbar {

    EditText mToolbarSearchview;
    TextView mToolbarTitle;
    Button mToolbarRightButton;
    private View mView;
    private TintTypedArray mTintTypedArray;

    public MyToolbar(Context context) {
        this(context, null);

    }

    public MyToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        //可以理解为padding
        setContentInsetsRelative(10, 10);
        //封装了TypedArray，功能更强大
        //拿到toolbar自定义的属性，主要都是button的
        if (attrs != null) {

            mTintTypedArray = TintTypedArray.obtainStyledAttributes(getContext(), attrs, R.styleable.MyToolBar, defStyleAttr, 0);
            //拿到自定义属性
            Drawable buttonDrawable = mTintTypedArray.getDrawable(R.styleable.MyToolBar_rightButtonIcon);
            boolean isShowSearchView = mTintTypedArray.getBoolean(R.styleable.MyToolBar_isShowSearchView,false);
            String buttonText = mTintTypedArray.getString(R.styleable.MyToolBar_rightButtonText);
            //设置按钮图标
            if (buttonDrawable != null) {
                setRightButtonDrawable(buttonDrawable);
            }
            //设置按钮内容
            setRightButtonText(buttonText);
            if (isShowSearchView) {
                showSerachView();
                hideTextView();
            }else {
                hideSerachView();
                showTextView();
            }
            mTintTypedArray.recycle();//刷新
        }

    }

    //自定义自己的view
    private void initView() {

        if (mView == null) {
            mView = View.inflate(getContext(), R.layout.toolbar, null);
            mToolbarTitle = (TextView) mView.findViewById(R.id.toolbar_title);
            mToolbarSearchview = (EditText) mView.findViewById(R.id.toolbar_searchview);
            mToolbarRightButton = (Button) mView.findViewById(R.id.toolbar_rightButton);

        }
        //显示视图，和形状,默认不要焦点

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
        addView(mView, params);//这样就可以显示视图了
    }

    public void hideTextView() {
        if (mToolbarTitle != null) {
            mToolbarTitle.setVisibility(GONE);
        }
    }
    public void showTextView() {
        if (mToolbarTitle != null) {
            mToolbarTitle.setVisibility(VISIBLE);
        }
    }

    public void showSerachView() {
        if (mToolbarSearchview != null) {
            mToolbarSearchview.setVisibility(VISIBLE);
        }
    }
    public void hideSerachView() {
        if (mToolbarSearchview != null) {
            mToolbarSearchview.setVisibility(GONE);
        }
    }

    public EditText getToolbarSearchview() {
        return mToolbarSearchview;
    }


    public void setRightButtonText(String buttonText) {
        mToolbarRightButton.setVisibility(VISIBLE);
        if (mToolbarRightButton != null) {
            mToolbarRightButton.setText(buttonText);
        }

    }
    public void setTextTitle(String buttonText) {
        mToolbarTitle.setVisibility(VISIBLE);
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(buttonText);
        }

    }
    public Button getRightButton(){

        return this.mToolbarRightButton;
    }



    public Button getToolbarRightButton() {
        return mToolbarRightButton;
    }

    public void setRightButtonDrawable(Drawable drawable) {

        if (mToolbarRightButton != null) {
            mToolbarRightButton.setVisibility(VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mToolbarRightButton.setBackground(drawable);
            } else {
                mToolbarRightButton.setBackgroundDrawable(drawable);
            }


        }
    }
    //按钮监听事件
    public void setButtonOnClickListener(OnClickListener listener){
        if (mToolbarRightButton != null) {
            mToolbarRightButton.setOnClickListener(listener);
        }
    }


}

