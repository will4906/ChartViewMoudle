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

    /**
     * 设置横向分辨率
     * @param horizontalResolution
     */
    void setHorizontalResolution(float horizontalResolution);

    void setHorizontalResolution(int index, float horizontalResolution);

    /**
     * 设置是否可视
     * @param index
     * @param visibility
     */
    void setMainLineVisibility(int index, boolean visibility);

    /**
     * 设置单条主线的初始分辨率
     * @param index
     * @param initHorizontalResolution
     */
    void setInitHorizontalResolution(int index, float initHorizontalResolution);

    /**
     * 设置单条主线初始化时应该显示多少个点
     * @param initAViewPointsSum
     */
    void setInitAViewPointsSum(int index, int initAViewPointsSum);

    /**
     * 统一设置主线无手指触摸时的离右Y轴的偏移
     * @param normalOffsetX
     */
    void setNormalOffsetX(float normalOffsetX);

    /**
     * 设置单条主线无手指触摸时的离右Y轴的偏移
     * @param index
     * @param normalOffsetX
     */
    void setNormalOffsetX(int index, float normalOffsetX);
}
