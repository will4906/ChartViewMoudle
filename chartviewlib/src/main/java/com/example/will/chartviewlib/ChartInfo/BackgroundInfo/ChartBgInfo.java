package com.example.will.chartviewlib.ChartInfo.BackgroundInfo;

import android.graphics.Color;

/**
 * @author will4906.
 * @Time 2016/11/22.
 */

public class ChartBgInfo {
    /**
     * 背景色
     * 缺省值：默认为透明
     */
    int bgColor = Color.TRANSPARENT;
    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }
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
     * 对坐标轴的宏定义
     */
    public static final int LEFT_SCALE = 0;
    public static final int BOTTOM_SCALE = 1;
    public static final int RIGHT_SCALE = 2;
    public static final int TOP_SCALE = 3;
    /**
     * 有哪个坐标轴，按左Y轴，下X轴，右Y轴，上X轴的顺序
     * 缺省值：左Y轴，下X轴
     */
    boolean bWhichScale[] = new boolean[]{true,true,false,false};
    public boolean[] getbWhichScale() {
        return bWhichScale;
    }

    public void setbWhichScale(boolean[] bWhichScale) {
        for (boolean whichScale:bWhichScale) {
            if (whichScale){
                this.setbHasScale(true);
            }
        }
        this.bWhichScale = bWhichScale;
    }
    public void setLeftYScale(boolean bHas){
        if (bHas){
            this.setbHasScale(true);
        }
        bWhichScale[LEFT_SCALE] = bHas;
    }
    public void setBottomXScale(boolean bHas){
        if (bHas){
            this.setbHasScale(true);
        }
        bWhichScale[BOTTOM_SCALE] = bHas;
    }
    public void setRightYScale(boolean bHas){
        if (bHas){
            this.setbHasScale(true);
        }
        bWhichScale[RIGHT_SCALE] = bHas;
    }
    public void setTopXScale(boolean bHas){
        if (bHas){
            this.setbHasScale(true);
        }
        bWhichScale[TOP_SCALE] = bHas;
    }
    /**
     * 背景线
     * 缺省值：无
     */
    boolean bHasBgLine = false;
    public boolean isbHasBgLine() {
        return bHasBgLine;
    }

    public void setbHasBgLine(boolean bHasBgLine) {
        this.bHasBgLine = bHasBgLine;
    }

}
