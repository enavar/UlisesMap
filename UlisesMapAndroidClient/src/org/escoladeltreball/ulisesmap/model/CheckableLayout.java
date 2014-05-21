package org.escoladeltreball.ulisesmap.model;



import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.RelativeLayout;

public class CheckableLayout extends RelativeLayout implements Checkable {
	private CheckedTextView _checkbox;

	public CheckableLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}	

	public CheckableLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public CheckableLayout(Context context) {
		super(context);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		// find checked text view
		int childCount = getChildCount();
		for (int i = 0; i < childCount; ++i) {
			View v = getChildAt(i);
			if (v instanceof CheckedTextView) {
				_checkbox = (CheckedTextView) v;
			}
		}
	}

	@Override
	public boolean isChecked() {
		return _checkbox != null ? _checkbox.isChecked() : false;
	}

	@Override
	public void setChecked(boolean checked) {
		if (_checkbox != null) {
			_checkbox.setChecked(checked);
		}
	}

	@Override
	public void toggle() {
		if (_checkbox != null) {
			_checkbox.toggle();
		}
	}
}