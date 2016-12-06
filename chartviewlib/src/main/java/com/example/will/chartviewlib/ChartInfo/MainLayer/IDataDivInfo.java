package com.example.will.chartviewlib.ChartInfo.MainLayer;

/**
 * @author will4906.
 * @Time 2016/12/6.
 */

public interface IDataDivInfo {
    /**
     * 统一设置DIV的字体颜色
     * @param textColor
     */
    void setDivTextColor(int textColor);

    /**
     * 设置单条主线的DIV的字体颜色
     * @param index
     * @param textColor
     */
    void setDivTextColor(int index, int textColor);

    /**
     * 统一设置DIV的背景色
     * @param backgroundColor
     */
    void setDivBackgroundColor(int backgroundColor);

    /**
     * 设置单个DIV的背景色
     * @param index
     * @param backgroundColor
     */
    void setDivBackgroundColor(int index, int backgroundColor);
}
