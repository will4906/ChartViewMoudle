package com.example.will.chartviewlib.DrawFactory;

import android.util.Log;

import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.AxisInfo;
import com.example.will.chartviewlib.ChartInfo.MainLayer.DataPoint;
import com.example.will.chartviewlib.ChartInfo.MainLayer.MainLineInfo;
import com.example.will.chartviewlib.Common.CanvasTool;

import java.util.ArrayList;
import java.util.List;

import static com.example.will.chartviewlib.LineChartView.BOTTOM_AXIS;
import static com.example.will.chartviewlib.LineChartView.LEFT_AXIS;

/**
 * @author will4906.
 * @Time 2016/12/18.
 */

public class ScanEngine {

    private static final int SCAN_BUFFER_SZIE = 10000;
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

    private AxisInfo[] axisInfos;

    public AxisInfo[] getAxisInfos() {
        return axisInfos;
    }

    public void setAxisInfos(AxisInfo[] axisInfos) {
        this.axisInfos = axisInfos;
    }
    /**
     * 绘制扫描式的波形
     * @param canvasTool
     */
    public void drawScanLine(CanvasTool canvasTool){
        for (int i = 0; i < mainLineInfoList.size(); i ++){
            MainLineInfo mainLineInfo = mainLineInfoList.get(i);
            if (mainLineInfo.isVisibility()){
                try {
                    drawOneMainLine(canvasTool, mainLineInfo);
                }catch (Exception e){
                    Log.v("Exception",String.valueOf(e));
                }
            }
        }
    }

    /**
     * 绘制单道主线
     * @param canvasTool
     * @param mainLineInfo
     */
    private void drawOneMainLine(CanvasTool canvasTool, MainLineInfo mainLineInfo){
        canvasTool.startDrawOnABitmap((int) chartWidth, (int) chartHeight);

        List<DataPoint> dataList = mainLineInfo.getDataList();
        float radius = mainLineInfo.getMainPointInfo().getRadius();

        int aViewPointsSum = DrawComputer.computePoints(mainLineInfo,chartWidth);
        int aViewTheoryPointsSum = DrawComputer.computePoints(mainLineInfo,chartWidth - 15);

        List<Float> viewList = new ArrayList<>();
        addPointsIntoViewList(viewList, dataList, aViewTheoryPointsSum, aViewPointsSum);


        canvasTool.flushBitmap(axisInfos[LEFT_AXIS].getSpace() + axisInfos[LEFT_AXIS].getAxisWidth() / 2, chartHeight + axisInfos[BOTTOM_AXIS].getSpace() + axisInfos[BOTTOM_AXIS].getAxisWidth() / 2);
    }

    /**
     * 应该起始的点的索引
     */
    private int startIndex = 0;

    private void addPointsIntoViewList(List<Float> viewList, List<DataPoint> dataList, int viewTheorySize, int viewSize){
        if (dataList.size() < viewTheorySize){
            for (int i = 0; i < dataList.size(); i ++){
                viewList.add(dataList.get(i).getYData());
            }
        }else{
            if (dataList.size() > SCAN_BUFFER_SZIE){
                dataList.remove(0);
            }
            int interval = viewTheorySize - viewSize;
        }
    }
}
