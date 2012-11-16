package hu.velorum.ConCopy.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import hu.velorum.ConCopy.R;
import hu.velorum.ConCopy.backend.UploadService;
import hu.velorum.ConCopy.backend.entity.ContactItem;
import hu.velorum.ConCopy.backend.entity.QueryParams;
import hu.velorum.ConCopy.backend.interfaces.AbstractUpdateListener;
import hu.velorum.ConCopy.backend.tasks.GetDetailsFromContactsTask;

import java.util.ArrayList;
import java.util.List;

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
		QueryParams params = new QueryParams();
		params.setUri(ContactsContract.Contacts.CONTENT_URI);

		new GetDetailsFromContactsTask(new DbUpdateListener(), params, new ArrayList<ContactItem>()).executeTask();
	}

	private class DbUpdateListener extends AbstractUpdateListener<Cursor> implements ContactItemGetFace<ContactItem, Cursor> {
		public static final String TAB_SYMBOL = "\u0009";

		public DbUpdateListener() {
			super(UploadActivity.this);
		}

		@Override
		public void updateContacts(List<ContactItem> itemsList) {


			Log.d("OUT", getString(R.string.version_number));
			for (ContactItem contactItem : itemsList) {
				Log.d("OUT", "" + contactItem.getOutputLine());
			}

//			updateList(itemsList);
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