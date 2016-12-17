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
     * @param bWhichAxis
     */
    void enableAxiss(boolean[] bWhichAxis);

    /**
     * 使能左边坐标轴
     * @param bHas
     */
    void enableLeftAxis(boolean bHas);

    /**
     * 使能底部坐标轴
     * @param bHas
     */
    void enableBottomAxis(boolean bHas);

    /**
     * 使能右边坐标轴
     * @param bHas
     */
    void enableRightAxis(boolean bHas);

    /**
     * 使能顶部坐标轴
     * @param bHas
     */
    void enableTopAxis(boolean bHas);
}
