package com.xiaoai.heartbubbling;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.xiaoai.heartbubbling.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;

public class LayoutAnimation extends RelativeLayout {

	private static final int[] ICONS = new int[] { R.drawable.icon1,
			R.drawable.icon2, R.drawable.icon3, R.drawable.icon4,
			R.drawable.icon5, R.drawable.icon6 };

	private static final int COUNT = 6;

	private static final int MAX_DEGREES = 15;

	private static final long DURATION_TOTAL = 1500;
	private static final long DURATION_SCALE = 800;
	private static final long DURATION_ALPHA = 300;
	private static final long DURATION_ROTATE = 0;

	private List<View> views = new ArrayList<View>();

	private Context mContext;

	private boolean mIsInitial;

	public LayoutAnimation(Context context) {
		super(context);
		this.mContext = context;
		initView();
	}

	public LayoutAnimation(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initView();
	}

	public LayoutAnimation(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		initView();
	}

	private void initView() {
		addViews();
	}

	private void addViews() {
		RelativeLayout layContent = (RelativeLayout) findViewById(R.id.content);
		for (int i = 0; i < COUNT; i++) {
			View view = new View(mContext);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					40, 40);
			lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
			lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
			view.setBackgroundResource(ICONS[i % ICONS.length]);
			view.setLayoutParams(lp);
			layContent.addView(view);
			view.setVisibility(View.GONE);
			views.add(view);
		}
	}

	public void reset() {
		this.mIsInitial = true;
	}

	public void startAnimation() {
		reset();
		for (int i = 0; i < views.size(); i++) {
			View view = views.get(i);
			view.setVisibility(View.VISIBLE);
			startAnim(i, view);
		}
	}

	private void startAnim(final int index, final View view) {
		Animation anim = getAnimSet(index, view);
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				startAnim(index, view);
			}
		});
		view.startAnimation(anim);
	}

	private Animation getAnimSet(int index, final View img) {
		float degrees;
		if (index < 4) {
			degrees = randomDegrees((index % 2 == 0));
		} else {
			degrees = randomDegrees();
		}

		Random random = new Random();
		int startOffset = 0;
		if (mIsInitial) {
			if (index > 0) {
				startOffset = random.nextInt(300) + index * 300;
			}
			if (index == views.size() - 1) {
				mIsInitial = false;
			}
			Log.e("test", "============= start offset: " + startOffset);
		}

		AnimationSet anim = new AnimationSet(false);
		anim.addAnimation(getRotateAnim(img, degrees));

		Animation sAnim = getScale(img);
		sAnim.setStartOffset(startOffset);
		anim.addAnimation(sAnim);

		Animation tAnim = getTranslateAnim(degrees);
		tAnim.setDuration(DURATION_TOTAL);
		tAnim.setStartOffset(startOffset);
		anim.addAnimation(tAnim);

		Animation aAnim = getAlphaAnim();
		aAnim.setStartOffset(DURATION_TOTAL - DURATION_ALPHA + startOffset);

		anim.addAnimation(aAnim);

		img.startAnimation(anim);
		return anim;
	}

	private Animation getScale(final View view) {
		Animation anim = new ScaleAnimation(0f, 1.0f, 0f, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				1.0f);
		anim.setDuration(DURATION_SCALE);
		anim.setFillAfter(true);
		return anim;
	}

	private Animation getRotateAnim(final View img, final float degrees) {
		Animation anim = new RotateAnimation(0f, degrees,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				1.0f);
		anim.setDuration(DURATION_ROTATE);
		anim.setFillAfter(true);
		anim.start();
		return anim;
	}

	private Animation getTranslateAnim(final float degrees) {
		double dis = Math.tan(degrees * Math.PI / 180) * 100;
		Animation anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
				Animation.ABSOLUTE, (float) dis, Animation.RELATIVE_TO_PARENT,
				0f, Animation.ABSOLUTE, -70);
		anim.start();
		return anim;
	}

	private Animation getAlphaAnim() {
		Animation anim = new AlphaAnimation(1.0f, 0.2f);
		anim.setDuration(DURATION_ALPHA);
		return anim;
	}

	private float randomDegrees() {
		Random random = new Random();
		return MAX_DEGREES - random.nextFloat() * MAX_DEGREES * 2;
	}

	private float randomDegrees(boolean isPositive) {
		Random random = new Random();
		if (isPositive) {
			return random.nextFloat() * MAX_DEGREES;
		} else {
			return -random.nextFloat() * MAX_DEGREES;
		}
	}

}
