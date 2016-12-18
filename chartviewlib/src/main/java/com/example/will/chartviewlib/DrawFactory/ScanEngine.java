package com.example.will.chartviewlib.DrawFactory;

import android.graphics.Paint;
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

    public static final int SCAN_BUFFER_SZIE = 10000;
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
    public void drawScanLine(CanvasTool canvasTool) {
        for (int i = 0; i < mainLineInfoList.size(); i++) {
            MainLineInfo mainLineInfo = mainLineInfoList.get(i);
            if (mainLineInfo.isVisibility()) {
                try {
                    if (mainLineInfo.getDataList().size() > 0) {
                        drawOneMainLine(canvasTool, mainLineInfo);
                    }
                } catch (Exception e) {
                    Log.v("Exception", String.valueOf(e));
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

        int aViewPointsSum = DrawComputer.computePoints(mainLineInfo,chartWidth - 15);
        int aViewTheoryPointsSum = DrawComputer.computePoints(mainLineInfo,chartWidth);

        addPointsIntoViewList(mainLineInfo, dataList, aViewTheoryPointsSum, aViewPointsSum);

        drawLineFunction(canvasTool, mainLineInfo, aViewTheoryPointsSum);
        canvasTool.flushBitmap(axisInfos[LEFT_AXIS].getSpace() + axisInfos[LEFT_AXIS].getAxisWidth() / 2, chartHeight + axisInfos[BOTTOM_AXIS].getSpace() + axisInfos[BOTTOM_AXIS].getAxisWidth() / 2);
    }

    /**
     * 计算点的分布
     * @param mainLineInfo
     * @param dataList
     * @param viewTheorySize
     * @param viewSize
     */
    private void addPointsIntoViewList(MainLineInfo mainLineInfo, List<DataPoint> dataList, int viewTheorySize, int viewSize){
        int startIndex = mainLineInfo.getStartIndex();
        int scanSize = mainLineInfo.getScanBufferList().size();
        int interval = viewTheorySize - viewSize;
        if (scanSize != viewTheorySize){
            if (scanSize < viewTheorySize){
                for (int i = scanSize; i < viewTheorySize; i ++){
                    mainLineInfo.getScanBufferList().add(null);
                }
            }else {
                for (int i = scanSize - 1; i >= viewTheorySize; i --){
                    mainLineInfo.getScanBufferList().remove(i);
                }
                startIndex = 0;
            }
        }

        mainLineInfo.getScanBufferList().set(startIndex, dataList.get(dataList.size() - 1).getYData());
        for (int i = 1; i < interval + 1; i ++){
            int index = i;
            if (startIndex + i >= viewTheorySize){
                index = -(startIndex - (viewTheorySize - startIndex + i));
            }
            mainLineInfo.getScanBufferList().set(startIndex + index, null);
        }
        startIndex++;
        if (startIndex >= viewTheorySize){
            startIndex = 0;
        }
        mainLineInfo.setStartIndex(startIndex);
    }

    private void drawLineFunction(CanvasTool canvasTool, MainLineInfo mainLineInfo, int viewTheorySize){
        float radius = mainLineInfo.getMainPointInfo().getRadius();
        List<Float> scanBufferList = mainLineInfo.getScanBufferList();
        for (int i = 0; i < viewTheorySize; i ++){
            if (scanBufferList.get(i) != null){
                float pointX = radius + i * (mainLineInfo.getHorizontalResolution() + radius * 2);
                float pointHeight = DrawComputer.changeUserDataToChartViewData(scanBufferList.get(i),chartHeight,axisInfos[LEFT_AXIS]);
                if (mainLineInfo.isHasLine() && i != 0 ){
                    if (scanBufferList.get(i - 1) != null){
                        canvasTool.drawLine(radius + (i - 1)* (mainLineInfo.getHorizontalResolution() + radius * 2),
                                DrawComputer.changeUserDataToChartViewData(scanBufferList.get(i - 1), chartHeight,axisInfos[LEFT_AXIS]),
                                pointX,
                                pointHeight,
                                mainLineInfo.getPaint());
                    }
                }
                if (mainLineInfo.isHasPoint()){
                    Paint paint = mainLineInfo.getMainPointInfo().getPaint();
                    canvasTool.drawCircle(pointX,pointHeight,mainLineInfo.getMainPointInfo().getRadius(),paint);
                }
            }
        }
    }
}
