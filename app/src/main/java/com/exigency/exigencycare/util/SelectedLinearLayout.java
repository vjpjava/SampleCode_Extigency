package com.exigency.exigencycare.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

@SuppressLint("NewApi")
public class SelectedLinearLayout extends LinearLayout {
	private int selectedPosition = -1;

	public SelectedLinearLayout(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr);
	}

	public SelectedLinearLayout(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public SelectedLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SelectedLinearLayout(Context context) {
		super(context);
	}

	public void setselected(int position) {
		selectedPosition = position;
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			view.setEnabled(!(i == position));
			setSetectd(view, i == position);
		}
	}

	public int getselected() {
		return selectedPosition;
	}

	private void setSetectd(View view, boolean selected) {
		view.setSelected(selected);

		if (view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				View chieldView = ((ViewGroup) view).getChildAt(i);

				setSetectd(chieldView, selected);
			}
		}

	}

}