package com.fif.iclass.common.widget;



import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fif.baselib.utils.UIUtils;
import com.fif.iclass.R;

/**
 * 右侧的sideBar,显示的是二十六个字母以及*，和#号，
 * 点击字母，自动导航到相应拼音的汉字上
 * @author http://blog.csdn.net/finddreams
 */ 
public class SideBar extends View {
	// 触摸事件
	private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	// 26个字母
	public static String[] b = { "*","A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z","#"};
	private int choose = -1;// 选中
	private Paint paint = new Paint();

	private TextView mTextDialog;
	private int singleHeight;

	public void setTextView(TextView mTextDialog) {
		this.mTextDialog = mTextDialog;
	}
	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SideBar(Context context) {
		super(context);
	}

	/**
	 * 重写这个方法
	 */
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获取焦点改变背景颜色.
		int height = getHeight();// 获取对应高度
		int width = getWidth(); // 获取对应宽度
		if (height == 0 || b.length == 0) return;
		// 获取每一个字母的高度
		singleHeight = height / b.length;
		//如果高度过高，说明字母很少，将高度设为20，即最大高度为20
		if (singleHeight > UIUtils.dip2px(getContext(), 30)) singleHeight = UIUtils.dip2px(getContext(), 30);
		float fromYPos = (height - singleHeight * b.length) / 2;
		for (int i = 0; i < b.length; i++) {
			paint.setColor(getResources().getColor(R.color.main_color));
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			paint.setTextSize(UIUtils.dip2px(getContext(), 14));
			// 选中的状态
			if (i == choose) {
				paint.setColor(getResources().getColor(R.color.main_pressed_color));
				paint.setFakeBoldText(true);
			}
			// x坐标等于中间-字符串宽度的一半.
			float xPos = width / 2 - paint.measureText(b[i]) / 2;
			float yPos = fromYPos + singleHeight * i + singleHeight;
			canvas.drawText(b[i], xPos, yPos, paint);
			paint.reset();// 重置画笔
		}

	}

	
	@SuppressLint("NewApi")
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		float fromYPos = (getHeight() - singleHeight * b.length) / 2;
		final float y = event.getY();// 点击y坐标
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) ((y - fromYPos) / singleHeight);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

		switch (action) {
		case MotionEvent.ACTION_UP:
			setBackgroundDrawable(new ColorDrawable(0x00000000));
			choose = -1;//
			invalidate();
			if (mTextDialog != null) {
				//父类为CardView
				((FrameLayout)mTextDialog.getParent()).setVisibility(View.INVISIBLE);
			}
			break;

		default:
			setBackgroundResource(R.drawable.shape_rounded_corners);
			setAlpha((float) 0.7);
			if (oldChoose != c) {
				if (c >= 0 && c < b.length) {
					if (listener != null) {
						listener.onTouchingLetterChanged(b[c]);
					}
					if (mTextDialog != null) {
						mTextDialog.setText(b[c]);
						//父类为CardView
						((FrameLayout)mTextDialog.getParent()).setVisibility(View.VISIBLE);
					}
					
					choose = c;
					invalidate();
				}
			}

			break;
		}
		return true;
	}

	/**
	 * 向外公开的方法
	 */
	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	public void setArray(String[] b) {
		SideBar.b = b;
	}

	/**
	 * 接口
	 * 
	 * @author coder
	 * 
	 */
	public interface OnTouchingLetterChangedListener {
		void onTouchingLetterChanged(String s);
	}

}