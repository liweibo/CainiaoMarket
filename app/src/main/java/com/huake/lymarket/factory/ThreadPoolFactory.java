package com.huake.lymarket.factory;

/*
 * @创建者     兰昱
 * @创建时间  2016/9/14 17:49
 * @描述	      自定义线程池
 */
public class ThreadPoolFactory {
    //得到线程的方法
    static ThreadPoolProxy	mNormalPool;
    static ThreadPoolProxy	mDownlPool;

    /**得到一个普通的线程池*/
    public static ThreadPoolProxy getNormalThreadPool(){
        if (mNormalPool == null) {
            synchronized (ThreadPoolFactory.class){
                if (mNormalPool == null) {
                    mNormalPool=new ThreadPoolProxy(5,5,3000);
                }
            }
        }
        return mNormalPool;
    }

    /**得到一个普通的线程池*/
    public static ThreadPoolProxy getDownThreadPool(){
        if (mDownlPool == null) {
            synchronized (ThreadPoolFactory.class){
                if (mDownlPool == null) {
                    mDownlPool=new ThreadPoolProxy(3,3,3000);
                }
            }
        }
        return mDownlPool;
    }

}
