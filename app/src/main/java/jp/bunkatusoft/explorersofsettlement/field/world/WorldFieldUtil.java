package jp.bunkatusoft.explorersofsettlement.field.world;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jp.bunkatusoft.explorersofsettlement.util.Util;

/**
 * Created by m_kagaya on 2015/01/27.
 */
public class WorldFieldUtil {

	/** JSONキー フィールドチップデータ */
	public static final String KEY_TIP_HEADER = "pieces";
	public static final String KEY_TIP_ID = "id";
	public static final String KEY_TIP_X = "X";
	public static final String KEY_TIP_Y = "Y";
	public static final String KEY_TIP_TYPE = "Type";
	public static final String KEY_TIP_CONNECTS = "connects";
	public static final String KEY_TIP_CONNECTS_ID = "id";
	/** JSONキー フィールドルートデータ */
	public static final String KEY_ROAD_HEADER = "roads";
	public static final String KEY_ROAD_ID = "id";
	public static final String KEY_ROAD_CONNECT_A = "connectA";
	public static final String KEY_ROAD_CONNECT_B = "connectB";
	public static final String KEY_ROAD_DIRECTION = "direction";

	/**
	 * フィールドのチップデータを読み込むメソッド
	 * @return	読み込んだチップデータ
	 */
	public static ArrayList<FieldPiece> loadFieldPieceData(Context context,String filePath){
		ArrayList<FieldPiece> resultPieces = new ArrayList<FieldPiece>();
		try {
			JSONObject rawJSONObject = new JSONObject(Util.getAssetsJSONText(context, filePath));
			JSONArray rawJSONArray = rawJSONObject.getJSONArray(KEY_TIP_HEADER);
			for(int i=0;i<rawJSONArray.length();i++){
				FieldPiece buffPiece = new FieldPiece();
				JSONObject elements = rawJSONArray.getJSONObject(i);
				if(elements.has(KEY_TIP_ID)){
					buffPiece.id = elements.getInt(KEY_TIP_ID);
				}
				if(elements.has(KEY_TIP_X)){
					buffPiece.x = elements.getInt(KEY_TIP_X);
				}
				if(elements.has(KEY_TIP_Y)){
					buffPiece.y = elements.getInt(KEY_TIP_Y);
				}
				if(elements.has(KEY_TIP_TYPE)) {
					buffPiece.type = PieceTypeEnum.valueOf(elements.getInt(KEY_TIP_TYPE));
				}
				if(elements.has(KEY_TIP_CONNECTS)){
					buffPiece.connects = new ArrayList<Integer>();
					JSONArray connectArray = elements.getJSONArray(KEY_TIP_CONNECTS);
					for(int j=0;j<connectArray.length();j++){
						JSONObject innerElements = connectArray.getJSONObject(j);
						if(innerElements.has(KEY_TIP_CONNECTS_ID)){
							buffPiece.connects.add(elements.getInt(KEY_TIP_CONNECTS_ID));
						}
					}
				}
				//確定して追加
				resultPieces.add(buffPiece);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return resultPieces;
	}

	public static ArrayList<FieldRoad> loadFieldRoadData(Context context, String filePath){
		ArrayList<FieldRoad> resultRoads = new ArrayList<FieldRoad>();
		try {
			JSONObject rawJSONObject = new JSONObject(Util.getAssetsJSONText(context,filePath));
			JSONArray rawJSONArray = rawJSONObject.getJSONArray(KEY_ROAD_HEADER);
			for(int i=0;i<rawJSONArray.length();i++){
				FieldRoad buffRoad = new FieldRoad();
				JSONObject elements = rawJSONArray.getJSONObject(i);
				if(elements.has(KEY_ROAD_ID)){
					buffRoad.id = elements.getInt(KEY_ROAD_ID);
				}
				if(elements.has(KEY_ROAD_CONNECT_A)){
					buffRoad.connectA = elements.getInt(KEY_ROAD_CONNECT_A);
				}
				if(elements.has(KEY_ROAD_CONNECT_B)){
					buffRoad.connectB = elements.getInt(KEY_ROAD_CONNECT_B);
				}
				if(elements.has(KEY_ROAD_DIRECTION)){
					buffRoad.directivity = getRoadDirection(elements.getString(KEY_ROAD_DIRECTION));
				}
				//確定して追加
				resultRoads.add(buffRoad);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return resultRoads;
	}

	//TODO Enum化
	public static int getRoadDirection(String direction){
		if("mutual".equals(direction)){
			return 0;
		} else if("AtoB".equals(direction)){
			return 1;
		} else if("BtoA".equals(direction)){
			return 2;
		} else {
			return -1;
		}
	}
}
