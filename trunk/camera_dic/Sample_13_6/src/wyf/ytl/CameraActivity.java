package wyf.ytl;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

//import android.widget.EditText;
public class CameraActivity extends Activity implements SurfaceHolder.Callback,
		OnClickListener {
	SurfaceView mySurfaceView;// SurfaceView的引用
	SurfaceHolder mySurfaceHolder;// SurfaceHolder的引用
	Button button1;// 打开按钮
	Button button2;// 关闭按钮
	Button button3;// 拍照按钮
	Button button4;
	android.hardware.Camera myCamera;// Camera的引用
	boolean isView = false;// 是否在浏览中
	boolean isClick = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.mylayout);
		mySurfaceView = (SurfaceView) findViewById(R.id.mySurfaceView);// 得到SurfaceView的引用
		button1 = (Button) findViewById(R.id.button1);// 得到按钮的引用
		button2 = (Button) findViewById(R.id.button2);// 得到按钮的引用
		button3 = (Button) findViewById(R.id.button3);// 得到按钮的引用
		button4 = (Button) findViewById(R.id.button4);
		button1.setOnClickListener(this);// 为按钮添加监听
		button2.setOnClickListener(this);// 为按钮添加监听
		button3.setOnClickListener(this);// 为按钮添加监听
		button4.setOnClickListener(this);
		mySurfaceHolder = mySurfaceView.getHolder();// 获得SurfaceHolder
		mySurfaceHolder.addCallback(this);// 添加接口的实现
		mySurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

	}

	public void onClick(View v) {
		initCamera();
		if (v == button1) {// 打开按钮
			// initCamera();//初始化相机
			Intent intent = new Intent();
			intent.setClass(CameraActivity.this, ProtoType.class);
			startActivity(intent);
			CameraActivity.this.finish();
		} else if (v == button2) {
			if (!isClick) {
				initCamera();
				isClick = true;
			} else {
				if (myCamera != null && isView) {// 当正在显示时
					isView = false;
					myCamera.stopPreview();
					myCamera.release();
					myCamera = null;
					isClick = false;
				}
			}
		} else if (v == button4) {
			Intent intent = new Intent();
			intent.setClass(CameraActivity.this, WebExtension.class);
			Bundle bundle = new Bundle();
			String myurl = "http://en.wikipedia.org/wiki/France";
			bundle.putString("myurl", myurl);
			intent.putExtras(bundle);
			startActivityForResult(intent, 0);
			// CameraActivity.this.finish();
		} else if (v == button3) {
			Intent intent = new Intent();
			intent.setClass(CameraActivity.this, WebExtension.class);
			Bundle bundle = new Bundle();
			String myurl = "http://www.ieee.org";
			bundle.putString("myurl", myurl);
			intent.putExtras(bundle);
			startActivityForResult(intent, 0);
			// CameraActivity.this.finish();
		}
	}

	public void initCamera() {
		if (!isView) {
			myCamera = Camera.open();
		}
		if (myCamera != null && !isView) {
			try {
				Camera.Parameters myParameters = myCamera.getParameters();
				// myParameters.setPictureSize(myParameters.getPreviewSize().width,
				// myParameters.getPreviewSize().height);
				myParameters.setPictureFormat(PixelFormat.RGB_565);
				myParameters.setPreviewSize(480, 320);// 屏幕大小
				myCamera.setParameters(myParameters);
				myCamera.setDisplayOrientation(90);
				myCamera.setPreviewDisplay(mySurfaceHolder);
				myCamera.startPreview();// 立即运行Preview
			} catch (IOException e) {// 捕获异常
				e.printStackTrace();// 打印错误信息
			}
			isView = true;
		}
	}

	ShutterCallback myShutterCallback = new ShutterCallback() {
		public void onShutter() {
		}
	};
	PictureCallback myRawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
		}
	};

	/*
	 * PictureCallback myjpegCallback = new PictureCallback(){
	 * 
	 * @Override public void onPictureTaken(byte[] data, Camera camera) { Bitmap
	 * bm = BitmapFactory.decodeByteArray(data, 0, data.length); ImageView
	 * myImageView = (ImageView) findViewById(R.id.myImageView);
	 * myImageView.setImageBitmap(bm);//将图片显示到下方的ImageView中 isView = false;
	 * myCamera.stopPreview(); myCamera.release(); myCamera = null;
	 * initCamera();//初始化相机 } };
	 */
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
	}
}