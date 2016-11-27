package com.example.will.chartviewlib.TouchFactory;

/**
 * @author will4906.
 * @Time 2016/11/26.
 */

public class TouchParam {
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

    /**
     * 双指点击时两手指横向间距
     */
    private float doubleTouchDistanceX = 0;

    public float getDoubleTouchDistanceX() {
        return doubleTouchDistanceX;
    }

    public void setDoubleTouchDistanceX(float doubleTouchDistanceX) {
        this.doubleTouchDistanceX = doubleTouchDistanceX;
    }

    /**
     * 初次双指按压的对应两个横向坐标值
     */
    private float startX1 = 0;
    private float startX2 = 0;

    public float getStartX1() {
        return startX1;
    }

    public void setStartX1(float startX1) {
        this.startX1 = startX1;
    }

    public float getStartX2() {
        return startX2;
    }

    public void setStartX2(float startX2) {
        this.startX2 = startX2;
    }

    /**
     * 双指点击时两手指纵向间距
     */
    private float doubleTouchDistanceY = 0;

    public float getDoubleTouchDistanceY() {
        return doubleTouchDistanceY;
    }

    public void setDoubleTouchDistanceY(float doubleTouchDistanceY) {
        this.doubleTouchDistanceY = doubleTouchDistanceY;
    }

    /**
     * 初次双指按压的对应两个纵坐标值
     */
    private float startY1 = 0;
    private float startY2 = 0;

    public float getStartY1() {
        return startY1;
    }

    public void setStartY1(float startY1) {
        this.startY1 = startY1;
    }

    public float getStartY2() {
        return startY2;
    }

    public void setStartY2(float startY2) {
        this.startY2 = startY2;
    }

    /**
     * 初次单指触摸对应横纵坐标值
     */
    private float downX = 0;
    private float downY = 0;

    public float getDownX() {
        return downX;
    }

    public void setDownX(float downX) {
        this.downX = downX;
    }

    public float getDownY() {
        return downY;
    }

    public void setDownY(float downY) {
        this.downY = downY;
    }

    /**
     * 手指触摸偏移
     */
    private float touchOffsetX = 0;

    public float getTouchOffsetX() {
        return touchOffsetX;
    }

    public void setTouchOffsetX(float touchOffsetX) {
        this.touchOffsetX = touchOffsetX;
    }
}
