package hu.velorum.ConCopy.backend.tasks;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import hu.velorum.ConCopy.backend.interfaces.TaskUpdateInterface;
import hu.velorum.ConCopy.backend.statics.DBDataManager;
import hu.velorum.ConCopy.backend.statics.StaticData;

import static android.provider.ContactsContract.CommonDataKinds.Phone;
import static android.provider.ContactsContract.CommonDataKinds.StructuredName;




/**
 * Used to get a much amout of data, because query is performs in UI thread
 */
public class QueryForCursorTask2 extends AbstractUpdateTask<Cursor, Long> {

	private ContentResolver contentResolver;

	public QueryForCursorTask2(TaskUpdateInterface<Cursor> taskFace) {
		super(taskFace);
		contentResolver = taskFace.getMeContext().getContentResolver();
	}

	@Override
	protected Integer doTheTask(Long... ids) {

		// Run query
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		String[] projection = new String[] {
				ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME
		};

		item = contentResolver.query(uri, null, null, null, null);
		String lookup = "";
		if (item.moveToFirst()) {

			do { // iterate all data
				String displayName = DBDataManager.getString(item, ContactsContract.Contacts.DISPLAY_NAME);
				lookup = DBDataManager.getString(item, "lookup");
				Log.d("Contact", "  displayName = " + displayName);
				Log.d("Contact", "  lookup = " + lookup);

			} while (item.moveToNext());
		}


		Cursor dataCursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,
				new String[]{ContactsContract.Data._ID, StructuredName.GIVEN_NAME,
						StructuredName.FAMILY_NAME, StructuredName.DISPLAY_NAME
				}, null, null, null);

		if (dataCursor.moveToFirst()) {
			String indexGivenName = StructuredName.GIVEN_NAME;
			String indexFamilyName = StructuredName.FAMILY_NAME;

			do {
				String firstName = DBDataManager.getString(dataCursor, indexGivenName);
				Log.d("Data", " first name = " + firstName);
				String lastName = DBDataManager.getString(dataCursor, indexFamilyName);
				Log.d("Data", " lastName = " + lastName);

			} while (dataCursor.moveToNext());
		}

		Cursor phoneCursor = contentResolver.query(Phone.CONTENT_URI, null, null, null, null);

		if (phoneCursor.moveToFirst()) {

			do {
				String mobileNumber = DBDataManager.getString(phoneCursor, Phone.NUMBER);
				Log.d("TEST", "Phone mobileNumber = " + mobileNumber);
				String mobileNumberType = DBDataManager.getString(phoneCursor, Phone.TYPE);
				Log.d("TEST", "Phone mobileNumberType = " + mobileNumberType);
				String mobileNumberLabel = DBDataManager.getString(phoneCursor, Phone.LABEL);
				Log.d("TEST", "Phone mobileNumberLabel = " + mobileNumberLabel);


			} while (phoneCursor.moveToNext());
		}


		result = StaticData.RESULT_OK;

		return result;
	}


	private static final String[] PHONE_PROJECTION = new String[]{
			Phone._ID,
			Phone.TYPE,
			Phone.LABEL,
			Phone.NUMBER
	};
}
