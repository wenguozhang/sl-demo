package com.sltas.demo.utils;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import ma.glasnost.orika.metadata.TypeFactory;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * ProjectName: common
 * ClassName: BeanMapper
 * Date: 2019/5/21 14:05
 * Content: 简单封装orika
 *          实现深度的BeanOfClassA<->BeanOfClassB复制
 *
 * @author soulasuna
 * @version 1.0
 * @since JDK1.8
 */
public final class BeanMapper {

    /*--------------------static--------------------*/

    private static MapperFacade BEAN_MAPPER;
    static {
        // 忽略null值
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder()
                .mapNulls(false)
                .build();
        BEAN_MAPPER = mapperFactory.getMapperFacade();
    }

    /*--------------------business_method--------------------*/

    /**
     * 根据源对象复制出新类型对象.
     * 复制对象时自动忽略源对象中null值
     *
     * @param sourceBean    源对象
     * @param sClazz        源类型字节码文件
     * @param tClazz        目标类型字节码文件
     * @param <S>           源类型泛型
     * @param <T>           目标类型泛型
     * @return              目标类型的对象
     */
    public static <S,T> T copyBean(S sourceBean, Class<S> sClazz, Class<T> tClazz){
        Assert.notNull(sourceBean,"error : beanMapper copyBean sourceBean can not be null");
        Assert.notNull(sClazz,"error : beanMapper copyBean source clazz can not be null");
        Assert.notNull(tClazz,"error : beanMapper copyBean target clazz can not be null");

        Type<S> sType = getType(sClazz);
        Type<T> tType = getType(tClazz);
        return BEAN_MAPPER.map(sourceBean, sType, tType);
    }

    /**
     * 源对象数据复制到目标对象.
     * 复制对象时自动忽略源对象中null值
     * 源对象当中的null不会覆盖目标对象的值
     *
     * @param sourceBean    源对象
     * @param targetBean    目标对象
     * @param sClazz        源类型字节码文件
     * @param tClazz        目标类型字节码文件
     * @param <S>           源类型泛型
     * @param <T>           目标类型泛型
     */
    public static <S,T> void copyBean(S sourceBean, T targetBean, Class<S> sClazz, Class<T> tClazz){
        Assert.notNull(sourceBean,"error : beanMapper copyBean sourceBean can not be null");
        Assert.notNull(sourceBean,"error : beanMapper copyBean targetBean can not be null");
        Assert.notNull(sClazz,"error : beanMapper copyBean source clazz can not be null");
        Assert.notNull(tClazz,"error : beanMapper copyBean target clazz can not be null");

        Type<S> sType = getType(sClazz);
        Type<T> tType = getType(tClazz);
        BEAN_MAPPER.map(sourceBean,targetBean,sType,tType);
    }

    /**
     * 根据源对象列表复制出新类型对象列表.
     * 复制对象时自动忽略源对象中null值
     *
     * @param sourceBeanList    源对象列表
     * @param sClazz            源类型字节码文件
     * @param tClazz            目标类型字节码文件
     * @param <S>               源类型泛型
     * @param <T>               目标类型泛型
     * @return                  目标类型的对象列表
     */
    public static <S,T> List<T> copyBeanList(List<S> sourceBeanList, Class<S> sClazz, Class<T> tClazz){
        Assert.notNull(sClazz,"error : beanMapper copyBeanList source clazz can not be null");
        Assert.notNull(tClazz,"error : beanMapper copyBeanList target clazz can not be null");
        Assert.notEmpty(sourceBeanList,"error : beanMapper copyBeanList sourceBeanList can not be empty");

        Type<S> sType = getType(sClazz);
        Type<T> tType = getType(tClazz);
        List<T> tList = new ArrayList<>(sourceBeanList.size());
        sourceBeanList.forEach(s -> {
            tList.add(BEAN_MAPPER.map(s, sType, tType));
        });
        return tList;
    }

    /**
     * 根据源对象列表中数据复制到新类型对象列表.
     * 复制对象时自动忽略源对象中null值
     * 源对象当中的null不会覆盖目标对象的值
     *
     * @param sourceBeanList    源对象列表
     * @param targetBeanList    目标对象列表
     * @param sClazz            源类型字节码文件
     * @param tClazz            目标类型字节码文件
     * @param <S>               目标类型泛型
     * @param <T>               目标类型的对象列表
     */
    public static <S,T> List<T> copyBeanList(List<S> sourceBeanList, List<T> targetBeanList,
                                          Class<S> sClazz, Class<T> tClazz){
        Assert.notNull(sClazz,"error : beanMapper copyBeanList source clazz can not be null");
        Assert.notNull(tClazz,"error : beanMapper copyBeanList target clazz can not be null");
        Assert.notEmpty(sourceBeanList,"error : beanMapper copyBeanList sourceBeanList can not be empty");
        Assert.notEmpty(sourceBeanList,"error : beanMapper copyBeanList targetBeanList can not be empty");
        Assert.isTrue(sourceBeanList.size() == targetBeanList.size(),
                "error : beanMapper copyBeanList sourceBeanList and targetBeanList must be same size");

        Type<S> sType = getType(sClazz);
        Type<T> tType = getType(tClazz);
        int size = sourceBeanList.size();
        List<T> tList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            S s = sourceBeanList.get(i);
            T t = targetBeanList.get(i);
            BEAN_MAPPER.map(s, t, sType, tType);
            tList.add(t);
        }
        return tList;
    }

    /*--------------------tools_method--------------------*/

    /**
     * 避免每次copy对象都是用反射消耗性能
     * 可以预先获得Orika转换所需的Type类型
     * @param rawType   获得类型的字节码文件
     * @param <E>       类型的泛型
     * @return          对应的Type对象
     */
    private static <E> Type<E> getType(final Class<E> rawType) {
        return TypeFactory.valueOf(rawType);
    }

}
