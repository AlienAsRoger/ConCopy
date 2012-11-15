package hu.velorum.ConCopy.backend.entity;

import android.text.TextUtils;
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
	private String email;
	private PhoneItem phoneItemOne;
	private PhoneItem phoneItemTwo;

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public PhoneItem getPhoneItemOne() {
		return phoneItemOne;
	}

	public void setPhoneItemOne(PhoneItem phoneItemOne) {
		this.phoneItemOne = phoneItemOne;
	}

	public PhoneItem getPhoneItemTwo() {
		return phoneItemTwo;
	}

	public void setPhoneItemTwo(PhoneItem phoneItemTwo) {
		this.phoneItemTwo = phoneItemTwo;
	}


	/*
			One line = tab separated values:
first name,
last name,
first phone number, type of first phone number(one char code, details to be discussed),
second phone number (if the contact has a second phone number),type of second phone number (if applicable).

			 */

	public String getOutputLine() {

		String requiredPart = StaticData.SYMBOL_EMPTY;
		if (phoneItemOne != null && !TextUtils.isEmpty(phoneItemOne.getPhone())) {
			requiredPart = firstName + TAB_SYMBOL + lastName
				+ TAB_SYMBOL + phoneItemOne.getPhone() + TAB_SYMBOL + phoneItemOne.getType();
		}
		String optionalPart = StaticData.SYMBOL_EMPTY;
		if (phoneItemTwo != null && !TextUtils.isEmpty(phoneItemTwo.getPhone())) {
			optionalPart = TAB_SYMBOL + phoneItemOne.getPhone() + TAB_SYMBOL + phoneItemOne.getType();
		}
		return requiredPart + optionalPart;
	}

}
