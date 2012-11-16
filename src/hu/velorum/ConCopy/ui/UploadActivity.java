package hu.velorum.ConCopy.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import hu.velorum.ConCopy.R;
import hu.velorum.ConCopy.backend.UploadService;

/**
 * UploadActivity class
 *
 * @author alien_roger
 * @created at: 14.11.12 22:22
 */
public class UploadActivity extends Activity implements View.OnClickListener, UploadFace {

	private boolean isBound;
	private Button uploadBtn;
	private UploadService uploadService;
	private Context context;
	private TextView uploadTxt;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_screen);

		context = this;

		widgetsInit();
	}


	@Override
	protected void onStart() {
		super.onStart();
		bindService(new Intent(context, UploadService.class), mConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.uploadBtn) {
			uploadService.startUpload();
		}
	}


	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			// This is called when the connection with the service has been
			// established, giving us the service object we can use to
			// interact with the service.  Because we have bound to a explicit
			// service that we know is running in our own process, we can
			// cast its IBinder to a concrete class and directly access it.
			uploadService = ((UploadService.LocalService) service).getService();
			uploadService.setUploadInterface((UploadFace) context);
			isBound = true;
			uploadBtn.setEnabled(true);
		}

		public void onServiceDisconnected(ComponentName className) {
			// This is called when the connection with the service has been
			// unexpectedly disconnected -- that is, its process crashed.
			// Because it is running in our same process, we should never
			// see this happen.
			uploadService = null;
		}
	};


	void doUnbindService() {
		if (isBound) {
			// Detach our existing connection.
			unbindService(mConnection);
			isBound = false;
		}
	}

	private void widgetsInit() {
		uploadBtn = (Button) findViewById(R.id.uploadBtn);
		uploadBtn.setOnClickListener(this);
		uploadBtn.setEnabled(false);

		uploadTxt = (TextView) findViewById(R.id.uploadTxt);
	}

	@Override
	public void onProgressUpdated(final int current, int total) {
		Log.d("TEST", " progress " + current);
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (context != null) {
					uploadTxt.setText(" progress % " + current);
				}
			}
		});
	}

	@Override
	public void onUploadFinished(final String result) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (context != null) {
					uploadTxt.setText(" your id = " + result);
				}
			}
		});
	}

	@Override
	public boolean exist() {
		return context != null;
	}
}