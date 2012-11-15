package hu.velorum.ConCopy.backend;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import hu.velorum.ConCopy.backend.interfaces.AbstractUpdateListener;
import hu.velorum.ConCopy.backend.tasks.PostDataTask;

/**
 * UploadService class
 *
 * @author alien_roger
 * @created at: 14.11.12 23:23
 */
public class UploadService extends Service {


	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		new PostDataTask(new PostUpdateListener()).executeTask();

		return START_STICKY_COMPATIBILITY;
	}


	private void startUpload() {
		new PostDataTask(new PostUpdateListener()).executeTask();
	}




	private class PostUpdateListener extends AbstractUpdateListener<String> {
		public PostUpdateListener() {
			super(UploadService.this);
		}

		@Override
		public void updateData(String returnedObj) {
			super.updateData(returnedObj);
			// TODO change default method
		}
	}
}
