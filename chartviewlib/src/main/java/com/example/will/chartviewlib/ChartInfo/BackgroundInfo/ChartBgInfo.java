package com.example.will.chartviewlib.ChartInfo.BackgroundInfo;

import android.graphics.Color;
import android.graphics.Paint;

//静态导入，jdk5新特性
import static com.example.will.chartviewlib.LineChartView.BOTTOM_AXIS;
import static com.example.will.chartviewlib.LineChartView.LEFT_AXIS;
import static com.example.will.chartviewlib.LineChartView.RIGHT_AXIS;
import static com.example.will.chartviewlib.LineChartView.TOP_AXIS;

/**
 * @author will4906.
 * @Time 2016/11/22.
 */

public class ChartBgInfo {

    /**
     * 是否有坐标轴
     * 缺省值：有
     */
    boolean bHasAxis = true;
    public boolean isbHasAxis() {
        return bHasAxis;
    }

    public void setbHasAxis(boolean bHasAxis) {
        this.bHasAxis = bHasAxis;
    }

    /**
     * 有哪个坐标轴，按左Y轴，下X轴，右Y轴，上X轴的顺序
     * 缺省值：左Y轴，下X轴
     */
    boolean bWhichAxis[] = new boolean[]{true,true,false,false};
    public boolean[] getWhichAxisEnable() {
        return bWhichAxis;
    }

    public void enableAxiss(boolean[] bWhichAxis) {
        for (boolean whichAxis:bWhichAxis) {
            if (whichAxis){
                this.setbHasAxis(true);
            }
        }
        this.bWhichAxis = bWhichAxis;
    }
    public void enableLeftAxis(boolean bHas){
        if (bHas){
            this.setbHasAxis(true);
        }
        bWhichAxis[LEFT_AXIS] = bHas;
    }
    public void enableBottomAxis(boolean bHas){
        if (bHas){
            this.setbHasAxis(true);
        }
        bWhichAxis[BOTTOM_AXIS] = bHas;
    }
    public void enableRightAxis(boolean bHas){
        if (bHas){
            this.setbHasAxis(true);
        }
        bWhichAxis[RIGHT_AXIS] = bHas;
    }
    public void enableTopAxis(boolean bHas){
        if (bHas){
            this.setbHasAxis(true);
        }
        bWhichAxis[TOP_AXIS] = bHas;
    }
    /**
     * 背景线
     * 缺省值：无
     */
    boolean bHasBgLine = true;
    public boolean hasBgLine() {
        return bHasBgLine;
    }

    public void enableBgLine(boolean bHasBgLine) {
        this.bHasBgLine = bHasBgLine;
    }

    /**
     * 使用默认背景线
     */
    private boolean useDefaultBgLines = true;

    public boolean isUseDefaultBgLines() {
        return useDefaultBgLines;
    }

    public void setUseDefaultBgLines(boolean useDefaultBgLines) {
        this.useDefaultBgLines = useDefaultBgLines;
    }
}
