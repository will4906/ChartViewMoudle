package com.example.will.chartviewlib.DrawFactory;

import android.graphics.Paint;

import com.example.will.chartviewlib.ChartInfo.MainLayer.DataPoint;
import com.example.will.chartviewlib.Common.CanvasTool;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.AxisInfo;
import com.example.will.chartviewlib.ChartInfo.ChartViewInfo;
import com.example.will.chartviewlib.ChartInfo.MainLayer.MainLineInfo;
import com.example.will.chartviewlib.TouchFactory.TouchParam;

import static com.example.will.chartviewlib.LineChartView.FORWARD_MODE;
import static com.example.will.chartviewlib.LineChartView.SCAN_MODE;
import static com.example.will.chartviewlib.LineChartView.TOP_AXIS;
import static com.example.will.chartviewlib.LineChartView.BOTTOM_AXIS;
import static com.example.will.chartviewlib.LineChartView.RIGHT_AXIS;
import static com.example.will.chartviewlib.LineChartView.LEFT_AXIS;

import java.util.List;

/**
 * 其实这个类做的不是很好，本来只想用来处理关于绘图的大部分事件，可是现在很多数据处理也丢到里面去了
 * @author will4906.
 * @Time 2016/11/22.
 */

public class DrawEngine extends Colleague{

    //信息中介
    private InformationMediator informationMediator;

    public DrawEngine(InformationMediator informationMediator) {
        this.informationMediator = informationMediator;
        informationMediator.setDrawEngine(this);
    }

    private int backgroundHeight = 0;
    private int backgroundWidth = 0;

    public int getBackgroundHeight() {
        return backgroundHeight;
    }

    public void setBackgroundHeight(int backgroundHeight) {
        this.backgroundHeight = backgroundHeight;
    }

    public int getBackgroundWidth() {
        return backgroundWidth;
    }

    public void setBackgroundWidth(int backgroundWidth) {
        this.backgroundWidth = backgroundWidth;
    }

    private ChartViewInfo chartViewInfo;

    private AxisInfo[] axisInfos;

    private TouchParam touchParam;

    public TouchParam getTouchParam() {
        return touchParam;
    }

    public void setTouchParam(TouchParam touchParam) {
        this.touchParam = touchParam;
    }

    public AxisInfo[] getAxisInfos() {
        return axisInfos;
    }

    public void setAxisInfos(AxisInfo[] axisInfos) {
        this.axisInfos = axisInfos;
    }

    public ChartViewInfo getChartViewInfo() {
        return chartViewInfo;
    }

    public void setChartViewInfo(ChartViewInfo chartViewInfo) {
        this.chartViewInfo = chartViewInfo;
    }

    private List<MainLineInfo> mainLineInfoList;

    public List<MainLineInfo> getMainLineInfoList() {
        return mainLineInfoList;
    }

    public void setMainLineInfoList(List<MainLineInfo> mainLineInfoList) {
        this.mainLineInfoList = mainLineInfoList;
    }

    /**
     * 表的理论宽度，若在触摸事件TouchEngine中使用，请谨慎使用
     */
    private float chartWidth = 0;
    /**
     * 表的理论高度，若在触摸事件TouchEngine中使用，请谨慎使用
     */
    private float chartHeight = 0;

    public float getChartWidth() {
        return chartWidth;
    }

    public void setChartWidth(float chartWidth) {
        this.chartWidth = chartWidth;
    }

    public float getChartHeight() {
        return chartHeight;
    }

    public void setChartHeight(float chartHeight) {
        this.chartHeight = chartHeight;
    }

    private int downPoint = -1;

    public int getDownPoint() {
        return downPoint;
    }

    public void setDownPoint(int downPoint) {
        this.downPoint = downPoint;
    }

    /**
     * 画波形图
     */
    public void drawMainLine(final CanvasTool canvasTool) {
        int drawMode = chartViewInfo.getDrawMode();
        switch (drawMode) {
            case FORWARD_MODE:
                drawForwardLine(canvasTool);
                break;
            case SCAN_MODE:
                synchronized (this){
                    drawScanLine(canvasTool);
                }
                break;
            default:
                break;
        }
        informationMediator.change(this);
    }

    private void drawForwardLine(CanvasTool canvasTool){
        ForwardEngine forwardEngine = new ForwardEngine();
        forwardEngine.setChartViewInfo(chartViewInfo);
        forwardEngine.setChartHeight(chartHeight);
        forwardEngine.setChartWidth(chartWidth);
        forwardEngine.setAxisInfos(axisInfos);
        forwardEngine.setBackgroundHeight(backgroundHeight);
        forwardEngine.setBackgroundWidth(backgroundWidth);
        forwardEngine.setMainLineInfoList(mainLineInfoList);
        forwardEngine.setTouchParam(touchParam);
        forwardEngine.setDownPoint(downPoint);
        forwardEngine.drawForwardLine(canvasTool);
    }


    private void drawScanLine(CanvasTool canvasTool){
        ScanEngine scanEngine = new ScanEngine();
        scanEngine.setMainLineInfoList(mainLineInfoList);
        scanEngine.setChartWidth(chartWidth);
        scanEngine.setChartHeight(chartHeight);
        scanEngine.setAxisInfos(axisInfos);
        scanEngine.drawScanLine(canvasTool);
    }

}
