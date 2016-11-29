package com.example.will.chartviewlib.ChartInfo;

import android.graphics.Paint;

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
     * 横向分辨率
     */
    public float horizontalResolution = 0;

    public float getHorizontalResolution() {
        return horizontalResolution;
    }

    public void setHorizontalResolution(float horizontalResolution) {
        this.horizontalResolution = horizontalResolution;
    }

    /**
     * 用户可见屏幕理论位置
     */
    private float screenPos = 0;

    public float getScreenPos() {
        return screenPos;
    }

    public void setScreenPos(float screenPos) {
        this.screenPos = screenPos;
    }
}
