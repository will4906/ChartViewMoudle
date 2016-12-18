package com.example.will.chartviewlib.DrawFactory;

/**
 * 信息中介者，用来处理信息的相互传递
 * @author will4906.
 * @Time 2016/12/17.
 */

public class InformationMediator extends AbstractMediator{

    private BackgroundEngine backgroundEngine;

    private DrawEngine drawEngine;

    public BackgroundEngine getBackgroundEngine() {
        return backgroundEngine;
    }

    public void setBackgroundEngine(BackgroundEngine backgroundEngine) {
        this.backgroundEngine = backgroundEngine;
    }

    public DrawEngine getDrawEngine() {
        return drawEngine;
    }

    public void setDrawEngine(DrawEngine drawEngine) {
        this.drawEngine = drawEngine;
    }

    @Override
    public void change(Colleague colleague) {
        if (colleague == backgroundEngine){
            if (drawEngine != null){
                drawEngine.setBackgroundWidth(backgroundEngine.getBackgroundWidth());
                drawEngine.setBackgroundHeight(backgroundEngine.getBackgroundHeight());
                drawEngine.setAxisInfos(backgroundEngine.getAxisInfos());               //这一句其实只要一次，因为是引用对象。
                drawEngine.setChartWidth(backgroundEngine.getChartWidth());
                drawEngine.setChartHeight(backgroundEngine.getChartHeight());
            }
        }else if (colleague == drawEngine){
            //这里暂时不知道要处理什么事情
        }
    }
}
