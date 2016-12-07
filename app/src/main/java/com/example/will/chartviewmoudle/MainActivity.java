package com.example.will.chartviewmoudle;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.will.chartviewlib.ChartInfo.BackgroundInfo.BgLineInfo;
import com.example.will.chartviewlib.ChartInfo.MainLayer.MainLineInfo;
import com.example.will.chartviewlib.LineChartView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Button button;
    float data = 0;
    boolean addFlag = false;
    private LineChartView lineChartView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    textViewl.setText(String.valueOf(lineChartView.getMainLineInfoList().get(0).getDataList().size()));
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private TextView textViewl;
    private Timer timer = new Timer();
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            data++;
            if (data % 5 == 0){
                lineChartView.addPoint(0, (float) Math.sin((data * Math.PI) / 50), String.valueOf(data),true);
            }
            lineChartView.addPoint(1, (float) Math.sin((data * Math.PI) / 50));
//            lineChartView.addPoint(1, (float) Math.cos((data * Math.PI) / 50));
//            lineChartView.addPoint(2,(float)Math.tan((data * Math.PI) / 50));
            if (data >= 100){
                data = 0;
            }
            lineChartView.drawWave();
            Message message = new Message();
            message.what = 0;
            handler.sendMessage(message);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewl = (TextView)findViewById(R.id.textView);
        lineChartView = (LineChartView)findViewById(R.id.line_chart_view);
        lineChartView.setScaleTitle(LineChartView.LEFT_SCALE,"左Y轴");
        lineChartView.setScaleTitle(LineChartView.RIGHT_SCALE,"");
        lineChartView.setScaleTitle(LineChartView.TOP_SCALE,"");
        lineChartView.setScaleTitle(LineChartView.BOTTOM_SCALE,"");
//        lineChartView.setScaleWidth(20);
//        lineChartView.setBackgroundColor(Color.BLUE);
        lineChartView.setDefaultLineColor(Color.RED);
        lineChartView.useDefaultBackgroundLines(false);
        lineChartView.setDefaultLineIsDotted(true);
        lineChartView.enableDefaultVerticalBackgroundLine(true);
        lineChartView.enableDefaultHorizontalBackgroundLine(true);
//        lineChartView.setScaleWidth(5);
        lineChartView.setYRange(-1,1);
        lineChartView.setScaleColor(Color.GRAY);
        lineChartView.enableLeftScale(true);
        lineChartView.enableTopScale(true);
        lineChartView.enableRightScale(true);
        lineChartView.enableBottomScale(true);
        lineChartView.addMainLine();
        BgLineInfo bgLineInfo = new BgLineInfo();
        bgLineInfo.setDirection(LineChartView.BGLINE_HORIZONTAL);
        bgLineInfo.setStrTitle("hello");
        bgLineInfo.setTitlePos(LineChartView.LEFT_SCALE);
        bgLineInfo.setIsDotted(true);
        bgLineInfo.setLinePos(0);
        lineChartView.addBackgroundLine(bgLineInfo);
        BgLineInfo bgLineInfo1 = new BgLineInfo();
        bgLineInfo1.setDirection(LineChartView.BGLINE_HORIZONTAL);
        bgLineInfo1.setStrTitle("world");
        bgLineInfo1.setIsDotted(true);
        bgLineInfo1.setLinePos(0.75f);
        lineChartView.addBackgroundLine(bgLineInfo1);
        BgLineInfo bgLineInfo2 = new BgLineInfo();
        bgLineInfo2.setLineColor(Color.YELLOW);
        bgLineInfo2.setDirection(LineChartView.BGLINE_HORIZONTAL);
        bgLineInfo2.setIsDotted(false);
        bgLineInfo2.setTitlePos(LineChartView.RIGHT_SCALE);
        bgLineInfo2.setStrTitle("-0.75f");
        bgLineInfo2.setLinePos(-0.75f);
        bgLineInfo2.setLineWidth(5);
        bgLineInfo2.setTextColor(Color.YELLOW);
        lineChartView.addBackgroundLine(bgLineInfo2);
        lineChartView.addMainLine();
        lineChartView.setMainLineWidth(1,4);
        lineChartView.setMainLineColor(1,Color.GREEN);
        lineChartView.setMainPointColor(1,Color.GREEN);
//        lineChartView.addMainLine();
        lineChartView.setMainPointRadius(0, 10);
        lineChartView.setMainPointRadius(1, 2);

//        lineChartView.setMainPointRadius(0, 10);
        lineChartView.setMainLineWidth(0,4);
        lineChartView.addMainLine();
        lineChartView.setMainLineVisibility(2,false);
        lineChartView.setMainLineVisibility(1,true);
        lineChartView.setScaleVisibility(LineChartView.LEFT_SCALE,false);
        lineChartView.setScaleVisibility(LineChartView.RIGHT_SCALE,false);
        lineChartView.setScaleVisibility(LineChartView.TOP_SCALE,false);
        lineChartView.setHorizontalResolution(0,lineChartView.getMeasuredWidth() / 8);
//        lineChartView.setScaleTextSize(LineChartView.BOTTOM_SCALE,12);
//        lineChartView.setInitAViewPointsSum(0,6);
        lineChartView.setShowDataDiv(0,true);
        lineChartView.setHasPoints(1,false);
        lineChartView.setHasLine(0,false);
        lineChartView.setDivBackgroundColor(Color.BLUE);
//        lineChartView.setNormalOffsetX(50);
//        lineChartView.setHasLine(0,false);
//        lineChartView.setMainLineWidth(0, (float) 0.2);
//        lineChartView.setHorizontalReslution(80);
        timer.schedule(timerTask,1000,1000);        //每秒24帧的放映速度

//        timer.schedule(timerTask,1000,84);
//        timer.schedule(timerTask,1000,1000);
        button = (Button)findViewById(R.id.button_hello);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lineChartView.changeBackground();
                data++;
                lineChartView.addPoint(0, (float) Math.sin((data * Math.PI) / 50), String.valueOf(data));
                lineChartView.addPoint(1, (float) Math.cos((data * Math.PI) / 50));
                lineChartView.addPoint(2,(float)Math.tan((data * Math.PI) / 50));
                if (data >= 100){
                    data = 0;
                }
                lineChartView.drawWave();
            }
        });
    }
}
