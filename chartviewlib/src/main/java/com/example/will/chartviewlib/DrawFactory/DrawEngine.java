package com.example.will.chartviewlib.DrawFactory;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.will.canvaslib.CanvasTool;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.BgLineInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.ChartBgInfo;
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

    private BgLineInfo tmpBgLineInfo = new BgLineInfo();

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
        float widthIndex = scaleInfos[LEFT_SCALE].getSpace();
        float heightIndex = scaleInfos[BOTTOM_SCALE].getSpace();
        widthIndex += 100;
        Paint paint = tmpBgLineInfo.getPaint();
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(2);
        while (widthIndex < width - scaleInfos[RIGHT_SCALE].getSpace()){
            while (heightIndex < height - scaleInfos[TOP_SCALE].getSpace()){
                if (heightIndex + 20 >= height - scaleInfos[TOP_SCALE].getSpace()){
                    canvasTool.drawLine(widthIndex, heightIndex, widthIndex, height - scaleInfos[TOP_SCALE].getSpace(), paint);
                }else{
                    canvasTool.drawLine(widthIndex, heightIndex, widthIndex, heightIndex + 20, paint);
                }
                heightIndex += 40;
            }
            heightIndex = scaleInfos[BOTTOM_SCALE].getSpace();
            widthIndex += 100;
        }
        widthIndex = scaleInfos[LEFT_SCALE].getSpace();
        heightIndex = scaleInfos[BOTTOM_SCALE].getSpace();
        heightIndex += 100;
        while (heightIndex < height - scaleInfos[TOP_SCALE].getSpace()){
            while (widthIndex < width - scaleInfos[RIGHT_SCALE].getSpace()){
                if (widthIndex + 20 >= width - scaleInfos[RIGHT_SCALE].getSpace()){
                    canvasTool.drawLine(widthIndex,heightIndex,width - scaleInfos[RIGHT_SCALE].getSpace(), heightIndex,paint);
                }else{
                    canvasTool.drawLine(widthIndex,heightIndex,widthIndex + 20, heightIndex,paint);
                }
                widthIndex += 40;
            }
            widthIndex = scaleInfos[LEFT_SCALE].getSpace();
            heightIndex += 100;
        }
    }
}
