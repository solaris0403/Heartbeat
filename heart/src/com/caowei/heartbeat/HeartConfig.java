package com.caowei.heartbeat;

/**
 * 心跳配置
 */
public class HeartConfig {
    //心跳包发出去后的超时时间
    public static final int TIMEOUT = 10 * 1000;
    //最小的心跳，如果比这个还小，那么说明当前网络不正常，不能再小了60s
    public static final int MIN_HEART = 10 * 1000;
    //成功-失败时间的最小范围
    public static final int CRITICAL = 10 * 1000;
    //第一阶段的增长倍数
    public static final float RISK_MULTIPLE = 1.75F;
    public static final int MAX_SUCCESS_COUNT = 10;
    public static final int MAX_FAILED_COUNT = 2;
    public static final int HEART_ADJUST_STEP = 5 * 1000;

    //心跳类型
    public static final int HEART_TYPE_RISK = 0;//0 初始类型，该阶段需要快速上涨，
    public static final int HEART_TYPE_DICHOTOMY = 1;//1 该阶段需要二分法判断，
    public static final int HEART_TYPE_STABLE = 2;//2 稳定调节阶段，

}
