package wyf.ytl; //声明包语句

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WebExtension extends Activity {
	WebView wv;

	// 获得WebView对象
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.mylayout2);
		Bundle bunde = this.getIntent().getExtras();
		String url = bunde.getString("myurl");
		wv = (WebView) findViewById(R.id.wv);
		wv.setWebChromeClient(new WebChromeClient() { // 为WebView设置WebChromeClient
			@Override
			public void onProgressChanged(WebView view, int newProgress) {// 重写onProgressChanged方法
				WebExtension.this.setProgress(newProgress * 100);
			}
		});
		wv.setWebViewClient(new WebViewClient() { // 为WebView设置WebViewClient
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {// 重写onReceivedError方法
				Toast.makeText(WebExtension.this, "Sorry!" + description,
						Toast.LENGTH_SHORT).show();
			}
		});
		EditText et = (EditText) findViewById(R.id.et);
		et.setText(url);
		String myurl = et.getText().toString().trim();
		if (URLUtil.isNetworkUrl(myurl)) { // 判断是否是网址
			wv.loadUrl(myurl);
		} else {
			Toast.makeText(WebExtension.this, "对不起，您输入的网址有错。",
					Toast.LENGTH_SHORT).show();
			et.requestFocus(); // 将焦点移到EditText
		}
		Button btn = (Button) findViewById(R.id.btnweb); // 获取Button对象
		btn.setOnClickListener(new View.OnClickListener() { // 为Button对象设置OnClickListener监听器
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(WebExtension.this, CameraActivity.class);
				// WebExtension.this.finish();
				startActivity(intent);

				/*
				 * String url = et.getText().toString().trim();
				 * if(URLUtil.isNetworkUrl(url)){ //判断是否是网址 wv.loadUrl(url); }
				 * else{ Toast.makeText(Sample_10_4.this, "对不起，您输入的网址有错。",
				 * Toast.LENGTH_SHORT).show(); et.requestFocus();
				 * //将焦点移到EditText }
				 */
			}
		});

	}

}