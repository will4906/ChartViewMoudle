package com.example.will.chartviewlib;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.will.canvaslib.CanvasTool;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.BgLineInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.ChartBgInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.DefaultBgLineInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.IBgLineInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.IChartBgInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.IDefaultBgLineInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.IScaleInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.ScaleInfo;
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

public class LineChartView extends BaseLineChart implements IScaleInfo,IChartViewInfo,IChartBgInfo, IBgLineInfo, IDefaultBgLineInfo, IMainLineInfo,IMainPointInfo, ITouchInfo {

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
        setScaleWidth(textView.getTextSize() / 7);
        drawEngine.setDefaultBgLineInfo(defaultBgLineInfo);
        drawEngine.setOnDrawBackgroundListener(onDrawBackgroundListener);
        drawEngine.setCharBgInfo(chartBgInfo);
        drawEngine.setChartViewInfo(chartViewInfo);
        drawEngine.setScaleInfos(new ScaleInfo[]{ScaleInfoEnum.LEFT.getScaleInfo(),ScaleInfoEnum.BOTTOM.getScaleInfo(),ScaleInfoEnum.RIGHT.getScaleInfo(),ScaleInfoEnum.TOP.getScaleInfo()});
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
    public void setYRange(int min, int max) {
        ScaleInfo scaleInfo = ScaleInfoEnum.LEFT.getScaleInfo();
        String strMax = String.valueOf(max);
        String strMin = String.valueOf(min);
        int oldLen = String.valueOf(scaleInfo.getMaxValue()).length() > String.valueOf(scaleInfo.getMinVale()).length()
                ? String.valueOf(scaleInfo.getMaxValue()).length() : String.valueOf(scaleInfo.getMinVale()).length();
        if (strMax.length() > oldLen|| strMin.length() > oldLen){
            int len = strMax.length() > strMin.length() ? strMax.length() : strMin.length();
            len = (len + 1) / 2;
            scaleInfo.setSpace(scaleInfo.getSpace() + chartViewInfo.getTextSize() / 12 * 7 * len);
        }else if (strMax.length() != oldLen && strMin.length() != oldLen){
            int len = strMax.length() > strMin.length() ? strMax.length() : strMin.length();
            len = (len + 1) / 2;
            scaleInfo.setSpace(scaleInfo.getSpace() - chartViewInfo.getTextSize() / 12 * 7 * len);
        }
        scaleInfo.setMaxValue(max);
        scaleInfo.setMinVale(min);
        if (hasDrawTheBackground()){
            invalidate();
        }
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
    public void setHorizontalReslution(float horizontalReslution) {
        chartViewInfo.setHorizontalReslution(horizontalReslution);
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
     * @param data
     */
    public void drawWave(int index, float data){
        mainLineInfoList.get(index).addData(data);
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
     * @param data
     */
    public void addPoint(float data){
        for (MainLineInfo mainLineInfo : mainLineInfoList){
            mainLineInfo.addData(data);
        }
    }

    /**
     * 为某条波形增加点
     * @param index
     * @param data
     */
    public void addPoint(int index, float data){
        mainLineInfoList.get(index).addData(data);
    }

}
