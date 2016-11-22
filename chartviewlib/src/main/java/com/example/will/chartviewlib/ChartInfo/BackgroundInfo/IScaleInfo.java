package com.example.will.chartviewlib.ChartInfo.BackgroundInfo;

/**
 * 此接口定义了所有有关坐标轴的接口函数
 * @author will4906.
 * @Time 2016/11/23.
 */

public interface IScaleInfo {
    /**
     * 统一设置坐标轴的颜色
     * @param color
     */
    void setScaleColor(int color);
    /**
     * 设置单个坐标轴的颜色
     * @param whichScale
     * @param color
     */
    void setScaleColor(int whichScale, int color);
    /**
     * 统一设置坐标轴的宽度
     * @param width
     */
    void setScaleWidth(float width);
    /**
     * 设置单个坐标轴的宽度
     * @param whichScale
     * @param width
     */
    void setScaleWidth(int whichScale, float width);

    /**
     * 设置单个坐标轴信息
     * @param whichScale
     * @param scaleInfo
     */
    void setScaleInfo(int whichScale, ScaleInfo scaleInfo);

    /**
     * 统一设置坐标轴是否拥有三角形
     * @param hasTriangle
     */
    void setScaleHasTriangle(boolean hasTriangle);

    /**
     * 设置单个坐标轴是否拥有三角形
     * @param whichScale
     * @param hasTriangle
     */
    void setScaleHasTriangle(int whichScale, boolean hasTriangle);

    /**
     * 统一设置坐标轴初始化时是否拥有数据
     * @param hasData
     */
    void setScaleHasData(boolean hasData);

    /**
     * 设置单个坐标轴是否拥有数据
     * @param whichScale
     * @param hasData
     */
    void setScaleHasData(int whichScale, boolean hasData);

    /**
     * 设置单个坐标轴标题
     * @param strTitle
     */
    void setScaleTitle(int whichScale, String strTitle);
}
