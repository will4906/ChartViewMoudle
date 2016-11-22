package com.example.will.chartviewlib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.ChartBgInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.IScaleInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.ScaleInfo;

/**
 * 线性表的父类，主要用来做信息的接口
 * @author will4906.
 * @Time 2016/11/22.
 */

public class BaseLineChart extends View implements IScaleInfo{

    private ChartBgInfo chartBgInfo = new ChartBgInfo();

    /**
     * 对坐标轴数据的枚举
     */
    protected enum ScaleInfoEnum{
        LEFT(new ScaleInfo()),BOTTOM(new ScaleInfo()),RIGHT(new ScaleInfo()),TOP(new ScaleInfo());
        private ScaleInfo scaleInfo;
        public ScaleInfo getScaleInfo(){
            return scaleInfo;
        }
        public void setScaleInfo(ScaleInfo scaleInfo){
            this.scaleInfo = scaleInfo;
        }
        private ScaleInfoEnum(ScaleInfo scaleInfo){
            this.scaleInfo = scaleInfo;
        }
    }

    @Override
    public void setScaleColor(int color){
        for (ScaleInfoEnum s:ScaleInfoEnum.values()) {
            s.getScaleInfo().setScaleColor(color);
        }
    }

    @Override
    public void setScaleColor(int whichScale,int color){
        ScaleInfoEnum[] enumArr = ScaleInfoEnum.values();
        enumArr[whichScale].getScaleInfo().setScaleColor(color);
    }

    @Override
    public void setScaleWidth(float width){
        for (ScaleInfoEnum s:ScaleInfoEnum.values()) {
            s.getScaleInfo().setScaleWidth(width);
        }
    }

    @Override
    public void setScaleWidth(int whichScale, float width){
        ScaleInfoEnum[] enumArr = ScaleInfoEnum.values();
        enumArr[whichScale].getScaleInfo().setScaleWidth(width);
    }

    @Override
    public void setScaleInfo(int whichScale, ScaleInfo scaleInfo) {
        ScaleInfoEnum[] enumArr = ScaleInfoEnum.values();
        enumArr[whichScale].setScaleInfo(scaleInfo);
    }

    @Override
    public void setScaleHasTriangle(boolean hasTriangle) {
        for (ScaleInfoEnum s:ScaleInfoEnum.values()) {
            s.getScaleInfo().setbHasTriangle(hasTriangle);
        }
    }

    @Override
    public void setScaleHasTriangle(int whichScale, boolean hasTriangle) {
        ScaleInfoEnum[] enumArr = ScaleInfoEnum.values();
        enumArr[whichScale].getScaleInfo().setbHasTriangle(hasTriangle);
    }

    @Override
    public void setScaleHasData(boolean hasData) {
        for (ScaleInfoEnum s:ScaleInfoEnum.values()) {
            s.getScaleInfo().setHasData(hasData);
        }
    }

    @Override
    public void setScaleHasData(int whichScale, boolean hasData) {
        ScaleInfoEnum[] enumArr = ScaleInfoEnum.values();
        enumArr[whichScale].getScaleInfo().setHasData(hasData);
    }

    @Override
    public void setScaleTitle(int whichScale, String strTitle) {
        ScaleInfoEnum[] enumArr = ScaleInfoEnum.values();
        enumArr[whichScale].getScaleInfo().setScaleTitle(strTitle);
    }

    public BaseLineChart(Context context) {
        super(context);
    }

    public BaseLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
