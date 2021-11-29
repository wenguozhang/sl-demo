package com.sltas.demo.utils;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

/**
 * Gson序列化反序列化工具类
 */
public class GsonBUILDERUtil{
	{
	}
	public static final Gson GSON_BUILDER = new GsonBuilder()
			//序列化LocalDateTime、LocalDate
			.registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
			    @Override
			    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
			    return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			}})
			.registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
			    @Override
			    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
			        return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			}})
			//反序列化LocalDateTime、LocalDate
			.registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
			    @Override
			    public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			    String datetime = json.getAsJsonPrimitive().getAsString();
			    return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			}}).registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
			    @Override
			    public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
			    String datetime = json.getAsJsonPrimitive().getAsString();
			    return LocalDate.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));}
			})
			
            //.setPrettyPrinting() //对json结果格式化.
            .serializeNulls()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES) // 字段转换成下划线格式，对于实体上使用了@SerializedName注解的不会生效.
            .disableHtmlEscaping()
            //.excludeFieldsWithoutExposeAnnotation() //不导出实体中没有用@Expose注解的属性  
            //.enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式 
            .setDateFormat("yyyy-MM-dd HH:mm:ss")//时间转化为特定格式 
            //.setVersion(1.0)    //有的字段不是一开始就有的,会随着版本的升级添加进来,那么在进行序列化和返序列化的时候就会根据版本号来选择是否要序列化.  
            //@Since(版本号)能完美地实现这个功能.还的字段可能,随着版本的升级而删除,那么  
            //@Until(版本号)也能实现这个功能,GsonBuilder.setVersion(double)方法需要调用. 
            .create();
	public static final Gson GSON_BUILDER_COMMON = new GsonBuilder()
			//序列化LocalDateTime、LocalDate
			.registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
				@Override
				public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
					return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				}})
			.registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
				@Override
				public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
					return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				}})
			//反序列化LocalDateTime、LocalDate
			.registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
				@Override
				public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
					String datetime = json.getAsJsonPrimitive().getAsString();
					return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				}}).registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
					@Override
					public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
						String datetime = json.getAsJsonPrimitive().getAsString();
						return LocalDate.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));}
				})
			
			//.setPrettyPrinting() //对json结果格式化.
			.serializeNulls()
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
	 * Gson gson = GsonBUILDERUtil.GSON_BUILDER_MAP_COMMON
	 * gson.fromJson(s,new TypeToken<Map<String,Object>>(){}.getType()));
	 */
	public static final Gson GSON_BUILDER_MAP_COMMON = new GsonBuilder()
			.serializeNulls() //当字段值为空或null时，依然对该字段进行转换
			.disableHtmlEscaping() //防止特殊字符出现乱码
			.registerTypeAdapter(new TypeToken<Map<String, Object>>() {
			}.getType(), new MapTypeAdapter())  //Map,int变double处理
			.create();
}