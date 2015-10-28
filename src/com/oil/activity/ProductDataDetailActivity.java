package com.oil.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.oilclient.R;
import com.oil.weidget.OilContentViewPager;
import com.oil.weidget.PagerSlidingTabStrip;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ScrollView;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Êý¾ÝÍ¼±íÒ³ (!!!!·ÏÆú)
 * 
 * @author Administrator
 *
 */
public class ProductDataDetailActivity extends Activity implements
		OnClickListener {
	ImageView iv_back;
	LineChartView lineChart;
	PagerSlidingTabStrip psts;
	OilContentViewPager ocViewPager;
	ScrollView scrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_product_data_details);
		initWeidget();
	}

	private void initWeidget() {
		// TODO Auto-generated method stub
		lineChart = (LineChartView) findViewById(R.id.chart_product_details);
		psts = (PagerSlidingTabStrip) findViewById(R.id.ps_indicator);
		ocViewPager = (OilContentViewPager) findViewById(R.id.vp_content);
		iv_back = (ImageView) findViewById(R.id.iv_pageback);
		iv_back.setOnClickListener(this);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initDemoData();
	}

	boolean hasAxes = true;
	boolean hasAxesNames = true;

	private void initDemoData() {
		// TODO Auto-generated method stub
		LineChartData data = new LineChartData();
		List<Line> lines = new ArrayList<Line>();
		int pointNumber = 12;
		List<PointValue> values = new ArrayList<PointValue>();
		for (int i = 0; i < pointNumber; i++) {
			PointValue pValue = new PointValue(i, 0);
			values.add(pValue);
		}
		Line line = new Line(values);
		line.setStrokeWidth(2);
		line.setColor(ChartUtils.COLOR_RED);
		lines.add(line);
		data.setLines(lines);
		data.setBaseValue(Float.NEGATIVE_INFINITY);
		lineChart.setLineChartData(data);
		if (hasAxes) {
			Axis axisX = new Axis();
			Axis axisY = new Axis().setHasLines(true);
			if (hasAxesNames) {
				axisX.setName("Axis X");
				axisY.setName("Axis Y");
			}
			data.setAxisXBottom(axisX);
			data.setAxisYLeft(axisY);
		} else {
			data.setAxisXBottom(null);
			data.setAxisYLeft(null);
		}
		for (PointValue pointValue : values) {
			pointValue.setTarget(pointValue.getX(), new Random().nextInt(100));
		}
		lineChart.startDataAnimation();
		// lineChart.startDataAnimation(1000);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_pageback:
			finish();
			break;

		default:
			break;
		}
	}
}
