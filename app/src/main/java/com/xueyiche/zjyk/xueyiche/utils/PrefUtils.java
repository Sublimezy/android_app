package com.xueyiche.zjyk.xueyiche.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.xueyiche.zjyk.xueyiche.constants.App;

import java.util.ArrayList;
import java.util.List;

/**
 * 专门访问和设置SharePreference的工具类, 保存和配置一些设置信息
 * 
 * @author Kevin
 * 
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



}
