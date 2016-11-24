package com.example.will.chartviewlib.ChartInfo.BackgroundInfo;

/**
 * @author will4906.
 * @Time 2016/11/24.
 */

public interface IDefaultBgLineInfo {

    /**
     * 设置默认背景线宽度
     * @param lineWidth
     */
    void setDefaultLineWidth(float lineWidth);

    /**
     * 设置默认背景线颜色
     * @param lineColor
     */
    void setDefaultLineColor(int lineColor);

    /**
     * 使能纵向背景线
     * @param enable
     */
    void enableDefaultVerticalBackgroundLine(boolean enable);

    /**
     * 使能横向背景线
     * @param enable
     */
    void enableDefaultHorizontalBackgroundLine(boolean enable);

    /**
     * 设置背景线是否为虚线
     * @param isDotted
     */
    void setDefaultLineIsDotted(boolean isDotted);
}
