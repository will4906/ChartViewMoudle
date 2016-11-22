package com.example.will.chartviewlib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.example.will.canvaslib.CanvasTool;
import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.ScaleInfo;
import com.example.will.chartviewlib.DrawFactory.DrawEngine;
import com.example.will.chartviewlib.DrawFactory.OnDrawBackgroundListener;
import com.example.will.viewcontrollib.ViewInsideTool;

/**
 * Created by will on 2016/11/21.
 */

public class LineChartView extends BaseLineChart {

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

    /**
     * 是否绘制过背景
     */
    private boolean bHasDrawTheBackground = false;

    public boolean hasDrawTheBackground(){
        return bHasDrawTheBackground;
    }

    private void setbHasDrawTheBackground(boolean hasDrawTheBackground){
        this.bHasDrawTheBackground = hasDrawTheBackground;
    }

    /**
     * 背景类全局位图，因为背景若无改变只需绘制一次便可重复使用，所以定义为类全局
     */
    private Bitmap backgroundBitmap;
    /**
     * 绘图引擎
     */
    private DrawEngine drawEngine = new DrawEngine();

    /**
     * view工具类，放置一些与view处理相关的函数
     */
    private ViewInsideTool viewInsideTool = ViewInsideTool.getInstance();

    public LineChartView(Context context) {
        super(context);
        initLineChartView();
    }

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLineChartView();
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLineChartView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = viewInsideTool.getUserSize(500,widthMeasureSpec);
        int height = viewInsideTool.getUserSize(300,heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        CanvasTool canvasTool = new CanvasTool(canvas);
        if (hasDrawTheBackground() == false){
            backgroundBitmap = drawEngine.drawChartViewBackground(canvas.getWidth(),canvas.getHeight());
            setbHasDrawTheBackground(true);
        }
        canvasTool.drawBitmap(backgroundBitmap,0,canvas.getHeight());
    }

    /**
     * 初始化图表
     */
    private void initLineChartView(){
        drawEngine.setOnDrawBackgroundListener(onDrawBackgroundListener);
        drawEngine.setScaleInfos(new ScaleInfo[]{ScaleInfoEnum.LEFT.getScaleInfo(),ScaleInfoEnum.BOTTOM.getScaleInfo(),ScaleInfoEnum.RIGHT.getScaleInfo(),ScaleInfoEnum.TOP.getScaleInfo()});
    }

}
