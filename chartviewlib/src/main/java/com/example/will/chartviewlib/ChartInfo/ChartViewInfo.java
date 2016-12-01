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


}
