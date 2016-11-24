package com.example.will.chartviewlib.ChartInfo.MainLayer;

import android.graphics.Color;

import com.example.will.chartviewlib.ChartInfo.BaseInfo.BaseLineInfo;


/**
 * @author will4906.
 * @Time 2016/11/24.
 */

public class MainLineInfo extends BaseLineInfo{

    public MainLineInfo(){
        setLineColor(Color.GREEN);
    }
    /**
     * 是否显示点，若为否，该点位置只用线连接
     */
    private boolean hasPoint = true;

    public boolean isHasPoint() {
        return hasPoint;
    }

    public void setHasPoint(boolean hasPoint) {
        this.hasPoint = hasPoint;
    }

    /**
     * 是否显示线，若为否则只显示点
     */
    private boolean hasLine = true;
    public boolean isHasLine() {
        return hasLine;
    }

    public void setHasLine(boolean hasLine) {
        this.hasLine = hasLine;
    }

    /**
     * 考虑到代码复杂性和实用性问题，决定只使用同一的点样式，若有更改另行改变
     */
    private MainPointInfo mainPointInfo = new MainPointInfo();

    public MainPointInfo getMainPointInfo() {
        return mainPointInfo;
    }

    public void setMainPointInfo(MainPointInfo mainPointInfo) {
        this.mainPointInfo = mainPointInfo;
    }
}
