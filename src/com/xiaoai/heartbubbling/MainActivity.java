package com.xiaoai.heartbubbling;

import com.xiaoai.heartbubbling.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	private LayoutAnimation mLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mLayout = (LayoutAnimation) findViewById(R.id.content);

		findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mLayout.startAnimation();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		mLayout.reset();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, 1, 1, "setCount");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == 1) {
			showCountDialog();
		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void showCountDialog() {
		String[] items = new String[13];
		for (int i = 0; i < items.length; i++) {
			items[i] = (i + 3) + "";
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("Count").setItems(items, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				mLayout.setCount(which + 3);
			}
		});
		builder.show();
	}

}
