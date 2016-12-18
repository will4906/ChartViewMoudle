package com.example.will.chartviewlib.ChartInfo;

import android.graphics.Paint;

import static com.example.will.chartviewlib.LineChartView.FORWARD_MODE;

/**
 * @author will4906.
 * @Time 2016/11/23.
 */

public class ChartViewInfo {
    /**
     * 画图信息核心
     */
    private Paint paint = new Paint();

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    /**
     * 字体大小
     * @return
     */
    public float getTextSize() {
        return paint.getTextSize();
    }

    public void setTextSize(float textSize) {
        paint.setTextSize(textSize);
    }

    /**
     * Y轴方向缩放比例限制
     */
    private int limitY = 1;

    public int getLimitY() {
        return limitY;
    }

    public void setLimitY(int limitY) {
        this.limitY = limitY;
    }

    /**
     * 绘制模式，默认为递进模式
     */
    private int drawMode = FORWARD_MODE;

    public int getDrawMode() {
        return drawMode;
    }

    public void setDrawMode(int drawMode) {
        this.drawMode = drawMode;
    }
}
