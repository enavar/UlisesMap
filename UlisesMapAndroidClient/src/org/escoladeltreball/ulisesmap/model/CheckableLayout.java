/**
 * Copyright (c) 2014, Oleksander Dovbysh & Elisabet Navarro & Sheila Perez
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.escoladeltreball.ulisesmap.model;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.RelativeLayout;

/**
 * CheckableLayout
 * Layout which override methods of android RelativeLayout for
 * implement checkable methods used to detect click over textView
 * 
 * @Author: Oleksandr Dovbysh, Elisabet Navarro, Sheila Perez
 * @version: 1.0
 */
public class CheckableLayout extends RelativeLayout implements Checkable {
	/** CheckedTextView */
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
	/**
	 * When layout is created find a field for detect future clicks
	 */
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
	/**
	 * Determine if field is checked
	 */
	public boolean isChecked() {
		return _checkbox != null ? _checkbox.isChecked() : false;
	}

	@Override
	/**
	 * Change field status
	 */
	public void setChecked(boolean checked) {
		if (_checkbox != null) {
			_checkbox.setChecked(checked);
		}
	}

	/**
	 * Change field status to opposite
	 */
	@Override
	public void toggle() {
		if (_checkbox != null) {
			_checkbox.toggle();
		}
	}
}