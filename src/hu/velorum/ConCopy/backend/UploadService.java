package hu.velorum.ConCopy.backend;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.IBinder;
import android.provider.ContactsContract;
import hu.velorum.ConCopy.R;
import hu.velorum.ConCopy.backend.entity.ContactItem;
import hu.velorum.ConCopy.backend.entity.LoadItem;
import hu.velorum.ConCopy.backend.entity.QueryParams;
import hu.velorum.ConCopy.backend.interfaces.AbstractUpdateListener;
import hu.velorum.ConCopy.backend.statics.StaticData;
import hu.velorum.ConCopy.backend.tasks.GetDetailsFromContactsTask;
import hu.velorum.ConCopy.backend.tasks.PostDataTask;
import hu.velorum.ConCopy.ui.ContactItemGetFace;
import hu.velorum.ConCopy.ui.UploadFace;

import java.util.ArrayList;
import java.util.List;

/**
 * UploadService class
 *
 * @author alien_roger
 * @created at: 14.11.12 23:23
 */
public class UploadService extends Service {


	private final Binder binder = new LocalService();
	private UploadFace uploadInterface;

	public void setUploadInterface(UploadFace uploadInterface) {
		this.uploadInterface = uploadInterface;
	}

	public class LocalService extends Binder {
		public UploadService getService() {
			return UploadService.this;
		}
	}

	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY_COMPATIBILITY;
	}


	public void startUpload() {
		getContacts();
	}

	private void getContacts() {
		QueryParams params = new QueryParams();
		params.setUri(ContactsContract.Contacts.CONTENT_URI);

		new GetDetailsFromContactsTask(new DbUpdateListener(), params, new ArrayList<ContactItem>()).executeTask();
	}

	private class DbUpdateListener extends AbstractUpdateListener<Cursor> implements ContactItemGetFace<ContactItem, Cursor>, UploadFace {
		public DbUpdateListener() {
			super(UploadService.this);
		}

		@Override
		public void updateContacts(List<ContactItem> itemsList) {
			StringBuilder builder = new StringBuilder();
			builder.append(getString(R.string.version_number)).append(StaticData.SYMBOL_NEW_STR);
			for (ContactItem contactItem : itemsList) {
				builder.append(contactItem.getOutputLine());
			}

			LoadItem loadItem = new LoadItem();
			loadItem.setLoadPath(RestHelper.BASE_URL);
			loadItem.setPostEntity(builder.toString());
			new PostDataTask(new PostDataListener()).executeTask(loadItem);
		}

		@Override
		public void onProgressUpdated(int current, int total) {
			uploadInterface.onProgressUpdated(current, total);
			// TODO change body of implemented methods use File | Settings | File Templates.
		}

		@Override
		public void onUploadFinished(int result) {
			// TODO change body of implemented methods use File | Settings | File Templates.
		}
	}

	private class PostDataListener extends AbstractUpdateListener<String> {
		public PostDataListener() {
			super(UploadService.this);
		}

		@Override
		public void updateData(String returnedObj) {
			super.updateData(returnedObj);

		}
	}


}

