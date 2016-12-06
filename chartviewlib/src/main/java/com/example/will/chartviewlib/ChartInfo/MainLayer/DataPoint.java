package com.example.will.chartviewlib.ChartInfo.MainLayer;

import com.example.will.chartviewlib.ChartInfo.BaseInfo.BasePointInfo;

/**
 * @author will4906.
 * @Time 2016/12/5.
 */

public class DataPoint extends MainPointInfo {

    /**
     * 对应Y轴数据
     */
    private float YData;

    public float getYData() {
        return YData;
    }

    public void setYData(float YData) {
        this.YData = YData;
    }

    /**
     * X轴上显示的数据
     */
    private String XData = "";

    public String getXData() {
        return XData;
    }

    public void setXData(String XData) {
        this.XData = XData;
    }

    /**
     * 是否显示X轴数据
     */
    private boolean isShowXData = false;

    public boolean isShowXData() {
        return isShowXData;
    }

    public void setShowXData(boolean showXData) {
        isShowXData = showXData;
    }

}
