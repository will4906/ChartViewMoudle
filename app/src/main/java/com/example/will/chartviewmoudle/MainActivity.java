package com.example.will.chartviewmoudle;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.will.chartviewlib.LineChartView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Button button;
    int data = 0;
    boolean addFlag = false;
    private LineChartView lineChartView;
    private Timer timer = new Timer();
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
//            if (data >= 30){
//                addFlag = true;
//            }
//            if (data <= -30){
//                addFlag = false;
//            }
//            if (addFlag == false){
                data++;
                lineChartView.addPoint((float) Math.sin((data * Math.PI) / 100));
            if (data >= 200){
                data = 0;
            }
//            }else{
//                data--;
//                lineChartView.addPoint((float) Math.sin(Math.PI / data));
//            }
            lineChartView.drawWave();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lineChartView = (LineChartView)findViewById(R.id.line_chart_view);
        lineChartView.setScaleTitle(LineChartView.LEFT_SCALE,"你好吗");
        lineChartView.setScaleTitle(LineChartView.RIGHT_SCALE,"你不好");
        lineChartView.setScaleTitle(LineChartView.TOP_SCALE,"我试试");
        lineChartView.setScaleTitle(LineChartView.BOTTOM_SCALE,"你猜猜");
//        lineChartView.setScaleWidth(20);
//        lineChartView.setBackgroundColor(Color.BLUE);
        lineChartView.setDefaultLineColor(Color.RED);
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
//        lineChartView.addMainLine();
//        lineChartView.setMainPointRadius(0, (float) 0.5);
        lineChartView.setMainPointRadius(0, 10);
        lineChartView.setMainLineWidth(0,10);
//        lineChartView.setHasLine(0,false);
//        lineChartView.setMainLineWidth(0, (float) 0.2);
//        lineChartView.setHorizontalReslution(80);
        timer.schedule(timerTask,1000,42);        //每秒24帧的放映速度
//        timer.schedule(timerTask,1000,1000);
        button = (Button)findViewById(R.id.button_hello);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0 ; i < 100; i ++){
                    if (data >= 99){
                        addFlag = true;
                    }
                    if (data == -20){
                        addFlag = false;
                    }
                    if (addFlag == false){
                        lineChartView.addPoint(0,data++);
//                        lineChartView.addPoint(1,data * (float)3);
                    }else{
                        data -= 10;
                        lineChartView.addPoint(0,data--);
//                        lineChartView.addPoint(1,data * (float)3);
                    }
                }
                lineChartView.drawWave();
            }
        });
    }
}
