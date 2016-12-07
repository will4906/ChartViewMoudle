package com.example.will.chartviewlib.TouchFactory;

import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import com.example.will.chartviewlib.Common.FloatTool;
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

    public TouchParam getTouchParam() {
        return touchParam;
    }

    public void setTouchParam(TouchParam touchParam) {
        this.touchParam = touchParam;
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

    @Override
    public void setTwoPointsMiddleY(float twoPointsMiddleY) {
        touchParam.setTwoPointsMiddleY(twoPointsMiddleY);
    }

    @Override
    public void setDoubleTouchDistance(float doubleTouchDistance) {
        touchParam.setDoubleTouchDistance(doubleTouchDistance);
    }

    private boolean bChangeBackground = false;

    public boolean isChangeBackground() {
        return bChangeBackground;
    }

    public void setChangeBackground(boolean bChangeBackground) {
        this.bChangeBackground = bChangeBackground;
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
                if (bY){
                    setChangeBackground(true);
                }
            }
            if (bX || bY){
                bAskForReDraw = true;
            }
            //必须要，不然会导致放大夸张，缩小极难
            setDoubleTouchDistance((float) Math.sqrt(Math.abs(Math.pow(event.getX(0) - event.getX(1),2) + Math.pow(event.getY(0) - event.getY(1),2))));
        }
        return bAskForReDraw;
    }

    private float addXResolution = 0;
    /**
     * 应答双指按压横向数据处理
     * @param event
     */
    private boolean answerDoubleTouchX(MotionEvent event){
//        float nowXlen = (float) Math.sqrt(Math.abs(Math.pow(event.getX(0) - event.getX(1),2) + Math.pow(event.getY(0) - event.getY(1),2)));
        float nowXlen = Math.abs(event.getX(0) - event.getX(1));
        addXResolution += nowXlen - touchParam.getDoubleTouchDistanceX();
        setAddResolutionX(addXResolution / MAGNIFICATION);              //此处是否需要比例缩放，缩放比例多少有待考量
        addXResolution = 0;
        touchParam.setDoubleTouchDistanceX(nowXlen);
        return true;
    }

    /**
     * Y轴方向缩放比例
     */
    private final static int Y_MAGNIFICATION = 20;
    /**
     * 应答双指按压纵向数据处理，这真的是一个艰苦卓绝的缩放函数，或许以后还会在改进，但是最近的话就这样吧
     * @param event
     */
    private boolean answerDoubleTouchY(MotionEvent event){
        //TODO 关于Y轴方向的缩放有待升级
        float userMax = drawEngine.getScaleInfos()[LineChartView.LEFT_SCALE].getUserMax();
        float userMin = drawEngine.getScaleInfos()[LineChartView.LEFT_SCALE].getUserMin();
        float userLen = FloatTool.accurateMinus(userMax, userMin);
        float userDiv = userLen / Y_MAGNIFICATION;
        int limit = drawEngine.getChartViewInfo().getLimitY();
        float addLimit = limit * userLen;
//        float nowYnolen = (float) Math.sqrt(Math.abs(Math.pow(event.getX(0) - event.getX(1),2) + Math.pow(event.getY(0) - event.getY(1),2)));
        float nowYnolen = Math.abs(event.getY(0) - event.getY(1));
        float nowYlen = nowYnolen - touchParam.getDoubleTouchDistanceY();
        float chartHeight = drawEngine.getChartHeight();            //此处应考虑到上下两轴的间距应不会总是改变所以可用，但是不排除以后会有风险
        float limitDiv = chartHeight / Y_MAGNIFICATION;
        boolean reDrawOkMin = false;
        boolean reDrawOkMax = false;

        if (Math.abs(nowYlen) > limitDiv){
            //缩放了几个单位
            int addYMultiple = (int)FloatTool.Rounding(0,nowYlen / limitDiv);
            float addY = FloatTool.accurateMultiply(addYMultiple, userDiv);
            //此处得到的middle为坐标轴向下的Y轴坐标，应先进行转换
            float middle = touchParam.getTwoPointsMiddleY();
            float max = drawEngine.getScaleInfos()[LineChartView.LEFT_SCALE].getMaxValue();
            float min = drawEngine.getScaleInfos()[LineChartView.LEFT_SCALE].getMinVale();
            middle = FloatTool.accurateMinus(drawEngine.getBackgroundHeight(), middle);
            middle -= drawEngine.getScaleInfos()[LineChartView.BOTTOM_SCALE].getSpace() + drawEngine.getScaleInfos()[LineChartView.BOTTOM_SCALE].getScaleWidth();
            if (middle < 0){
                middle = 0;
            }
            if (middle - chartHeight > 0){
                middle = chartHeight;
            }
            float div = middle / chartHeight;
            //求出中点对应的Y值
            float middleData = (max - min) * div + min;
            /*根据以下公式
            middleData = (max - min) * (middle / chartHeight) + min
            不变	        可能变	   不变	        不变	   变
            middleData - min = (max - min) * (middle / chartHeight)
            (middleData - min) / (middle / chartHeight) + min = max*/
            if (min < userMin - addLimit || min > userMax + addLimit){
                min = FloatTool.accurateAdd(min, addY);
                if (min < userMin - addLimit|| min > userMax + addLimit){
                    reDrawOkMin = false;
                }else{
                    reDrawOkMin = true;
                    max = (middleData - min) / (middle / chartHeight) + min;
                    max =  Float.valueOf(FloatTool.getFormatPointAfterString(max,FloatTool.getPointAfter(min)));
                    if (max >= userMax - addLimit && max <= userMax + addLimit){
                        if (max <= min){
                            reDrawOkMax = false;
                        }else{
                            reDrawOkMax = true;
                            if (min > max - userDiv){
                                min = max - userDiv;
                            }
                            drawEngine.getScaleInfos()[LineChartView.LEFT_SCALE].setMinVale(min);
                            drawEngine.getScaleInfos()[LineChartView.LEFT_SCALE].setMaxValue(max);
                        }
                    }
                }
            }else{
                reDrawOkMin = true;
                min = FloatTool.accurateAdd(min, addY);
                if (min < userMin - addLimit){
                    min = userMin - addLimit;
                }
                if (min > userMax + addLimit){
                    min = userMax + addLimit;
                }
                max = (middleData - min) / (middle / chartHeight) + min;
                max =  Float.valueOf(FloatTool.getFormatPointAfterString(max,FloatTool.getPointAfter(min)));
                if (max >= userMax - addLimit && max <= userMax + addLimit){
                    if (max <= min){
                        reDrawOkMax = false;
                    }else{
                        reDrawOkMax = true;
                        if (min > max - userDiv){
                            min = max - userDiv;
                        }
                        drawEngine.getScaleInfos()[LineChartView.LEFT_SCALE].setMinVale(min);
                        drawEngine.getScaleInfos()[LineChartView.LEFT_SCALE].setMaxValue(max);
                    }
                }
            }
        }else {
            return false;
        }
        touchParam.setDoubleTouchDistanceY(nowYnolen);
        //目前基本是直接return true就好，不排除将来会有其他情况
        return reDrawOkMax && reDrawOkMin;
    }

    /**
     * 应答单指按压数据处理
     * @param event
     * @return
     */
    public boolean answerSingleTouch(MotionEvent event){
        boolean bX = false;
        boolean bY = false;
        if (touchInfo.isAllowTouchX()){
            bX = answerSingleTouchX(event);
        }
        if (touchInfo.isAllowTouchY()){
            bY = answerSingleTouchY(event);
            if (bY){
                setChangeBackground(true);
            }
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
        touchParam.setTmpOffsetX(touchParam.getTouchOffsetX() + downX - nowX);
        touchParam.setTouchOffsetX(downX - nowX);
        //必须要，否则会导致反向难
        touchParam.setDownX(nowX);
        return true;
    }

    private float addYResolution = 0;
    /**
     * 应答纵向处理数据
     * @param event
     * @return
     */
    private boolean answerSingleTouchY(MotionEvent event){
        boolean reDrawMin = false;
        boolean reDrawMax = false;
        float downY = touchParam.getDownY();
        float nowY = event.getY();
        float YLen = downY - nowY;
        addYResolution += YLen;
        float userMax = drawEngine.getScaleInfos()[LineChartView.LEFT_SCALE].getUserMax();
        float userMin = drawEngine.getScaleInfos()[LineChartView.LEFT_SCALE].getUserMin();
        float userLen = FloatTool.accurateMinus(userMax, userMin);
        float userDiv = userLen / Y_MAGNIFICATION;
        int limit = drawEngine.getChartViewInfo().getLimitY();
        float addLimit = limit * userLen;
        float chartHeight = drawEngine.getChartHeight();            //此处应考虑到上下两轴的间距应不会总是改变所以可用，但是不排除以后会有风险
        float limitDiv = chartHeight / Y_MAGNIFICATION;
        if (Math.abs(addYResolution) > limitDiv){
            //移动了几个单位
            int addYMultiple = (int)FloatTool.Rounding(0,addYResolution / limitDiv);
            addYResolution = 0;
            float addY = FloatTool.accurateMultiply(addYMultiple, userDiv);
            float max = drawEngine.getScaleInfos()[LineChartView.LEFT_SCALE].getMaxValue();
            float min = drawEngine.getScaleInfos()[LineChartView.LEFT_SCALE].getMinVale();
            if (addY < 0){
                if (max >= userMax + addLimit){
                    reDrawMax = false;
                }else{
                    reDrawMax = true;
                    reDrawMin = true;
                    max -= addY;
                    min -= addY;
                    if (max > userMax + addLimit){
                        min = min - (max - (userMax + addLimit));
                        max = userMax + addLimit;
                    }
                    drawEngine.getScaleInfos()[LineChartView.LEFT_SCALE].setMinVale(min);
                    drawEngine.getScaleInfos()[LineChartView.LEFT_SCALE].setMaxValue(max);
                }
            }else{
                if (min <= userMin - addLimit){
                    reDrawMin = false;
                }else{
                    reDrawMax = true;
                    reDrawMin = true;
                    max -= addY;
                    min -= addY;
                    if (min < userMin - addLimit){
                        max = max - (min - (userMin - addLimit));
                        min = userMin - addLimit;
                    }
                    drawEngine.getScaleInfos()[LineChartView.LEFT_SCALE].setMinVale(min);
                    drawEngine.getScaleInfos()[LineChartView.LEFT_SCALE].setMaxValue(max);
                }
            }
        }
        //必须要，否则会导致反向难
        touchParam.setDownY(nowY);
        return reDrawMax && reDrawMin;
    }
}
