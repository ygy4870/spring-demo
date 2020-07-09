package com.ygy.study.mybatisdemo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by lynn on 2015/8/24.
 */
public class BaseBeanUtil {

    protected static Logger logger = LoggerFactory.getLogger(BeanUtil.class);


    public static Map<String, BeanCopier> beanCopierMap = new HashMap<>();

    public static void beanCopy(Object source, Object target) {
        if (null == source || null == target) {
            return;
        }

        String beanKey = generateKey(source.getClass(), target.getClass());
        BeanCopier copier = beanCopierMap.get(beanKey);

        if (null == copier) {
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            beanCopierMap.put(beanKey, copier);
        }
        copier.copy(source, target, new DateConverterBeanCopier());
    }

    public static <T> T copy(Object source, T target) {
        if (null == source || null == target) {
            return null;
        }
        beanCopy(source, target);
        return target;
    }

    public static <T> T copy(Object source, Class<T> targetClass) {
        if (null == source || null == targetClass) {
            return null;
        }
        T target = null;
        try {
            target = targetClass.newInstance();
        } catch (InstantiationException e) {
            logger.error("bean copy InstantiationException", e);
        } catch (IllegalAccessException e) {
            logger.error("bean copy IllegalAccessException", e);
        }
        if (null == target) {
            return null;
        }
        return copy(source, target);
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }

    public static <T> List<T> copyList(List<?> source, Class<T> cls) {
        if (source == null || source.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> resultList = new ArrayList<>();
        for (Object o : source) {
            resultList.add(copy(o, cls));
        }
        return resultList;
    }


//    public static Map<String, Object> transBean2Map(Object obj) {
//        if (obj == null) {
//            return null;
//        }
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
//            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//            Map<String, Object> proMap = null;
//            List<Map<String, Object>> beanMapList = null;
//            for (PropertyDescriptor property : propertyDescriptors) {
//                String key = property.getName();
//                // 过滤class属性
//                if (!key.equals("class") && !key.equals("page") && !key.equals("html")) {
//                    // 得到property对应的getter方法
//                    Method getter = property.getReadMethod();
//
//                    if (getter != null) {
//                        Object value = getter.invoke(obj);
//                        if (value != null) {
//                            if (value instanceof String) {
//                                if (EduStringUtil.isNotBlank(value.toString())) {
//                                    map.put(key, value);
//                                }
//                            } else if (isBaseDataType(value.getClass())) {    //基本类型
//                                map.put(key, value);
//                            } else if (value.getClass().isArray()) {  //数组
////                                Object arrayList = Array.newInstance(value.getClass().getComponentType(), Array.getLength(value));
////                                for(int index = 0; index < Array.getLength(value); index ++){
////                                    Array.set(arrayList, index, transBean2Map(Array.get(value, index)));
////                                }
//                            } else if (value instanceof Collection) {
//                                beanMapList = new ArrayList<Map<String, Object>>();
//                                Iterator iter = ((Collection) value).iterator();
//                                while (iter.hasNext()) {
//                                    beanMapList.add(transBean2Map(iter.next()));
//                                }
//                                map.put(key, beanMapList);
//                            } else {                                  // 对象类型
//                                proMap = transBean2Map(value);
//                                map.put(key, proMap);
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            logger.error("transBean2Map err.", e);
//        }
//        return map;
//    }

//    public static Map<String, String> transBean2StringMap(Object object) {
//        Map<String, String> dataMap = new HashMap<>();
//        try {
//            BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
//            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
//            for (PropertyDescriptor property : propertyDescriptors) {
//                String key = property.getName();
//                Class propertyType = property.getPropertyType();
//                // 过滤class属性
//                if (Class.class.equals(propertyType)) {
//                    continue;
//                }
//                // 得到property对应的getter方法
//                Method getter = property.getReadMethod();
//                if (getter == null) {
//                    continue;
//                }
//                Object value = getter.invoke(object);
//                if (value == null) {
//                    continue;
//                }
//                if (value instanceof String) {
//                    dataMap.put(key, value.toString());
//                } else {
//                    dataMap.put(key, EduGsonUtils.toJson(value));
//                }
//            }
//        } catch (Exception e) {
//            logger.error("transBean2Map Error", e);
//        }
//        return dataMap;
//    }

    /**
     * 判断一个类是否为基本数据类型。
     *
     * @param clazz 要判断的类。
     * @return true 表示为基本数据类型。
     */
    public static boolean isBaseDataType(Class clazz) throws Exception {
        return
                (
                        clazz.equals(String.class) ||
                                clazz.equals(Integer.class) ||
                                clazz.equals(Byte.class) ||
                                clazz.equals(Long.class) ||
                                clazz.equals(Double.class) ||
                                clazz.equals(Float.class) ||
                                clazz.equals(Character.class) ||
                                clazz.equals(Short.class) ||
                                clazz.equals(BigDecimal.class) ||
                                clazz.equals(BigInteger.class) ||
                                clazz.equals(Boolean.class) ||
                                clazz.equals(Date.class) ||
//                                clazz.equals(DateTime.class) ||
                                clazz.isPrimitive()
                );
    }

    public static <T> Page<T> copyPage(Page<?> source, Class<T> cls) {
        if (null == source || null == cls) {
            return Page.emptyPage();
        }

        Page<T> page = copyPageCommon(source);
        List<T> list = new ArrayList<T>();
        if (source.getList() != null && !source.getList().isEmpty()) {
            for (Object o : source.getList()) {
                if (null == o) {
                    continue;
                }
                list.add(copy(o, cls));
            }
        }
        page.setList(list);


        return page;

    }
//
//    public static <T> Page<T> copyPage(cn.huanju.edu100.common.Page<?> source, Class<T> cls) {
//        if (null != source && null != cls) {
//            Page<T> page = copyPageCommon(source);
//            List<T> list = new ArrayList<T>();
//            if (source.getList() != null && !source.getList().isEmpty()) {
//                for (Object o : source.getList()) {
//                    if (null == o) {
//                        continue;
//                    }
//                    list.add(copy(o, cls));
//                }
//            }
//            page.setList(list);
//
//
//            return page;
//        } else {
//            return Page.emptyPage();
//        }
//    }

    public static <T> Page<T> copyPageCommon(Page<?> src) {
        if (src == null) {
            return Page.emptyPage();
        } else {
            Page<T> page = new Page<>();
            page.setTotal(src.getTotal());
            page.setPageNo(src.getPageNo());
            page.setPageSize(src.getPageSize());
            page.setOrderBy(src.getOrderBy());
            page.setFuncName(src.getFuncName());
            page.setFuncParam(src.getFuncParam());
            page.setLastPage(src.isLastPage());
            page.setFirstPage(src.isFirstPage());
            return page;
        }
    }
//
//    public static <T> Page<T> copyPageCommon(cn.huanju.edu100.common.Page<?> src) {
//        if (src == null) {
//            return Page.emptyPage();
//        } else {
//            Page<T> page = new Page<>();
//            page.setTotal(src.getTotal());
//            page.setPageNo(src.getPageNo());
//            page.setPageSize(src.getPageSize());
//            page.setOrderBy(src.getOrderBy());
//            page.setFuncName(src.getFuncName());
//            page.setFuncParam(src.getFuncParam());
//            page.setLastPage(src.isLastPage());
//            page.setFirstPage(src.isFirstPage());
//            return page;
//        }
//    }

}
