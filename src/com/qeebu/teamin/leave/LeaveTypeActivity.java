package com.qeebu.teamin.leave;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qeebu.teamin.leave.bean.LeaveTypeBean;
import com.qeebu.teamin.leave.bean.LeaveTypeBean.Data;
import com.qeebu.teamin.leave.constant.LeaveAPIStateCode;
import com.qeebu.teamin.leave.constant.LeaveConstant;
import com.qeebu.teamin.leave.constant.LeaveHandlerMsg;
import com.qeebu.teamin.leave.custom.CustomImageButton;
import com.qeebu.teamin.leave.util.SharedPreferenceUtil;
import com.qeebu.teamin.okhttp.OkHttpUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class LeaveTypeActivity extends Activity implements OnClickListener,
		OnItemClickListener {

	private Context context;
	// 自定义ImageButton上面显示的字体的颜色
	private final static int BTN_TEXTCOLOR = Color.WHITE;
	// 自定义ImageButton上面显示的字体的大小
	private static float BTN_TEXTSIZE = 80f;

	// 后退
	private ImageView typeBack;
	// //请假类型
	// private TextView typeName;

	// 请假类型以表格形式显示
	private GridView leaveTypeGV;

	private static int[] mPics = new int[] { R.drawable.leave_blue,
			R.drawable.leave_orange, R.drawable.leave_green,
			R.drawable.leave_yellow, R.drawable.leave_red,
			R.drawable.leave_purple, R.drawable.leave_list_bg };

	// private static String[] mTypeItems = new
	// String[]{"病假申请","事假申请","婚假申请","年假申请"};
	private ArrayList<Data> categoryList;

	private String firstText;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LeaveHandlerMsg.NET_ACCESS_SUCCESS:
				String json = msg.obj.toString();
				int state = OkHttpUtil.getState(json);
				if (state == 0) {
					Gson g = new Gson();
					/**
					 * fromJson params json字符串 需要转换对象的类型
					 */
					LeaveTypeBean leaveTypeBean = g.fromJson(json,
							LeaveTypeBean.class);
					if (categoryList != null) {
						categoryList.clear();
					} else {
						categoryList = leaveTypeBean.getData();
						leaveTypeGV.setAdapter(new LeaveTypeAdapter(
								categoryList));
					}
				} else {
					String message = LeaveAPIStateCode.getMessage(state);
					Toast.makeText(context, message, 0).show();
				}
				break;
			case LeaveHandlerMsg.NET_ACCESS_FAILURE:
				Toast.makeText(context, "请检查您的网络", Toast.LENGTH_SHORT).show();

			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leave_addlist);
		initView();
		requestServer();
	}

	// 向服务器请求请假类型
	private void requestServer() {
		// 请求地址
		String url = LeaveConstant.OBTAIN_ALL_LEAVE_TYPE;
		List<BasicNameValuePair> params = new LinkedList<>();
		params.add(new BasicNameValuePair("cid", SharedPreferenceUtil
				.getString(context,
						LeaveConstant.LEAVE_SHAREDPREFERENCE_FILE_NAME,
						LeaveConstant.CID, null)));
		params.add(new BasicNameValuePair("uid", SharedPreferenceUtil
				.getString(context,
						LeaveConstant.LEAVE_SHAREDPREFERENCE_FILE_NAME,
						LeaveConstant.UID, null)));
		OkHttpUtil.asynHttpGet(url, params, mCallback);
	}

	private Callback mCallback = new Callback() {

		@Override
		public void onFailure(Request response, IOException arg1) {
			mHandler.sendEmptyMessage(LeaveHandlerMsg.NET_ACCESS_FAILURE);

		}

		@Override
		public void onResponse(Response response) throws IOException {
			if (response.isSuccessful()) {
				Message msg = mHandler.obtainMessage();
				msg.what = LeaveHandlerMsg.NET_ACCESS_SUCCESS;
				msg.obj = response.body().string();
				mHandler.sendMessage(msg);
			} else {
				mHandler.sendEmptyMessage(LeaveHandlerMsg.NET_ACCESS_FAILURE);
			}
		}

	};

	// 初始化view
	private void initView() {
		leaveTypeGV = (GridView) findViewById(R.id.gv_leave_type);
		leaveTypeGV.setOnItemClickListener(this);
		typeBack = (ImageView) findViewById(R.id.leave_type_back);
		typeBack.setOnClickListener(this);

	}

	// 自定义的girdview的adapter
	class LeaveTypeAdapter extends BaseAdapter {
		private ArrayList<Data> list;

		public LeaveTypeAdapter(ArrayList<Data> categoryList) {
			super();
			this.list = categoryList;
		}

		@Override
		public int getCount() {
			return list == null ? 0 : list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			final ViewHoler holder;
			if (convertView == null) {
				holder = new ViewHoler();
				convertView = View.inflate(LeaveTypeActivity.this,
						R.layout.activity_leave_add_item, null);
				holder.typePic = (CustomImageButton) convertView
						.findViewById(R.id.leave_type_item_ib_pic);
				holder.leaveTypeName = (TextView) convertView
						.findViewById(R.id.tv_leave_type_item_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHoler) convertView.getTag();
			}

			// 需要处理复用类型图块儿的问题
			holder.typePic.setImageResource(mPics[position]);
			holder.typePic.set_bgColor(BTN_TEXTCOLOR);
			holder.typePic.set_textSize(BTN_TEXTSIZE);
			holder.leaveTypeName.setText(list.get(position).getType_name());
			// 设置图块上的字
			holder.typePic.set_text(list.get(position).getType_name()
					.substring(0, 1));
			return convertView;
		}

	}

	class ViewHoler {
		// 请假类型的图片
		CustomImageButton typePic;
		// 请假类型的名称
		TextView leaveTypeName;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.leave_type_back:// 退出
			finish();
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			break;

		default:
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(LeaveTypeActivity.this,
				CreateNewLeaveActivity.class);
//		intent.putExtra(name, value);
		startActivity(intent);
		overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);

	}

}
