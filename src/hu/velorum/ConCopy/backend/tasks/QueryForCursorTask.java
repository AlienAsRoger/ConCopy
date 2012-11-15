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


/*
iterate all contacts stored either in phone memory or on the SIM card.
 Read first name, last name, 1 or 2 phone numbers per contact
 + type of phone number (mobile, home, work or other + which is the default phone number to be called).
  Create a text file from these contacts: one line = one contact.

   One line = tab separated values:
   first name,
   last name,
   first phone number, type of first phone number(one char code, details to be discussed),
    second phone number (if the contact has a second phone number),type of second phone number (if applicable).

    Send text file to server: HTTP POST request, text file is
    the body of the POST request.  Server responds with a 6 character code ("access code").

    Display the access code to user on the second screen with a success message.

 */

/**
 * Used to get a much amout of data, because query is performs in UI thread
 */
public class QueryForCursorTask extends AbstractUpdateTask<Cursor, Long> {

    private ContentResolver contentResolver;

    public QueryForCursorTask(TaskUpdateInterface<Cursor> taskFace) {
        super(taskFace);
        contentResolver = taskFace.getMeContext().getContentResolver();
    }

    @Override
    protected Integer doTheTask(Long... ids) {

		// Run query
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
//		String[] projection = new String[] {
//				ContactsContract.Contacts._ID,
//				ContactsContract.Contacts.DISPLAY_NAME,
//				ContactsContract.Contacts.DISPLAY_NAME_ALTERNATIVE,
//				ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
//				ContactsContract.Contacts.DISPLAY_NAME_SOURCE
//		};
//		String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + " = '" +
//				"0" + "'";
//		String[] selectionArgs = null;
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";




		item = contentResolver.query(uri, null, null, null, sortOrder);




		Cursor dataCursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,
				new String[]{ContactsContract.Data._ID, StructuredName.GIVEN_NAME,
						StructuredName.FAMILY_NAME, StructuredName.DISPLAY_NAME
				},null,/*new String[]{String.valueOf(contactId)}*/ null, null);
		if (dataCursor.moveToFirst()) {
			String indexGivenName = StructuredName.GIVEN_NAME;
			String indexFamilyName = StructuredName.FAMILY_NAME;
			String indexDisplayName = StructuredName.DISPLAY_NAME;

			do {
				String displayName = DBDataManager.getString(dataCursor,  indexDisplayName);
				Log.d("Data", " displayName = " + displayName);
				String firstName = DBDataManager.getString(dataCursor,  indexGivenName);
				Log.d("Data", " first name = " + firstName);
				String lastName = DBDataManager.getString(dataCursor,  indexFamilyName);
				Log.d("Data", " lastName = " + lastName);

			}while (dataCursor.moveToNext());
		}

		Cursor phoneCursor = contentResolver.query(Phone.CONTENT_URI, PHONE_PROJECTION, null, null, null);

		if (phoneCursor.moveToFirst()) {

			do {
				String mobileNumber = DBDataManager.getString(phoneCursor,  Phone.NUMBER);
				Log.d("TEST", "Phone mobileNumber = " + mobileNumber);
				String mobileNumberType = DBDataManager.getString(phoneCursor,  Phone.TYPE);
				Log.d("TEST", "Phone mobileNumberType = " + mobileNumberType);
				String mobileNumberLabel = DBDataManager.getString(phoneCursor,  Phone.LABEL);
				Log.d("TEST", "Phone mobileNumberLabel = " + mobileNumberLabel);


			}while (phoneCursor.moveToNext());
		}

		Cursor rawCursor = contentResolver.query(ContactsContract.RawContacts.CONTENT_URI, null, null, null, null);

		if (rawCursor.moveToFirst()) {

			do {
				String mobileNumber = DBDataManager.getString(phoneCursor,  Phone.NUMBER);
				Log.d("TEST", "Phone mobileNumber = " + mobileNumber);
				String mobileNumberType = DBDataManager.getString(phoneCursor,  Phone.TYPE);
				Log.d("TEST", "Phone mobileNumberType = " + mobileNumberType);
				String mobileNumberLabel = DBDataManager.getString(phoneCursor,  Phone.LABEL);
				Log.d("TEST", "Phone mobileNumberLabel = " + mobileNumberLabel);


			}while (phoneCursor.moveToNext());
		}

        if (item.moveToFirst()) {

			String indexGivenName = StructuredName.GIVEN_NAME;
			String indexFamilyName = StructuredName.FAMILY_NAME;

//			do { // iterate all data
//				String displayName = DBDataManager.getString(item,  ContactsContract.Contacts.DISPLAY_NAME);
//				Log.d("TEST", "  displayName = " + displayName);
//
//			} while (item.moveToNext());

            result = StaticData.RESULT_OK;
        } else {
            result = StaticData.VALUE_NOT_EXIST;
        }
        return result;
    }


	private static final String[] PHONE_PROJECTION = new String[] {
			Phone._ID,
			Phone.TYPE,
			Phone.LABEL,
			Phone.NUMBER
	};
}
