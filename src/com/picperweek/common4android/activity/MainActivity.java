package com.picperweek.common4android.activity;

import com.picperweek.common4android.R;
import com.picperweek.common4android.api.HttpTag;
import com.picperweek.common4android.base.BaseActivity;
import com.picperweek.common4android.config.Constants;
import com.picperweek.common4android.http.HttpEngine.HttpCode;
import com.picperweek.common4android.http.command.HttpDataResponse;
import com.picperweek.common4android.http.command.HttpPostRequest;
import com.picperweek.common4android.http.task.TaskManager;
import com.picperweek.common4android.manager.ThemeSettingsHelper;
import com.picperweek.common4android.manager.ThemeSettingsHelper.ThemeCallback;
import com.picperweek.common4android.util.DialogUtil;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements HttpDataResponse, ThemeCallback {

	private Button mBtn;
	
	private ThemeSettingsHelper mThemeSettingsHelper;
	
	@Override
	public boolean needTranslucent() {
		return true;
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_main);
	}

	@Override
	public void initStaticData() {

	}

	@Override
	public void initData() {

	}

	@Override
	public void initView() {
		mThemeSettingsHelper = ThemeSettingsHelper.getThemeSettingsHelper(this);
		mThemeSettingsHelper.changeTheme(this, ThemeSettingsHelper.THEME_NIGHT);
		setStatusBarAlpha(0);
		mBtn = (Button) findViewById(R.id.test_send_request);
	}

	@Override
	public void initListener() {
		mThemeSettingsHelper.registerThemeCallback(this);
		mBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				goToActivity(NextActivity.class, false);
				sendRequest();
			}
		});
	}

	@Override
	public void onHttpRecvOK(HttpTag tag, Object extraInfo, Object result) {
		DialogUtil.showToast(this, (String) result, Toast.LENGTH_LONG);
	}

	@Override
	public void onHttpRecvError(HttpTag tag, HttpCode retCode, String msg) {
		DialogUtil.showToast(this, "onHttpRecvError retCode:" + retCode + " msg:" + msg, Toast.LENGTH_LONG);
	}

	@Override
	public void onHttpRecvCancelled(HttpTag tag) {
		DialogUtil.showToast(this, "onHttpRecvCancelled", Toast.LENGTH_LONG);
	}

	private void sendRequest() {
		HttpPostRequest request = new HttpPostRequest();
		request.setTag(HttpTag.TEST);
		request.setSort(Constants.REQUEST_METHOD_POST); // application/x-www-form-urlencoded
		//request.setSort(Constants.REQUEST_METHOD_POST_MULTIPLE); // multipart/form-data
		request.setGzip(true);
		request.setRetry(false);
		request.setNeedAuth(false);
		TaskManager.startHttpDataRequset(request, this);

	}

	@Override
	public void applyTheme() {
		if (mThemeSettingsHelper.isDefaultTheme()) {
			
		}
		
	}

}
