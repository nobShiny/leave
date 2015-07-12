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

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leave_main);
		
		MyLeaveFragment myleaveF = new MyLeaveFragment();
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft =  fm.beginTransaction();
		ft.replace(R.id.fl, myleaveF);
		ft.commit();
	}
	
	public void leaveClick(View v){
		MyLeaveFragment myleaveF = new MyLeaveFragment();
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft =  fm.beginTransaction();
		ft.replace(R.id.fl, myleaveF);
		ft.commit();
	}
	public void approveClick(View v){
		ApproveFragment appF = new ApproveFragment();
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft =  fm.beginTransaction();
		ft.replace(R.id.fl, appF);
		ft.commit();
	}

}
