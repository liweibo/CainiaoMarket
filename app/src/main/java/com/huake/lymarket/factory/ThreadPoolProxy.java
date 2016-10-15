package com.huake.lymarket.factory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
 * @创建者     兰昱
 * @创建时间  2016/9/14 17:39
 * @描述	      自定义线程池
 */
public class ThreadPoolProxy {

    private ThreadPoolExecutor mThreadPoolExecutor;
    private int mCorePoolSize;
    private long mKeepAliveTime;
    private int mMaximumPoolSize;

    public ThreadPoolProxy(int corePoolSize, long keepAliveTime, int maximumPoolSize) {
        mCorePoolSize = corePoolSize;
        mKeepAliveTime = keepAliveTime;
        mMaximumPoolSize = maximumPoolSize;
    }

    private ThreadPoolExecutor initThreadPoolExecutor(){

        if (mThreadPoolExecutor == null) {
            synchronized (ThreadPoolProxy.class){
                if (mThreadPoolExecutor == null) {  //参数很多，非常麻烦
                    TimeUnit unit=TimeUnit.MILLISECONDS;
                    BlockingQueue<Runnable> workQueue=new LinkedBlockingDeque<Runnable>();// 无界队列
                    ThreadFactory threadFactory= Executors.defaultThreadFactory();
                    RejectedExecutionHandler handler=new ThreadPoolExecutor.AbortPolicy();// 丢弃任务并抛出RejectedExecutionException异常。
                    mThreadPoolExecutor=new ThreadPoolExecutor(
                            mCorePoolSize,// 核心的线程数
                            mMaximumPoolSize,// 最大的线程数
                            mKeepAliveTime, // 保持时间
                            unit, // 保持时间对应的单位
                            workQueue,// 缓存队列/阻塞队列
                            threadFactory, // 线程工厂
                            handler// 异常捕获器
                    );
                }
            }
        }

        return mThreadPoolExecutor;
    }

    /**执行任务*/
    public void execute(Runnable task) {
        initThreadPoolExecutor();
        mThreadPoolExecutor.execute(task);
    }
    /**取消任务*/
    public void removeTask(Runnable task) {
        initThreadPoolExecutor();
        mThreadPoolExecutor.remove(task);
    }
    /**提交任务*/
    public Future<?> submit(Runnable task) {
        initThreadPoolExecutor();
        return  mThreadPoolExecutor.submit(task);
    }

}
