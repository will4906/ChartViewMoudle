package com.example.will.chartviewlib.DrawFactory;

import android.graphics.Bitmap;
import android.graphics.Paint;

import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.BgLineInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.AxisInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.ChartBgInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.DefaultBgLineInfo;
import com.example.will.chartviewlib.Common.CanvasTool;
import com.example.will.chartviewlib.LineChartView;

import java.util.List;

import static com.example.will.chartviewlib.LineChartView.BGLINE_HORIZONTAL;
import static com.example.will.chartviewlib.LineChartView.BGLINE_VERTICAL;
import static com.example.will.chartviewlib.LineChartView.BOTTOM_AXIS;
import static com.example.will.chartviewlib.LineChartView.LEFT_AXIS;
import static com.example.will.chartviewlib.LineChartView.RIGHT_AXIS;
import static com.example.will.chartviewlib.LineChartView.TOP_AXIS;

/**
 * @author will4906.
 * @Time 2016/12/17.
 */

public class BackgroundEngine extends Colleague{

    //信息中介
    private InformationMediator informationMediator;

    public BackgroundEngine(InformationMediator informationMediator){
        this.informationMediator = informationMediator;
        informationMediator.setBackgroundEngine(this);
    }

    //背景宽度
    private int backgroundWidth = 0;
    //背景高度
    private int backgroundHeight = 0;

    public int getBackgroundWidth() {
        return backgroundWidth;
    }

    public void setBackgroundWidth(int backgroundWidth) {
        this.backgroundWidth = backgroundWidth;
    }

    public int getBackgroundHeight() {
        return backgroundHeight;
    }

    public void setBackgroundHeight(int backgroundHeight) {
        this.backgroundHeight = backgroundHeight;
    }

    /**
     * 自定义绘制的回调
     */
    private OnDrawBackgroundListener onDrawBackgroundListener;

    public OnDrawBackgroundListener getOnDrawBackgroundListener() {
        return onDrawBackgroundListener;
    }

    public void setOnDrawBackgroundListener(OnDrawBackgroundListener onDrawBackgroundListener) {
        this.onDrawBackgroundListener = onDrawBackgroundListener;
    }

    /**
     * 背景线链表
     */
    private List<BgLineInfo> bgLineInfoList;

    public List<BgLineInfo> getBgLineInfoList() {
        return bgLineInfoList;
    }

    public void setBgLineInfoList(List<BgLineInfo> bgLineInfoList) {
        this.bgLineInfoList = bgLineInfoList;
    }

    private AxisInfo[] axisInfos;

    public AxisInfo[] getAxisInfos() {
        return axisInfos;
    }

    public void setAxisInfos(AxisInfo[] axisInfos) {
        this.axisInfos = axisInfos;
    }
    
    //表宽
    private float chartWidth = 0;
    //表高
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

    /**
     * 背景线信息
     */
    private ChartBgInfo chartBgInfo;

    public ChartBgInfo getChartBgInfo() {
        return chartBgInfo;
    }

    public void setChartBgInfo(ChartBgInfo chartBgInfo) {
        this.chartBgInfo = chartBgInfo;
    }

    private DefaultBgLineInfo defaultBgLineInfo;

    public DefaultBgLineInfo getDefaultBgLineInfo() {
        return defaultBgLineInfo;
    }

    public void setDefaultBgLineInfo(DefaultBgLineInfo defaultBgLineInfo) {
        this.defaultBgLineInfo = defaultBgLineInfo;
    }

    public Bitmap drawChartViewBackground(int width, int height){
        backgroundHeight = height;
        backgroundWidth = width;
        if (height <= 0){
            height = 1;
        }
        if (width <= 0){
            width = 1;
        }
        CanvasTool canvasTool = new CanvasTool();
        CanvasTool customCanvasTool = new CanvasTool();
        Bitmap bitmap;
        canvasTool.startDrawOnABitmap(width,height);
        computeForBackground(width,height);
        drawBackground(canvasTool,width,height);
        drawAxis(canvasTool,width,height);
        drawBgLine(canvasTool, width, height);

        customCanvasTool.startDrawOnABitmap(width,height);
        if (onDrawBackgroundListener.onBackgroundDraw(customCanvasTool) == false){
            canvasTool = customCanvasTool;
        }else{
            canvasTool.drawBitmap(customCanvasTool.getCacheBitmap(),0,height);
        }
        bitmap = canvasTool.getCacheBitmap();
        informationMediator.change(this);
        return bitmap;
    }

    /**
     * 绘制底景
     * @param canvasTool
     * @param width
     * @param height
     */
    public void drawBackground(CanvasTool canvasTool, int width, int height){
        //暂时留作接口，没啥可以画的
    }

    /**
     * 绘制坐标轴
     * @param canvasTool
     * @param width
     * @param height
     */
    public void drawAxis(CanvasTool canvasTool,int width, int height){
        if (chartBgInfo.isbHasAxis()){
            if (chartBgInfo.getWhichAxisEnable()[LineChartView.LEFT_AXIS]){
                drawLeftAxis(canvasTool,width,height);
            }
            if (chartBgInfo.getWhichAxisEnable()[LineChartView.BOTTOM_AXIS]){
                drawBottomAxis(canvasTool,width,height);
            }
            if (chartBgInfo.getWhichAxisEnable()[LineChartView.RIGHT_AXIS]){
                drawRightAxis(canvasTool,width,height);
            }
            if (chartBgInfo.getWhichAxisEnable()[LineChartView.TOP_AXIS]){
                drawTopAxis(canvasTool,width,height);
            }
        }
    }

    /**
     * 绘制左边坐标轴
     * @param canvasTool
     * @param width
     * @param height
     */
    public void drawLeftAxis(CanvasTool canvasTool, int width, int height){
        AxisInfo axisInfo = axisInfos[LineChartView.LEFT_AXIS];
        float textSize = axisInfo.getTextSize();
        Paint paint = axisInfo.getPaint();
        float space = axisInfo.getSpace();

        if (axisInfo.isVisibility()){
            canvasTool.drawLine(space, axisInfos[LineChartView.BOTTOM_AXIS].getSpace() - paint.getStrokeWidth() / 2,
                    space, height - axisInfos[LineChartView.TOP_AXIS].getSpace() + paint.getStrokeWidth() / 2, paint);
        }
        paint = axisInfo.getTextPaint();
        paint.setTextAlign(Paint.Align.LEFT);
        canvasTool.drawText(axisInfo.getAxisTitle(),0,height - textSize,paint);
    }

    /**
     * 绘制底部坐标轴
     * @param canvasTool
     * @param width
     * @param height
     */
    public void drawBottomAxis(CanvasTool canvasTool, int width, int height){
        AxisInfo axisInfo = axisInfos[LineChartView.BOTTOM_AXIS];
        float textSize = axisInfo.getTextSize();
        Paint paint = axisInfo.getPaint();
        float space = axisInfo.getSpace();
        paint.setTextAlign(Paint.Align.RIGHT);
        if (axisInfo.isVisibility()){
            canvasTool.drawLine(axisInfos[LineChartView.LEFT_AXIS].getSpace(), space, width - axisInfos[LineChartView.RIGHT_AXIS].getSpace(), space, paint);
        }
        if (axisInfos[LineChartView.RIGHT_AXIS].getSpace() >= textSize * 2){
            paint = axisInfo.getTextPaint();
            canvasTool.drawTextOnPath(axisInfo.getAxisTitle(),width,height / 2,width,10, 0,textSize,paint);
        }
    }

    /**
     * 绘制右边坐标轴
     * @param canvasTool
     * @param width
     * @param height
     */
    public void drawRightAxis(CanvasTool canvasTool, int width, int height){
        AxisInfo axisInfo = axisInfos[LineChartView.RIGHT_AXIS];
        float textSize = axisInfo.getTextSize();
        Paint paint = axisInfo.getPaint();
        float space = axisInfo.getSpace();
        if (axisInfo.isVisibility()){
            canvasTool.drawLine(width - space, axisInfos[LineChartView.BOTTOM_AXIS].getSpace() - paint.getStrokeWidth() / 2,
                    width - space, height - axisInfos[LineChartView.TOP_AXIS].getSpace() + paint.getStrokeWidth() / 2,paint);
        }
        axisInfo.getTextPaint().setTextAlign(Paint.Align.RIGHT);
        if (axisInfos[LineChartView.TOP_AXIS].getSpace() >= textSize * 2){
            canvasTool.drawText(axisInfo.getAxisTitle(),width,height - textSize, axisInfo.getTextPaint());
        }
    }

    /**
     * 绘制顶部坐标轴
     * @param canvasTool
     * @param width
     * @param height
     */
    public void drawTopAxis(CanvasTool canvasTool, int width, int height){
        AxisInfo axisInfo = axisInfos[LineChartView.TOP_AXIS];
        float textSize = axisInfo.getTextSize();
        Paint paint = axisInfo.getPaint();
        float space = axisInfo.getSpace();
        if (axisInfos[TOP_AXIS].isVisibility()){
            canvasTool.drawLine(axisInfos[LineChartView.LEFT_AXIS].getSpace(), height - space,width - axisInfos[LineChartView.RIGHT_AXIS].getSpace(), height - space,paint);
        }
        paint.setTextAlign(Paint.Align.LEFT);
        paint = axisInfo.getTextPaint();
        if (axisInfos[LineChartView.RIGHT_AXIS].getSpace() >= textSize * 2){
            canvasTool.drawTextOnPath(axisInfo.getAxisTitle(),width,height - textSize,width,height / 2,5,textSize,paint);
        }
    }

    /**
     * 绘制背景线函数
     * @param canvasTool
     * @param width
     * @param height
     */
    public void drawBgLine(CanvasTool canvasTool, int width, int height){
        if (chartBgInfo.hasBgLine()){
            if (chartBgInfo.isUseDefaultBgLines()){
                drawDefaultBgLine(canvasTool,width,height);
            }
            drawUserBgLines(canvasTool,width,height);
        }
    }

    /**
     * 绘制用户自己设定的背景线
     * @param canvasTool
     * @param width
     * @param height
     */
    public void drawUserBgLines(CanvasTool canvasTool, int width, int height){
        for (BgLineInfo bgLineInfo : bgLineInfoList){
            Paint paint = bgLineInfo.getPaint();
            int direction = bgLineInfo.getDirection();
            switch (direction){
                case BGLINE_HORIZONTAL:{
                    float pos = bgLineInfo.getLinePos();
                    pos = changeUserDataToChartViewData(pos,chartHeight,LEFT_AXIS);
                    pos += axisInfos[BOTTOM_AXIS].getSpace() + axisInfos[BOTTOM_AXIS].getAxisWidth();
                    if (pos >= axisInfos[BOTTOM_AXIS].getSpace() + axisInfos[BOTTOM_AXIS].getAxisWidth() && pos <= axisInfos[BOTTOM_AXIS].getSpace() + axisInfos[BOTTOM_AXIS].getAxisWidth() + chartHeight){
                        if (bgLineInfo.isbIsDotted()){
                            canvasTool.drawDottedLine(axisInfos[LEFT_AXIS].getSpace() + axisInfos[LEFT_AXIS].getAxisWidth() / 2,pos, axisInfos[LEFT_AXIS].getSpace() + axisInfos[LEFT_AXIS].getAxisWidth() / 2 + chartWidth, pos,20,paint);
                        }else{
                            canvasTool.drawLine(axisInfos[LEFT_AXIS].getSpace() + axisInfos[LEFT_AXIS].getAxisWidth() / 2,pos, axisInfos[LEFT_AXIS].getSpace() + axisInfos[LEFT_AXIS].getAxisWidth() / 2 + chartWidth, pos,paint);
                        }
                        String strTitle = bgLineInfo.getStrTitle();
                        if (!strTitle.equals("")){
                            switch (bgLineInfo.getTitlePos()){
                                case LEFT_AXIS:{
                                    if (axisInfos[LEFT_AXIS].isHasData()){
                                        Paint leftPaint = bgLineInfo.getTextPaint();
                                        leftPaint.setTextSize(axisInfos[LEFT_AXIS].getTextSize());
                                        leftPaint.setTextAlign(Paint.Align.LEFT);
                                        canvasTool.drawText(strTitle,0,pos - axisInfos[LEFT_AXIS].getTextSize() / 2,leftPaint);
                                    }
                                }
                                break;
                                case RIGHT_AXIS:{
                                    if (axisInfos[RIGHT_AXIS].isHasData()){
                                        Paint leftPaint = bgLineInfo.getTextPaint();
                                        leftPaint.setTextSize(axisInfos[RIGHT_AXIS].getTextSize());
                                        leftPaint.setTextAlign(Paint.Align.RIGHT);
                                        if (axisInfos[TOP_AXIS].getAxisTitle().equals("") && axisInfos[BOTTOM_AXIS].getAxisTitle().equals("")){
                                            canvasTool.drawText(strTitle,backgroundWidth,pos - axisInfos[RIGHT_AXIS].getTextSize() / 2,leftPaint);
                                        }else{
                                            canvasTool.drawText(strTitle,backgroundWidth - axisInfos[RIGHT_AXIS].getTextSize(),pos - axisInfos[RIGHT_AXIS].getTextSize() / 2,leftPaint);
                                        }
                                    }
                                }
                                break;
                                default:
                                    break;
                            }
                        }
                    }
                }
                break;
                case BGLINE_VERTICAL:{
                    float pos = bgLineInfo.getLinePos();
                    pos = changeUserDataToChartViewData(pos,chartWidth,BOTTOM_AXIS);
                    pos += axisInfos[LEFT_AXIS].getSpace() + axisInfos[LEFT_AXIS].getAxisWidth();
                    if (pos > axisInfos[LEFT_AXIS].getSpace() + axisInfos[LEFT_AXIS].getAxisWidth() && pos < axisInfos[LEFT_AXIS].getSpace() + axisInfos[LEFT_AXIS].getAxisWidth() + chartWidth){
                        if (bgLineInfo.isbIsDotted()){
                            canvasTool.drawDottedLine(pos, axisInfos[BOTTOM_AXIS].getSpace() + axisInfos[BOTTOM_AXIS].getAxisWidth() / 2, pos, axisInfos[BOTTOM_AXIS].getSpace() + axisInfos[BOTTOM_AXIS].getAxisWidth() / 2 + chartHeight,20,paint);
                        }else{
                            canvasTool.drawLine(pos, axisInfos[BOTTOM_AXIS].getSpace() + axisInfos[BOTTOM_AXIS].getAxisWidth() / 2, pos, axisInfos[BOTTOM_AXIS].getSpace() + axisInfos[BOTTOM_AXIS].getAxisWidth() / 2 + chartHeight,paint);
                        }
                    }
                }
                break;
                default:
                    break;
            }
        }
    }
    /**
     * 默认模式画背景线
     * @param canvasTool
     * @param width
     * @param height
     */
    private void drawDefaultBgLine(CanvasTool canvasTool, int width, int height){
        Paint paint = defaultBgLineInfo.getPaint();
        if (defaultBgLineInfo.isVertical()){
            drawVerticalBgLine(canvasTool,width,height,paint);
        }
        if (defaultBgLineInfo.isHorizontal()){
            drawHorizontalBgLine(canvasTool,width,height,paint);
        }
    }

    /**
     * 绘制纵向背景线
     * @param canvasTool
     * @param width
     * @param height
     * @param paint
     */
    private void drawVerticalBgLine(CanvasTool canvasTool, int width, int height, Paint paint){
        float widthIndex = axisInfos[LEFT_AXIS].getSpace();
        float heightIndex = axisInfos[BOTTOM_AXIS].getSpace();
        widthIndex += 100;
        while (widthIndex < width - axisInfos[RIGHT_AXIS].getSpace()){
            if (defaultBgLineInfo.isbIsDotted()){                                               //画虚线
                while (heightIndex < height - axisInfos[TOP_AXIS].getSpace()){
                    if (heightIndex + 20 >= height - axisInfos[TOP_AXIS].getSpace()){
                        canvasTool.drawLine(widthIndex, heightIndex, widthIndex, height - axisInfos[TOP_AXIS].getSpace(), paint);
                    }else{
                        canvasTool.drawLine(widthIndex, heightIndex, widthIndex, heightIndex + 20, paint);
                    }
                    heightIndex += 40;
                }
                heightIndex = axisInfos[BOTTOM_AXIS].getSpace();
            }else{                                                                              //画实线
                canvasTool.drawLine(widthIndex, axisInfos[BOTTOM_AXIS].getSpace(),widthIndex,height - axisInfos[TOP_AXIS].getSpace(),paint);
            }
            widthIndex += 100;
        }
    }

    /**
     * 绘制横向背景线
     * @param canvasTool
     * @param width
     * @param height
     * @param paint
     */
    private void drawHorizontalBgLine(CanvasTool canvasTool, int width, int height, Paint paint){
        float widthIndex = axisInfos[LEFT_AXIS].getSpace();
        float heightIndex = axisInfos[BOTTOM_AXIS].getSpace();
        heightIndex += 100;
        while (heightIndex < height - axisInfos[TOP_AXIS].getSpace()){
            if (defaultBgLineInfo.isbIsDotted()){
                while (widthIndex < width - axisInfos[RIGHT_AXIS].getSpace()){
                    if (widthIndex + 20 >= width - axisInfos[RIGHT_AXIS].getSpace()){
                        canvasTool.drawLine(widthIndex,heightIndex,width - axisInfos[RIGHT_AXIS].getSpace(), heightIndex,paint);
                    }else{
                        canvasTool.drawLine(widthIndex,heightIndex,widthIndex + 20, heightIndex,paint);
                    }
                    widthIndex += 40;
                }
                widthIndex = axisInfos[LEFT_AXIS].getSpace();
            }else{
                canvasTool.drawLine(axisInfos[LEFT_AXIS].getSpace(),heightIndex,width - axisInfos[RIGHT_AXIS].getSpace(),heightIndex,paint);
            }
            heightIndex += 100;
        }
    }

    /**
     * 计算背景的各种数据
     * @param width
     * @param height
     */
    private void computeForBackground(int width, int height){
        for (BgLineInfo bgLineInfo : bgLineInfoList){
            int direction = bgLineInfo.getDirection();
            switch (direction){
                case BGLINE_HORIZONTAL:{
                    int pos = bgLineInfo.getTitlePos();
                    switch (pos){
                        case LEFT_AXIS:{
                            AxisInfo axisInfo = axisInfos[pos];
                            Paint leftPaint = axisInfo.getTextPaint();
                            float space = axisInfo.getSpace();
                            String strTitle = bgLineInfo.getStrTitle();
                            float strLen = leftPaint.measureText(strTitle);
                            if (strLen > space){
                                if (axisInfo.isHasData()){
                                    axisInfo.setSpace(strLen + axisInfo.getAxisWidth() / 2);
                                }
                            }
                        }
                        case RIGHT_AXIS:{
                            AxisInfo axisInfo = axisInfos[pos];
                            Paint leftPaint = axisInfo.getTextPaint();
                            float space = axisInfo.getSpace();
                            String strTitle = bgLineInfo.getStrTitle();
                            float strLen = leftPaint.measureText(strTitle);
                            if (strLen > space){
                                if (axisInfo.isHasData()){
                                    if (axisInfo.getAxisTitle().equals("")){
                                        axisInfo.setSpace(strLen + axisInfo.getAxisWidth() / 2);
                                        if (!axisInfos[BOTTOM_AXIS].getAxisTitle().equals("") || !axisInfos[TOP_AXIS].getAxisTitle().equals("")){
                                            axisInfo.setSpace(axisInfo.getSpace() + axisInfos[BOTTOM_AXIS].getTextSize());
                                        }
                                    }else{
                                        axisInfo.setSpace(strLen + axisInfo.getTextSize() + axisInfo.getAxisWidth() / 2);
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
                break;
                case BGLINE_VERTICAL:
                    //纵向暂时不允许添加东西
                    break;
                default:
                    break;
            }
        }
        axisInfos[TOP_AXIS].setSpace(axisInfos[TOP_AXIS].getTextSize() * 2);
        //表的理论宽度
        chartWidth = width - axisInfos[LEFT_AXIS].getSpace() - axisInfos[RIGHT_AXIS].getSpace() - axisInfos[LEFT_AXIS].getAxisWidth() / 2 - axisInfos[RIGHT_AXIS].getAxisWidth() / 2;
        //理论高度
        chartHeight = height - axisInfos[TOP_AXIS].getSpace() - axisInfos[BOTTOM_AXIS].getSpace() - axisInfos[TOP_AXIS].getAxisWidth() / 2 - axisInfos[BOTTOM_AXIS].getAxisWidth() / 2;
    }

    /**
     * 将用户传进来的数据转换为像素数据
     * @param userData
     * @param length
     * @return
     */
    private float changeUserDataToChartViewData(float userData, float length, int which){
        float chartViewData = 0;
        float max = axisInfos[which].getMaxValue();
        float min = axisInfos[which].getMinVale();
        float div = (max - min) / length;
        chartViewData = (userData - min) / div;
        return chartViewData;
    }
}
