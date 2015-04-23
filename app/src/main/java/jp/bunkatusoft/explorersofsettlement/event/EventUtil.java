package jp.bunkatusoft.explorersofsettlement.event;

import android.content.Context;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.util.Util;

public class EventUtil {

	public static List<Event> loadEventsJSONData(Context context, String filePath) throws IOException {
		return new ObjectMapper().readValue(Util.getAssetsJSONText(context, filePath),
				new TypeReference<ArrayList<Event>>() {});
	}
}
