package com.example.will.chartviewlib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.will.canvaslib.CanvasTool;
import com.example.will.chartviewlib.ChartInfo.TouchListener.TouchEngine;
import com.example.will.chartviewlib.ChartInfo.TouchListener.TouchMode;
import com.example.will.chartviewlib.DrawFactory.DrawEngine;
import com.example.will.viewcontrollib.ViewInsideTool;

import java.util.List;

/**
 * 线性表的父类，画图的处理
 * @author will4906.
 * @Time 2016/11/22.
 */

public class BaseLineChart extends View  {

    /**
     * 是否绘制过背景
     */
    protected boolean bHasDrawTheBackground = false;

    public boolean hasDrawTheBackground(){
        return bHasDrawTheBackground;
    }

    protected void setbHasDrawTheBackground(boolean hasDrawTheBackground){
        this.bHasDrawTheBackground = hasDrawTheBackground;
    }

    /**
     * 背景类全局位图，因为背景若无改变只需绘制一次便可重复使用，所以定义为类全局
     */
    protected Bitmap backgroundBitmap;
    /**
     * 绘图引擎
     */
    protected DrawEngine drawEngine = new DrawEngine();

    /**
     * view工具类，放置一些与view处理相关的函数
     */
    protected ViewInsideTool viewInsideTool = ViewInsideTool.getInstance();

    public BaseLineChart(Context context) {
        super(context);
//        initBaseLineChart(context);
    }

    public BaseLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
//        initBaseLineChart(context);
    }

    public BaseLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        initBaseLineChart(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = viewInsideTool.getUserSize(500,widthMeasureSpec);
        int height = viewInsideTool.getUserSize(500,heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        CanvasTool canvasTool = new CanvasTool(canvas);
        //绘制背景位图并显示在view上
        if (hasDrawTheBackground() == false){
            backgroundBitmap = drawEngine.drawChartViewBackground(canvas.getWidth(),canvas.getHeight());
            setbHasDrawTheBackground(true);
        }
        canvasTool.drawBitmap(backgroundBitmap,0,canvas.getHeight());
        drawEngine.drawMainLine(canvasTool, canvas.getWidth(),canvas.getHeight());
    }

    private TouchEngine touchEngine = new TouchEngine();
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_MOVE:
//                drawEngine.getChartViewInfo()touchEngine.answerSingleTouch(event);
                break;
            case MotionEvent.ACTION_DOWN:
                touchEngine.setTouchMode(TouchMode.SINGLE_TOUCH);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                touchEngine.setTouchMode(TouchMode.DOUBLE_TOUCH);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                touchEngine.setTouchMode(TouchMode.NO_TOUCH);
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    //    /**
//     * 初始化信息
//     * @param context
//     */
//    private void initBaseLineChart(Context context){
//        TextView textView = new TextView(context);
//        setTextSize(textView.getTextSize());
//    }
}
