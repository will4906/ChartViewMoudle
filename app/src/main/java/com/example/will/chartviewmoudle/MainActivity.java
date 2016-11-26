package com.example.will.chartviewmoudle;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.will.canvaslib.CanvasTool;
import com.example.will.chartviewlib.DrawFactory.OnDrawBackgroundListener;
import com.example.will.chartviewlib.LineChartView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Button button;
    int data = 20;
    boolean addFlag = false;
    private LineChartView lineChartView;
    private Timer timer = new Timer();
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (data >= 99){
                addFlag = true;
            }
            if (data == 1){
                addFlag = false;
            }
            if (addFlag == false){
                lineChartView.addPoint(data++);
            }else{
                lineChartView.addPoint(data--);
            }
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
        lineChartView.setYRange(0,200);
        lineChartView.setScaleColor(Color.GRAY);
        lineChartView.enableLeftScale(true);
        lineChartView.enableTopScale(true);
        lineChartView.enableRightScale(true);
        lineChartView.enableBottomScale(true);
        lineChartView.addMainLine();
//        lineChartView.addMainLine();
//        lineChartView.setHorizontalReslution(10);
        timer.schedule(timerTask,1000,1);
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
                        lineChartView.addPoint(0,data--);
//                        lineChartView.addPoint(1,data * (float)3);
                    }
                }
                lineChartView.drawWave();
            }
        });
    }
}
