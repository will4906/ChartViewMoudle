package com.example.will.chartviewlib.ChartInfo.MainLayer;

/**
 * @author will4906.
 * @Time 2016/11/24.
 */

public interface IMainLineInfo {

    /**
     * 设置线的宽度
     * @param lineWidth
     */
    void setMainLineWidth(int index, float lineWidth);

    /**
     * 设置线的颜色
     * @param color
     */
    void setMainLineColor(int index, int color);

    /**
     * 设置主线是否为虚线
     * @param isDotted
     */
    void setMainLineIsDotted(int index, boolean isDotted);

    /**
     * 设置主线是否拥有点
     * @param hasPoints
     */
    void setHasPoints(int index, boolean hasPoints);

    /**
     * 设置主线是否显示线
     * @param hasLine
     */
    void setHasLine(int index, boolean hasLine);

    /**
     * 为主线传递点的样式
     * @param mainLinePointStyle
     */
    void setMainLinePointStyle(int index, MainPointInfo mainLinePointStyle);

    /**
     * 增加主线
     */
    void addMainLine();

    void addMainLine(MainLineInfo mainLineInfo);

    /**
     * 删除主线
     * @param index
     */
    void removeMainLine(int index);

    void removeAllMainLine();

}