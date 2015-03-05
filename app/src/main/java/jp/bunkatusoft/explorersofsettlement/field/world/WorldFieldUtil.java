package jp.bunkatusoft.explorersofsettlement.field.world;

import android.content.Context;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jp.bunkatusoft.explorersofsettlement.util.Util;

public class WorldFieldUtil {
	/**
	 * フィールドのチップデータを読み込むメソッド
	 * @return	読み込んだチップデータ
	 */
	public static ArrayList<FieldPiece> loadFieldPieceData(Context context, String filePath) throws IOException {
		return new ObjectMapper().readValue(Util.getAssetsJSONText(context, filePath),
				new TypeReference<ArrayList<FieldPiece>>() {});
	}

	public static ArrayList<FieldRoad> loadFieldRoadData(Context context, String filePath) throws IOException {
		return new ObjectMapper().readValue(Util.getAssetsJSONText(context, filePath),
				new TypeReference<ArrayList<FieldRoad>>() {});
	}
}
