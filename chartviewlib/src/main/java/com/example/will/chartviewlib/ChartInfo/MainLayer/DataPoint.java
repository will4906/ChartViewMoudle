package com.example.will.chartviewlib.ChartInfo.MainLayer;

import android.graphics.Color;

import com.example.will.chartviewlib.ChartInfo.BaseInfo.BasePointInfo;

/**
 * 根据测试结果，由于此类基本是丢进链表中使用的，如果继承了MainPointInfo会导致一直没有释放而占满内存。日后再根据算法调优
 * @author will4906.
 * @Time 2016/12/5.
 */

public class DataPoint {

    /**
     * 对应Y轴数据
     */
    private float YData;

    public float getYData() {
        return YData;
    }

    public void setYData(float YData) {
        this.YData = YData;
    }

    /**
     * X轴上显示的数据
     */
    private String XData = "";

    public String getXData() {
        return XData;
    }

    public void setXData(String XData) {
        this.XData = XData;
    }

    /**
     * 是否显示X轴数据
     */
    private boolean isShowXData = false;

    public boolean isShowXData() {
        return isShowXData;
    }

    public void setShowXData(boolean showXData) {
        isShowXData = showXData;
    }

    /**
     * 是否改变过颜色
     */
    private boolean hasChangeColor = false;

    public boolean isHasChangeColor() {
        return hasChangeColor;
    }

    public void setHasChangeColor(boolean hasChangeColor) {
        this.hasChangeColor = hasChangeColor;
    }

    private int color = Color.GREEN;
    /**
     * 点的颜色
     */
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        hasChangeColor = true;
        this.color = color;
    }

    /**
     * 是否改变过
     */
    private boolean hasChangeRadius = false;

    public boolean isHasChangeRadius() {
        return hasChangeRadius;
    }

    public void setHasChangeRadius(boolean hasChangeRadius) {
        this.hasChangeRadius = hasChangeRadius;
    }

    /**
     * 点的半径
     */
    private float radius = 2;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        hasChangeRadius = true;
        this.radius = radius;
    }

    /**
     * 是否空心
     */
    private boolean isStroke = false;

    public boolean isStroke() {
        return isStroke;
    }

    public void setStroke(boolean stroke) {
        isStroke = stroke;
    }

}
