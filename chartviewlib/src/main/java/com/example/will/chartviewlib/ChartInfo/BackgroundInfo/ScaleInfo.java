package com.example.will.chartviewlib.ChartInfo.BackgroundInfo;

import android.graphics.Color;

/**
 * Created by will on 2016/11/22.
 */

public class ScaleInfo {
    /**
     * 坐标轴颜色
     * 缺省值：黑
     */
    int scaleColor = Color.BLACK;

    public int getScaleColor() {
        return scaleColor;
    }

    public void setScaleColor(int scaleColor) {
        this.scaleColor = scaleColor;
    }

    /**
     * 坐标轴宽度
     * 缺省值：5px
     */
    float scaleWidth = 5;

    public float getScaleWidth() {
        return scaleWidth;
    }

    public void setScaleWidth(float scaleWidth) {
        this.scaleWidth = scaleWidth;
    }

    /**
     * 是否带三角箭头
     * 缺省值：有
     */
    boolean bHasTriangle = true;

    public boolean isbHasTriangle() {
        return bHasTriangle;
    }

    public void setbHasTriangle(boolean bHasTriangle) {
        this.bHasTriangle = bHasTriangle;
    }

    /**
     * 轴上是否带数据
     * 缺省值：带
     */
    boolean hasData = true;

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }

    /**
     * 坐标轴标题
     * 缺省值：带
     */
   String strTitle = "";

    public String getScaleTitle() {
        return strTitle;
    }

    public void setScaleTitle(String strTitle) {
        this.strTitle = strTitle;
    }
}
