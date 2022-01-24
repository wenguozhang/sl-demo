package com.sltas.demo.common.excel;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.google.gson.*;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.sltas.demo.common.MapFACTORYTypeAdapter;

/**
 * Gson序列化反序列化工具类
 * @author 麻欣喆
 * @CreateDate 2017年7月25日 下午4:12:26
 * @version 1.0
 */
public class GsonBUILDERUtil{
	public static final String DATETIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
	public static final String DATE_FORMAT_PATTERN_DOT = "yyyy.MM.dd";
	public static final String TIME_FORMAT_PATTERN = "HH:mm:ss";
	
	/**
	 * 字段转换成下划线格式、时间转化为特定格式、打印null字段
	 */
	public static final Gson GSON_BUILDER = new GsonBuilder()
            //.setPrettyPrinting() //对json结果格式化.
			
			//序列化LocalDateTime、LocalDate
			.registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
			    @Override
			    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
			    return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT_PATTERN)));
			}})
			.registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
			    @Override
			    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
			        return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN_DOT)));
			}})
			//反序列化LocalDateTime、LocalDate
			.registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
			    @Override
			    public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			    String datetime = json.getAsJsonPrimitive().getAsString();
			    return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(DATETIME_FORMAT_PATTERN));
			}}).registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
			    @Override
			    public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			    String datetime = json.getAsJsonPrimitive().getAsString();
			    return LocalDate.parse(datetime, DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));}
			})
			
            .serializeNulls()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES) // 字段转换成下划线格式，对于实体上使用了@SerializedName注解的不会生效.
            .disableHtmlEscaping()
            //.excludeFieldsWithoutExposeAnnotation() //不导出实体中没有用@Expose注解的属性  
            //.enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式 
            .setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")//时间转化为特定格式 
            //.setVersion(1.0)    //有的字段不是一开始就有的,会随着版本的升级添加进来,那么在进行序列化和返序列化的时候就会根据版本号来选择是否要序列化.  
            //@Since(版本号)能完美地实现这个功能.还的字段可能,随着版本的升级而删除,那么  
            //@Until(版本号)也能实现这个功能,GsonBuilder.setVersion(double)方法需要调用. 
            .create();
	/**
	 * 字段转换成下划线格式、时间转化为特定格式、不打印null字段
	 */
	public static final Gson GSON_BUILDER_NOTNULL = new GsonBuilder()
			//.setPrettyPrinting() //对json结果格式化.
			
			//序列化LocalDateTime、LocalDate
			.registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
			    @Override
			    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
			    return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT_PATTERN)));
			}})
			.registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
			    @Override
			    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
			        return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN)));
			}})
			//反序列化LocalDateTime、LocalDate
			.registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
			    @Override
			    public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			    String datetime = json.getAsJsonPrimitive().getAsString();
			    return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(DATETIME_FORMAT_PATTERN));
			}}).registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
			    @Override
			    public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			    String datetime = json.getAsJsonPrimitive().getAsString();
			    return LocalDate.parse(datetime, DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));}
			})
			
//			.serializeNulls()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES) // 字段转换成下划线格式，对于实体上使用了@SerializedName注解的不会生效.
			.disableHtmlEscaping()
			//.excludeFieldsWithoutExposeAnnotation() //不导出实体中没有用@Expose注解的属性  
			//.enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式 
			.setDateFormat("yyyy-MM-dd HH:mm:ss")//时间转化为特定格式 
			//.setVersion(1.0)    //有的字段不是一开始就有的,会随着版本的升级添加进来,那么在进行序列化和返序列化的时候就会根据版本号来选择是否要序列化.  
			//@Since(版本号)能完美地实现这个功能.还的字段可能,随着版本的升级而删除,那么  
			//@Until(版本号)也能实现这个功能,GsonBuilder.setVersion(double)方法需要调用. 
			.create();
	
	/**
	 * 普通，打印null值的字段
	 */
	public static final Gson GSON_BUILDER_COMMON = new GsonBuilder()
            //.setPrettyPrinting() //对json结果格式化.
            .serializeNulls()
            
          //序列化LocalDateTime、LocalDate
			.registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
			    @Override
			    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
			    return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT_PATTERN)));
			}})
			.registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
			    @Override
			    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
			        return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN_DOT)));
			}})
			//反序列化LocalDateTime、LocalDate
			.registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
			    @Override
			    public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			    String datetime = json.getAsJsonPrimitive().getAsString();
			    return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(DATETIME_FORMAT_PATTERN));
			}}).registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
			    @Override
			    public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			    String datetime = json.getAsJsonPrimitive().getAsString();
			    return LocalDate.parse(datetime, DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));}
			})
            
            //.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES) // 字段转换成下划线格式，对于实体上使用了@SerializedName注解的不会生效.
            .disableHtmlEscaping()
            //.excludeFieldsWithoutExposeAnnotation() //不导出实体中没有用@Expose注解的属性  
            //.enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式 
            .setDateFormat("yyyy-MM-dd HH:mm:ss")//时间转化为特定格式 
            //.setVersion(1.0)    //有的字段不是一开始就有的,会随着版本的升级添加进来,那么在进行序列化和返序列化的时候就会根据版本号来选择是否要序列化.  
            //@Since(版本号)能完美地实现这个功能.还的字段可能,随着版本的升级而删除,那么  
            //@Until(版本号)也能实现这个功能,GsonBuilder.setVersion(double)方法需要调用. 
            .create();
	
	/**
	 * 输出给前端使用，打印null值的字段，序列化时日期转换为毫秒数Long，反序列化时还是按字符串
	 */
	public static final Gson GSON_BUILDER_FRONT_H5 = new GsonBuilder()
			//.setPrettyPrinting() //对json结果格式化.
			.serializeNulls()
			//序列化LocalDateTime、LocalDate、Date
			.registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
				@Override
				public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
					long longDate = src.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
					return new JsonPrimitive(longDate);
				}})
			.registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
				@Override
				public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
					LocalDateTime localDateFrom = LocalDateTime.of(src, LocalTime.now());
					long longDate = localDateFrom.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
					return new JsonPrimitive(longDate);
				}})
			.registerTypeAdapter(LocalTime.class, new JsonSerializer<LocalTime>() {
				@Override
				public JsonElement serialize(LocalTime src, Type typeOfSrc, JsonSerializationContext context) {
					LocalDateTime localDateFrom = LocalDateTime.of(LocalDate.now(), src);
					long longDate = localDateFrom.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
					return new JsonPrimitive(longDate);
				}})
			.registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
				@Override
				public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
					return new JsonPrimitive(src.getTime());
				}})
			//反序列化LocalDateTime、LocalDate、date
			.registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
			    @Override
			    public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			    String datetime = json.getAsJsonPrimitive().getAsString();
			    return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(DATETIME_FORMAT_PATTERN));
			}}).registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
			    @Override
			    public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			    String datetime = json.getAsJsonPrimitive().getAsString();
			    return LocalDate.parse(datetime, DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));
			}}).registerTypeAdapter(LocalTime.class, new JsonDeserializer<LocalTime>() {
			    	@Override
			    	public LocalTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			    		String time = json.getAsJsonPrimitive().getAsString();
			    		return LocalTime.parse(time, DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN));
			}}).registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			    @Override
			    public Date deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			    String datetimeStr = json.getAsJsonPrimitive().getAsString();
			    
			    SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT_PATTERN);
			    Date date = null;
		    	try {
					date = sdf.parse(datetimeStr);
				} catch (ParseException e) {
					throw new RuntimeException("json字段转换Date类型失败,json="+datetimeStr,e);
				}
			    return date;
			}})
			
			.disableHtmlEscaping()
			.create();
	/**
	 * 普通，不打印null值的字段
	 */
	public static final Gson GSON_BUILDER_COMMON_NOTNULL = new GsonBuilder()
			//.setPrettyPrinting() //对json结果格式化.
//			.serializeNulls()
			
			//序列化LocalDateTime、LocalDate
			.registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
			    @Override
			    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
			    return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT_PATTERN)));
			}})
			.registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
			    @Override
			    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
			        return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN_DOT)));
			}})
			//反序列化LocalDateTime、LocalDate
			.registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
			    @Override
			    public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			    String datetime = json.getAsJsonPrimitive().getAsString();
			    return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern(DATETIME_FORMAT_PATTERN));
			}}).registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
			    @Override
			    public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			    String datetime = json.getAsJsonPrimitive().getAsString();
			    return LocalDate.parse(datetime, DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));}
			})
			
			//.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES) // 字段转换成下划线格式，对于实体上使用了@SerializedName注解的不会生效.
			.disableHtmlEscaping()
			//.excludeFieldsWithoutExposeAnnotation() //不导出实体中没有用@Expose注解的属性  
			//.enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式 
			//.setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")//时间转化为特定格式 
			//.setVersion(1.0)    //有的字段不是一开始就有的,会随着版本的升级添加进来,那么在进行序列化和返序列化的时候就会根据版本号来选择是否要序列化.  
			//@Since(版本号)能完美地实现这个功能.还的字段可能,随着版本的升级而删除,那么  
			//@Until(版本号)也能实现这个功能,GsonBuilder.setVersion(double)方法需要调用. 
			.create();

	public static final Gson GSON_BUILDER_REFINE= new GsonBuilder()
			.excludeFieldsWithoutExposeAnnotation() //不对没有用@Expose注解的属性进行操作
			.enableComplexMapKeySerialization() //当Map的key为复杂对象时,需要开启该方法
			.serializeNulls() //当字段值为空或null时，依然对该字段进行转换
			.setDateFormat("yyyy-MM-dd HH:mm:ss:SSS") //时间转化为特定格式
			.setPrettyPrinting() //对结果进行格式化，增加换行
			.disableHtmlEscaping() //防止特殊字符出现乱码
			.registerTypeAdapter(new TypeToken<Map<String, Object>>() {
			}.getType(), new MapTypeAdapter()) //Map,int便double处理
			.create();

	public static final Gson GSON_BUILDER_MAP_COMMON = new GsonBuilder()
            .serializeNulls() //当字段值为空或null时，依然对该字段进行转换
            .disableHtmlEscaping() //防止特殊字符出现乱码
            .registerTypeAdapter(new TypeToken<Map<String, Object>>() {
			}.getType(), new MapTypeAdapter())  //Map,int变double处理
            .create();


    /**
     * null值转""串
     */
	public class NullStringToEmptyAdapterFactory implements TypeAdapterFactory {
		@Override
		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
			Class<T> rawType = (Class<T>) type.getRawType();
			if (rawType != String.class) {
				return null;
			}
			return (TypeAdapter<T>) new StringAdapter();
		}
	}


    class StringAdapter extends TypeAdapter<String> {
        @Override
        public void write(JsonWriter out, String value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            out.value(value);
        }

        @Override
        public String read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return "";
            }
            return in.nextString();
        }
    }

	/**
	 * 使用源码替换方案，解决map值int变double问题
	 * @return
	 */
	public static Gson mapGson() {
		Gson gson = new GsonBuilder().create();
		try {
			Field factories = Gson.class.getDeclaredField("factories");
			factories.setAccessible(true);
			Object o = factories.get(gson);
			Class<?>[] declaredClasses = Collections.class.getDeclaredClasses();
			for (Class c : declaredClasses) {
				if ("java.util.Collections$UnmodifiableList".equals(c.getName())) {
					Field listField = c.getDeclaredField("list");
					listField.setAccessible(true);
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
	public static Gson GSON_DUBBO_INTEGER = new GsonBuilder().registerTypeAdapter(HashMap.class, new JsonDeserializer<HashMap>() {

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

	}).create();
}