package com.huake.lymarket.views;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.huake.lymarket.R;
import com.huake.lymarket.bean.HeadPicBean;
import com.huake.lymarket.utils.CommonUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/*
 * @创建者     兰昱
 * @创建时间  2016/9/7 10:56
 * @描述	      
 */
public class HeadView {

    @InjectView(R.id.item_home_picture)
    ViewPager mItemHomePicture;
    @InjectView(R.id.item_home_picture_page)
    LinearLayout mItemHomePicturePage;
    private List<HeadPicBean> mDatas;
    private Context mContext;
    private int length = 0;
    private View mInflate;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mItemHomePicture != null) {
                int currentItem = mItemHomePicture.getCurrentItem();
                currentItem++;
                mItemHomePicture.setCurrentItem(currentItem);
            }
            mHandler.removeMessages(0);
            mHandler.sendEmptyMessageDelayed(0, 5000);

        }
    };

    public View getInflate() {
        return mInflate;
    }

    public HeadView(List<HeadPicBean> datas, Context context) {
        mDatas = datas;
        mContext = context;
        //原点长度
        length = CommonUtils.dip2px(5, mContext);
        ;
        initView();
        if (mDatas != null) {
            refreshHoldView();
        }
    }

    public View initView() {

        mInflate = View.inflate(mContext, R.layout.item_home_picture, null);
        ButterKnife.inject(this, mInflate);
        return mInflate;

    }

    public void refreshHoldView() {

        mItemHomePicture.setAdapter(new PictureAdapter());
        //添加右侧下面的导航标题
        for (int i = 0; i < mDatas.size(); i++) {

            View point = new View(mContext);
            point.setBackgroundResource(R.drawable.white_point);
            //这个params修饰的还是point自己，为了适配，我们还会进行dp转px
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(length, length);
            params.leftMargin = length;
            params.bottomMargin = length;
            mItemHomePicturePage.addView(point, params);

            //默认第一个亮
            if (i == 0) {
                point.setBackgroundResource(R.drawable.red_point);
            }
        }
        //处理点的变化
        mItemHomePicture.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = position % mDatas.size();
                //首先全黑，然后再选择对的亮
                for (int i = 0; i < mDatas.size(); i++) {
                    mItemHomePicturePage.getChildAt(i).setBackgroundResource(R.drawable.white_point);
                    if (i == position) {
                        mItemHomePicturePage.getChildAt(i).setBackgroundResource(R.drawable.red_point);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置中间滑动只要直接显示中间的图片即可
        int diff = 100 % mDatas.size();
        int index = 100;
        mItemHomePicture.setCurrentItem(index - diff);

        //如果用户点到图片不应该移动吧
        mItemHomePicture.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.removeMessages(0);
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.sendEmptyMessageDelayed(0,5000);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        mHandler.sendEmptyMessageDelayed(0,5000);
    }

    class PictureAdapter extends PagerAdapter {

        @Override
        public int getCount() {

            if (mDatas != null) {
                return 1000;
            }
            return 0;

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //默认位置肯定是从0开始的，取余是防止超出界限引发空指针，如果想左右滑动，就需要先把位置调整到中间咯
            position = position % mDatas.size();
            Glide.with(mContext).load(mDatas.get(position).getImgUrl()).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public Handler getHandler() {
        return mHandler;
    }
}
