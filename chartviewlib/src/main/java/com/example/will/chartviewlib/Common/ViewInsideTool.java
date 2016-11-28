package com.example.will.chartviewlib.Common;

import android.view.View;

/**
 * Created by will on 2016/11/21.
 */
public class ViewInsideTool {
    private static class ViewInsideToolHolder {
        private static final ViewInsideTool INSTANCE = new ViewInsideTool();
    }
    private ViewInsideTool(){}
    public static final ViewInsideTool getInstance() {
        return ViewInsideToolHolder.INSTANCE;
    }

    /**
     * 解析measureSper的数据
     * @param size
     * @param measureSpec
     * @return 返回设定的值
     */
    public int getUserSize(int size, int measureSpec) {
        int result = size;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case View.MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case View.MeasureSpec.AT_MOST:
                result = size;
                break;
            case View.MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }
}
