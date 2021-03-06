package hu.velorum.ConCopy.ui;

import actionbarcompat.ActionBarActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import hu.velorum.ConCopy.R;
import hu.velorum.ConCopy.backend.UploadService;
import hu.velorum.ConCopy.backend.statics.StaticData;
import hu.velorum.ConCopy.utils.AppUtils;

/**
 * UploadActivity class
 *
 * @author alien_roger
 * @created at: 14.11.12 22:22
 */
public class UploadActivity extends ActionBarActivity implements View.OnClickListener, UploadFace {

	private boolean isBound;
	private Button uploadBtn;
	private UploadService uploadService;
	private Context context;
	private TextView uploadTxt;
	private ProgressBar uploadProgress;
	private TextView docIdTxt;
	private TextView infoTxt;

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
			if(!AppUtils.checkNetworkSettings(this)){
				return;
			}

			infoTxt.setVisibility(View.INVISIBLE);

			uploadBtn.setVisibility(View.INVISIBLE);

			uploadTxt.setVisibility(View.VISIBLE);
			uploadTxt.setText(R.string.collecting_contacts);

			uploadProgress.setVisibility(View.VISIBLE);

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

		infoTxt = (TextView) findViewById(R.id.infoTxt);
		uploadTxt = (TextView) findViewById(R.id.uploadTxt);
		docIdTxt = (TextView) findViewById(R.id.docIdTxt);

		uploadProgress = (ProgressBar) findViewById(R.id.uploadProgress);
	}

	@Override
	public void onProgressUpdated(final int current, final int total) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {

				if (context != null) {
					uploadBtn.setVisibility(View.GONE);

					uploadProgress.setVisibility(View.VISIBLE);

					uploadTxt.setVisibility(View.VISIBLE);
					uploadTxt.setText(getString(R.string.contacts_parsed, current));
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
					uploadProgress.setVisibility(View.GONE);

					uploadTxt.setText(getString(R.string.upload_finished));

					docIdTxt.setVisibility(View.VISIBLE);
					docIdTxt.setText(getString(R.string.your_id, result));
				}
			}
		});
	}

	@Override
	public boolean exist() {
		return context != null;
	}

	@Override
	public void onUploading() {
		if (context != null) {
			uploadTxt.setText(getString(R.string.loading_to_server));
		}
	}

	@Override
	public void onError(int code) {
		if (context != null){
			uploadProgress.setVisibility(View.GONE);

			switch (code) {
				case StaticData.VALUE_DOESNT_EXIST:
					uploadTxt.setText(getString(R.string.no_contacts_found));
					break;
				case StaticData.NO_NETWORK:
					AppUtils.showNoNetworkDialog(this);

					uploadTxt.setText(R.string.network_unreachable);
					break;
			}

		}

	}
}