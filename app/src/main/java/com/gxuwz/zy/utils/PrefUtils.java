package com.gxuwz.zy.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.gxuwz.zy.constants.App;

import java.util.ArrayList;
import java.util.List;

/**
 * 专门访问和设置SharePreference的工具类, 保存和配置一些设置信息
 * SharedPreferences是一种轻量级的数据存储方式，
 * 适用于保存简单的键值对数据，如应用的配置信息、用户偏好设置等。
 */
public class PrefUtils {


	private static final String SHARE_PREFS_NAME = "xueyiche_new";

	public static void putBoolean(Context ctx, String key, boolean value) {
		SharedPreferences pref =  ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);

		pref.edit().putBoolean(key, value).commit();
	}

	public static boolean getBoolean(Context ctx, String key,
			boolean defaultValue) {
		SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);

		return pref.getBoolean(key, defaultValue);
	}

	public static void putString(Context ctx, String key, String value) {
		SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);

		pref.edit().putString(key, value).commit();
	}

	public static String getString(Context ctx, String key, String defaultValue) {
		SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);

		return pref.getString(key, defaultValue);
	}

	public static void putInt(Context ctx, String key, int value) {
		SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);

		pref.edit().putInt(key, value).commit();
	}

	public static int getInt(Context ctx, String key, int defaultValue) {
		SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);

		return pref.getInt(key, defaultValue);
	}

	public static String getParameter(String parameter) {
		String string = PrefUtils.getString(App.context, parameter, "");
		return string;
	}
	public static void putParameter(String key,String value) {
		PrefUtils.putString(App.context, key,value);
	}


	/**
	 * 存储List<String>
	 *
	 * @param context
	 * @param key
	 *            List<String>对应的key
	 * @param strList
	 *            对应需要存储的List<String>
	 */
	public static void putStrListValue(Context context, String key,
									   List<String> strList) {
		if (null == strList) {
			return;
		}
		// 保存之前先清理已经存在的数据，保证数据的唯一性
		removeStrList(context, key);
		int size = strList.size();
		putInt(context, key + "size", size);
		for (int i = 0; i < size; i++) {
			putString(context, key + i, strList.get(i));
		}
	}

	/**
	 * 取出List<String>
	 *
	 * @param context
	 * @param key
	 *            List<String> 对应的key
	 * @return List<String>
	 */
	public static List<String> getStrListValue(Context context, String key) {
		List<String> strList = new ArrayList<String>();
		int size = getInt(context, key + "size", 0);
		for (int i = 0; i < size; i++) {
			strList.add(getString(context, key + i, null));
		}
		return strList;
	}
	public static void removeStrList(Context context, String key) {
		int size = getInt(context, key + "size", 0);
		if (0 == size) {
			return;
		}
		remove(context, key + "size");
		for (int i = 0; i < size; i++) {
			remove(context, key + i);
		}
	}
	/**
	 * 清空对应key数据
	 *
	 * @param context
	 * @param key
	 */
	public static void remove(Context context, String key) {
		SharedPreferences.Editor sp = context.getSharedPreferences(SHARE_PREFS_NAME, Context.MODE_PRIVATE)
				.edit();
		sp.remove(key);
		sp.commit();
	}

	/**
	 * 清空所有数据
	 *
	 * @param context
	 */
	public static void clear(Context context) {
		SharedPreferences.Editor sp = context.getSharedPreferences(SHARE_PREFS_NAME, Context.MODE_PRIVATE).edit();
		sp.clear();
		sp.commit();
	}

	// 保存实体信息到SharedPreferences
	public static void putParameter(Context context, String key, Object object) {

		SharedPreferences preferences = context.getSharedPreferences(SHARE_PREFS_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();

		// 使用 Gson 将对象转换为 JSON 字符串
		Gson gson = new Gson();
		String json = gson.toJson(object);

		editor.putString(key, json);
		editor.apply();
	}

	// 获取实体信息从SharedPreferences
	public static <T> T getParameter(Context context, String key, Class<T> clazz) {
		SharedPreferences preferences = context.getSharedPreferences(SHARE_PREFS_NAME, Context.MODE_PRIVATE);
		String json = preferences.getString(key, null);

		if (json != null) {
			// 使用 Gson 将 JSON 字符串转换为对象
			Gson gson = new Gson();
			return gson.fromJson(json, clazz);
		} else {
			return null;
		}
	}


}
