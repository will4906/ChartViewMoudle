package com.example.will.chartviewlib.ChartInfo.BackgroundInfo;

import android.graphics.Color;
import android.graphics.Paint;

import static com.example.will.chartviewlib.LineChartView.BGLINE_HORIZONTAL;

/**
 * @author will4906.
 * @Time 2016/11/22.
 */

public class BgLineInfo {

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
     * 背景线对应纵坐标
     */
    float linePosY;

    public float getLinePosY() {
        return linePosY;
    }

    public void setLinePosY(float linePosY) {
        this.linePosY = linePosY;
    }

    /**
     * 是否虚线
     * 缺省值：否
     */
    boolean bIsDotted = false;

    public boolean isbIsDotted() {
        return bIsDotted;
    }

    public void setbIsDotted(boolean bIsDotted) {
        this.bIsDotted = bIsDotted;
    }

    /**
     * 线的方向
     */
    private int direction = BGLINE_HORIZONTAL;

    public int setDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
