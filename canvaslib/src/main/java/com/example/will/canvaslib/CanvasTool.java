package com.example.will.canvaslib;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * @author will4906.
 * @Time 2016/11/23.
 */

public class CanvasTool {
    private Canvas canvas;
    private Bitmap cacheBitmap;
    private Canvas cacheCanvas;
    private Canvas oldCanvas;

    /**
     * 一般为使用双缓冲机制时使用
     */
    public CanvasTool(){}

    public CanvasTool(Canvas canvas) {
        this.canvas = canvas;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public Bitmap getCacheBitmap() {
        return cacheBitmap;
    }

    public void setCacheBitmap(Bitmap cacheBitmap) {
        this.cacheBitmap = cacheBitmap;
    }

    /**
     * 画线
     * @param startX
     * @param startY
     * @param stopX
     * @param stopY
     * @param paint
     */
    public void drawLine(float startX, float startY, float stopX, float stopY, Paint paint){
        int userHeight = canvas.getHeight();
        canvas.drawLine(startX,userHeight - startY,stopX,userHeight - stopY,paint);
    }

    /**
     * 画点
     * @param x
     * @param y
     * @param paint
     */
    public void drawPoint(float x, float y, Paint paint){
        int userHeight = canvas.getHeight();
        canvas.drawPoint(x,userHeight - y,paint);
    }

    /**
     * 画圆
     * @param cx
     * @param cy
     * @param radius
     * @param paint
     */
    public void drawCircle(float cx, float cy, float radius, Paint paint){
        int userHeight = canvas.getHeight();
        canvas.drawCircle(cx,userHeight - cy,radius,paint);
    }

    /**
     * 画椭圆
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param paint
     */
    public void drawOval(float left, float top, float right, float bottom, Paint paint){
        int userHeight = canvas.getHeight();
        canvas.drawOval(left,userHeight - top,right,userHeight - bottom,paint);
    }

    /**
     * 画三角形
     * @param topX
     * @param topY
     * @param leftBottomX
     * @param leftBottomY
     * @param rightBottomX
     * @param rightBottomY
     * @param paint
     * @param bFilling 是否填充
     */
    public void drawTriangle(float topX, float topY, float leftBottomX, float leftBottomY, float rightBottomX, float rightBottomY, boolean bFilling, Paint paint){
        int height = canvas.getHeight();
        topY = height - topY;
        leftBottomY = height - leftBottomY;
        rightBottomY = height - rightBottomY;
        if (bFilling){//填充
            Path path = new Path();
            path.moveTo(topX, topY);
            path.lineTo(leftBottomX, leftBottomY);
            path.lineTo(rightBottomX, rightBottomY);
            path.close();
            canvas.drawPath(path, paint);
        }else{//不填充
            drawLine(leftBottomX,leftBottomY,topX,topY,paint);
            drawLine(topX,topY,rightBottomX,rightBottomY,paint);
            drawLine(rightBottomX,rightBottomY,leftBottomX,leftBottomY,paint);
        }
    }

    /**
     * 画菱形
     * @param left 左边点横坐标
     * @param top 顶点纵坐标
     * @param right 右边点横坐标
     * @param bottom 低点纵坐标
     * @param bFilling 是否填充
     * @param paint
     */
    public void drawRhombus(float left, float top, float right, float bottom, boolean bFilling, Paint paint){
        int height = canvas.getHeight();
        top = height - top;
        bottom = height - bottom;
        if (bFilling){
            Path path = new Path();
            path.moveTo(left,height - (bottom - top) / 2);
            path.lineTo((right - left) / 2, top);
            path.lineTo(right, height - (bottom - top) / 2);
            path.lineTo((right - left) / 2, bottom);
            path.lineTo(left,height - (bottom - top) / 2);
            path.close();
            canvas.drawPath(path,paint);
        }else{
            canvas.drawLine(left,height - (bottom - top) / 2,(right - left) / 2,top,paint);
            canvas.drawLine((right - left) / 2, top, right, height - (bottom - top) / 2,paint);
            canvas.drawLine(right, height - (bottom - top) / 2, (right - left) / 2, bottom, paint);
            canvas.drawLine((right - left) / 2, bottom, left, height - (bottom - top) / 2, paint);
        }
    }

    /**
     * 填充颜色
     * @param color
     */
    public void drawColor(int color){
        canvas.drawColor(color);
    }

    /**
     * 绘制文字
     * @param strText
     * @param x
     * @param y
     * @param paint
     */
    public void drawText(String strText, float x, float y, Paint paint){
        int height = canvas.getHeight();
        canvas.drawText(strText,x,height - y,paint);
    }

    /**
     * 按照一定的路径绘制文字
     * @param strText
     * @param startX
     * @param startY
     * @param stopX
     * @param stopY
     * @param hOffset 文字距离路径开头的距离
     * @param vOffset 文字离线的偏移，<0 在上方， >0 在下方
     * @param paint
     */
    public void drawTextOnPath(String strText, float startX, float startY, float stopX, float stopY, float hOffset, float vOffset, Paint paint){
        int height = canvas.getHeight();
        Path path = new Path();
        path.moveTo(startX,height - startY);
        path.lineTo(stopX,height - stopY);
        canvas.drawTextOnPath(strText,path,hOffset,vOffset,paint);
    }
    /**
     * 将位图显示到画布上
     * @param bitmap
     * @param left
     * @param top
     */
    public void drawBitmap(Bitmap bitmap, float left, float top){
        //根据需要，不用传入新的paint免得麻烦，若需要可以添加新的一个函数传入
        Paint paint = new Paint();
        int height = canvas.getHeight();
        canvas.drawBitmap(bitmap,left,height - top,paint);
    }

    /**
     * 开启双缓冲机制模式
     */
    public void startDrawOnABitmap(){
        cacheCanvas = new Canvas();
        if (canvas != null){
            cacheBitmap = Bitmap.createBitmap(canvas.getWidth(),canvas.getHeight(), Bitmap.Config.ARGB_8888);//透明bitmap
        }
        cacheCanvas.setBitmap(cacheBitmap);
        oldCanvas = canvas;
        canvas = cacheCanvas;
    }

    /**
     * 以一定的大小开始双缓冲机制
     * @param width
     * @param height
     */
    public void startDrawOnABitmap(int width, int height){
        cacheCanvas = new Canvas();
        cacheBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);//透明bitmap
        cacheCanvas.setBitmap(cacheBitmap);
        oldCanvas = canvas;
        canvas = cacheCanvas;
    }

    /**
     * 将画好的bitmap刷新到画布上
     */
    public void flushBitmap(){
        if (oldCanvas != null){
            canvas = oldCanvas;
            drawBitmap(cacheBitmap,0,canvas.getHeight());
            canvas = cacheCanvas;
        }
        clearBufferCanvas();
    }

    public void flushBitmap(float x, float y){
        if (oldCanvas != null){
            canvas = oldCanvas;
            drawBitmap(cacheBitmap,x,y);
            canvas = cacheCanvas;
        }
        clearBufferCanvas();
    }
    /**
     * 清空缓存
     */
    public void clearBufferCanvas(){
        canvas = oldCanvas;
        cacheCanvas = null;
        oldCanvas = null;
        cacheBitmap.recycle();
    }
}
