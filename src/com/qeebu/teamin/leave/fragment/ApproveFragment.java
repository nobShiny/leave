package com.qeebu.teamin.leave.fragment;

import com.qeebu.teamin.leave.HistoryApproveActivity;
import com.qeebu.teamin.leave.LeaveTypeActivity;
import com.qeebu.teamin.leave.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 我的审批
 * @author shiny_Jia
 *
 */
public class ApproveFragment extends Fragment implements OnClickListener {

	private ImageButton leave_history;
	private RelativeLayout rltip;
	private Intent intent ;
	private LinearLayout comment_back;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(
				R.layout.activity_leave_myapprove_fragment, null);
		rltip = (RelativeLayout) view
				.findViewById(R.id.fragment_leavetiplayout);
		comment_back = (LinearLayout) view.findViewById(R.id.comment_back);
		leave_history = (ImageButton) view.findViewById(R.id.leave_history);
		leave_history.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.leave_history://历史审批查看
			intent = new Intent(getActivity(), HistoryApproveActivity.class);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.fade, R.anim.hold);
			break;
		case R.id.comment_back://退出
			getActivity().overridePendingTransition(R.anim.fade, R.anim.hold);
			getActivity().finish();
			break;

		default:
			break;
		}
	}

}
