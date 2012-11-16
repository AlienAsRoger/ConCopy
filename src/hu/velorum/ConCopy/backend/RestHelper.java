package hu.velorum.ConCopy.backend;

import hu.velorum.ConCopy.backend.entity.LoadItem;
import hu.velorum.ConCopy.backend.statics.StaticData;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * RestHelper class
 *
 * @author alien_roger
 * @created at: 14.03.12 5:47
 */
public class RestHelper {

	/* Results */
	public static final String VERSION = "version:3";
	public static final String BASE_URL = "https://velorum-1.appspot.com/upload";


	/* Parameters */
	public static final String P_USER_NAME = "username";


	/* Returned Values */
	public static final String R_ERROR_MESSAGE = "error_message";


	/* Values */
	public static final String V_RESIGN = "RESIGN";

	public static final String V_ZERO = "0";
	public static final String V_ONE = "1";

	//private static final String TAG = "Encode";
	public static final int MAX_ITEMS_CNT = 2000;

	private static final String Q_ = "?";
	private static final String EQUALS = "=";
	private static final String AND = "&";


	public static String formCustomRequest(LoadItem loadItem) {

		String fullUrl = formUrl(loadItem.getRequestParams());
		return loadItem.getLoadPath() + fullUrl;
	}

	public static String formPostRequest(LoadItem loadItem) {
		return loadItem.getLoadPath();
	}

	private static String formUrl(List<NameValuePair> nameValuePairs) {
		List<NameValuePair> safeList = new ArrayList<NameValuePair>();
		safeList.addAll(nameValuePairs);
		StringBuilder builder = new StringBuilder();
		builder.append(Q_);
		String separator = StaticData.SYMBOL_EMPTY;
		for (NameValuePair pair : safeList) {
			builder.append(separator);
			separator = AND;
			builder.append(pair.getName()).append(EQUALS).append(pair.getValue());
		}
		return builder.toString();
	}

	public static String formJsonData(List<NameValuePair> requestParams) {
		StringBuilder data = new StringBuilder();
		String separator = StaticData.SYMBOL_EMPTY;
		data.append("{");
		for (NameValuePair requestParam : requestParams) {

			data.append(separator);
			separator = StaticData.SYMBOL_COMMA;
			data.append("\"")
					.append(requestParam.getName()).append("\"")
					.append(":")
					.append("\"")
					.append(requestParam.getValue())
					.append("\"");
		}
		data.append("}");
		return data.toString();
	}

}
