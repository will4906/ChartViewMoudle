package com.example.will.chartviewlib.ChartInfo.TouchListener;

/**
 * @author will4906.
 * @Time 2016/11/26.
 */

public class TouchMode {
    /**
     * 无手指触摸
     */
    public static final int NO_TOUCH = 0x100;
    /**
     * 单指触摸
     */
    public static final int SINGLE_TOUCH = 0x101;
    /**
     * 双指触摸
     */
    public static final int DOUBLE_TOUCH = 0x102;

    /**
     * 触摸模式
     */
    private int touchMode = NO_TOUCH;

    public int getTouchMode() {
        return touchMode;
    }

    public void setTouchMode(int touchMode) {
        this.touchMode = touchMode;
    }
}
