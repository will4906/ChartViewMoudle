package com.example.will.chartviewlib.ChartInfo.BaseInfo;

import android.graphics.Paint;

/**
 * 线的基类
 * @author will4906.
 * @Time 2016/11/24.
 */

public class BaseLineInfo {

    public BaseLineInfo(){
        paint.setAntiAlias(true);
    }

    protected Paint paint = new Paint();

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    /**
     * 线宽度
     */
    public float getLineWidth() {
        return paint.getStrokeWidth();
    }

    public void setLineWidth(float lineWidth) {
        paint.setStrokeWidth(lineWidth);
    }

    /**
     * 线颜色
     */
    public int getLineColor() {
        return paint.getColor();
    }

    public void setLineColor(int lineColor) {
        paint.setColor(lineColor);
    }

    /**
     * 是否虚线
     * 缺省值：否
     */
    boolean bIsDotted = false;

    public boolean isbIsDotted() {
        return bIsDotted;
    }

    public void setIsDotted(boolean bIsDotted) {
        this.bIsDotted = bIsDotted;
    }

}
