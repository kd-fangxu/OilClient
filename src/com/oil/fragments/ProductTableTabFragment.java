package com.oil.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.oilclient.R;

public class ProductTableTabFragment extends Fragment {
	LineChartView lineChart;
	Button btn_test;

	public ProductTableTabFragment() {
	};

	public static ProductTableTabFragment getInstance() {
		return new ProductTableTabFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.fragment_protab_table,
				null);
		lineChart = (LineChartView) view
				.findViewById(R.id.chart_product_details);
		lineChart.getParent().requestDisallowInterceptTouchEvent(true);
		initDemoData();
		btn_test = (Button) view.findViewById(R.id.btn_test);
		btn_test.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				initData();
			}
		});
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		for (Line line : data.getLines()) {
			for (PointValue pointValue : line.getValues()) {
				pointValue.setTarget(pointValue.getX(),
						new Random().nextInt(max));
			}
		}

		lineChart.startDataAnimation();
	}

	boolean hasAxes = true;
	boolean hasAxesNames = true;
	List<PointValue> values;
	LineChartData data;
	int max = 100;

	private void initDemoData() {
		// TODO Auto-generated method stub
		data = new LineChartData();
		List<Line> lines = new ArrayList<Line>();
		int pointNumber = 13;
		values = new ArrayList<PointValue>();
		for (int i = 0; i < pointNumber; i++) {
			PointValue pValue = new PointValue(i, new Random().nextInt(max));
			values.add(pValue);
		}
		Line line = new Line(values);
		line.setStrokeWidth(2);
		line.setColor(ChartUtils.COLOR_RED);
		lines.add(line);
		data.setLines(lines);
		// data.setBaseValue(10);

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
		// for (Line aline : data.getLines()) {
		// for (PointValue pointValue : aline.getValues()) {
		// pointValue.setTarget(pointValue.getX(), 10);
		// }
		// }
		lineChart.setLineChartData(data);
		// lineChart.startDataAnimation();
		// lineChart.startDataAnimation(1000);
	}
}
