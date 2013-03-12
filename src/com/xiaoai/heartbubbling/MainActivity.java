package com.xiaoai.heartbubbling;

import com.xiaoai.heartbubbling.R;

import android.app.Activity;
import android.os.Bundle;
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

}
