package com.example.will.chartviewlib;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.will.canvaslib.CanvasTool;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.BgLineInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.ChartBgInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.IBgLineInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.IChartBgInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.IScaleInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.ScaleInfo;
import com.example.will.chartviewlib.ChartInfo.ChartViewInfo;
import com.example.will.chartviewlib.ChartInfo.IChartViewInfo;
import com.example.will.chartviewlib.DrawFactory.DrawEngine;
import com.example.will.chartviewlib.DrawFactory.OnDrawBackgroundListener;
import com.example.will.viewcontrollib.ViewInsideTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by will on 2016/11/21.
 */

public class LineChartView extends BaseLineChart implements IScaleInfo,IChartViewInfo,IChartBgInfo,IBgLineInfo {

    /**
     * 对坐标轴的宏定义
     */
    public static final int LEFT_SCALE = 0;
    public static final int BOTTOM_SCALE = 1;
    public static final int RIGHT_SCALE = 2;
    public static final int TOP_SCALE = 3;

    public static final int BGLINE_VERTICAL = 90;
    public static final int BGLINE_HORIZONTAL = 0;
    /**
     * 绘制背景监听
     */
    private OnDrawBackgroundListener onDrawBackgroundListener = new OnDrawBackgroundListener() {
        @Override
        public boolean onBackgroundDraw(CanvasTool canvasTool) {
            return true;
        }
    };

    public OnDrawBackgroundListener getOnDrawBackgroundListener() {
        return onDrawBackgroundListener;
    }

    public void setOnDrawBackgroundListener(OnDrawBackgroundListener onDrawBackgroundListener) {
        this.onDrawBackgroundListener = onDrawBackgroundListener;
        drawEngine.setOnDrawBackgroundListener(onDrawBackgroundListener);
    }

    public LineChartView(Context context) {
        super(context);
        initLineChartView(context);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLineChartView(context);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLineChartView(context);
    }

    /**
     * 初始化图表
     */
    private void initLineChartView(Context context){
        TextView textView = new TextView(context);
        setTextSize(textView.getTextSize() / 4 * 3);
        drawEngine.setOnDrawBackgroundListener(onDrawBackgroundListener);
        drawEngine.setCharBgInfo(chartBgInfo);
        drawEngine.setChartViewInfo(chartViewInfo);
        drawEngine.setScaleInfos(new ScaleInfo[]{ScaleInfoEnum.LEFT.getScaleInfo(),ScaleInfoEnum.BOTTOM.getScaleInfo(),ScaleInfoEnum.RIGHT.getScaleInfo(),ScaleInfoEnum.TOP.getScaleInfo()});
    }

    protected ChartBgInfo chartBgInfo = new ChartBgInfo();
    protected ChartViewInfo chartViewInfo = new ChartViewInfo();
    protected List<BgLineInfo> bgLineInfoList = new ArrayList<>();

    public List<BgLineInfo> getBgLineInfoList() {
        return bgLineInfoList;
    }

    public void setBgLineInfoList(List<BgLineInfo> bgLineInfoList) {
        this.bgLineInfoList = bgLineInfoList;
    }

    @Override
    public void enableBackgroundLine(boolean bHas) {
        chartBgInfo.enableBgLine(bHas);
    }

    @Override
    public void addBackgroundLine(BgLineInfo bgLineInfo) {
        bgLineInfoList.add(bgLineInfo);
    }

    @Override
    public void removeBackgroundLine(int index) {
        bgLineInfoList.remove(index);
    }

    @Override
    public void removeAllBackgroundLines() {
        while (bgLineInfoList.size() > 0){
            bgLineInfoList.remove(0);
        }
    }

    @Override
    public void useDefaultBackgroundLines(boolean use) {
        chartBgInfo.setUseDefaultBgLines(use);
    }

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

    @Override
    public void setTextSize(float textSize) {
        chartViewInfo.setTextSize(textSize);
        for (ScaleInfoEnum s:ScaleInfoEnum.values()) {
            s.getScaleInfo().setTextSize(textSize);
            s.getScaleInfo().setSpace(textSize * 2);
        }
    }

    @Override
    public void setBackgroundColor(int color) {
        //view本身就有一个设置背景颜色的函数
        super.setBackgroundColor(color);
    }

    @Override
    public void setYRange(float min, float max) {
        ScaleInfoEnum.LEFT.getScaleInfo().setMaxValue(max);
        ScaleInfoEnum.RIGHT.getScaleInfo().setMinVale(min);
    }

    @Override
    public void enableScales(boolean[] bWhichScale) {
        chartBgInfo.enableScales(bWhichScale);
    }

    @Override
    public void enableLeftScale(boolean bHas) {
        chartBgInfo.enableLeftScale(bHas);
    }

    @Override
    public void enableBottomScale(boolean bHas) {
        chartBgInfo.enableBottomScale(bHas);
    }

    @Override
    public void enableRightScale(boolean bHas) {
        chartBgInfo.enableRightScale(bHas);
    }

    @Override
    public void enableTopScale(boolean bHas) {
        chartBgInfo.enableTopScale(bHas);
    }
}
