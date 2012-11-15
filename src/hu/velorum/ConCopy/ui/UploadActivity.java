package hu.velorum.ConCopy.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import hu.velorum.ConCopy.R;
import hu.velorum.ConCopy.backend.UploadService;
import hu.velorum.ConCopy.backend.interfaces.AbstractUpdateListener;
import hu.velorum.ConCopy.backend.tasks.QueryForCursorTask;

/**
 * UploadActivity class
 *
 * @author alien_roger
 * @created at: 14.11.12 22:22
 */
public class UploadActivity extends Activity implements View.OnClickListener {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_screen);

		findViewById(R.id.uploadBtn).setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		getContacts();

	}

	private void getContacts(){
		new QueryForCursorTask(new ContactsCursorUpdateListener()).executeTask();
	}

	private class ContactsCursorUpdateListener extends AbstractUpdateListener<Cursor> {
		public ContactsCursorUpdateListener() {
			super(UploadActivity.this);
		}

		@Override
		public void updateData(Cursor returnedObj) {
			super.updateData(returnedObj);

		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.uploadBtn) {
			startUpload();
		}
	}

	private void startUpload() {
		startService(new Intent(this, UploadService.class));
	}

}