package com.example.will.chartviewlib.DrawFactory;

import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.AxisInfo;
import com.example.will.chartviewlib.ChartInfo.MainLayer.MainLineInfo;

/**
 * @author will4906.
 * @Time 2016/12/18.
 */

public class DrawComputer {

    /**
     * 计算起始位置
     * @param screenMove
     * @param radius
     * @param size
     * @return
     */
    public static int computeStart(MainLineInfo mainLineInfo, float screenMove, float radius, int size){
        int start = (int)((screenMove - radius) / (mainLineInfo.getHorizontalResolution() + radius * 2));
        if (start < 0){
            start = 0;
        }
        if (start > size){
            start = size;
        }
        return start;
    }

    /**
     * 将用户传进来的数据转换为像素数据
     * @param userData
     * @param length
     * @return
     */
    public static float changeUserDataToChartViewData(float userData, float length, AxisInfo axisInfo){
        float chartViewData = 0;
        float max = axisInfo.getMaxValue();
        float min = axisInfo.getMinVale();
        float div = (max - min) / length;
        chartViewData = (userData - min) / div;
        return chartViewData;
    }

    /**
     * 计算一个图表里应该有多少数据
     * @param width
     * @return
     */
    public static int computePoints(MainLineInfo mainLineInfo, float width){
        float radius = mainLineInfo.getMainPointInfo().getRadius();
        int pointSum = (int)(width / (radius * 2 + mainLineInfo.getHorizontalResolution()));
        return ++pointSum;
    }

}
