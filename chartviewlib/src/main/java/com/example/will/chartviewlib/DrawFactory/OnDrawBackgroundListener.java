package com.example.will.chartviewlib.DrawFactory;

import com.example.will.chartviewlib.Common.CanvasTool;

/**
 * @author will4906.
 * @Time 2016/11/22.
 */

public interface OnDrawBackgroundListener {
    /**
     * 监听画背景，用户可以在这里自定义背景的绘制
     * @param canvasTool
     * @return true叠加模式，false清空模式
     */
    boolean onBackgroundDraw(CanvasTool canvasTool);
}
