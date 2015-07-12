package com.qeebu.teamin.leave;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class HistoryApproveActivity extends Activity implements OnClickListener {

	private LinearLayout comment_back;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_approve_myapprove_history);
		comment_back = (LinearLayout) findViewById(R.id.comment_back);
		comment_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.comment_back:// 返回假单审批
			intent = new Intent(this, HistoryApproveActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.fade, R.anim.hold);
			finish();
			break;

		default:
			break;
		}
	}

}
