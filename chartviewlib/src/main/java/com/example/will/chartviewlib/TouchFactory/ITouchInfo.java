package com.example.will.chartviewlib.TouchFactory;

/**
 * @author will4906.
 * @Time 2016/11/27.
 */

public interface ITouchInfo {

    /**
     * 设置允许触摸事件
     * @param allowTouchEvent
     */
    void setAllowTouchEvent(boolean allowTouchEvent);

    /**
     * 设置允许x轴方向触摸事件
     * @param allowTouchX
     */
    void setAllowTouchX(boolean allowTouchX);

    /**
     * 设置允许y轴方向触摸事件
     * @param allowTouchY
     */
    void setAllowTouchY(boolean allowTouchY);

    /**
     * 设置允许缩放
     * @param allowZoom
     */
    void setAllowZoom(boolean allowZoom);

    /**
     * 设置允许平移
     * @param allowTranslation
     */
    void setAllowTranslation(boolean allowTranslation);
}
