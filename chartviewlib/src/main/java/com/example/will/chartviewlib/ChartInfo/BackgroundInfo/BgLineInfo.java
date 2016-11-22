package com.example.will.chartviewlib.ChartInfo.BackgroundInfo;

import android.graphics.Color;

/**
 * @author will4906.
 * @Time 2016/11/22.
 */

public class BgLineInfo {
    /**
     * 背景线宽度
     * 缺省值：3
     */
    float lineWidth = 3;
    /**
     * 背景线颜色
     * 缺省值：GRAY
     */
    int lineColor = Color.GRAY;
    /**
     * 背景线对应纵坐标
     */
    float linePosY;
    /**
     * 是否虚线
     * 缺省值：否
     */
    boolean bIsDotted = false;
}
