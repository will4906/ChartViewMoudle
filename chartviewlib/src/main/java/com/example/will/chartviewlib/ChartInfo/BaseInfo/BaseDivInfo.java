package com.example.will.chartviewlib.ChartInfo.BaseInfo;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * @author will4906.
 * @Time 2016/12/6.
 */

public class BaseDivInfo {

    public BaseDivInfo(){
        paint.setColor(Color.GRAY);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
    }
    protected Paint paint = new Paint();

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    /**
     * 框的背景颜色
     */

    public int getBackgroundColor() {
        return paint.getColor();
    }

    public void setBackgroundColor(int backgroundColor) {
        paint.setColor(backgroundColor);
    }

    /**
     * 框的宽度
     */
    private float width = 50;

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * 框的高度
     */
    private float height = 50;

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
