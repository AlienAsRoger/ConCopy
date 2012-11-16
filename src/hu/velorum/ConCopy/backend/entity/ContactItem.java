package hu.velorum.ConCopy.backend.entity;

import android.text.TextUtils;
import android.util.Log;
import hu.velorum.ConCopy.backend.statics.StaticData;

/**
 * FBContactItem class
 *
 * @author alien_roger
 * @created at: 21.08.12 22:41
 */
public class ContactItem {

	public static final String TAB_SYMBOL = "\u0009";

	private String firstName = StaticData.SYMBOL_EMPTY;
	private String lastName = StaticData.SYMBOL_EMPTY;
	private String email = StaticData.SYMBOL_EMPTY;
	private PhoneItem phoneItemOne;
	private PhoneItem phoneItemTwo;

	public void setFirstName(String firstName) {
		this.firstName = firstName == null ? StaticData.SYMBOL_EMPTY : firstName;
	}

	public void setEmail(String email) {
		this.email = email == null ? StaticData.SYMBOL_EMPTY : email;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName == null ? StaticData.SYMBOL_EMPTY : lastName;
	}

	public void setPhoneItemOne(PhoneItem phoneItemOne) {
		this.phoneItemOne = phoneItemOne;
	}



	/*
			One line = tab separated values:
first name,
last name,
first phone number, type of first phone number(one char code, details to be discussed),
second phone number (if the contact has a second phone number),type of second phone number (if applicable).

			 */

	public String getOutputLine() {

		String requiredPart = firstName + TAB_SYMBOL + lastName + TAB_SYMBOL;
		if (phoneItemOne != null && !TextUtils.isEmpty(phoneItemOne.getPhone())) {
			requiredPart +=  phoneItemOne.getPhone() + TAB_SYMBOL + phoneItemOne.getType();
		}
		String optionalPart = StaticData.SYMBOL_EMPTY;
		if (phoneItemTwo != null && !TextUtils.isEmpty(phoneItemTwo.getPhone())) {
			optionalPart = TAB_SYMBOL + phoneItemTwo.getPhone() + TAB_SYMBOL + phoneItemTwo.getType();
		}
		String result = requiredPart + optionalPart + TAB_SYMBOL + email + StaticData.SYMBOL_NEW_STR;
		Log.d("TEST", " result = " +result);
		return result;
	}

	public void setPhoneItemTwo(PhoneItem phoneItemTwo) {
		this.phoneItemTwo = phoneItemTwo;
	}
}
