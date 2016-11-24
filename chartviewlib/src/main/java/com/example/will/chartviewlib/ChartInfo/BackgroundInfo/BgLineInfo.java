package com.example.will.chartviewlib.ChartInfo.BackgroundInfo;

import com.example.will.chartviewlib.ChartInfo.BaseInfo.BaseLineInfo;

import static com.example.will.chartviewlib.LineChartView.BGLINE_HORIZONTAL;

/**
 * @author will4906.
 * @Time 2016/11/22.
 */

public class BgLineInfo extends BaseLineInfo{

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
