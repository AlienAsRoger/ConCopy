package hu.velorum.ConCopy.backend.entity;

import hu.velorum.ConCopy.backend.statics.StaticData;

import static android.provider.ContactsContract.CommonDataKinds.Phone;

/**
 * PhoneItem class
 *
 * @author alien_roger
 * @created at: 15.11.12 23:54
 */
public class PhoneItem {
	/*
	Mobile number -> 'm'
	Home number -> 'h'
	Work (or office) number -> 'w'
	Other number -> 'o'
	Preferred (default) number -> 'p'
	One phone number might be preferred and another type at the same time
	(so "pm" is a default, mobile number),
	but two "non-preferred" numbers never can appear together (e.g. "wh" is invalid)
	*/

	public static String TYPE_MOBILE = "m";
	public static String TYPE_HOME 	= "h";
	public static String TYPE_WORK = "w";
	public static String TYPE_OTHER = "o";
	public static String PRIMARY = "p";

	private String phone = StaticData.SYMBOL_EMPTY;
	private String type = StaticData.SYMBOL_EMPTY;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getType() {
		return type;
	}

	public void setType(int origType, boolean primary) {
		switch (origType) {
			case Phone.TYPE_MOBILE: type = TYPE_MOBILE; break;
			case Phone.TYPE_HOME: type = TYPE_HOME; break;
			case Phone.TYPE_WORK: type = TYPE_WORK; break;
			case Phone.TYPE_OTHER: type = TYPE_OTHER; break;
		}

		if (primary) {
			type = PRIMARY + type;
		}
	}
}