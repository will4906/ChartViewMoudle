package com.example.will.chartviewmoudle;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.will.canvaslib.CanvasTool;
import com.example.will.chartviewlib.DrawFactory.OnDrawBackgroundListener;
import com.example.will.chartviewlib.LineChartView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LineChartView lineChartView = (LineChartView)findViewById(R.id.line_chart_view);
        lineChartView.setScaleTitle(LineChartView.LEFT_SCALE,"你好吗");
        lineChartView.setScaleTitle(LineChartView.RIGHT_SCALE,"你不好");
        lineChartView.setScaleTitle(LineChartView.TOP_SCALE,"我试试");
        lineChartView.setScaleTitle(LineChartView.BOTTOM_SCALE,"你猜猜");
        lineChartView.setBackgroundColor(Color.BLUE);
        lineChartView.setScaleWidth(5);
        lineChartView.setScaleColor(Color.GRAY);
        lineChartView.enableLeftScale(true);
        lineChartView.enableTopScale(true);
        lineChartView.enableRightScale(true);
        lineChartView.enableBottomScale(true);
    }
}
