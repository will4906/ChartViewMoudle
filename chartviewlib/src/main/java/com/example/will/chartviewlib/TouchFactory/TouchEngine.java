package com.example.will.chartviewlib.TouchFactory;

import android.view.MotionEvent;

import com.example.will.chartviewlib.DrawFactory.DrawEngine;
import com.example.will.chartviewlib.LineChartView;

/**
 * @author will4906.
 * @Time 2016/11/26.
 */

public class TouchEngine implements ITouchParam {

    private TouchParam touchParam = new TouchParam();
    private TouchInfo touchInfo = new TouchInfo();
    private DrawEngine drawEngine;

    public TouchInfo getTouchInfo() {
        return touchInfo;
    }

    public void setTouchInfo(TouchInfo touchInfo) {
        this.touchInfo = touchInfo;
    }

    /**
     * 去view中接收他的画图引擎
     * @param drawEngine
     */
    public void setDrawEngine(DrawEngine drawEngine) {
        this.drawEngine = drawEngine;
        drawEngine.setTouchParam(touchParam);
    }

    @Override
    public int getTouchMode() {
        return touchParam.getTouchMode();
    }

    @Override
    public void setTouchMode(int touchMode) {
        this.touchParam.setTouchMode(touchMode);
    }

    @Override
    public void setDoubleTapX(float x1, float x2) {
        touchParam.setStartX1(x1);
        touchParam.setStartX2(x2);
        touchParam.setDoubleTouchDistanceX(Math.abs(x1 - x2));
    }

    @Override
    public void setDoubleTapY(float y1, float y2) {
        touchParam.setStartY1(y1);
        touchParam.setStartY2(y2);
        touchParam.setDoubleTouchDistanceY(Math.abs(y1 - y2));
    }

    @Override
    public void setDownX(float downX) {
        touchParam.setDownX(downX);
    }

    @Override
    public void setDownY(float downY) {
        touchParam.setDownY(downY);
    }

    @Override
    public void setTouchOffsetX(float touchOffsetX) {
        touchParam.setTouchOffsetX(touchOffsetX);
    }

    @Override
    public void setTwoPointsMiddleX(float twoPointsMiddleX) {
        touchParam.setTwoPointsMiddleX(twoPointsMiddleX);
    }

    @Override
    public void setAddResolutionX(float addResolutionX) {
        touchParam.setAddResolutionX(addResolutionX);
    }

    /**
     * 缩放比例，防止过快的缩放，这个应该不打算让用户调整
     */
    public static int MAGNIFICATION = 10;
    /**
     * 处理触摸事件
     * @param event
     * @return
     */
    public boolean answerTouch(MotionEvent event){
        synchronized (this){
            boolean bAskForReDraw = false;
            switch (getTouchMode()){
                case TouchParam.DOUBLE_TOUCH: {
                    if (touchInfo.isAllowZoom()){
                        bAskForReDraw = answerDoubleTouch(event);
                    }
                }
                break;
                case TouchParam.SINGLE_TOUCH:{
                    if (touchInfo.isAllowTranslation()){
                        bAskForReDraw = answerSingleTouch(event);
                    }
                }
                break;
                default:
                    break;
            }
            //待会再改
            return bAskForReDraw;
        }
    }

    /**
     * 应答双指按压
     * @param event
     * @return
     */
    private boolean answerDoubleTouch(MotionEvent event){
        boolean bAskForReDraw = false;
        boolean bX = false;
        boolean bY = false;
        int cnt = event.getPointerCount();
        if (cnt == 2){
            if (touchInfo.isAllowTouchX()){
                bX = answerDoubleTouchX(event);
            }
            if (touchInfo.isAllowTouchY()){
                bY = answerDoubleTouchY(event);
            }
            if (bX || bY){
                bAskForReDraw = true;
            }
        }
        return bAskForReDraw;
    }

    private float addXResolution = 0;
    /**
     * 应答双指按压横向数据处理
     * @param event
     */
    private boolean answerDoubleTouchX(MotionEvent event){
        float middle = (event.getX(0) - event.getX(1)) / 2 + event.getX(0);
        setTwoPointsMiddleX(middle);
        float nowXlen = Math.abs(event.getX(0) - event.getX(1));
        addXResolution += nowXlen - touchParam.getDoubleTouchDistanceX();
        setAddResolutionX(addXResolution / MAGNIFICATION);
//        float newXResolution = drawEngine.getChartViewInfo().getHorizontalResolution() + addXResolution / MAGNIFICATION;
//        if (newXResolution < 0) {
//            newXResolution = 0;
//        }
//        if (newXResolution > drawEngine.getBackgroundWidth() - drawEngine.getScaleInfos()[LineChartView.RIGHT_SCALE].getSpace() - drawEngine.getScaleInfos()[LineChartView.LEFT_SCALE].getSpace()) {
//            newXResolution = drawEngine.getBackgroundWidth() - drawEngine.getScaleInfos()[LineChartView.RIGHT_SCALE].getSpace() - drawEngine.getScaleInfos()[LineChartView.LEFT_SCALE].getSpace();
//        }
//        drawEngine.getChartViewInfo().setHorizontalResolution(newXResolution);
        addXResolution = 0;
        //必须要，不然会导致放大夸张，缩小极难
        touchParam.setDoubleTouchDistanceX(nowXlen);
        return true;
    }

    /**
     * 应答双指按压纵向数据处理
     * @param event
     */
    private boolean answerDoubleTouchY(MotionEvent event){
        float nowYlen = Math.abs(event.getY(0) - event.getY(1));

        //目前基本是直接return true就好，不排除将来会有其他情况
        return false;
    }

    /**
     * 应答单指按压数据处理
     * @param event
     * @return
     */
    private boolean answerSingleTouch(MotionEvent event){
        boolean bX = false;
        boolean bY = false;
        if (touchInfo.isAllowTouchX()){
            bX = answerSingleTouchX(event);
        }
        if (touchInfo.isAllowTouchY()){

        }
        if (bX || bY){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 应答横向处理数据
     * @param event
     * @return
     */
    private boolean answerSingleTouchX(MotionEvent event){
        float downX = touchParam.getDownX();
        float nowX = event.getX();
        float oldOffsetX = touchParam.getTouchOffsetX();
//        touchParam.setTouchOffsetX(downX - nowX + oldOffsetX);
        touchParam.setTouchOffsetX(downX - nowX);
        //必须要，否则会导致反向难
        touchParam.setDownX(nowX);
        return true;
    }
}
