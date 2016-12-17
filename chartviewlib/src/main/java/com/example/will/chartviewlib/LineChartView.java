package com.example.will.chartviewlib;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.will.chartviewlib.ChartInfo.MainLayer.DataPoint;
import com.example.will.chartviewlib.ChartInfo.MainLayer.IDataDivInfo;
import com.example.will.chartviewlib.Common.CanvasTool;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.BgLineInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.ChartBgInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.DefaultBgLineInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.IBgLineInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.IChartBgInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.IDefaultBgLineInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.IAxisInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.AxisInfo;
import com.example.will.chartviewlib.ChartInfo.ChartViewInfo;
import com.example.will.chartviewlib.ChartInfo.IChartViewInfo;
import com.example.will.chartviewlib.ChartInfo.MainLayer.IMainLineInfo;
import com.example.will.chartviewlib.ChartInfo.MainLayer.IMainPointInfo;
import com.example.will.chartviewlib.ChartInfo.MainLayer.MainLineInfo;
import com.example.will.chartviewlib.ChartInfo.MainLayer.MainPointInfo;
import com.example.will.chartviewlib.DrawFactory.OnDrawBackgroundListener;
import com.example.will.chartviewlib.TouchFactory.ITouchInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by will on 2016/11/21.
 */

public class LineChartView extends BaseLineChart implements IAxisInfo,IChartViewInfo,IChartBgInfo, IBgLineInfo, IDefaultBgLineInfo, IMainLineInfo,IMainPointInfo, ITouchInfo, IDataDivInfo {

    /**
     * 对坐标轴的宏定义
     */
    public static final int LEFT_AXIS = 0;
    public static final int BOTTOM_AXIS = 1;
    public static final int RIGHT_AXIS = 2;
    public static final int TOP_AXIS = 3;

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
        backgroundEngine.setOnDrawBackgroundListener(onDrawBackgroundListener);
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
     * 图背景信息
     */
    protected ChartBgInfo chartBgInfo = new ChartBgInfo();
    /**
     * 图主要信息
     */
    protected ChartViewInfo chartViewInfo = new ChartViewInfo();
    /**
     * 背景线链表
     */
    protected List<BgLineInfo> bgLineInfoList = new ArrayList<>();
    /**
     * 默认背景线信息
     */
    protected DefaultBgLineInfo defaultBgLineInfo = new DefaultBgLineInfo();

    public ChartBgInfo getChartBgInfo() {
        return chartBgInfo;
    }

    public void setChartBgInfo(ChartBgInfo chartBgInfo) {
        this.chartBgInfo = chartBgInfo;
    }

    public DefaultBgLineInfo getDefaultBgLineInfo() {
        return defaultBgLineInfo;
    }

    public void setDefaultBgLineInfo(DefaultBgLineInfo defaultBgLineInfo) {
        this.defaultBgLineInfo = defaultBgLineInfo;
    }

    public ChartViewInfo getChartViewInfo() {
        return chartViewInfo;
    }

    public void setChartViewInfo(ChartViewInfo chartViewInfo) {
        this.chartViewInfo = chartViewInfo;
    }

    /**
     * 用户自定义背景线信息，目前是没有任何作用的，因为画图引擎没有实现，以后再行添加
     * @return
     */
    public List<BgLineInfo> getBgLineInfoList() {
        return bgLineInfoList;
    }

    public void setBgLineInfoList(List<BgLineInfo> bgLineInfoList) {
        this.bgLineInfoList = bgLineInfoList;
    }

    /**
     * 初始化图表
     */
    private void initLineChartView(Context context){
        TextView textView = new TextView(context);
        setTextSize(textView.getTextSize() / 4 * 3);
        setAxisWidth(textView.getTextSize() / 7);
        backgroundEngine.setDefaultBgLineInfo(defaultBgLineInfo);
        backgroundEngine.setOnDrawBackgroundListener(onDrawBackgroundListener);
        backgroundEngine.setChartBgInfo(chartBgInfo);
        backgroundEngine.setBgLineInfoList(bgLineInfoList);
        drawEngine.setChartViewInfo(chartViewInfo);
        backgroundEngine.setAxisInfos(new AxisInfo[]{AxisInfoEnum.LEFT.getAxisInfo(),AxisInfoEnum.BOTTOM.getAxisInfo(),AxisInfoEnum.RIGHT.getAxisInfo(),AxisInfoEnum.TOP.getAxisInfo()});
        drawEngine.setMainLineInfoList(mainLineInfoList);
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
    protected enum AxisInfoEnum{
        LEFT(new AxisInfo()),BOTTOM(new AxisInfo()),RIGHT(new AxisInfo()),TOP(new AxisInfo());
        private AxisInfo axisInfo;
        public AxisInfo getAxisInfo(){
            return axisInfo;
        }
        public void setAxisInfo(AxisInfo axisInfo){
            this.axisInfo = axisInfo;
        }
        private AxisInfoEnum(AxisInfo axisInfo){
            this.axisInfo = axisInfo;
        }
    }

    @Override
    public void setAxisColor(int color){
        for (AxisInfoEnum s:AxisInfoEnum.values()) {
            s.getAxisInfo().setAxisColor(color);
        }
    }

    @Override
    public void setAxisColor(int whichAxis,int color){
        AxisInfoEnum[] enumArr = AxisInfoEnum.values();
        enumArr[whichAxis].getAxisInfo().setAxisColor(color);
    }

    @Override
    public void setAxisWidth(float width){
        for (AxisInfoEnum s:AxisInfoEnum.values()) {
            s.getAxisInfo().setAxisWidth(width);
        }
    }

    @Override
    public void setAxisWidth(int whichAxis, float width){
        AxisInfoEnum[] enumArr = AxisInfoEnum.values();
        enumArr[whichAxis].getAxisInfo().setAxisWidth(width);
    }

    @Override
    public void setAxisInfo(int whichAxis, AxisInfo axisInfo) {
        AxisInfoEnum[] enumArr = AxisInfoEnum.values();
        enumArr[whichAxis].setAxisInfo(axisInfo);
    }

    @Override
    public void setAxisHasData(boolean hasData) {
        for (AxisInfoEnum s:AxisInfoEnum.values()) {
            s.getAxisInfo().setHasData(hasData);
        }
    }

    @Override
    public void setAxisHasData(int whichAxis, boolean hasData) {
        AxisInfoEnum[] enumArr = AxisInfoEnum.values();
        enumArr[whichAxis].getAxisInfo().setHasData(hasData);
    }

    @Override
    public void setAxisVisibility(int whichAxis, boolean visibility) {
        AxisInfoEnum[] enumArr = AxisInfoEnum.values();
        enumArr[whichAxis].getAxisInfo().setVisibility(visibility);
    }

    @Override
    public void setAxisVisibility(boolean visibility) {
        for (AxisInfoEnum s:AxisInfoEnum.values()) {
            s.getAxisInfo().setVisibility(visibility);
        }
    }

    @Override
    public void setAxisTitle(int whichAxis, String strTitle) {
        AxisInfoEnum[] enumArr = AxisInfoEnum.values();
        enumArr[whichAxis].getAxisInfo().setAxisTitle(strTitle);
    }

    @Override
    public void setAxisTextSize(int which, float textSize) {
        AxisInfoEnum[] enumArr = AxisInfoEnum.values();
        enumArr[which].getAxisInfo().setTextSize(textSize);
    }

    @Override
    public void setTextSize(float textSize) {
        chartViewInfo.setTextSize(textSize);
        for (AxisInfoEnum s:AxisInfoEnum.values()) {
            s.getAxisInfo().setTextSize(textSize);
        }
    }

    @Override
    public void setAxisAutoText(boolean autoText) {
        for (AxisInfoEnum s:AxisInfoEnum.values()) {
            s.getAxisInfo().setAutoText(autoText);
        }
    }

    @Override
    public void setAxisAutoText(int index, boolean autoText) {
        AxisInfoEnum[] enumArr = AxisInfoEnum.values();
        enumArr[index].getAxisInfo().setAutoText(autoText);
    }
    @Override
    public void setBackgroundColor(int color) {
        //view本身就有一个设置背景颜色的函数
        super.setBackgroundColor(color);
    }

    @Override
    public void setYRange(float min, float max) {
        AxisInfo axisInfo = AxisInfoEnum.LEFT.getAxisInfo();
        axisInfo.setMaxValue(max);
        axisInfo.setUserMax(max);
        axisInfo.setMinVale(min);
        axisInfo.setUserMin(min);
        if (hasDrawTheBackground()){
            setbHasDrawTheBackground(false);
            Message message = new Message();
            message.what = ASK_FOR_DRAW_WAVE;
            handler.sendMessage(message);
        }
    }

    @Override
    public void enableAxiss(boolean[] bWhichAxis) {
        chartBgInfo.enableAxiss(bWhichAxis);
    }

    @Override
    public void enableLeftAxis(boolean bHas) {
        chartBgInfo.enableLeftAxis(bHas);
    }

    @Override
    public void enableBottomAxis(boolean bHas) {
        chartBgInfo.enableBottomAxis(bHas);
    }

    @Override
    public void enableRightAxis(boolean bHas) {
        chartBgInfo.enableRightAxis(bHas);
    }

    @Override
    public void enableTopAxis(boolean bHas) {
        chartBgInfo.enableTopAxis(bHas);
    }

    @Override
    public void setDefaultLineWidth(float lineWidth) {
        defaultBgLineInfo.setLineWidth(lineWidth);
    }

    @Override
    public void setDefaultLineColor(int lineColor) {
        defaultBgLineInfo.setLineColor(lineColor);
    }

    @Override
    public void enableDefaultVerticalBackgroundLine(boolean enable) {
        defaultBgLineInfo.enableVertical(enable);
    }

    @Override
    public void enableDefaultHorizontalBackgroundLine(boolean enable) {
        defaultBgLineInfo.enableHorizontal(enable);
    }

    @Override
    public void setDefaultLineIsDotted(boolean isDotted) {
        defaultBgLineInfo.setIsDotted(isDotted);
    }

    /**
     * 波形线的链表
     */
    private List<MainLineInfo> mainLineInfoList = new ArrayList<>();

    public List<MainLineInfo> getMainLineInfoList() {
        return mainLineInfoList;
    }

    public void setMainLineInfoList(List<MainLineInfo> mainLineInfoList) {
        this.mainLineInfoList = mainLineInfoList;
    }

    @Override
    public void setMainLineWidth(int index, float lineWidth) {
        mainLineInfoList.get(index).setLineWidth(lineWidth);
    }

    @Override
    public void setMainLineColor(int index, int color) {
        mainLineInfoList.get(index).setLineColor(color);
    }

    @Override
    public void setMainLineIsDotted(int index, boolean isDotted) {
        mainLineInfoList.get(index).setIsDotted(isDotted);
    }

    @Override
    public void setHasPoints(int index, boolean hasPoints) {
        mainLineInfoList.get(index).setHasPoint(hasPoints);
    }

    @Override
    public void setHasLine(int index, boolean hasLine) {
        mainLineInfoList.get(index).setHasLine(hasLine);
    }

    @Override
    public void setMainLinePointStyle(int index, MainPointInfo mainLinePointStyle) {
        mainLineInfoList.get(index).setMainPointInfo(mainLinePointStyle);
    }

    @Override
    public void addMainLine() {
        MainLineInfo mainLineInfo = new MainLineInfo();
        mainLineInfoList.add(mainLineInfo);
    }

    @Override
    public void addMainLine(MainLineInfo mainLineInfo) {
        mainLineInfoList.add(mainLineInfo);
    }

    @Override
    public void removeMainLine(int index) {
        mainLineInfoList.remove(index);
    }

    @Override
    public void removeAllMainLine() {
        while (mainLineInfoList.size() > 0){
            mainLineInfoList.remove(0);
        }
    }

    @Override
    public void setMainLineVisibility(int index, boolean visibility) {
        mainLineInfoList.get(index).setVisibility(visibility);
    }

    @Override
    public void setMainPointColor(int index, int color) {
        mainLineInfoList.get(index).getMainPointInfo().setColor(color);
    }

    @Override
    public void setMainPointRadius(int index, float radius) {
        mainLineInfoList.get(index).getMainPointInfo().setRadius(radius);
    }

    @Override
    public void setIsStroke(int index, boolean isStroke) {
        mainLineInfoList.get(index).getMainPointInfo().setStroke(isStroke);
    }

    @Override
    public void setHorizontalResolution(float horizontalResolution) {
        for (MainLineInfo mainLineInfo : mainLineInfoList){
            mainLineInfo.setHorizontalResolution(horizontalResolution);
        }
    }

    @Override
    public void setHorizontalResolution(int index, float horizontalResolution) {
        mainLineInfoList.get(index).setHorizontalResolution(horizontalResolution);
    }

    @Override
    public void setAllowTouchEvent(boolean allowTouchEvent) {
        touchEngine.getTouchInfo().setAllowTouchEvent(allowTouchEvent);
    }

    @Override
    public void setAllowTouchX(boolean allowTouchX) {
        touchEngine.getTouchInfo().setAllowTouchX(allowTouchX);
    }

    @Override
    public void setAllowTouchY(boolean allowTouchY) {
        touchEngine.getTouchInfo().setAllowTouchY(allowTouchY);
    }

    @Override
    public void setAllowZoom(boolean allowZoom) {
        touchEngine.getTouchInfo().setAllowZoom(allowZoom);
    }

    @Override
    public void setAllowTranslation(boolean allowTranslation) {
        touchEngine.getTouchInfo().setAllowTranslation(allowTranslation);
    }

    @Override
    public void setAllowTouchXZoom(boolean allowTouchXZoom) {
        touchEngine.getTouchInfo().setAllowTouchXZoom(allowTouchXZoom);
    }

    @Override
    public void setAllowTouchYZoom(boolean allowTouchYZoom) {
        touchEngine.getTouchInfo().setAllowTouchYZoom(allowTouchYZoom);
    }

    @Override
    public void setInitHorizontalResolution(int index, float initHorizontalResolution) {
        if (mainLineInfoList.get(index).getInitHorizontalResolution() == 0){
            mainLineInfoList.get(index).setInitHorizontalResolution(initHorizontalResolution);
            mainLineInfoList.get(index).setHorizontalResolution(initHorizontalResolution);
        }
    }

    @Override
    public void setInitAViewPointsSum(int index, int initAViewPointsSum) {
        mainLineInfoList.get(index).setInitAViewPointsSum(initAViewPointsSum);
    }

    @Override
    public void setNormalOffsetX(float normalOffsetX) {
        for (MainLineInfo mainLineInfo : mainLineInfoList){
            mainLineInfo.setNormalOffsetX(normalOffsetX);
        }
    }

    @Override
    public void setNormalOffsetX(int index, float normalOffsetX) {
        mainLineInfoList.get(index).setNormalOffsetX(normalOffsetX);
    }

    @Override
    public void setShowDataDiv(boolean bShowDataDiv) {
        for (MainLineInfo mainLineInfo : mainLineInfoList){
            mainLineInfo.setShowDataDiv(bShowDataDiv);
        }
    }

    @Override
    public void setShowDataDiv(int index, boolean bShowDataDiv) {
        mainLineInfoList.get(index).setShowDataDiv(bShowDataDiv);
    }

    @Override
    public void setDivTextColor(int textColor) {
        for (MainLineInfo mainLineInfo : mainLineInfoList){
            mainLineInfo.getDataDivInfo().setTextColor(textColor);
        }
    }

    @Override
    public void setDivTextColor(int index, int textColor) {
        mainLineInfoList.get(index).getDataDivInfo().setTextColor(textColor);
    }

    @Override
    public void setDivBackgroundColor(int backgroundColor) {
        for (MainLineInfo mainLineInfo : mainLineInfoList){
            mainLineInfo.getDataDivInfo().setBackgroundColor(backgroundColor);
        }
    }

    @Override
    public void setDivBackgroundColor(int index, int backgroundColor) {
        mainLineInfoList.get(index).getDataDivInfo().setBackgroundColor(backgroundColor);
    }

    /**
     * 改变背景
     */
    public void changeBackground(){
        if (hasDrawTheBackground()){
            setbHasDrawTheBackground(false);
            Message message = new Message();
            message.what = ASK_FOR_DRAW_WAVE;
            handler.sendMessage(message);
        }
    }

    public static final int ASK_FOR_DRAW_WAVE = 0x01;

    /**
     * 开启一个Handler防止用户在非ui线程中调用绘图函数
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ASK_FOR_DRAW_WAVE:
                    invalidate();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 单独更新某条波形图
     * @param index
     * @param XData
     * @param YData
     */
    public void drawWave(int index, String XData, float YData){
        mainLineInfoList.get(index).addData(XData,YData);
        Message message = new Message();
        message.what = ASK_FOR_DRAW_WAVE;
        handler.sendMessage(message);
    }

    /**
     * 更新全部波形图，前提是有点的假如，否则只是重新绘制了图片而已
     */
    public void drawWave(){
        Message message = new Message();
        message.what = ASK_FOR_DRAW_WAVE;
        handler.sendMessage(message);
    }

    /**
     * 为每条波形增加点
     * @param dataPoint
     */
    public void addPoint(DataPoint dataPoint){
        for (MainLineInfo mainLineInfo : mainLineInfoList){
            mainLineInfo.addPoint(dataPoint);
        }
    }

    /**
     * 为每条波形增加点
     * @param XData
     * @param YData
     */
    public void addPoint(String XData, float YData){
        for (MainLineInfo mainLineInfo : mainLineInfoList){
            mainLineInfo.addData(XData,YData);
        }
    }

    /**
     * 为每条波形增加点
     * @param YData
     */
    public void addPoint(float YData){
        for (MainLineInfo mainLineInfo : mainLineInfoList){
            mainLineInfo.addData("",YData);
        }
    }

    /**
     * 为某条数据添加点
     * @param index
     * @param YData
     */
    public void addPoint(int index, float YData){
        mainLineInfoList.get(index).addData("",YData);
    }
    /**
     * 为某条波形增加点
     * @param index
     * @param YData
     * @param XData
     */
    public void addPoint(int index, float YData, String XData){
        mainLineInfoList.get(index).addData(XData,YData);
    }

    /**
     * 为某条波形添加点
     * @param index
     * @param YData
     * @param XData
     * @param show
     */
    public void addPoint(int index, float YData, String XData, boolean show){
        mainLineInfoList.get(index).addData(XData,YData);
        mainLineInfoList.get(index).getDataList().get(mainLineInfoList.get(index).getDataList().size() - 1).setShowXData(show);
    }
    /**
     * 为某条数据添加点
     * @param index
     * @param dataPoint
     */
    public void addPoint(int index, DataPoint dataPoint){
        mainLineInfoList.get(index).addPoint(dataPoint);
    }

//TODO 接口的健壮性有待提高，比如很多都没有判断index是否超出size
    @Override
    public float getMainLineWidth(int index) {
        if (mainLineInfoList.size() - 1 < index){
            return -1;
        }
        return mainLineInfoList.get(index).getLineWidth();
    }

    @Override
    public int getMainLineColor(int index) {
        if (mainLineInfoList.size() - 1 < index){
            return -1;
        }
        return mainLineInfoList.get(index).getLineColor();
    }

    @Override
    public boolean isMainLineDotted(int index) {

        return mainLineInfoList.get(index).isbIsDotted();
    }

    @Override
    public boolean isMainLineHasPoints(int index) {
        return mainLineInfoList.get(index).isHasPoint();
    }

    @Override
    public boolean isMainLineHasLine(int index) {
        return mainLineInfoList.get(index).isHasLine();
    }

    @Override
    public MainPointInfo getMainLinePointSyle(int index) {
        return mainLineInfoList.get(index).getMainPointInfo();
    }

    @Override
    public List<MainLineInfo> getMainLineList() {
        return mainLineInfoList;
    }

    @Override
    public MainLineInfo getMainLine(int index) {
        return mainLineInfoList.get(index);
    }

    @Override
    public float getHorizontalResolution(int index) {
        return mainLineInfoList.get(index).getHorizontalResolution();
    }

    @Override
    public boolean getMainLineVisibility(int index) {
        return mainLineInfoList.get(index).isVisibility();
    }


}
