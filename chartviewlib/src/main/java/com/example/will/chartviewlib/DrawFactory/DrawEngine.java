package com.example.will.chartviewlib.DrawFactory;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.will.chartviewlib.ChartInfo.MainLayer.DataPoint;
import com.example.will.chartviewlib.Common.CanvasTool;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.BgLineInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.ChartBgInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.DefaultBgLineInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.ScaleInfo;
import com.example.will.chartviewlib.ChartInfo.ChartViewInfo;
import com.example.will.chartviewlib.ChartInfo.MainLayer.MainLineInfo;
import com.example.will.chartviewlib.Common.FloatTool;
import com.example.will.chartviewlib.TouchFactory.TouchParam;
import com.example.will.chartviewlib.LineChartView;

import static com.example.will.chartviewlib.LineChartView.BGLINE_HORIZONTAL;
import static com.example.will.chartviewlib.LineChartView.BGLINE_VERTICAL;
import static com.example.will.chartviewlib.LineChartView.TOP_SCALE;
import static com.example.will.chartviewlib.LineChartView.BOTTOM_SCALE;
import static com.example.will.chartviewlib.LineChartView.RIGHT_SCALE;
import static com.example.will.chartviewlib.LineChartView.LEFT_SCALE;

import java.util.List;

/**
 * 其实这个类做的不是很好，本来只想用来处理关于绘图的大部分事件，可是现在很多数据处理也丢到里面去了
 * @author will4906.
 * @Time 2016/11/22.
 */

public class DrawEngine {

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

    private OnDrawBackgroundListener onDrawBackgroundListener;

    public OnDrawBackgroundListener getOnDrawBackgroundListener() {
        return onDrawBackgroundListener;
    }

    public void setOnDrawBackgroundListener(OnDrawBackgroundListener onDrawBackgroundListener) {
        this.onDrawBackgroundListener = onDrawBackgroundListener;
    }

    private ChartViewInfo chartViewInfo;
    private ChartBgInfo charBgInfo;
    private ScaleInfo[] scaleInfos;
    private List<BgLineInfo> bgLineInfoList;
    private DefaultBgLineInfo defaultBgLineInfo;
    private TouchParam touchParam;

    public TouchParam getTouchParam() {
        return touchParam;
    }

    public void setTouchParam(TouchParam touchParam) {
        this.touchParam = touchParam;
    }

    public DefaultBgLineInfo getDefaultBgLineInfo() {
        return defaultBgLineInfo;
    }

    public void setDefaultBgLineInfo(DefaultBgLineInfo defaultBgLineInfo) {
        this.defaultBgLineInfo = defaultBgLineInfo;
    }

    public ChartBgInfo getCharBgInfo() {
        return charBgInfo;
    }

    public void setCharBgInfo(ChartBgInfo charBgInfo) {
        this.charBgInfo = charBgInfo;
    }

    public ScaleInfo[] getScaleInfos() {
        return scaleInfos;
    }

    public void setScaleInfos(ScaleInfo[] scaleInfos) {
        this.scaleInfos = scaleInfos;
    }

    public ChartViewInfo getChartViewInfo() {
        return chartViewInfo;
    }

    public void setChartViewInfo(ChartViewInfo chartViewInfo) {
        this.chartViewInfo = chartViewInfo;
    }

    public List<BgLineInfo> getBgLineInfoList() {
        return bgLineInfoList;
    }

    public void setBgLineInfoList(List<BgLineInfo> bgLineInfoList) {
        this.bgLineInfoList = bgLineInfoList;
    }

    public Bitmap drawChartViewBackground(int width, int height){
        backgroundHeight = height;
        backgroundWidth = width;
        CanvasTool canvasTool = new CanvasTool();
        CanvasTool customCanvasTool = new CanvasTool();
        Bitmap bitmap;
        canvasTool.startDrawOnABitmap(width,height);
        computeForBackground(width,height);
        drawBackground(canvasTool,width,height);
        drawBgLine(canvasTool, width, height);
        drawScale(canvasTool,width,height);

        customCanvasTool.startDrawOnABitmap(width,height);
        if (onDrawBackgroundListener.onBackgroundDraw(customCanvasTool) == false){
            canvasTool = customCanvasTool;
        }else{
            canvasTool.drawBitmap(customCanvasTool.getCacheBitmap(),0,height);
        }
        bitmap = canvasTool.getCacheBitmap();
        return bitmap;
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
                        case LEFT_SCALE:{
                            ScaleInfo scaleInfo = scaleInfos[pos];
                            Paint leftPaint = scaleInfo.getTextPaint();
                            float space = scaleInfo.getSpace();
                            String strTitle = bgLineInfo.getStrTitle();
                            float strLen = leftPaint.measureText(strTitle);
                            if (strLen > space){
                                if (scaleInfo.isHasData()){
                                    scaleInfo.setSpace(strLen + scaleInfo.getScaleWidth() / 2);
                                }
                            }
                        }
                        case RIGHT_SCALE:{
                            ScaleInfo scaleInfo = scaleInfos[pos];
                            Paint leftPaint = scaleInfo.getTextPaint();
                            float space = scaleInfo.getSpace();
                            String strTitle = bgLineInfo.getStrTitle();
                            float strLen = leftPaint.measureText(strTitle);
                            if (strLen > space){
                                if (scaleInfo.isHasData()){
                                    if (scaleInfo.getScaleTitle().equals("")){
                                        scaleInfo.setSpace(strLen + scaleInfo.getScaleWidth() / 2);
                                        if (scaleInfos[BOTTOM_SCALE].getScaleTitle().equals("") || scaleInfos[TOP_SCALE].getScaleTitle().equals("")){
                                            scaleInfo.setSpace(scaleInfo.getSpace() + scaleInfos[BOTTOM_SCALE].getTextSize());
                                        }
                                    }else{
                                        scaleInfo.setSpace(strLen + scaleInfo.getTextSize() + scaleInfo.getScaleWidth() / 2);
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
        scaleInfos[TOP_SCALE].setSpace(scaleInfos[TOP_SCALE].getTextSize() * 2);
        //表的理论宽度
        chartWidth = width - scaleInfos[LEFT_SCALE].getSpace() - scaleInfos[RIGHT_SCALE].getSpace() - scaleInfos[LEFT_SCALE].getScaleWidth() / 2 - scaleInfos[RIGHT_SCALE].getScaleWidth() / 2;
        //理论高度
        chartHeight = height - scaleInfos[TOP_SCALE].getSpace() - scaleInfos[BOTTOM_SCALE].getSpace() - scaleInfos[TOP_SCALE].getScaleWidth() / 2 - scaleInfos[BOTTOM_SCALE].getScaleWidth() / 2;
    }
    /**
     * 绘制坐标轴
     * @param canvasTool
     * @param width
     * @param height
     */
    public void drawScale(CanvasTool canvasTool,int width, int height){
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        float textSize = chartViewInfo.getTextSize();
        paint.setTextSize(textSize);
        paint.setAntiAlias(true);
        if (charBgInfo.isbHasScale()){
            if (charBgInfo.getWhichScaleEnable()[LineChartView.LEFT_SCALE]){
                drawLeftScale(canvasTool,width,height);
            }
            if (charBgInfo.getWhichScaleEnable()[LineChartView.BOTTOM_SCALE]){
                drawBottomScale(canvasTool,width,height);
            }
            if (charBgInfo.getWhichScaleEnable()[LineChartView.RIGHT_SCALE]){
                drawRightScale(canvasTool,width,height);
            }
            if (charBgInfo.getWhichScaleEnable()[LineChartView.TOP_SCALE]){
                drawTopScale(canvasTool,width,height);
            }
        }
    }

    /**
     * 绘制左边坐标轴
     * @param canvasTool
     * @param width
     * @param height
     */
    public void drawLeftScale(CanvasTool canvasTool, int width, int height){
        float textSize = chartViewInfo.getTextSize();
        ScaleInfo scaleInfo = scaleInfos[LineChartView.LEFT_SCALE];
        Paint paint = scaleInfo.getPaint();
        float space = scaleInfo.getSpace();

        if (scaleInfo.isVisibility()){
            canvasTool.drawLine(space, scaleInfos[LineChartView.BOTTOM_SCALE].getSpace() - paint.getStrokeWidth() / 2,
                    space, height - scaleInfos[LineChartView.TOP_SCALE].getSpace() + paint.getStrokeWidth() / 2, paint);
        }
        paint.setTextAlign(Paint.Align.LEFT);
        canvasTool.drawText(scaleInfo.getScaleTitle(),0,height - textSize,paint);
    }

    /**
     * 绘制底部坐标轴
     * @param canvasTool
     * @param width
     * @param height
     */
    public void drawBottomScale(CanvasTool canvasTool, int width, int height){
        float textSize = chartViewInfo.getTextSize();
        ScaleInfo scaleInfo = scaleInfos[LineChartView.BOTTOM_SCALE];
        Paint paint = scaleInfo.getPaint();
        float space = scaleInfo.getSpace();
        paint.setTextAlign(Paint.Align.RIGHT);
        if (scaleInfo.isVisibility()){
            canvasTool.drawLine(scaleInfos[LineChartView.LEFT_SCALE].getSpace(), space, width - scaleInfos[LineChartView.RIGHT_SCALE].getSpace(), space, paint);
        }
        if (scaleInfos[LineChartView.RIGHT_SCALE].getSpace() >= textSize * 2){
            canvasTool.drawTextOnPath(scaleInfo.getScaleTitle(),width,height / 2,width,10, 0,textSize,paint);
        }
    }

    /**
     * 绘制右边坐标轴
     * @param canvasTool
     * @param width
     * @param height
     */
    public void drawRightScale(CanvasTool canvasTool, int width, int height){
        float textSize = chartViewInfo.getTextSize();
        ScaleInfo scaleInfo = scaleInfos[LineChartView.RIGHT_SCALE];
        Paint paint = scaleInfo.getPaint();
        float space = scaleInfo.getSpace();
        if (scaleInfo.isVisibility()){
            canvasTool.drawLine(width - space, scaleInfos[LineChartView.BOTTOM_SCALE].getSpace() - paint.getStrokeWidth() / 2,
                    width - space, height - scaleInfos[LineChartView.TOP_SCALE].getSpace() + paint.getStrokeWidth() / 2,paint);
        }
        scaleInfo.getTextPaint().setTextAlign(Paint.Align.RIGHT);
        if (scaleInfos[LineChartView.TOP_SCALE].getSpace() >= textSize * 2){
            canvasTool.drawText(scaleInfo.getScaleTitle(),width,height - textSize,scaleInfo.getTextPaint());
        }
    }

    /**
     * 绘制顶部坐标轴
     * @param canvasTool
     * @param width
     * @param height
     */
    public void drawTopScale(CanvasTool canvasTool, int width, int height){
        float textSize = chartViewInfo.getTextSize();
        ScaleInfo scaleInfo = scaleInfos[LineChartView.TOP_SCALE];
        Paint paint = scaleInfo.getPaint();
        float space = scaleInfo.getSpace();
        if (scaleInfos[TOP_SCALE].isVisibility()){
            canvasTool.drawLine(scaleInfos[LineChartView.LEFT_SCALE].getSpace(), height - space,width - scaleInfos[LineChartView.RIGHT_SCALE].getSpace(), height - space,paint);
        }
        paint.setTextAlign(Paint.Align.LEFT);
        if (scaleInfos[LineChartView.RIGHT_SCALE].getSpace() >= textSize * 2){
            canvasTool.drawTextOnPath(scaleInfo.getScaleTitle(),width,height - textSize,width,height / 2,5,textSize,paint);
        }
    }

    /**
     * 绘制底景
     * @param canvasTool
     * @param width
     * @param height
     */
    public void drawBackground(CanvasTool canvasTool, int width, int height){

    }

    /**
     * 绘制背景线函数
     * @param canvasTool
     * @param width
     * @param height
     */
    public void drawBgLine(CanvasTool canvasTool, int width, int height){
        if (charBgInfo.hasBgLine()){
            if (charBgInfo.isUseDefaultBgLines()){
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
                    pos = changeUserDataToChartViewData(pos,chartHeight,LEFT_SCALE);
                    pos += scaleInfos[BOTTOM_SCALE].getSpace() + scaleInfos[BOTTOM_SCALE].getScaleWidth();
                    if (pos >= scaleInfos[BOTTOM_SCALE].getSpace() + scaleInfos[BOTTOM_SCALE].getScaleWidth() && pos <= scaleInfos[BOTTOM_SCALE].getSpace() + scaleInfos[BOTTOM_SCALE].getScaleWidth() + chartHeight){
                        if (bgLineInfo.isbIsDotted()){
                            canvasTool.drawDottedLine(scaleInfos[LEFT_SCALE].getSpace() + scaleInfos[LEFT_SCALE].getScaleWidth() / 2,pos,scaleInfos[LEFT_SCALE].getSpace() + scaleInfos[LEFT_SCALE].getScaleWidth() / 2 + chartWidth, pos,20,paint);
                        }else{
                            canvasTool.drawLine(scaleInfos[LEFT_SCALE].getSpace() + scaleInfos[LEFT_SCALE].getScaleWidth() / 2,pos,scaleInfos[LEFT_SCALE].getSpace() + scaleInfos[LEFT_SCALE].getScaleWidth() / 2 + chartWidth, pos,paint);
                        }
                        String strTitle = bgLineInfo.getStrTitle();
                        if (!strTitle.equals("")){
                            switch (bgLineInfo.getTitlePos()){
                                case LEFT_SCALE:{
                                    if (scaleInfos[LEFT_SCALE].isHasData()){
                                        Paint leftPaint = bgLineInfo.getTextPaint();
                                        leftPaint.setTextSize(scaleInfos[LEFT_SCALE].getTextSize());
                                        leftPaint.setTextAlign(Paint.Align.LEFT);
                                        canvasTool.drawText(strTitle,0,pos - scaleInfos[LEFT_SCALE].getTextSize() / 2,leftPaint);
                                    }
                                }
                                break;
                                case RIGHT_SCALE:{
                                    if (scaleInfos[RIGHT_SCALE].isHasData()){
                                        Paint leftPaint = bgLineInfo.getTextPaint();
                                        leftPaint.setTextSize(scaleInfos[LEFT_SCALE].getTextSize());
                                        leftPaint.setTextAlign(Paint.Align.RIGHT);
                                        canvasTool.drawText(strTitle,backgroundWidth - scaleInfos[RIGHT_SCALE].getTextSize(),pos - scaleInfos[RIGHT_SCALE].getTextSize() / 2,leftPaint);
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
                    pos = changeUserDataToChartViewData(pos,chartWidth,BOTTOM_SCALE);
                    pos += scaleInfos[LEFT_SCALE].getSpace() + scaleInfos[LEFT_SCALE].getScaleWidth();
                    if (pos > scaleInfos[LEFT_SCALE].getSpace() + scaleInfos[LEFT_SCALE].getScaleWidth() && pos < scaleInfos[LEFT_SCALE].getSpace() + scaleInfos[LEFT_SCALE].getScaleWidth() + chartWidth){
                        if (bgLineInfo.isbIsDotted()){
                            canvasTool.drawDottedLine(pos, scaleInfos[BOTTOM_SCALE].getSpace() + scaleInfos[BOTTOM_SCALE].getScaleWidth() / 2, pos, scaleInfos[BOTTOM_SCALE].getSpace() + scaleInfos[BOTTOM_SCALE].getScaleWidth() / 2 + chartHeight,20,paint);
                        }else{
                            canvasTool.drawLine(pos, scaleInfos[BOTTOM_SCALE].getSpace() + scaleInfos[BOTTOM_SCALE].getScaleWidth() / 2, pos, scaleInfos[BOTTOM_SCALE].getSpace() + scaleInfos[BOTTOM_SCALE].getScaleWidth() / 2 + chartHeight,paint);
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
        float widthIndex = scaleInfos[LEFT_SCALE].getSpace();
        float heightIndex = scaleInfos[BOTTOM_SCALE].getSpace();
        widthIndex += 100;
        while (widthIndex < width - scaleInfos[RIGHT_SCALE].getSpace()){
            if (defaultBgLineInfo.isbIsDotted()){                                               //画虚线
                while (heightIndex < height - scaleInfos[TOP_SCALE].getSpace()){
                    if (heightIndex + 20 >= height - scaleInfos[TOP_SCALE].getSpace()){
                        canvasTool.drawLine(widthIndex, heightIndex, widthIndex, height - scaleInfos[TOP_SCALE].getSpace(), paint);
                    }else{
                        canvasTool.drawLine(widthIndex, heightIndex, widthIndex, heightIndex + 20, paint);
                    }
                    heightIndex += 40;
                }
                heightIndex = scaleInfos[BOTTOM_SCALE].getSpace();
            }else{                                                                              //画实线
                canvasTool.drawLine(widthIndex,scaleInfos[BOTTOM_SCALE].getSpace(),widthIndex,height - scaleInfos[TOP_SCALE].getSpace(),paint);
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
        float widthIndex = scaleInfos[LEFT_SCALE].getSpace();
        float heightIndex = scaleInfos[BOTTOM_SCALE].getSpace();
        heightIndex += 100;
        while (heightIndex < height - scaleInfos[TOP_SCALE].getSpace()){
            if (defaultBgLineInfo.isbIsDotted()){
                while (widthIndex < width - scaleInfos[RIGHT_SCALE].getSpace()){
                    if (widthIndex + 20 >= width - scaleInfos[RIGHT_SCALE].getSpace()){
                        canvasTool.drawLine(widthIndex,heightIndex,width - scaleInfos[RIGHT_SCALE].getSpace(), heightIndex,paint);
                    }else{
                        canvasTool.drawLine(widthIndex,heightIndex,widthIndex + 20, heightIndex,paint);
                    }
                    widthIndex += 40;
                }
                widthIndex = scaleInfos[LEFT_SCALE].getSpace();
            }else{
                canvasTool.drawLine(scaleInfos[LEFT_SCALE].getSpace(),heightIndex,width - scaleInfos[RIGHT_SCALE].getSpace(),heightIndex,paint);
            }
            heightIndex += 100;
        }
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

    /**
     * 画波形图
     */
    public void drawMainLine(CanvasTool canvasTool, int width, int height) {
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
                drawOneMainLine(canvasTool, mainLineInfo,chartWidth,chartHeight, index);
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
     * @param chartWidth
     * @param chartHeight
     * @param index
     */
    private void drawOneMainLine(CanvasTool canvasTool, MainLineInfo mainLineInfo,float chartWidth, float chartHeight, int index){
        synchronized (this){
            canvasTool.startDrawOnABitmap((int) chartWidth, (int) chartHeight);
            List<DataPoint> dataList = mainLineInfo.getDataList();

            int chartPointsSum = computePoints(mainLineInfo, index, chartWidth);

            float radius = mainLineInfo.getMainPointInfo().getRadius();
            //计算
            XcomputeResolutionAndOffset(mainLineInfo,dataList.size(),radius,chartWidth);

            float screenMove = mainLineInfo.getScreenPos();
            int start = computeStart(mainLineInfo,screenMove,radius,dataList.size());

            //绘图
            drawLineFunction(canvasTool, start, radius,dataList,screenMove,chartHeight,mainLineInfo);
            canvasTool.flushBitmap(scaleInfos[LEFT_SCALE].getSpace() + scaleInfos[LEFT_SCALE].getScaleWidth() / 2, chartHeight + scaleInfos[BOTTOM_SCALE].getSpace() + scaleInfos[BOTTOM_SCALE].getScaleWidth() / 2);

            if (scaleInfos[BOTTOM_SCALE].isHasData() && mainLineInfo.isVisibility()){
                canvasTool.startDrawOnABitmap(backgroundWidth, (int)scaleInfos[BOTTOM_SCALE].getSpace() + (int)(scaleInfos[BOTTOM_SCALE].getScaleWidth() / 2));
                drawBottomText(canvasTool, start, radius, dataList, screenMove, mainLineInfo);

                canvasTool.flushBitmap(0, scaleInfos[BOTTOM_SCALE].getSpace() + scaleInfos[BOTTOM_SCALE].getScaleWidth() / 2);
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
    public void drawBottomText(CanvasTool canvasTool, int start, float radius, List<DataPoint> dataList, float screenMove, MainLineInfo mainLineInfo ){
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
                    Paint bottomPaint = scaleInfos[BOTTOM_SCALE].getTextPaint();
                    bottomPaint.setTextAlign(Paint.Align.CENTER);
                    if (scaleInfos[BOTTOM_SCALE].isAutoText()){
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
                                if (scaleInfos[LEFT_SCALE].getSpace() + cx > scaleInfos[LEFT_SCALE].getSpace() &&
                                        scaleInfos[LEFT_SCALE].getSpace() + cx < backgroundWidth - scaleInfos[RIGHT_SCALE].getScaleWidth() / 2 - scaleInfos[RIGHT_SCALE].getSpace()){
                                    canvasTool.drawText(strX,scaleInfos[LEFT_SCALE].getSpace() + cx, 0, bottomPaint);
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
                                    if (scaleInfos[LEFT_SCALE].getSpace() + cx > scaleInfos[LEFT_SCALE].getSpace() &&
                                            scaleInfos[LEFT_SCALE].getSpace() + cx < backgroundWidth - scaleInfos[RIGHT_SCALE].getScaleWidth() / 2 - scaleInfos[RIGHT_SCALE].getSpace()){
                                        canvasTool.drawText(strX,scaleInfos[LEFT_SCALE].getSpace() + cx, 0, bottomPaint);
                                    }
                                }
                            }else{
                                if (scaleInfos[LEFT_SCALE].getSpace() + cx > scaleInfos[LEFT_SCALE].getSpace() &&
                                        scaleInfos[LEFT_SCALE].getSpace() + cx < backgroundWidth - scaleInfos[RIGHT_SCALE].getScaleWidth() / 2 - scaleInfos[RIGHT_SCALE].getSpace()){
                                    canvasTool.drawText(strX,scaleInfos[LEFT_SCALE].getSpace() + cx, 0, bottomPaint);
                                }
                            }
                        }else if (i == dataList.size() - 1){
                            float lastLen = bottomPaint.measureText(dataList.get(i - 1).getXData());
                            if (!dataList.get(i - 1).isShowXData()){
                                lastLen = 0;
                            }
                            float nowLen = bottomPaint.measureText(strX);
                            if (lastX + lastLen / 2 < pointX - nowLen / 2){
                                if (scaleInfos[LEFT_SCALE].getSpace() + cx > scaleInfos[LEFT_SCALE].getSpace() &&
                                        scaleInfos[LEFT_SCALE].getSpace() + cx < backgroundWidth - scaleInfos[RIGHT_SCALE].getScaleWidth() / 2 - scaleInfos[RIGHT_SCALE].getSpace()){
                                    canvasTool.drawText(strX,scaleInfos[LEFT_SCALE].getSpace() + cx, 0, bottomPaint);
                                }
                            }
                        }
                    }else{
                        if (scaleInfos[LEFT_SCALE].getSpace() + cx > scaleInfos[LEFT_SCALE].getSpace() &&
                                scaleInfos[LEFT_SCALE].getSpace() + cx < backgroundWidth - scaleInfos[RIGHT_SCALE].getScaleWidth() / 2 - scaleInfos[RIGHT_SCALE].getSpace()){
                            canvasTool.drawText(strX,scaleInfos[LEFT_SCALE].getSpace() + cx, 0, bottomPaint);
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
            downX -= scaleInfos[LEFT_SCALE].getSpace() + scaleInfos[LEFT_SCALE].getScaleWidth();
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
            middle -= scaleInfos[LEFT_SCALE].getSpace() + scaleInfos[LEFT_SCALE].getScaleWidth();
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
     * 计算起始位置
     * @param screenMove
     * @param radius
     * @param size
     * @return
     */
    private int computeStart(MainLineInfo mainLineInfo, float screenMove, float radius, int size){
        int start = (int)((screenMove - radius) / (mainLineInfo.getHorizontalResolution() + radius * 2));
        if (start < 0){
            start = 0;
        }
        if (start > size){
            start = size;
        }
        return start;
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
            float pointHeight = changeUserDataToChartViewData(dataList.get(i).getYData(), chartHeight,LEFT_SCALE);
            if (mainLineInfo.isHasLine() && i != 0){
                canvasTool.drawLine(cx - (mainLineInfo.getHorizontalResolution() + radius * 2), changeUserDataToChartViewData(dataList.get(i - 1).getYData(), chartHeight,LEFT_SCALE),cx,pointHeight,mainLineInfo.getPaint());
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
    public void drawDataDiv(CanvasTool canvasTool, int i, MainLineInfo mainLineInfo, List<DataPoint> dataList, float cx, float pointHeight){
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
            float oneLen = divPaint.measureText(String.valueOf(dataList.get(i).getYData()) + scaleInfos[LEFT_SCALE].getScaleTitle());
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
            canvasTool.drawText(String.valueOf(dataList.get(i).getYData()) + scaleInfos[LEFT_SCALE].getScaleTitle(),(rightX - leftX) / 2 + leftX,textOneHeight,divPaint);
            if (!dataList.get(i).getXData().equals("")){
                canvasTool.drawText(dataList.get(i).getXData(),(rightX - leftX) / 2 + leftX,textOneHeight - textSize * 2,divPaint);
            }
        }
    }
    /**
     * 将用户传进来的数据转换为像素数据
     * @param userData
     * @param length
     * @return
     */
    private float changeUserDataToChartViewData(float userData, float length, int which){
        float chartViewData = 0;
        float max = scaleInfos[which].getMaxValue();
        float min = scaleInfos[which].getMinVale();
        float div = (max - min) / length;
        chartViewData = (userData - min) / div;
        return chartViewData;
    }

    /**
     * 计算一个图表里应该有多少数据
     * @param index
     * @param width
     * @return
     */
    public int computePoints(MainLineInfo mainLineInfo, int index, float width){
        float radius = mainLineInfoList.get(index).getMainPointInfo().getRadius();
        int pointSum = (int)(width / (radius * 2 + mainLineInfo.getHorizontalResolution()));
        return ++pointSum;
    }
}
