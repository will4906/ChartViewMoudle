package com.example.will.chartviewlib.ChartInfo.BackgroundInfo;

import android.graphics.Color;
import android.graphics.Paint;

import com.example.will.chartviewlib.ChartInfo.BaseInfo.BaseLineInfo;

/**
 * Created by will on 2016/11/22.
 */

public class ScaleInfo extends BaseLineInfo{
    /**
     * 坐标轴颜色处理
     */
    public int getScaleColor() {
        return super.getLineColor();
    }

    public void setScaleColor(int scaleColor) {
        super.setLineColor(scaleColor);
    }

    /**
     * 坐标轴宽度
     */

    public float getScaleWidth() {
        return super.getLineWidth();
    }

    public void setScaleWidth(float scaleWidth) {
        super.setLineWidth(scaleWidth);
    }

    /**
     * 是否带三角箭头，经试验似乎不那么需要这个三角形，决定默认不配备这个三角形，若需要让用户自己绘制
     * 缺省值：有
     */
//    boolean bHasTriangle = true;
//
//    public boolean isbHasTriangle() {
//        return bHasTriangle;
//    }
//
//    public void setbHasTriangle(boolean bHasTriangle) {
//        this.bHasTriangle = bHasTriangle;
//    }

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

    /**
     * 字体大小，最好按照textview的大小来设置
     * 缺省值：12
     */
    public float getTextSize() {
        return paint.getTextSize();
    }

    public void setTextSize(float textSize) {
        paint.setTextSize(textSize);
    }

    /**
     * 最大值，最小值
     */
    private int maxValue = 100;
    private int minVale = 0;

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMinVale() {
        return minVale;
    }

    public void setMinVale(int minVale) {
        this.minVale = minVale;
    }

    /**
     * 坐标轴与图表的边界空白大小
     */
    private float space = getTextSize() * 2;

    public float getSpace() {
        return space;
    }

    public void setSpace(float space) {
        this.space = space;
    }
}
