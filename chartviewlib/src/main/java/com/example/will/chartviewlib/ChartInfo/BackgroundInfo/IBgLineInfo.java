package com.example.will.chartviewlib.ChartInfo.BackgroundInfo;

/**
 * @author will4906.
 * @Time 2016/11/24.
 */

public interface IBgLineInfo {
    /**
     * 使能背景线
     * @param bHas
     */
    void enableBackgroundLine(boolean bHas);

    /**
     * 设置背景线
     * @param bgLineInfo
     */
    void addBackgroundLine(BgLineInfo bgLineInfo);

    /**
     * 删除某条背景线
     * @param index
     */
    void removeBackgroundLine(int index);

    /**
     * 删除全部背景线
     */
    void removeAllBackgroundLines();

    /**
     * 使用默认背景线
     * @param use
     */
    void useDefaultBackgroundLines(boolean use);
}
