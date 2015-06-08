package com.oil.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ObjectConvertUtils<T> {
	T t;

	public T convert(Object object) {
		Gson gson = new Gson();
		String json = gson.toJson(object);
		Log.e("json", json);
		t = gson.fromJson(json, new TypeToken<T>() {
		}.getType());
		return t;
	}
}
