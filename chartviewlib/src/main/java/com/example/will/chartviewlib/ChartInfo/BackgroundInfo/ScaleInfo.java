package com.example.will.chartviewlib.ChartInfo.BackgroundInfo;

import android.graphics.Paint;

import com.example.will.chartviewlib.ChartInfo.BaseInfo.BaseLineInfo;
import com.example.will.chartviewlib.Common.FloatTool;

/**
 * Created by will on 2016/11/22.
 */

public class ScaleInfo extends BaseLineInfo{

    public ScaleInfo(){
        textPaint = new Paint(paint);
    }
    /**
     * 字的画笔
     */
    private Paint textPaint;

    public Paint getTextPaint() {
        return textPaint;
    }

    public void setTextPaint(Paint textPaint) {
        this.textPaint = textPaint;
    }

    public void setTextColor(int color){
        this.textPaint.setColor(color);
    }

    public int getTextColor(){
        return this.textPaint.getColor();
    }
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
        return textPaint.getTextSize();
    }

    public void setTextSize(float textSize) {
        paint.setTextSize(textSize);
        textPaint.setTextSize(textSize);
    }

    /**
     * 最大值，最小值
     */
    private float maxValue = 100;
    private float minVale = 0;

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        int maxAfterPointLen = FloatTool.getPointAfter(maxValue);
        //若超过6位小数则保留6位
        if (maxAfterPointLen > 6){
            maxAfterPointLen = 6;
        }
        String strMax = FloatTool.getFormatPointAfterString(maxValue,maxAfterPointLen);
        maxValue = Float.valueOf(strMax);
        this.maxValue = maxValue;
    }

    public float getMinVale() {
        return minVale;
    }

    public void setMinVale(float minVale) {
        int minAfterPointLen = FloatTool.getPointAfter(minVale);
        if (minAfterPointLen > 6){
            minAfterPointLen = 6;
        }
        String strMin = FloatTool.getFormatPointAfterString(minVale,minAfterPointLen);
        minVale = Float.valueOf(strMin);
        this.minVale = minVale;
    }

    /**
     * 坐标轴与图表的边界空白大小
     */
    private float space = paint.getTextSize() * 2;

    public float getSpace() {
        return space;
    }

    public void setSpace(float space) {
        this.space = space;
    }

    /**
     * 用户设定的最大值
     */
    private float userMax = 0;
    /**
     * 用户设定的最小值
     */
    private float userMin = 0;

    public float getUserMax() {
        return userMax;
    }

    public void setUserMax(float userMax) {
        this.userMax = userMax;
    }

    public float getUserMin() {
        return userMin;
    }

    public void setUserMin(float userMin) {
        this.userMin = userMin;
    }

    /**
     * 自动调整字的相关属性
     */
    private boolean autoText = true;

    public boolean isAutoText() {
        return autoText;
    }

    public void setAutoText(boolean autoText) {
        this.autoText = autoText;
    }
}
