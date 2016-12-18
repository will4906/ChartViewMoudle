package com.example.will.chartviewlib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.will.chartviewlib.Common.CanvasTool;
import com.example.will.chartviewlib.DrawFactory.BackgroundEngine;
import com.example.will.chartviewlib.DrawFactory.InformationMediator;
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

    private GestureDetector gestureDetector;

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

    private InformationMediator informationMediator = new InformationMediator();
    /**
     * 绘图引擎
     */
    protected DrawEngine drawEngine = new DrawEngine(informationMediator);

    protected BackgroundEngine backgroundEngine = new BackgroundEngine(informationMediator);

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
            int width = canvas.getWidth();
            int height = canvas.getHeight();
            if (width <= 0){
                width = 1;
            }
            if (height <= 0){
                height = 1;
            }
            backgroundBitmap = backgroundEngine.drawChartViewBackground(width,height);
            setbHasDrawTheBackground(true);
            touchEngine.setChangeBackground(false);
        }
        canvasTool.drawBitmap(backgroundBitmap,0,canvas.getHeight());
        //画图表的函数，以后再考虑是否要换位置放吧，暂时还不需要
        drawEngine.drawMainLine(canvasTool);
    }

    protected TouchEngine touchEngine = new TouchEngine();
    private int nowAction;
    private boolean isFling = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        nowAction = action;
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_MOVE:
                //一旦符合重绘条件就重绘
                if (touchEngine.answerTouch(event)) {
                    if (touchEngine.isChangeBackground()){
                        setbHasDrawTheBackground(false);
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_DOWN:
                touchEngine.setTouchMode(TouchParam.SINGLE_TOUCH);
                touchEngine.setDownX(event.getX());
                touchEngine.setDownY(event.getY());
                drawEngine.setDownPoint(-1);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getPointerCount() >= 2) {
                    float middle;
                    if (event.getX(0) > event.getX(1)) {
                        middle = (event.getX(0) - event.getX(1)) / 2 + event.getX(1);
                    } else {
                        middle = (event.getX(1) - event.getX(0)) / 2 + event.getX(0);
                    }
                    touchEngine.setTwoPointsMiddleX(middle);
                    if (event.getY(0) > event.getY(1)){
                        middle = (event.getY(0) - event.getY(1)) / 2 + event.getY(1);
                    }else{
                        middle = (event.getY(1) - event.getY(0)) / 2 + event.getY(0);
                    }
                    touchEngine.setTwoPointsMiddleY(middle);
                    touchEngine.setTouchMode(TouchParam.DOUBLE_TOUCH);
                    touchEngine.setDoubleTapX(event.getX(0), event.getX(1));
                    touchEngine.setDoubleTapY(event.getY(0), event.getY(1));
                    touchEngine.setDoubleTouchDistance((float) Math.sqrt(Math.abs(Math.pow(event.getX(0) - event.getX(1),2) + Math.pow(event.getY(0) - event.getY(1),2))));
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                break;
            default:
                break;
        }
        gestureDetector.onTouchEvent(event);
        //此处需改为true，否则双指的条件无法执行
        return true;
    }

    private byte changeTouchModeIndex = 0;

    public byte getChangeTouchModeIndex() {
        return changeTouchModeIndex;
    }

    public void setChangeTouchModeIndex(byte changeTouchModeIndex) {
        this.changeTouchModeIndex = changeTouchModeIndex;
    }

    private Timer timer = new Timer();
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (nowAction == MotionEvent.ACTION_UP || nowAction == MotionEvent.ACTION_CANCEL){
                if (changeTouchModeIndex >= 4){             //临时测试原为4，4秒无操作则返回最末
                    resetLineChart();
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
        gestureDetector = new GestureDetector(context,gestureListener);
        gestureDetector.setOnDoubleTapListener(doubleTapListener);
    }

    //单指事件
    private GestureDetector.OnGestureListener gestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent motionEvent) {

            return true;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            float direction = motionEvent.getX() - motionEvent1.getX();
//            Log.v("direction",String.valueOf(direction));
            return true;
        }
    };

    //双击事件
    private GestureDetector.OnDoubleTapListener doubleTapListener = new GestureDetector.OnDoubleTapListener() {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent motionEvent) {
            //双击则返回最末
            resetLineChart();
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return false;
        }
    };

    /**
     * 将波形图重新置于最末的函数
     */
    public void resetLineChart(){
        nowAction = MotionEvent.ACTION_UP;
        touchEngine.setTouchOffsetX(0);
        touchEngine.setTouchMode(TouchParam.NO_TOUCH);
    }
}
