package com.example.will.chartviewlib.DrawFactory;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.will.canvaslib.CanvasTool;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.ChartBgInfo;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.ScaleInfo;

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

    private ChartBgInfo charBgInfo;
    private ScaleInfo[] scaleInfos;

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

    public Bitmap drawChartViewBackground(int width, int height){
        CanvasTool canvasTool = new CanvasTool();
        CanvasTool customCanvasTool = new CanvasTool();
        Bitmap bitmap;
        canvasTool.startDrawOnABitmap(width,height);
        drawBackground(canvasTool,width,height);
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

    public void drawScale(CanvasTool canvasTool,int width, int height){
        for (ScaleInfo scaleInfo:scaleInfos) {

        }
    }

    public void drawBackground(CanvasTool canvasTool, int width, int height){
        canvasTool.getCanvas().drawColor(Color.RED);
    }
}
