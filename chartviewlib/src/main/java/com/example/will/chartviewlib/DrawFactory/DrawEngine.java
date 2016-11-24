package com.example.will.chartviewlib.DrawFactory;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.will.canvaslib.CanvasTool;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.BgLineInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.ChartBgInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.DefaultBgLineInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.ScaleInfo;
import com.example.will.chartviewlib.ChartInfo.ChartViewInfo;
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
        canvasTool.drawLine(space, scaleInfos[LineChartView.BOTTOM_SCALE].getSpace(), space, height - scaleInfos[LineChartView.TOP_SCALE].getSpace(), paint);
        paint.setTextAlign(Paint.Align.LEFT);
        if (scaleInfos[LineChartView.TOP_SCALE].getSpace() >= textSize * 2){
            canvasTool.drawText(scaleInfo.getScaleTitle(),0,height - textSize,paint);
        }
        if (scaleInfo.isHasData()){
            String strMax = String.valueOf(scaleInfo.getMaxValue());
            String strMin = String.valueOf(scaleInfo.getMinVale());
            paint.setTextAlign(Paint.Align.RIGHT);
            canvasTool.drawText(strMax,space - 1,height - scaleInfos[TOP_SCALE].getSpace() - textSize,paint);
            canvasTool.drawText(strMin,space - 1,scaleInfos[BOTTOM_SCALE].getSpace(),paint);
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
        canvasTool.drawLine(width - space, scaleInfos[LineChartView.BOTTOM_SCALE].getSpace(),width - space, height - scaleInfos[LineChartView.TOP_SCALE].getSpace(),paint);
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

//    private BgLineInfo tmpBgLineInfo = new BgLineInfo();

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

    /**
     * 画波形图
     */
    public void drawMainLine() {

    }
}
