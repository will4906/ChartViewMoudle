package com.example.will.chartviewlib.ChartInfo.BaseInfo;

import android.graphics.Paint;

/**
 * @author will4906.
 * @Time 2016/11/24.
 */

public class BasePointInfo {

    protected Paint paint = new Paint();
    public BasePointInfo(){
        paint.setAntiAlias(true);
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    /**
     * 点的颜色
     */
    public int getColor() {
        return paint.getColor();
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    /**
     * 点的半径
     */
    private float radius = (float) 0.5;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
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
