package com.example.will.chartviewlib.ChartInfo.TouchListener;

import android.view.MotionEvent;

/**
 * @author will4906.
 * @Time 2016/11/26.
 */

public class TouchEngine implements ITouchMode{

    private TouchMode touchMode = new TouchMode();

    @Override
    public int getTouchMode() {
        return touchMode.getTouchMode();
    }

    @Override
    public void setTouchMode(int touchMode) {
        this.touchMode.setTouchMode(touchMode);
    }

    /**
     * 处理触摸事件
     * @param motionEvent
     * @return
     */
    public int answerSingleTouch(MotionEvent motionEvent){
        switch (getTouchMode()){
            case TouchMode.DOUBLE_TOUCH:
                break;
            case TouchMode.SINGLE_TOUCH:
                break;
            default:
                break;
        }
        //这个待会再改
        return 0;
    }
}
