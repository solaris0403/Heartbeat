package com.caowei.heartbeat;

/**
 * 心跳配置
 */
public class HeartConfig {
    //心跳包发出去后的超时时间
    public static final int TIMEOUT = 10 * 1000;
    //最小最大心跳
    public static final int MIN_HEART = 10 * 1000;
    public static final int MAX_HEART = 300 * 1000;
    public static final int DEFAULT_HEART = 10 * 1000;

    //成功-失败时间的误差范围
    public static final int CRITICAL = 10 * 1000;

    //第一阶段的增长倍数
    public static final float RISE_MULTIPLE = 1.75F;
    //探测次数
    public static final int DETECT_SUCCESS_COUNT = 10;
    public static final int DETECT_FAILED_COUNT = 3;
    //探测步伐
    public static final int HEART_DETECT_STEP = 5 * 1000;
    //稳定阶段
    public static final int STABLE_SUCCESS_COUNT = 20;
    public static final int STABLE_FAILED_COUNT = 5;

    public static final byte HEART_HISTORY = 20;

    //心跳类型
    public static final int HEART_TYPE_RISE = 1;//1 初始类型，该阶段需要快速上涨，
    public static final int HEART_TYPE_LOCATE = 2;//2 该阶段需要二分法判断，
    public static final int HEART_TYPE_DETECT = 3;//3 心跳稳定性探测阶段
    public static final int HEART_TYPE_STABLE = 4;//4 稳定阶段心跳阶段
}
