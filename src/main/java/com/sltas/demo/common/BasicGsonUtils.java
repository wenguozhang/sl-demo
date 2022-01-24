package com.sltas.demo.common;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.reflect.TypeToken;

public class BasicGsonUtils{

    private static Gson gson = null;
    static {
        if (gson == null) {
        	gson = gson();
        }
    }

    public BasicGsonUtils() {
    	
    }

    /**
     * 将object对象转成json字符串
     *
     * @param object
     * @return
     */
    public static String GsonToString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }


    /**
     * 将gsonString转成泛型bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }


    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }


    /**
     * 转成list
     * 解决泛型问题
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> JsonToList(String json, Class<T> cls) {
//        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for(final JsonElement elem : array){
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }




    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }


    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> GsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

    /**
     * 把一个bean（或者其他的字符串什么的）转成json
     * @param object
     * @return
     */
    public static String BeanToJson(Object object){
        return gson.toJson(object);
    }

	/**
	 * 使用源码替换方案，解决map值int变double问题
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Gson gson() {
			Gson gson =  new GsonBuilder()
			.registerTypeAdapter(BigDecimal.class, new JsonDeserializer<BigDecimal>() {
			    @Override
			    public BigDecimal deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			    BigDecimal asBigDecimal = json.getAsBigDecimal();
			    return new BigDecimal(String.valueOf(asBigDecimal));
			   }
			}).	
			registerTypeAdapter(HashMap.class, new JsonDeserializer<HashMap>() {
				@Override
				public HashMap<String, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
					HashMap<String, Object> resultMap = new HashMap<>();
					JsonObject jsonObject = json.getAsJsonObject();
					Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
					for (Map.Entry<String, JsonElement> entry : entrySet) {
						resultMap.put(entry.getKey(), entry.getValue());
					}
					return resultMap;
				}
			})
            .disableHtmlEscaping()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")//时间转化为特定格式 
            .create();
		try {
			Field factories = Gson.class.getDeclaredField("factories");
			factories.setAccessible(true);
			Object o = factories.get(gson);
			Class<?>[] declaredClasses = Collections.class.getDeclaredClasses();
			for (Class c : declaredClasses) {
				if ("java.util.Collections$UnmodifiableList".equals(c.getName())) {
					Field listField = c.getDeclaredField("list");
					listField.setAccessible(true);
					@SuppressWarnings("unchecked")
					List<TypeAdapterFactory> list = (List<TypeAdapterFactory>) listField.get(o);
					int i = list.indexOf(ObjectTypeAdapter.FACTORY);
					list.set(i, MapFACTORYTypeAdapter.FACTORY);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson;
	}
	
	/**
	 *  将object对象转成json字符串(仅供前后端交互使用)
	 */
	public static String GsonToStringJS(Object object) {
        String gsonString = null;
        
        Gson gsonJs = gsonToJS(); 
        if (gsonJs != null) {
            gsonString = gsonJs.toJson(object);
        }
        return gsonString;
    }
	
	/**
	 * 使用源码替换方案，解决map值int变double问题以及long类型的问题
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Gson gsonToJS() {
			Gson gson =  new GsonBuilder()
			.registerTypeAdapter(BigDecimal.class, new JsonDeserializer<BigDecimal>() {
			    @Override
			    public BigDecimal deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			    BigDecimal asBigDecimal = json.getAsBigDecimal();
			    return new BigDecimal(String.valueOf(asBigDecimal));
			   }
			}).	
			registerTypeAdapter(HashMap.class, new JsonDeserializer<HashMap>() {
				@Override
				public HashMap<String, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
					HashMap<String, Object> resultMap = new HashMap<>();
					JsonObject jsonObject = json.getAsJsonObject();
					Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
					for (Map.Entry<String, JsonElement> entry : entrySet) {
						resultMap.put(entry.getKey(), entry.getValue());
					}
					return resultMap;
				}
			})
            .disableHtmlEscaping()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")//时间转化为特定格式 
            .setLongSerializationPolicy(LongSerializationPolicy.STRING)
            .create();
		try {
			Field factories = Gson.class.getDeclaredField("factories");
			factories.setAccessible(true);
			Object o = factories.get(gson);
			Class<?>[] declaredClasses = Collections.class.getDeclaredClasses();
			for (Class c : declaredClasses) {
				if ("java.util.Collections$UnmodifiableList".equals(c.getName())) {
					Field listField = c.getDeclaredField("list");
					listField.setAccessible(true);
					@SuppressWarnings("unchecked")
					List<TypeAdapterFactory> list = (List<TypeAdapterFactory>) listField.get(o);
					int i = list.indexOf(ObjectTypeAdapter.FACTORY);
					list.set(i, MapFACTORYTypeAdapter.FACTORY);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson;
	}
	
}
