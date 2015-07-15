package com.qeebu.teamin.leave.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.qeebu.teamin.leave.LeaveTypeActivity;
import com.qeebu.teamin.leave.R;

/**
 * 我的假单
 * @author shiny_Jia
 *
 */
public class MyLeaveFragment extends Fragment implements OnClickListener{
	
	//请假类型选择
	private ImageButton leave_add;
	
	private RelativeLayout rltip;
	private Intent intent ;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
		}
		
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_leave_myleave_fragment, null);
		rltip = (RelativeLayout) view.findViewById(R.id.fragment_leavetiplayout);
		leave_add = (ImageButton) view.findViewById(R.id.leave_add_type);
		leave_add.setOnClickListener(this);
		init();	
		return view;
	}
	
	private void init() {
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.leave_add_type://请假类型选择
			intent = new Intent(getActivity(), LeaveTypeActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.fade, R.anim.hold);
			break;

		default:
			break;
		}
		
	}
//	private void overridePendingTransition(int fade, int hold) {
//		
//	}

}
