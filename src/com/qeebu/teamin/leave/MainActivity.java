package com.qeebu.teamin.leave;

import com.qeebu.teamin.leave.fragment.ApproveFragment;
import com.qeebu.teamin.leave.fragment.MyLeaveFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	ImageButton myleave,myapprove;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leave_main);
		
		MyLeaveFragment myleaveF = new MyLeaveFragment();
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft =  fm.beginTransaction();
		ft.replace(R.id.fl, myleaveF);
		ft.commit();
		
		myleave = (ImageButton) findViewById(R.id.btn_myleave);
		myapprove = (ImageButton) findViewById(R.id.btn_myapprove);
		myleave.setImageResource(R.drawable.leave_list_pressed);
		myapprove.setImageResource(R.drawable.leave_list2_normal);
	}
	
	public void leaveClick(View v){
		MyLeaveFragment myleaveF = new MyLeaveFragment();
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft =  fm.beginTransaction();
		ft.replace(R.id.fl, myleaveF);
		ft.commit();
		
		myapprove.setImageResource(R.drawable.leave_list2_normal);
		myleave.setImageResource(R.drawable.leave_list_pressed);
	}
	public void approveClick(View v){
		ApproveFragment appF = new ApproveFragment();
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft =  fm.beginTransaction();
		ft.replace(R.id.fl, appF);
		ft.commit();
		
		myapprove.setImageResource(R.drawable.leave_list2_pressed);
		myleave.setImageResource(R.drawable.leave_list_normal);
		
	}

}
