package com.example.will.chartviewlib.TouchFactory;

/**
 * @author will4906.
 * @Time 2016/11/26.
 */

public interface ITouchParam {
    /**
     * 获取触摸模式
     * @return
     */
    int getTouchMode();

    /**
     * 设置触摸模式
     * @param touchMode
     */
    void setTouchMode(int touchMode);

    /**
     * 设置初次双指点击时的x1,x2
     * @param x1
     * @param x2
     */
    void setDoubleTapX(float x1, float x2);

    /**
     * 设置初次双指点击时的y1,y2
     * @param y1
     * @param y2
     */
    void setDoubleTapY(float y1, float y2);
}
