package com.oil.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.oilclient.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.oil.event.FinishEvent;
import com.oil.fragments.MainFragment;
import com.oil.fragments.MenuFragment;
import com.oil.utils.ScreenUtils;

import de.greenrobot.event.EventBus;

/**
 * ������ҳ
 * 
 * @author user
 *
 */
public class MainActivity extends SlidingFragmentActivity {
	SlidingMenu sm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initSlidingMenu();
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		EventBus.getDefault().register(this);
	}

	// �ر�
	public void onEvent(FinishEvent event) {
		finish();
	}

	/**
	 * ��ʼ���໬��
	 */
	private void initSlidingMenu() {
		// TODO Auto-generated method stub
		setBehindContentView(R.layout.fragment_mainmenu);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// MainFragment mFragment = MainFragment.getInstance();
		// ((MainFragment) mFragment)
		// .setOnMainBackListener(new MainBackListener() {
		//
		// @Override
		// public void onMainBackListener() {
		// // TODO Auto-generated method stub
		// getSlidingMenu().toggle(true);
		// }
		// });
		ft.replace(R.id.fl_mainpage, MainFragment.getInstance());
		ft.replace(R.id.ll_mainmenu, new MenuFragment());
		sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT);
		sm.setShadowWidth(3);
		sm.setBehindOffset(ScreenUtils.getScreenWidth(MainActivity.this) / 4);
		sm.setFadeDegree(0.5f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		ft.commit();
	}

	boolean isQuit = false;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (!isQuit) {
				Toast.makeText(getApplicationContext(),
						getResources().getText(R.string.demo1), 1).show();
				isQuit = true;
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						isQuit = false;
					}
				}, 3000);
			} else {
				finish();
			}
			break;

		default:
			break;
		}
		return true;
	}
}
