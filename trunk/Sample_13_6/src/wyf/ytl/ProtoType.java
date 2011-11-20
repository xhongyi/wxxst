package wyf.ytl; //���������

import android.app.Activity; //���������
import android.app.AlertDialog; //���������
import android.app.Dialog; //���������
import android.app.AlertDialog.Builder; //���������
import android.content.DialogInterface; //���������
import android.content.Intent;
import android.content.DialogInterface.OnClickListener; //���������
import android.os.Bundle; //���������
import android.view.View; //���������
import android.widget.Button; //���������						
import android.widget.EditText; //���������

public class ProtoType extends Activity {
	final int COMMON_DIALOG = 1; // ��ͨ�Ի���id

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main); // ���õ�ǰ��Ļ
		Button btn = (Button) findViewById(R.id.Button01); // ���Button����
		Button btn_search = (Button) findViewById(R.id.Button02);
		Button btn_about = (Button) findViewById(R.id.Button04);
		Button btn_exit = (Button) findViewById(R.id.Button05);
		final EditText edt = (EditText) findViewById(R.id.EditText01);
		btn.setOnClickListener(new View.OnClickListener() { // ΪButton����OnClickListener������

			public void onClick(View v) { // ��дonClick����
				// showDialog(COMMON_DIALOG);
				Intent intent = new Intent();
				intent.setClass(ProtoType.this, CameraActivity.class);
				startActivity(intent);
				// ProtoType.this.finish();
			}
		});
		btn_search.setOnClickListener(new View.OnClickListener() { // ΪButton����OnClickListener������

					public void onClick(View v) { // ��дonClick����
						// showDialog(COMMON_DIALOG);
						Intent intent = new Intent();
						intent.setClass(ProtoType.this, Events.class);
						Bundle bundle = new Bundle();
						String word = edt.getText().toString();
						bundle.putString("searchword", word);
						intent.putExtras(bundle);
						startActivityForResult(intent, 0);
						// ProtoType.this.finish();
					}
				});
		btn_about.setOnClickListener(new View.OnClickListener() { // ΪButton����OnClickListener������
					public void onClick(View v) { // ��дonClick����
						showDialog(2); // ��ʾ��ͨ�Ի���
					}
				});
		btn_exit.setOnClickListener(new View.OnClickListener() { // ΪButton����OnClickListener������
			public void onClick(View v) { // ��дonClick����
				System.exit(0);
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) { // ��дonCreateDialog����
		Dialog dialog = null; // ����һ��Dialog�������ڷ���
		switch (id) { // ��id�����ж�
		case COMMON_DIALOG:
			Builder b = new AlertDialog.Builder(this);
			b.setIcon(R.drawable.header); // ���öԻ����ͼ��
			b.setTitle(R.string.btn); // ���öԻ���ı���
			b.setMessage(R.string.dialog_msg); // ���öԻ������ʾ����
			b.setPositiveButton( // ��Ӱ�ť
					R.string.ok, new OnClickListener() { // Ϊ��ť��Ӽ�����
						public void onClick(DialogInterface dialog, int which) {
							EditText et = (EditText) findViewById(R.id.EditText01);
							et.setText(R.string.dialog_msg);// ����EditText����
						}
					});
			dialog = b.create(); // ����Dialog����
			break;
		case 2:
			Builder b2 = new AlertDialog.Builder(this);
			b2.setIcon(R.drawable.header); // ���öԻ����ͼ��
			b2.setTitle(R.string.btn4); // ���öԻ���ı���
			b2.setMessage(R.string.about_msg); // ���öԻ������ʾ����
			b2.setPositiveButton( // ��Ӱ�ť
					R.string.ok2, new OnClickListener() { // Ϊ��ť��Ӽ�����
						public void onClick(DialogInterface dialog, int which) {

						}
					});
			dialog = b2.create(); // ����Dialog����
			break;
		default:
			break;
		}
		return dialog; // ��������Dialog�Ķ���
	}

}