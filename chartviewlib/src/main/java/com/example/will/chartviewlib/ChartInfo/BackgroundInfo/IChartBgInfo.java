package com.example.will.chartviewlib.ChartInfo.BackgroundInfo;

/**
 * @author will4906.
 * @Time 2016/11/23.
 */

public interface IChartBgInfo {
    /**
     * 设置背景色
     * @param color
     */
    void setBackgroundColor(int color);

    /**
     * 统一使能坐标轴
     * @param bWhichScale
     */
    void enableScales(boolean[] bWhichScale);

    /**
     * 使能左边坐标轴
     * @param bHas
     */
    void enableLeftScale(boolean bHas);

    /**
     * 使能底部坐标轴
     * @param bHas
     */
    void enableBottomScale(boolean bHas);

    /**
     * 使能右边坐标轴
     * @param bHas
     */
    void enableRightScale(boolean bHas);

    /**
     * 使能顶部坐标轴
     * @param bHas
     */
    void enableTopScale(boolean bHas);
}
