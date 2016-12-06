package com.example.will.chartviewlib.ChartInfo.MainLayer;

import android.graphics.Color;

import com.example.will.chartviewlib.ChartInfo.BaseInfo.BaseDivInfo;

/**
 * @author will4906.
 * @Time 2016/12/6.
 */

public class DataDivInfo extends BaseDivInfo {
    //TODO 这里可以逐渐完善，但是时间有限，暂时先保留此状

    /**
     * 数据框内字的颜色
     */
    private int textColor = Color.WHITE;

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
