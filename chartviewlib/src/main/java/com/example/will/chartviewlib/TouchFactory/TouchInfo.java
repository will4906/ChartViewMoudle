package com.example.will.chartviewlib.TouchFactory;

/**
 * @author will4906.
 * @Time 2016/11/27.
 */

public class TouchInfo {

    /**
     * 是否处理触摸事件
     */
    private boolean allowTouchEvent = true;

    public boolean isAllowTouchEvent() {
        return allowTouchEvent;
    }

    public void setAllowTouchEvent(boolean allowTouchEvent) {
        this.allowTouchEvent = allowTouchEvent;
    }

    /**
     * 是否处理x轴触摸事件
     */
    private boolean allowTouchX = true;

    public boolean isAllowTouchX() {
        return allowTouchX;
    }

    public void setAllowTouchX(boolean allowTouchX) {
        this.allowTouchX = allowTouchX;
    }

    /**
     * 是否处理y轴触摸事件
     */
    private boolean allowTouchY = true;

    public boolean isAllowTouchY() {
        return allowTouchY;
    }

    public void setAllowTouchY(boolean allowTouchY) {
        this.allowTouchY = allowTouchY;
    }

    /**
     * 是否处理缩放事件
     */
    private boolean allowZoom = true;

    public boolean isAllowZoom() {
        return allowZoom;
    }

    public void setAllowZoom(boolean allowZoom) {
        this.allowZoom = allowZoom;
    }

    /**
     * 是否处理平移事件
     */
    private boolean allowTranslation = true;

    public boolean isAllowTranslation() {
        return allowTranslation;
    }

    public void setAllowTranslation(boolean allowTranslation) {
        this.allowTranslation = allowTranslation;
    }
}
