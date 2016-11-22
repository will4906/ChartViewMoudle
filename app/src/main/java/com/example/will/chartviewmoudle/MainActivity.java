package com.example.will.chartviewmoudle;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.will.canvaslib.CanvasTool;
import com.example.will.chartviewlib.DrawFactory.OnDrawBackgroundListener;
import com.example.will.chartviewlib.LineChartView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LineChartView lineChartView = (LineChartView)findViewById(R.id.line_chart_view);
//        lineChartView.setOnDrawBackgroundListener(new OnDrawBackgroundListener() {
//            @Override
//            public boolean onBackgroundDraw(CanvasTool canvasTool) {
//                Paint paint = new Paint();
//                paint.setStrokeWidth(1);
//                paint.setColor(Color.GREEN);
//                paint.setAntiAlias(true);
//                canvasTool.drawCircle(200,200,50,paint);
//                return false;
//            }
//        });
    }
}
