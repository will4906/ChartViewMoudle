package com.example.will.chartviewlib.ChartInfo.BackgroundInfo;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * @author will4906.
 * @Time 2016/11/24.
 */

public class DefaultBgLineInfo {

    public DefaultBgLineInfo(){
        setLineColor(Color.GRAY);
        setIsDotted(true);
        setLineWidth(3);
    }
    private Paint paint = new Paint();

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    /**
     * 背景线宽度
     */

    public float getLineWidth() {
        return paint.getStrokeWidth();
    }

    public void setLineWidth(float lineWidth) {
        paint.setStrokeWidth(lineWidth);
    }

    /**
     * 背景线颜色
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

    /**
     * 是否竖直和是否水平
     */
    boolean isVertical = true;

    boolean isHorizontal = true;

    public boolean isVertical() {
        return isVertical;
    }

    public void enableVertical(boolean vertical) {
        isVertical = vertical;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void enableHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }
}
