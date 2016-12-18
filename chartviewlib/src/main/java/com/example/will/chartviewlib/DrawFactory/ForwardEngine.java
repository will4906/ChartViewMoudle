package com.example.will.chartviewlib.DrawFactory;

import android.graphics.Paint;
import android.util.Log;

import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.AxisInfo;
import com.example.will.chartviewlib.ChartInfo.ChartViewInfo;
import com.example.will.chartviewlib.ChartInfo.MainLayer.DataPoint;
import com.example.will.chartviewlib.ChartInfo.MainLayer.MainLineInfo;
import com.example.will.chartviewlib.Common.CanvasTool;
import com.example.will.chartviewlib.TouchFactory.TouchParam;

import java.util.List;

import static com.example.will.chartviewlib.LineChartView.BOTTOM_AXIS;
import static com.example.will.chartviewlib.LineChartView.LEFT_AXIS;
import static com.example.will.chartviewlib.LineChartView.RIGHT_AXIS;

/**
 * @author will4906.
 * @Time 2016/12/18.
 */

public class ForwardEngine {

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

    public ChartViewInfo getChartViewInfo() {
        return chartViewInfo;
    }

    public void setChartViewInfo(ChartViewInfo chartViewInfo) {
        this.chartViewInfo = chartViewInfo;
    }

    /**
     * 绘制递进式的波形
     * @param canvasTool
     */
    public void drawForwardLine(CanvasTool canvasTool){
        int index = 0;
        for (MainLineInfo mainLineInfo : mainLineInfoList) {
            int aViewPointsSum = mainLineInfo.getInitAViewPointsSum();
            if (aViewPointsSum > 0){
                /*根据以下公式计算
                pointSum = (int)(width / (radius * 2 + mainLineInfo.getHorizontalResolution()));
                pointSum += 1
                (int)(width / (radius * 2 + mainLineInfo.getHorizontalResolution())) + 1 = pointSum
                pointSum - 1 = width / (radius * 2 + mainLineInfo.getHorizontalResolution())
                width / (pointSum - 1) = radius * 2 + mainLineInfo.getHorizontalResolution()

                width / (pointSum - 1) - radius * 2 = mainLineInfo.getHorizontalResolution()*/
                mainLineInfo.setHorizontalResolution((chartWidth - mainLineInfo.getNormalOffsetX()) / (aViewPointsSum) - mainLineInfo.getMainPointInfo().getRadius() * 2);
                mainLineInfo.setInitAViewPointsSum(-aViewPointsSum);
            }
            if (mainLineInfo.isVisibility()){
                try {
                    drawOneMainLine(canvasTool, mainLineInfo);
                }catch (Exception e){
                    Log.v("Exception",String.valueOf(e));
                }
            }
            index++;
        }
        //最后处理
        afterMainLine();
    }

    /**
     * 处理完画图后要做的事情
     */
    private void afterMainLine(){
        touchParam.setTouchOffsetX(0);
        touchParam.setAddResolutionX(0);
    }

    /**
     * 画单个波形图
     * @param canvasTool
     * @param mainLineInfo
     */
    private void drawOneMainLine(CanvasTool canvasTool, MainLineInfo mainLineInfo){
        synchronized (this){
            canvasTool.startDrawOnABitmap((int) chartWidth, (int) chartHeight);
            List<DataPoint> dataList = mainLineInfo.getDataList();

            float radius = mainLineInfo.getMainPointInfo().getRadius();
            //计算
            XcomputeResolutionAndOffset(mainLineInfo,dataList.size(),radius,chartWidth);

            float screenMove = mainLineInfo.getScreenPos();
            int start = DrawComputer.computeStart(mainLineInfo,screenMove,radius,dataList.size());

            //绘图
            drawLineFunction(canvasTool, start, radius,dataList,screenMove,chartHeight,mainLineInfo);
            canvasTool.flushBitmap(axisInfos[LEFT_AXIS].getSpace() + axisInfos[LEFT_AXIS].getAxisWidth() / 2, chartHeight + axisInfos[BOTTOM_AXIS].getSpace() + axisInfos[BOTTOM_AXIS].getAxisWidth() / 2);

            if (axisInfos[BOTTOM_AXIS].isHasData() && mainLineInfo.isVisibility()){
                canvasTool.startDrawOnABitmap(backgroundWidth, (int) axisInfos[BOTTOM_AXIS].getSpace() + (int)(axisInfos[BOTTOM_AXIS].getAxisWidth() / 2));
                drawBottomText(canvasTool, start, radius, dataList, screenMove, mainLineInfo);

                canvasTool.flushBitmap(0, axisInfos[BOTTOM_AXIS].getSpace() + axisInfos[BOTTOM_AXIS].getAxisWidth() / 2);
            }
        }
    }

    /**
     * 绘制底部数据
     * @param canvasTool
     * @param start
     * @param radius
     * @param dataList
     * @param screenMove
     * @param mainLineInfo
     */
    private void drawBottomText(CanvasTool canvasTool, int start, float radius, List<DataPoint> dataList, float screenMove, MainLineInfo mainLineInfo ){
        for (int i = start; i < dataList.size(); i ++){
            //点的理论横坐标
            float pointX = radius + i * (mainLineInfo.getHorizontalResolution() + radius * 2);
            float lastX = radius + (i - 1) * (mainLineInfo.getHorizontalResolution() + radius * 2);
            float nextX = radius + (i + 1) * (mainLineInfo.getHorizontalResolution() + radius * 2);
            //点在屏幕上显示的横坐标
            float cx =  pointX - screenMove;
            if (mainLineInfo.isHasPoint()){
                String strX = dataList.get(i).getXData();
                if (!strX.equals("") && dataList.get(i).isShowXData()){
                    Paint bottomPaint = axisInfos[BOTTOM_AXIS].getTextPaint();
                    bottomPaint.setTextAlign(Paint.Align.CENTER);
                    if (axisInfos[BOTTOM_AXIS].isAutoText()){
                        if (i > 0 && i < dataList.size() - 1){
                            float lastLen = bottomPaint.measureText(dataList.get(i - 1).getXData());
                            if (!dataList.get(i - 1).isShowXData()){
                                lastLen = 0;
                            }
                            float nowLen = bottomPaint.measureText(strX);
                            float nextLen = bottomPaint.measureText(dataList.get(i + 1).getXData());
                            if (!dataList.get(i + 1).isShowXData()){
                                nextLen = 0;
                            }
                            if (lastX + lastLen / 2 < pointX - nowLen / 2 && pointX + nowLen / 2 < nextX - nextLen / 2 ){
                                if (axisInfos[LEFT_AXIS].getSpace() + cx > axisInfos[LEFT_AXIS].getSpace() &&
                                        axisInfos[LEFT_AXIS].getSpace() + cx < backgroundWidth - axisInfos[RIGHT_AXIS].getAxisWidth() / 2 - axisInfos[RIGHT_AXIS].getSpace()){
                                    canvasTool.drawText(strX, axisInfos[LEFT_AXIS].getSpace() + cx, 0, bottomPaint);
                                }
                            }
                        }else if (i == 0){
                            if (dataList.size() > 1){
                                float nowLen = bottomPaint.measureText(strX);
                                float nextLen = bottomPaint.measureText(dataList.get(i + 1).getXData());
                                if (!dataList.get(i + 1).isShowXData()){
                                    nextLen = 0;
                                }
                                if (pointX + nowLen / 2 < nextX - nextLen / 2 ){
                                    if (axisInfos[LEFT_AXIS].getSpace() + cx > axisInfos[LEFT_AXIS].getSpace() &&
                                            axisInfos[LEFT_AXIS].getSpace() + cx < backgroundWidth - axisInfos[RIGHT_AXIS].getAxisWidth() / 2 - axisInfos[RIGHT_AXIS].getSpace()){
                                        canvasTool.drawText(strX, axisInfos[LEFT_AXIS].getSpace() + cx, 0, bottomPaint);
                                    }
                                }
                            }else{
                                if (axisInfos[LEFT_AXIS].getSpace() + cx > axisInfos[LEFT_AXIS].getSpace() &&
                                        axisInfos[LEFT_AXIS].getSpace() + cx < backgroundWidth - axisInfos[RIGHT_AXIS].getAxisWidth() / 2 - axisInfos[RIGHT_AXIS].getSpace()){
                                    canvasTool.drawText(strX, axisInfos[LEFT_AXIS].getSpace() + cx, 0, bottomPaint);
                                }
                            }
                        }else if (i == dataList.size() - 1){
                            float lastLen = bottomPaint.measureText(dataList.get(i - 1).getXData());
                            if (!dataList.get(i - 1).isShowXData()){
                                lastLen = 0;
                            }
                            float nowLen = bottomPaint.measureText(strX);
                            if (lastX + lastLen / 2 < pointX - nowLen / 2){
                                if (axisInfos[LEFT_AXIS].getSpace() + cx > axisInfos[LEFT_AXIS].getSpace() &&
                                        axisInfos[LEFT_AXIS].getSpace() + cx < backgroundWidth - axisInfos[RIGHT_AXIS].getAxisWidth() / 2 - axisInfos[RIGHT_AXIS].getSpace()){
                                    canvasTool.drawText(strX, axisInfos[LEFT_AXIS].getSpace() + cx, 0, bottomPaint);
                                }
                            }
                        }
                    }else{
                        if (axisInfos[LEFT_AXIS].getSpace() + cx > axisInfos[LEFT_AXIS].getSpace() &&
                                axisInfos[LEFT_AXIS].getSpace() + cx < backgroundWidth - axisInfos[RIGHT_AXIS].getAxisWidth() / 2 - axisInfos[RIGHT_AXIS].getSpace()){
                            canvasTool.drawText(strX, axisInfos[LEFT_AXIS].getSpace() + cx, 0, bottomPaint);
                        }
                    }
                }
            }
        }

    }

    private int middlePioint = -1;
    private int downPoint = -1;

    public int getDownPoint() {
        return downPoint;
    }

    public void setDownPoint(int downPoint) {
        this.downPoint = downPoint;
    }

    /**
     * 计算屏幕分辨率和偏移
     * @param size
     * @param radius
     * @param chartWidth
     */
    private void XcomputeResolutionAndOffset(MainLineInfo mainLineInfo, int size, float radius, float chartWidth){
        //显示屏幕移动距离
        if (touchParam.getTouchMode() == TouchParam.NO_TOUCH){
            float tmpSreenPos = size * (mainLineInfo.getHorizontalResolution() + radius * 2) - chartWidth - mainLineInfo.getHorizontalResolution() + mainLineInfo.getNormalOffsetX();
            if (tmpSreenPos < 0){
                tmpSreenPos = 0;
            }
            mainLineInfo.setScreenPos(tmpSreenPos);
            middlePioint = -1;
        }else if (touchParam.getTouchMode() == TouchParam.SINGLE_TOUCH){
            float downX = touchParam.getDownX();
            float downY = touchParam.getDownY();
            downX -= axisInfos[LEFT_AXIS].getSpace() + axisInfos[LEFT_AXIS].getAxisWidth();
            if (downPoint == -1) {                        //如果已经定过位就不需要再重新定位了，免得总是偏移
                //获取距离两指中间对应横坐标最近的点的索引
                downPoint = (int) ((mainLineInfo.getScreenPos() + downX - radius) / (mainLineInfo.getHorizontalResolution() + radius * 2));
            }

            float tmpScreenPos = mainLineInfo.getScreenPos() + touchParam.getTouchOffsetX();
            if (tmpScreenPos < 0){
                tmpScreenPos = 0;
            }
            mainLineInfo.setScreenPos(tmpScreenPos);
            //touchParam.setTouchOffsetX(0);交由afterMainLine()处理
            middlePioint = -1;
        } else if (touchParam.getTouchMode() == TouchParam.DOUBLE_TOUCH){
            //获取两指中间对应横坐标，对应的坐标系是整个大canvas，应减掉轴宽和空白才能使用
            float middle = touchParam.getTwoPointsMiddleX();
            middle -= axisInfos[LEFT_AXIS].getSpace() + axisInfos[LEFT_AXIS].getAxisWidth();
            if (middlePioint == -1){                        //如果已经定过位就不需要再重新定位了，免得总是偏移
                //获取距离两指中间对应横坐标最近的点的索引
                middlePioint = (int)((mainLineInfo.getScreenPos() + middle - radius) / (mainLineInfo.getHorizontalResolution() + radius * 2));
            }
            //获取距离两指中间横坐标最近的点在屏幕显示上实际的位置
            float lockPoint = middlePioint * (mainLineInfo.getHorizontalResolution() + radius * 2) - mainLineInfo.getScreenPos();
            float newXResolution = touchParam.getAddResolutionX() + mainLineInfo.getHorizontalResolution();
            if (newXResolution < 0) {
                newXResolution = 0;
            }
            if (newXResolution > chartWidth) {
                newXResolution = chartWidth;
            }
            mainLineInfo.setHorizontalResolution(newXResolution);
            //先把刚才获取到的点移到最左在移到原来屏幕所在的位置
            float tmpSreenPos = middlePioint * (mainLineInfo.getHorizontalResolution() + radius * 2) - lockPoint;
            if (tmpSreenPos < 0){
                tmpSreenPos = 0;
            }
            mainLineInfo.setScreenPos(tmpSreenPos);
            //touchParam.setAddResolutionX(0);交由afterMainLine()处理
        }
    }

    /**
     * 绘制函数
     * @param canvasTool
     * @param start
     * @param radius
     * @param dataList
     * @param screenMove
     * @param chartHeight
     * @param mainLineInfo
     */
    private void drawLineFunction(CanvasTool canvasTool, int start, float radius, List<DataPoint> dataList, float screenMove, float chartHeight, MainLineInfo mainLineInfo){
        int whichShowDiv = -1;
        float showDivCx = 0;
        float showDivPointHeight = 0;
        for (int i = start; i < dataList.size(); i ++){
            //点的理论横坐标
            float pointX = radius + i * (mainLineInfo.getHorizontalResolution() + radius * 2);
            //点在屏幕上显示的横坐标
            float cx =  pointX - screenMove;
            float pointHeight = DrawComputer.changeUserDataToChartViewData(dataList.get(i).getYData(), chartHeight,axisInfos[LEFT_AXIS]);
            if (mainLineInfo.isHasLine() && i != 0){
                canvasTool.drawLine(cx - (mainLineInfo.getHorizontalResolution() + radius * 2), DrawComputer.changeUserDataToChartViewData(dataList.get(i - 1).getYData(), chartHeight,axisInfos[LEFT_AXIS]),cx,pointHeight,mainLineInfo.getPaint());
            }
            if (mainLineInfo.isHasPoint()){
                int color = Integer.MIN_VALUE;
                if (dataList.get(i).isHasChangeColor()) {
                    color = mainLineInfo.getMainPointInfo().getPaint().getColor();
                    mainLineInfo.getMainPointInfo().getPaint().setColor(dataList.get(i).getColor());
                }
                float radius2 = Float.MIN_VALUE;
                if (dataList.get(i).isHasChangeRadius()){
                    radius2 = mainLineInfo.getMainPointInfo().getRadius();
                    mainLineInfo.getMainPointInfo().setRadius(dataList.get(i).getRadius());
                }
                canvasTool.drawCircle(cx,pointHeight,mainLineInfo.getMainPointInfo().getRadius(),mainLineInfo.getMainPointInfo().getPaint());
                if (color != Integer.MIN_VALUE){
                    mainLineInfo.getMainPointInfo().getPaint().setColor(color);
                }
                if (radius2 != Float.MIN_VALUE){
                    mainLineInfo.getMainPointInfo().setRadius(radius2);
                }
                if (mainLineInfo.isShowDataDiv()){
                    if (i == downPoint){
                        whichShowDiv = i;
                        showDivCx = cx;
                        showDivPointHeight = pointHeight;
                    }
                }
            }
        }
        if (whichShowDiv != -1){
            drawDataDiv(canvasTool, whichShowDiv, mainLineInfo, dataList, showDivCx, showDivPointHeight);
        }
    }

    /**
     * 绘制数据框
     * @param canvasTool
     * @param i
     * @param mainLineInfo
     * @param dataList
     * @param cx
     * @param pointHeight
     */
    private void drawDataDiv(CanvasTool canvasTool, int i, MainLineInfo mainLineInfo, List<DataPoint> dataList, float cx, float pointHeight){
        if (i == downPoint){
            float textSize = chartViewInfo.getTextSize();
            Paint divPaint = new Paint();
            divPaint.setTextSize(textSize);
            divPaint.setColor(mainLineInfo.getDataDivInfo().getTextColor());
            divPaint.setTextAlign(Paint.Align.CENTER);
            if (dataList.get(i).getXData().equals("")){
                mainLineInfo.getDataDivInfo().setHeight(textSize * 2);
            }else{
                mainLineInfo.getDataDivInfo().setHeight(textSize * 4);
            }
            float oneLen = divPaint.measureText(String.valueOf(dataList.get(i).getYData()) + axisInfos[LEFT_AXIS].getAxisTitle());
            float secondLen = divPaint.measureText(dataList.get(i).getXData());
            mainLineInfo.getDataDivInfo().setWidth(oneLen > secondLen ? oneLen + 5 : secondLen + 5);

            float divX = cx;
            float trHeight = textSize;

            float tRightX = cx + trHeight / 3;
            float tLeftX = cx - trHeight / 3;
            float rightX = cx + mainLineInfo.getDataDivInfo().getWidth() / 2;
            float leftX = cx - mainLineInfo.getDataDivInfo().getWidth() / 2;
            if (cx > chartWidth - mainLineInfo.getDataDivInfo().getWidth() / 2){
                tRightX = divX;
                tLeftX -= trHeight / 3;
                rightX = divX;
                leftX = rightX - mainLineInfo.getDataDivInfo().getWidth();
            }else if (cx < mainLineInfo.getDataDivInfo().getWidth() / 2){
                tLeftX = divX;
                tRightX += trHeight / 3;
                leftX = divX;
                rightX = leftX + mainLineInfo.getDataDivInfo().getWidth();
            }

            float tBottomHeight = pointHeight + mainLineInfo.getMainPointInfo().getRadius();
            float tTopHeight = pointHeight + mainLineInfo.getMainPointInfo().getRadius() + trHeight;
            float topHeight = tTopHeight + mainLineInfo.getDataDivInfo().getHeight();
            float textOneHeight = topHeight - textSize;
            if (topHeight > chartHeight){
                tBottomHeight = pointHeight - mainLineInfo.getMainPointInfo().getRadius();
                tTopHeight = pointHeight - mainLineInfo.getMainPointInfo().getRadius() - trHeight;
                topHeight = tTopHeight - mainLineInfo.getDataDivInfo().getHeight();
                textOneHeight = tTopHeight - textSize;
            }

            canvasTool.drawTriangle(cx,tBottomHeight,tLeftX,tTopHeight,tRightX,tTopHeight,true,mainLineInfo.getDataDivInfo().getPaint());
            canvasTool.drawRect(leftX,topHeight,rightX,tTopHeight,mainLineInfo.getDataDivInfo().getPaint());
            canvasTool.drawText(String.valueOf(dataList.get(i).getYData()) + axisInfos[LEFT_AXIS].getAxisTitle(),(rightX - leftX) / 2 + leftX,textOneHeight,divPaint);
            if (!dataList.get(i).getXData().equals("")){
                canvasTool.drawText(dataList.get(i).getXData(),(rightX - leftX) / 2 + leftX,textOneHeight - textSize * 2,divPaint);
            }
        }
    }

}
