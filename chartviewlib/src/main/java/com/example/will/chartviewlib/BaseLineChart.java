package com.example.will.chartviewlib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.will.chartviewlib.Common.CanvasTool;
import com.example.will.chartviewlib.TouchFactory.TouchEngine;
import com.example.will.chartviewlib.TouchFactory.TouchParam;
import com.example.will.chartviewlib.DrawFactory.DrawEngine;
import com.example.will.chartviewlib.Common.ViewInsideTool;

import java.util.Timer;
import java.util.TimerTask;

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
        initBaseLineChart(context);
    }

    public BaseLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBaseLineChart(context);
    }

    public BaseLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBaseLineChart(context);
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

    protected TouchEngine touchEngine = new TouchEngine();
    private int nowAction;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        nowAction = action;
        switch (action & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_MOVE:
                //一旦符合重绘条件就重绘
                if (touchEngine.answerTouch(event)){
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_DOWN:
                touchEngine.setTouchMode(TouchParam.SINGLE_TOUCH);
                touchEngine.setDownX(event.getX());
                touchEngine.setDownY(event.getY());
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                touchEngine.setTouchMode(TouchParam.DOUBLE_TOUCH);
                touchEngine.setDoubleTapX(event.getX(0),event.getX(1));
                touchEngine.setDoubleTapY(event.getY(0),event.getY(1));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        //此处需改为true，否则双指的条件无法执行
        return true;
    }

    private int changeTouchModeIndex = 0;

    public int getChangeTouchModeIndex() {
        return changeTouchModeIndex;
    }

    public void setChangeTouchModeIndex(int changeTouchModeIndex) {
        this.changeTouchModeIndex = changeTouchModeIndex;
    }

    private Timer timer = new Timer();
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (nowAction == MotionEvent.ACTION_UP || nowAction == MotionEvent.ACTION_CANCEL){
                if (changeTouchModeIndex >= 5){
                    touchEngine.setTouchOffsetX(0);
                    touchEngine.setTouchMode(TouchParam.NO_TOUCH);
                }
                changeTouchModeIndex++;
            }else{
                changeTouchModeIndex = 0;
            }
        }
    };
    /**
     * 初始化信息
     * @param context
     */
    private void initBaseLineChart(Context context){
        touchEngine.setDrawEngine(drawEngine);
        timer.schedule(timerTask,1,1000);
    }

}
