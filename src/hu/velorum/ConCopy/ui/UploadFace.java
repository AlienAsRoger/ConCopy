package hu.velorum.ConCopy.ui;

/**
 * @author developer4Droid (developer4droid@gmail.com)
 * @created 16.11.12
 * @modified 16.11.12
 */
public interface UploadFace {

	void onProgressUpdated(int current, int total);

	void onUploadFinished(String result);

	boolean exist();

	void onUploading();
}
