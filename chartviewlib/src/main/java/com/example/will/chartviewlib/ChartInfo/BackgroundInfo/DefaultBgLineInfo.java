package com.example.will.chartviewlib.ChartInfo.BackgroundInfo;

import android.graphics.Color;

import com.example.will.chartviewlib.ChartInfo.BaseInfo.BaseLineInfo;

/**
 * @author will4906.
 * @Time 2016/11/24.
 */

public class DefaultBgLineInfo extends BaseLineInfo{

    public DefaultBgLineInfo(){
        setLineColor(Color.GRAY);
        setIsDotted(true);
        setLineWidth(3);
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
