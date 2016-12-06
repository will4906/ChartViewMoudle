package com.example.will.chartviewlib.ChartInfo.MainLayer;

/**
 * @author will4906.
 * @Time 2016/11/24.
 */

public interface IMainPointInfo {

    /**
     * 设置点的颜色
     * @param color
     */
    void setMainPointColor(int index, int color);

    /**
     * 设置点的半径
     * @param radius
     */
    void setMainPointRadius(int index, float radius);

    /**
     * 设置是否空心
     * @param isStroke
     */
    void setIsStroke(int index, boolean isStroke);

}
