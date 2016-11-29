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

    /**
     * 设置单指初次触摸是的x
     * @param downX
     */
    void setDownX(float downX);

    /**
     * 设置单指初次触摸的Y
     * @param downY
     */
    void setDownY(float downY);

    /**
     * 设置触摸手指偏移量
     * @param touchOffsetX
     */
    void setTouchOffsetX(float touchOffsetX);

    /**
     * 设置双指触摸时两指中间的横坐标
     * @param twoPointsMiddleX
     */
    void setTwoPointsMiddleX(float twoPointsMiddleX);

    /**
     * 设置双指触摸时增加的横向分辨率
     * @param addResolutionX
     */
    void setAddResolutionX(float addResolutionX);
}
