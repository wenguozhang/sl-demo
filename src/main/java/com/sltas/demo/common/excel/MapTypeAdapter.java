package com.sltas.demo.common.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class MapTypeAdapter extends TypeAdapter<Object> {
    private final Gson gson = new Gson();
	@Override
	public Object read(JsonReader in) throws IOException {
		JsonToken token = in.peek();
		switch (token) {
		case BEGIN_ARRAY:
			List<Object> list = new ArrayList<Object>();
			in.beginArray();
			while (in.hasNext()) {
				list.add(read(in));
			}
			in.endArray();
			return list;

		case BEGIN_OBJECT:
			Map<String, Object> map = new LinkedTreeMap<String, Object>();
			in.beginObject();
			while (in.hasNext()) {
				map.put(in.nextName(), read(in));
			}
			in.endObject();
			return map;

		case STRING:
			return in.nextString();

		case NUMBER:
			/**
			 * 改写数字的处理逻辑，将数字值分为整型与浮点型。
			 */
			double dbNum = in.nextDouble();

			// 数字超过long的最大值，返回浮点类型
			if (dbNum > Long.MAX_VALUE) {
				return dbNum;
			}

			// 判断数字是否为整数值
			long lngNum = (long) dbNum;
			if (dbNum == lngNum) {
				 if(lngNum > Integer.MAX_VALUE){
	                	return lngNum;
		           	}else{
		           		return (int)lngNum;
		           	}
			} else {
				return dbNum;
			}

		case BOOLEAN:
			return in.nextBoolean();

		case NULL:
			in.nextNull();
			return null;

		default:
			throw new IllegalStateException();
		}
	}

	@Override
	public void write(JsonWriter out, Object value) throws IOException {
		//序列化
        if (value == null) {
            out.nullValue();
            return;
        }

        TypeAdapter<Object> typeAdapter = (TypeAdapter<Object>) gson.getAdapter(value.getClass());
        if (typeAdapter instanceof ObjectTypeAdapter) {
            out.beginObject();
            out.endObject();
            return;
        }

        typeAdapter.write(out, value);
	}

}