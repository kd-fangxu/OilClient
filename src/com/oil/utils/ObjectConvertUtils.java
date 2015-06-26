package com.oil.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ObjectConvertUtils<T> {
	T t;

	public T convert(String json) {
		Gson gson = new Gson();

		t = gson.fromJson(json, new TypeToken<T>() {
		}.getType());
		return t;
	}
}
