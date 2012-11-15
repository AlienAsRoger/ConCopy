package hu.velorum.ConCopy.backend.entity;

import hu.velorum.ConCopy.backend.statics.StaticData;

/**
 * PhoneItem class
 *
 * @author alien_roger
 * @created at: 15.11.12 23:54
 */
public class PhoneItem {
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

	public void setType(String type) {
		this.type = type;
	}
}