package com.example.will.chartviewlib.DrawFactory;

import android.graphics.Bitmap;
import android.graphics.Paint;

import com.example.will.chartviewlib.Common.CanvasTool;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.BgLineInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.ChartBgInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.DefaultBgLineInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.ScaleInfo;
import com.example.will.chartviewlib.ChartInfo.ChartViewInfo;
import com.example.will.chartviewlib.ChartInfo.MainLayer.MainLineInfo;
import com.example.will.chartviewlib.TouchFactory.TouchParam;
import com.example.will.chartviewlib.LineChartView;

import static com.example.will.chartviewlib.LineChartView.TOP_SCALE;
import static com.example.will.chartviewlib.LineChartView.BOTTOM_SCALE;
import static com.example.will.chartviewlib.LineChartView.RIGHT_SCALE;
import static com.example.will.chartviewlib.LineChartView.LEFT_SCALE;

import java.util.List;

/**
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
        canvasTool.drawLine(space, scaleInfos[LineChartView.BOTTOM_SCALE].getSpace() - paint.getStrokeWidth() / 2,
                space, height - scaleInfos[LineChartView.TOP_SCALE].getSpace() + paint.getStrokeWidth() / 2, paint);
        paint.setTextAlign(Paint.Align.LEFT);
        if (scaleInfos[LineChartView.TOP_SCALE].getSpace() >= textSize * 2){
            canvasTool.drawText(scaleInfo.getScaleTitle(),0,height - textSize,paint);
        }
        if (scaleInfo.isHasData()){
            String strMax = String.valueOf(scaleInfo.getMaxValue());
            String strMin = String.valueOf(scaleInfo.getMinVale());
            paint.setTextAlign(Paint.Align.RIGHT);
            canvasTool.drawText(strMax,space - scaleInfo.getLineWidth() / 2,height - scaleInfos[TOP_SCALE].getSpace() - textSize,paint);
            canvasTool.drawText(strMin,space - scaleInfo.getLineWidth() / 2,scaleInfos[BOTTOM_SCALE].getSpace(),paint);
        }
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
        canvasTool.drawLine(scaleInfos[LineChartView.LEFT_SCALE].getSpace(), space, width - scaleInfos[LineChartView.RIGHT_SCALE].getSpace(), space, paint);
        if (scaleInfos[LineChartView.RIGHT_SCALE].getSpace() >= textSize * 2){
            canvasTool.drawTextOnPath(scaleInfo.getScaleTitle(),width,height / 2,width,10, 0,textSize,paint);
        }
        if (scaleInfo.isHasData()){
            String strMax = String.valueOf(scaleInfo.getMaxValue());
            String strMin = String.valueOf(scaleInfo.getMinVale());
            canvasTool.drawText(strMax,width - scaleInfos[RIGHT_SCALE].getSpace(),space - textSize,paint);
            paint.setTextAlign(Paint.Align.LEFT);
            canvasTool.drawText(strMin,scaleInfos[LEFT_SCALE].getSpace(),space - textSize,paint);
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
        canvasTool.drawLine(width - space, scaleInfos[LineChartView.BOTTOM_SCALE].getSpace() - paint.getStrokeWidth() / 2,
                width - space, height - scaleInfos[LineChartView.TOP_SCALE].getSpace() + paint.getStrokeWidth() / 2,paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        if (scaleInfos[LineChartView.TOP_SCALE].getSpace() >= textSize * 2){
            canvasTool.drawText(scaleInfo.getScaleTitle(),width,height - textSize,paint);
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
        canvasTool.drawLine(scaleInfos[LineChartView.LEFT_SCALE].getSpace(), height - space,width - scaleInfos[LineChartView.RIGHT_SCALE].getSpace(), height - space,paint);
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
     * 画波形图
     */
    public void drawMainLine(CanvasTool canvasTool, int width, int height) {
        //表的理论宽度
        float chartWidth = width - scaleInfos[LEFT_SCALE].getSpace() - scaleInfos[RIGHT_SCALE].getSpace() - scaleInfos[LEFT_SCALE].getScaleWidth() / 2 - scaleInfos[RIGHT_SCALE].getScaleWidth() / 2;
        //理论高度
        float chartHeight = height - scaleInfos[TOP_SCALE].getSpace() - scaleInfos[BOTTOM_SCALE].getSpace() - scaleInfos[TOP_SCALE].getScaleWidth() / 2 - scaleInfos[BOTTOM_SCALE].getScaleWidth() / 2;
        int index = 0;
        for (MainLineInfo mainLineInfo : mainLineInfoList) {
            drawOneMainLine(canvasTool, mainLineInfo,chartWidth,chartHeight, index);
            index++;
        }
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
            List<Float> dataList = mainLineInfo.getDataList();

            int chartPointsSum = computePoints(index, chartWidth);

            synchronized (this) {
                processOffsetAndStartIndex(dataList.size(),chartPointsSum,mainLineInfo);
            }
            drawLineFunction(canvasTool,mainLineInfo,chartPointsSum,dataList,chartWidth,chartHeight);
            canvasTool.flushBitmap(scaleInfos[LEFT_SCALE].getSpace() + scaleInfos[LEFT_SCALE].getScaleWidth() / 2, chartHeight + scaleInfos[BOTTOM_SCALE].getSpace() + scaleInfos[BOTTOM_SCALE].getScaleWidth() / 2);
        }
    }

    private void drawLineFunction(CanvasTool canvasTool, MainLineInfo mainLineInfo, int chartPointsSum, List<Float> dataList, float chartWidth, float chartHeight){
        float oldcX = -100;
        float oldcY = -100;

        float radius = mainLineInfo.getMainPointInfo().getRadius();
        float Xoffset = mainLineInfo.getOffsetX();
        int startIndex = mainLineInfo.getStartIndex();
        int i;

        //此处还有bug，明天再修，主要是偏移造成的小bug
        int left = 0;
        int right = 0;
        if (touchParam.getTouchOffsetX() < 0){
            left = (int)(Math.abs(touchParam.getTouchOffsetX()) / (radius + chartViewInfo.getHorizontalReslution()));
        }else{
            right = (int)(Math.abs(touchParam.getTouchOffsetX()) / (radius + chartViewInfo.getHorizontalReslution()));
        }
        int start = startIndex - left;
        int end = chartPointsSum + startIndex + right;
        if (start < 0){
            start = 0;
        }
        if (end > dataList.size()){
            end = dataList.size();
        }
        for (i = start; i < end; i++) {
            if (i < dataList.size()) {
                if (mainLineInfo.isHasPoint()) {
                    float pointHeight = changeUserDataToChartViewData(dataList.get(i), chartWidth, chartHeight);
                    float cx = radius + i * (radius * 2 + chartViewInfo.getHorizontalReslution()) - Xoffset;

                    if (dataList.get(i) >= scaleInfos[LEFT_SCALE].getMinVale() && dataList.get(i) <= scaleInfos[LEFT_SCALE].getMaxValue()) {
                        canvasTool.drawCircle(cx, pointHeight, radius, mainLineInfo.getMainPointInfo().getPaint());
                    }
                    if (i != start) {
                        if (mainLineInfo.isHasLine()) {
                            canvasTool.drawLine(oldcX, oldcY, cx, pointHeight, mainLineInfo.getPaint());
                        }
                    }
                    oldcX = cx;
                    oldcY = pointHeight;
                }
            }
        }
        if (i < dataList.size() && oldcX != -100){
            float cx = radius + i * (radius * 2 + chartViewInfo.getHorizontalReslution()) - Xoffset;
            float pointHeight = changeUserDataToChartViewData(dataList.get(i), chartWidth, chartHeight);
            canvasTool.drawLine(oldcX, oldcY, cx, pointHeight, mainLineInfo.getPaint());
        }
    }
    /**
     * 处理偏移和起始点
     * @param size
     * @param chartPointsSum
     * @param mainLineInfo
     */
    private void processOffsetAndStartIndex(int size, int chartPointsSum, MainLineInfo mainLineInfo){
        float Xoffset;
        int startIndex = mainLineInfo.getStartIndex();
        float radius = mainLineInfo.getMainPointInfo().getRadius();
        if (touchParam.getTouchMode() == TouchParam.NO_TOUCH) {
            if (size - chartPointsSum > 0) {
                Xoffset = (size - chartPointsSum) * (chartViewInfo.getHorizontalReslution() + radius * 2);
                startIndex = size - chartPointsSum;
            } else {
                Xoffset = 0;
                startIndex = 0;
            }
            mainLineInfo.setStartIndex(startIndex);
            mainLineInfo.setOffsetX(Xoffset);
        }else{
            if (size - chartPointsSum > 0) {
                Xoffset = startIndex * (chartViewInfo.getHorizontalReslution() + radius * 2);
            } else {
                Xoffset = 0;
            }
            mainLineInfo.setOffsetX(Xoffset);
        }
        mainLineInfo.setOffsetX(mainLineInfo.getOffsetX() + touchParam.getTouchOffsetX());
    }
    /**
     * 将用户传进来的数据转换为像素数据
     * @param userData
     * @param width
     * @param height
     * @return
     */
    private float changeUserDataToChartViewData(float userData, float width, float height){
        float chartViewData = 0;
        float max = Float.valueOf(scaleInfos[LEFT_SCALE].getMaxValue());
        float min = Float.valueOf(scaleInfos[LEFT_SCALE].getMinVale());
        float div = (max - min) / height;
        chartViewData = (userData - min) / div;
        return chartViewData;
    }

    /**
     * 计算一个图表里应该有多少数据
     * @param index
     * @param width
     * @return
     */
    public int computePoints(int index, float width){
        float radius = mainLineInfoList.get(index).getMainPointInfo().getRadius();
        int pointSum = (int)(width / (radius * 2 + chartViewInfo.getHorizontalReslution()));
        return ++pointSum;
    }
}
