package com.qeebu.teamin.leave.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * 自定义请假类型的ImageButton
 * 
 * @author shiny_Jia
 *
 */

public class CustomImageButton extends ImageButton {

	// 图片文字
	private String _text = "";
	// 图片背景
	private int _bgColor;
	// 文字大小
	private float _textSize;


	public CustomImageButton(Context context) {
		super(context);
	}

	public CustomImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomImageButton(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public CustomImageButton(Context context, String _text, int _bgColor,
			float _textSize) {
		super(context);
		this._text = _text;
		this._bgColor = _bgColor;
		this._textSize = _textSize;
	}

	public void set_text(String _text) {
		this._text = _text;
	}

	public void set_bgColor(int _bgColor) {
		this._bgColor = _bgColor;
	}

	public void set_textSize(float _textSize) {
		this._textSize = _textSize;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Paint paint = new Paint();
		paint.setTextAlign(Align.CENTER);
		paint.setColor(_bgColor);
		paint.setTextSize(_textSize);
		canvas.drawText(_text, canvas.getWidth() / 2,
				(canvas.getHeight() / 2) + 26, paint);
	}

	// private void init() {
	// View view = View.
	// }

}
