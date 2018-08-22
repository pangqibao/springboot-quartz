package com.boo.demo.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Job状态常量
 */
public class JobStateConstant {
    /**
     * 暂停
     */
    public static final String PAUSED="PAUSED";
    /**
     * 等待中
     */
    public static final String WAITING="WAITING";
    /**
     * 正常执行
     */
    public static final String ACQUIRED="ACQUIRED";
    /**
     * 阻塞
     */
    public static final String BLOCKED="BLOCKED";
    /**
     * 错误
     */
    public static final String ERROR="ERROR";
    /**
     * 正常
     */
    public static final String NORMAL="NORMAL";
    /**
     * 完成
     */
    public static final String COMPLETE="COMPLETE";
    /**
     * 无
     */
    public static final String NONE="NONE";

    public static final Map<String,String> JOB_STATE_MAP=new HashMap<>();
    static {
        JOB_STATE_MAP.put("PAUSED","暂停");
        JOB_STATE_MAP.put("WAITING","等待中");
        JOB_STATE_MAP.put("ACQUIRED","正常执行");
        JOB_STATE_MAP.put("BLOCKED","阻塞");
        JOB_STATE_MAP.put("ERROR","错误");
        JOB_STATE_MAP.put("NORMAL","正常");
        JOB_STATE_MAP.put("COMPLETE","完成");
        JOB_STATE_MAP.put("NONE","无");
    }
}
