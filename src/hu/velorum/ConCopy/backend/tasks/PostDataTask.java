package hu.velorum.ConCopy.backend.tasks;

import android.util.Log;
import hu.velorum.ConCopy.backend.RestHelper;
import hu.velorum.ConCopy.backend.entity.LoadItem;
import hu.velorum.ConCopy.backend.interfaces.TaskUpdateInterface;
import hu.velorum.ConCopy.backend.statics.StaticData;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class PostDataTask extends AbstractUpdateTask<String, LoadItem> {

    private static final String TAG = "PostDataTask";

	public PostDataTask(TaskUpdateInterface<String> taskFace) {
		super(taskFace);
	}

	@Override
	protected Integer doTheTask(LoadItem... loadItem) {

		String url = RestHelper.formPostRequest(loadItem[0]);
        result = postData(url, loadItem[0]);
		return result;
	}


	private int postData(String url, LoadItem loadItem) {
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);
		HttpConnectionParams.setSoTimeout(httpParameters, Integer.MAX_VALUE);

		HttpClient httpClient = new DefaultHttpClient(httpParameters);

		Log.d(TAG, "retrieving from url = " + url);

		HttpPost httpPost = new HttpPost(url);
		try {
			final byte[] utf8Bytes = loadItem.getPostEntity().getBytes("UTF-8");
			final String length = String.valueOf(utf8Bytes.length);

			httpPost.addHeader("Content-Type", "text/plain; charset=utf-8");
			httpPost.addHeader("Content-Length", length);  // in bytes

			httpPost.setEntity(new StringEntity(loadItem.getPostEntity(), HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			Log.d(TAG, e.toString());
		}

		try {
			HttpResponse response = httpClient.execute(httpPost);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.e(TAG, "Error " + statusCode + " while retrieving data from " + url);
				return StaticData.UNKNOWN_ERROR;
			}
			if (response != null){
				item = EntityUtils.toString(response.getEntity());
				result = StaticData.RESULT_OK;
				Log.d(TAG,"WebRequest SERVER RESPONSE: " + item);
			}

		} catch (IOException e) {
			httpPost.abort();
			Log.e(TAG, "I/O error while retrieving data from " + url, e);
			result = StaticData.UNKNOWN_ERROR;
		} catch (IllegalStateException e) {
			httpPost.abort();
			Log.e(TAG, "Incorrect URL: " + url, e);
			result = StaticData.UNKNOWN_ERROR;
		} catch (Exception e) {
			httpPost.abort();
			Log.e(TAG, "Error while retrieving data from " + url, e);
			result = StaticData.UNKNOWN_ERROR;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return result;
	}

	@Override
	protected void onPostExecute(Integer result) {
		blockScreenRotation(false);

		if(isCancelled()) {
			return;
		}
		taskFace.showProgress(false);
		if (result == StaticData.RESULT_OK) {
			if (item.contains(RestHelper.R_SUCCESS)) {
				taskFace.updateData(item);
			} else if (item.contains(RestHelper.R_ERROR)) {
				taskFace.errorHandle(item.substring(RestHelper.R_ERROR.length()));
			}

		} else {
			taskFace.errorHandle(result);
		}
	}

}
