package wyf.ytl; //���������

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

	// ���WebView����
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.mylayout2);
		Bundle bunde = this.getIntent().getExtras();
		String url = bunde.getString("myurl");
		wv = (WebView) findViewById(R.id.wv);
		wv.setWebChromeClient(new WebChromeClient() { // ΪWebView����WebChromeClient
			@Override
			public void onProgressChanged(WebView view, int newProgress) {// ��дonProgressChanged����
				WebExtension.this.setProgress(newProgress * 100);
			}
		});
		wv.setWebViewClient(new WebViewClient() { // ΪWebView����WebViewClient
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {// ��дonReceivedError����
				Toast.makeText(WebExtension.this, "Sorry!" + description,
						Toast.LENGTH_SHORT).show();
			}
		});
		EditText et = (EditText) findViewById(R.id.et);
		et.setText(url);
		String myurl = et.getText().toString().trim();
		if (URLUtil.isNetworkUrl(myurl)) { // �ж��Ƿ�����ַ
			wv.loadUrl(myurl);
		} else {
			Toast.makeText(WebExtension.this, "�Բ������������ַ�д�",
					Toast.LENGTH_SHORT).show();
			et.requestFocus(); // �������Ƶ�EditText
		}
		Button btn = (Button) findViewById(R.id.btnweb); // ��ȡButton����
		btn.setOnClickListener(new View.OnClickListener() { // ΪButton��������OnClickListener������
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(WebExtension.this, CameraActivity.class);
				// WebExtension.this.finish();
				startActivity(intent);

				/*
				 * String url = et.getText().toString().trim();
				 * if(URLUtil.isNetworkUrl(url)){ //�ж��Ƿ�����ַ wv.loadUrl(url); }
				 * else{ Toast.makeText(Sample_10_4.this, "�Բ������������ַ�д�",
				 * Toast.LENGTH_SHORT).show(); et.requestFocus();
				 * //�������Ƶ�EditText }
				 */
			}
		});

	}

}