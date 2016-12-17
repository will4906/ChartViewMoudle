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
            drawEngine.setBackgroundWidth(backgroundEngine.getBackgroundWidth());
            drawEngine.setBackgroundHeight(backgroundEngine.getBackgroundHeight());
            drawEngine.setAxisInfos(backgroundEngine.getAxisInfos());
            drawEngine.setChartWidth(backgroundEngine.getChartWidth());
            drawEngine.setChartHeight(backgroundEngine.getChartHeight());
        }else if (colleague == drawEngine){

        }
    }
}
