package com.example.will.chartviewlib.ChartInfo.BackgroundInfo;

import android.graphics.Color;
import android.graphics.Paint;

//静态导入，jdk5新特性
import static com.example.will.chartviewlib.LineChartView.BOTTOM_SCALE;
import static com.example.will.chartviewlib.LineChartView.LEFT_SCALE;
import static com.example.will.chartviewlib.LineChartView.RIGHT_SCALE;
import static com.example.will.chartviewlib.LineChartView.TOP_SCALE;

/**
 * @author will4906.
 * @Time 2016/11/22.
 */

public class ChartBgInfo {

    /**
     * 是否有坐标轴
     * 缺省值：有
     */
    boolean bHasScale = true;
    public boolean isbHasScale() {
        return bHasScale;
    }

    public void setbHasScale(boolean bHasScale) {
        this.bHasScale = bHasScale;
    }

    /**
     * 有哪个坐标轴，按左Y轴，下X轴，右Y轴，上X轴的顺序
     * 缺省值：左Y轴，下X轴
     */
    boolean bWhichScale[] = new boolean[]{true,true,false,false};
    public boolean[] getWhichScaleEnable() {
        return bWhichScale;
    }

    public void enableScales(boolean[] bWhichScale) {
        for (boolean whichScale:bWhichScale) {
            if (whichScale){
                this.setbHasScale(true);
            }
        }
        this.bWhichScale = bWhichScale;
    }
    public void enableLeftScale(boolean bHas){
        if (bHas){
            this.setbHasScale(true);
        }
        bWhichScale[LEFT_SCALE] = bHas;
    }
    public void enableBottomScale(boolean bHas){
        if (bHas){
            this.setbHasScale(true);
        }
        bWhichScale[BOTTOM_SCALE] = bHas;
    }
    public void enableRightScale(boolean bHas){
        if (bHas){
            this.setbHasScale(true);
        }
        bWhichScale[RIGHT_SCALE] = bHas;
    }
    public void enableTopScale(boolean bHas){
        if (bHas){
            this.setbHasScale(true);
        }
        bWhichScale[TOP_SCALE] = bHas;
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
