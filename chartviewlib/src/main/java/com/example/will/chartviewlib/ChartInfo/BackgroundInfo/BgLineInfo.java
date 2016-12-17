package com.example.will.chartviewlib.ChartInfo.BackgroundInfo;

import android.graphics.Paint;

import com.example.will.chartviewlib.ChartInfo.BaseInfo.BaseLineInfo;
import com.example.will.chartviewlib.LineChartView;

import static com.example.will.chartviewlib.LineChartView.BGLINE_HORIZONTAL;

/**
 * @author will4906.
 * @Time 2016/11/22.
 */

public class BgLineInfo extends BaseLineInfo{

    public BgLineInfo(){
        textPaint.setAntiAlias(true);
    }
    /**
     * 背景线对应坐标
     */
    private float linePos;

    public float getLinePos() {
        return linePos;
    }

    public void setLinePos(float linePos) {
        this.linePos = linePos;
    }

    /**
     * 线的方向
     */
    private int direction = BGLINE_HORIZONTAL;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * 背景线显示的标题
     */
    private String strTitle = "";

    public String getStrTitle() {
        return strTitle;
    }

    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    /**
     * 背景线标题的位置，水平方向默认在左Y轴
     */
    private int titlePos = LineChartView.LEFT_AXIS;

    public int getTitlePos() {
        return titlePos;
    }

    public void setTitlePos(int titlePos) {
        this.titlePos = titlePos;
    }

    /**
     * 字的画笔
     */
    private Paint textPaint = new Paint();

    public Paint getTextPaint() {
        return textPaint;
    }

    public void setTextPaint(Paint textPaint) {
        this.textPaint = textPaint;
    }

    public void setTextColor(int color){
        textPaint.setColor(color);
    }

    public int getTextColor(){
        return textPaint.getColor();
    }

    public void setTextSize(float textSize){
        textPaint.setTextSize(textSize);
    }

    public float getTextSize(){
        return textPaint.getTextSize();
    }

}
