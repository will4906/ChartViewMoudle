package com.example.will.chartviewlib.ChartInfo.MainLayer;

import android.graphics.Color;

import com.example.will.chartviewlib.ChartInfo.BaseInfo.BaseLineInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * @author will4906.
 * @Time 2016/11/24.
 */

public class MainLineInfo extends BaseLineInfo{

    public MainLineInfo(){
        setLineWidth(10);
        setLineColor(Color.GREEN);
    }
    /**
     * 是否显示点，若为否，该点位置只用线连接
     */
    private boolean hasPoint = true;

    public boolean isHasPoint() {
        return hasPoint;
    }

    public void setHasPoint(boolean hasPoint) {
        this.hasPoint = hasPoint;
    }

    /**
     * 是否显示线，若为否则只显示点
     */
    private boolean hasLine = true;
    public boolean isHasLine() {
        return hasLine;
    }

    public void setHasLine(boolean hasLine) {
        this.hasLine = hasLine;
    }

    /**
     * 考虑到代码复杂性和实用性问题，决定只使用同一的点样式，若有更改另行改变
     */
    private MainPointInfo mainPointInfo = new MainPointInfo();

    public MainPointInfo getMainPointInfo() {
        return mainPointInfo;
    }

    public void setMainPointInfo(MainPointInfo mainPointInfo) {
        this.mainPointInfo = mainPointInfo;
    }

    private List<DataPoint> dataList = new ArrayList<>();

    public List<DataPoint> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataPoint> dataList) {
        this.dataList = dataList;
    }

    /**
     * 添加数据
     * @param XData
     * @param YData
     */
    public void addData(String XData, float YData){
        DataPoint dataPoint = new DataPoint();
        dataPoint.setXData(XData);
        dataPoint.setYData(YData);
        this.dataList.add(dataPoint);
    }

    /**
     * 添加数据
     * @param dataPoint
     */
    public void addPoint(DataPoint dataPoint){
        this.dataList.add(dataPoint);
    }

    public void removeData(int index){
        this.dataList.remove(index);
    }

    public void removeAllData(){
        this.dataList.clear();
    }

    /**
     * 图的显示起始标志
     */
    public int startIndex = 0;

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * 用户可见屏幕理论位置
     */
    private float screenPos = 0;

    public float getScreenPos() {
        return screenPos;
    }

    public void setScreenPos(float screenPos) {
        this.screenPos = screenPos;
    }

    /**
     * 横向分辨率
     */
    public float horizontalResolution = 0;

    public float getHorizontalResolution() {
        return horizontalResolution;
    }

    public void setHorizontalResolution(float horizontalResolution) {
        this.horizontalResolution = horizontalResolution;
    }

}
