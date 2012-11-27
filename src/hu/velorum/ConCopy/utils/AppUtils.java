package hu.velorum.ConCopy.utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import hu.velorum.ConCopy.R;

/**
 * @author alirn_roger
 * @created 27.11.12
 * @modified 27.11.12
 */
public class AppUtils {
	public static final int NETWORK_REQUEST = 55;

	public static boolean checkNetworkSettings(final Activity activity) {

		ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();


		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else {
			showNoNetworkDialog(activity);
			return false;
		}
	}

	public static void showNoNetworkDialog(final Activity activity) {
		if (activity == null || activity.isFinishing()) {
			return;
		}
		new AlertDialog.Builder(activity)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(R.string.warning)
				.setMessage(R.string.no_network)
				.setPositiveButton(R.string.wireless_settings, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dlg, final int sumthin) {
						activity.startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS), NETWORK_REQUEST);
					}
				}).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(final DialogInterface dlg, final int sumthin) {

			}
		}).show();
	}
}
