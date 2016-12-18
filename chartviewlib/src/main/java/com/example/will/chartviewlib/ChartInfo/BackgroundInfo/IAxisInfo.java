package com.example.will.chartviewlib.ChartInfo.BackgroundInfo;

/**
 * 此接口定义了所有有关坐标轴的接口函数
 * @author will4906.
 * @Time 2016/11/23.
 */

public interface IAxisInfo {
    /**
     * 统一设置坐标轴的颜色
     * @param color
     */
    void setAxisColor(int color);
    /**
     * 设置单个坐标轴的颜色
     * @param whichAxis
     * @param color
     */
    void setAxisColor(int whichAxis, int color);
    /**
     * 统一设置坐标轴的宽度
     * @param width
     */
    void setAxisWidth(float width);
    /**
     * 设置单个坐标轴的宽度
     * @param whichAxis
     * @param width
     */
    void setAxisWidth(int whichAxis, float width);

    /**
     * 设置单个坐标轴信息
     * @param whichAxis
     * @param axisInfo
     */
    void setAxisInfo(int whichAxis, AxisInfo axisInfo);

    /**
     * 统一设置坐标轴是否拥有三角形
     * @param hasTriangle
     */
//    void setAxisHasTriangle(boolean hasTriangle);

    /**
     * 设置单个坐标轴是否拥有三角形，已被
     * @param whichAxis
     * @param hasTriangle
     */
//    void setAxisHasTriangle(int whichAxis, boolean hasTriangle);

    /**
     * 统一设置坐标轴初始化时是否拥有数据
     * @param hasData
     */
    void setAxisHasData(boolean hasData);

    /**
     * 设置单个坐标轴是否拥有数据
     * @param whichAxis
     * @param hasData
     */
    void setAxisHasData(int whichAxis, boolean hasData);

    /**
     * 设置单个坐标轴标题
     * @param strTitle
     */
    void setAxisTitle(int whichAxis, String strTitle);

    /**
     * 设置单个坐标轴是否可见
     * @param visibility
     */
    void setAxisVisibility(int whichAxis, boolean visibility);

    /**
     * 统一设置坐标轴是否可见
     * @param visibility
     */
    void setAxisVisibility(boolean visibility);
    /**
     * 设置y轴范围，不在针对哪个坐标轴进行依赖，默认以左Y轴和下x轴作为依赖，以后的依赖在重新考虑是否设置
     * @param min
     * @param max
     */
    void setYRange(float min, float max);

    /**
     * 设置单个坐标轴字体大小
     * @param which
     * @param textSize
     */
    void setAxisTextSize(int which, float textSize);

    /**
     * 设置单个坐标轴的字体颜色
     * @param which
     * @param textColor
     */
    void setAxisTextColor(int which, int textColor);

    /**
     * 统一设置坐标轴字体颜色
     * @param textColor
     */
    void setAxisTextColor(int textColor);

    /**
     * 统一设置是否允许坐标轴自动调整
     * @param autoText
     */
    void setAxisAutoText(boolean autoText);

    /**
     * 设置单个坐标轴是否允许自动调整
     * @param index
     * @param autoText
     */
    void setAxisAutoText(int index, boolean autoText);
}
