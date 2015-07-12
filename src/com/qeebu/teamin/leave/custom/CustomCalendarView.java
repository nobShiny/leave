package com.qeebu.teamin.leave.custom;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.qeebu.teamin.leave.R;
import com.qeebu.teamin.leave.adapter.CalendarAdapter;
import com.qeebu.teamin.leave.util.TimeUtils;

public class CustomCalendarView extends Activity {
	
	
	private String currentDate = "";
	private int jumpMonth = 0; // 每次滑动，增加或减去一个月,默认为0（即显示当前月）
	private int jumpYear = 0; // 滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
	private int year_c = 0;
	private int month_c = 0;
	private int day_c = 0;
	private GridView gridView = null;
	private TextView topText = null;
	private View btn_left_month;
	private View btn_right_month;
	private CalendarAdapter calV = null;
	private GestureDetector gestureDetector = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_view);
		initUI();
	}

	private void initUI() {
		btn_left_month = findViewById(R.id.btn_prev_month_leave);
		btn_right_month = findViewById(R.id.btn_next_month_leave);
		topText = (TextView) findViewById(R.id.tv_month_leave);
		
	}

	private void switchMonth(int offset) {
		addGridView(); // 添加一个gridView
		jumpMonth += offset; // 上/下一个月
		calV = new CalendarAdapter(this, getResources(), jumpMonth, jumpYear,
				year_c, month_c, day_c);
		gridView.setAdapter(calV);
//		calV.setADates(aDateManager.queryADates());
		calV.notifyDataSetChanged();
//		gvFlag++;
		addTextToTopTextView(topText);
	}

	// 添加gridview
	private void addGridView() {

		gridView = (GridView) findViewById(R.id.gridview_leave);
		gridView.setOnTouchListener(new OnTouchListener() {
			// 将gridview中的触摸事件回传给gestureDetector
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return CustomCalendarView.this.gestureDetector
						.onTouchEvent(event);
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO 处理点击某一个日期进入，定位到该日期
				// 点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
				int startPosition = calV.getStartPositon();
				int endPosition = calV.getEndPosition();
				if (startPosition <= position + 7
						&& position <= endPosition - 7) {
					String scheduleDay = calV.getDateByClickItem(position)
							.split("\\.")[0]; // 这一天的阳历
					// //这一天的阴历
					String scheduleYear = calV.getShowYear();
					String scheduleMonth = calV.getShowMonth();
//					ruzhuTime = scheduleMonth + "月" + scheduleDay + "日";
//					lidianTime = scheduleMonth + "月" + scheduleDay + "日";

					if (Integer.parseInt(scheduleDay) < 10) {
						scheduleDay = "0" + scheduleDay;
					}

					if (Integer.parseInt(scheduleMonth) < 10) {
						scheduleMonth = "0" + scheduleMonth;
					}

					// 传递给MyMeetingFragment页面
					setDate(scheduleYear + scheduleMonth + scheduleDay);

					// 用户获取的时间
					String date1 = "";
					if (Integer.parseInt(scheduleDay) < 10) {
						String newScheduleDay = "0" + scheduleDay;
						if (Integer.parseInt(scheduleMonth) < 10) {
							String newScheduleMonth = "0" + scheduleMonth;
							date1 = scheduleYear + newScheduleMonth
									+ newScheduleDay;
						} else {
							date1 = scheduleYear + scheduleMonth
									+ newScheduleDay;
						}
					} else {
						date1 = scheduleYear + scheduleMonth + scheduleDay;
					}

					// 系统当前时间
					String currentTime = TimeUtils.crrentTimeByYY_MM_DD();
					String year = currentTime.substring(0, 4);
					String month = currentTime.substring(5, 7);
					String day = currentTime.substring(8, 10);
					String date = year + month + day;

					// // 如果选择时间小于系统当前时间，则不跳转
					// if (Integer.parseInt(date1) < Integer.parseInt(date)) {
					// startActivity(new Intent(CalendarActivity.this,
					// com.qeebu.teamin.meeting.ui.AlertDialog.class).putExtra("msg",
					// "会议已过期"));
					// return;
					// }

//					Intent intent = new Intent(CalendarActivity.this,
//							MeetingManagerActivity.class);
//					intent.putExtra("defaultIndex", "0");
//					startActivity(intent);
				}
			}
		});
	}
	
	// 添加头部的年份 闰哪月等信息
	public void addTextToTopTextView(TextView view) {
		StringBuffer textDate = new StringBuffer();
		textDate.append(calV.getShowYear()).append("年")
				.append(calV.getShowMonth()).append("月").append("\t");
		view.setText(textDate);
		// view.setTextColor(Color.WHITE);
		view.setTypeface(Typeface.DEFAULT_BOLD);
	}
	
	private static String time;
	public void setDate(String time) {
		CustomCalendarView.time = time;
	}

	public static String getDate() {
		return time;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.activity_left_exit,
					R.anim.activity_left_exit_hide);

		}
		return true;
	}
}
