package com.qeebu.teamin.leave;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.qeebu.teamin.leave.custom.CustomImageButton;

public class LeaveTypeActivity extends Activity implements OnClickListener {
	
    //自定义ImageButton上面显示的字体的颜色
    private final static int BTN_TEXTCOLOR = Color.WHITE;
    //自定义ImageButton上面显示的字体的大小
    private static float BTN_TEXTSIZE = 80f;
    
    //后退
    private ImageView typeBack;
    //请假类型
    private TextView typeName;
    
	//请假类型以表格形式显示
	private GridView leaveType;
	//请假类型的图片
	private static CustomImageButton typePic;
	private static int[] mPics = new int[]{R.drawable.leave_blue,R.drawable.leave_green,R.drawable.leave_orange,R.drawable.leave_purple};
	//请假类型的名称
	private static TextView leaveTypeName;
	private static String[] mTypeItems = new String[]{"病假申请","事假申请","婚假申请","年假申请"};
	private String firstText;
	
	private SharedPreferences sp;
	
	static Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			leaveTypeName.setText(mTypeItems[(int) msg.obj]);
			typePic.setImageResource(mPics[(int) msg.obj]);
			typePic.set_bgColor(BTN_TEXTCOLOR);
			typePic.set_textSize(BTN_TEXTSIZE);
			typePic.set_text("病");
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leave_addlist);
		initView();
	}

	//初始化view
	private void initView() {
		leaveType = (GridView) findViewById(R.id.gv_leave_type);
		leaveType.setAdapter(new LeaveTypeAdapter());
		typeBack = (ImageView) findViewById(R.id.leave_type_back);
		typeBack.setOnClickListener(this);
	}
	
	private String SplitString(String[] mTypeItems,String firstText){
		return firstText;
	}
	
	
	//自定义的girdview的adapter
	class LeaveTypeAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mTypeItems.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mTypeItems[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View view = View.inflate(LeaveTypeActivity.this,R.layout.activity_leave_add_item , null);
			typePic = (CustomImageButton) view.findViewById(R.id.leave_type_item_ib_pic);
			leaveTypeName = (TextView) view.findViewById(R.id.tv_leave_type_item_name);
			
			
			//从服务器获取请假类型
			
			typeName = (TextView) findViewById(R.id.tv_leave_type_item_name);
			String name = typeName.getText().toString();
			sp = getSharedPreferences("leaveType", MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putString("type", name);
			editor.commit();
			
			Message msg = new Message();
			msg.obj = position;
			handler.sendMessage(msg);
			
			
			return view;
		}
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.leave_type_back://退出
			finish();
			overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			break;

		default:
			break;
		}
		
	}

}
