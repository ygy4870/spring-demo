package com.ygy.study.mybatisdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by lynn on 2015/8/24.
 */
public class BeanUtil extends BaseBeanUtil{

	protected static Logger logger = LoggerFactory.getLogger(BeanUtil.class);

	/**
	 *
	 * @param source
	 *            src instantiation
	 * @param targetClass
	 *            target class
	 * @param mapper
	 *            在拷贝后需要的额外操作
	 * @param <S>
	 *            source
	 * @param <T>
	 *            target
	 * @return target
	 */
	public static <S, T> T copy(S source, Class<T> targetClass, BiFunction<S, T, T> mapper) {
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
		return mapper.apply(source, copy(source, target));
	}

	/**
	 *
	 * @param source
	 *            src instantiation
	 * @param target
	 *            target instantiation
	 * @param mapper
	 *            在拷贝后需要的额外操作
	 * @param <S>
	 *            source
	 * @param <T>
	 *            target
	 * @return target
	 */
	public static <S, T> T copy(S source, T target, BiFunction<S, T, T> mapper) {
		if (null == source || null == target) {
			return null;
		}
		return mapper.apply(source, copy(source, target));
	}

	/**
	 *
	 * @param source
	 *            bean Page
	 * @param cls
	 *            model class
	 * @param mapper
	 *            在拷贝后需要的额外操作
	 * @param <S>
	 *            bean
	 * @param <T>
	 *            model
	 * @return
	 */
	public static <S, T> Page<T> copyPage(Page<S> source, Class<T> cls, BiFunction<S, T, T> mapper) {
		if (null == source || null == cls) {
			return Page.emptyPage();
		}
		Page<T> page = copyPageCommon(source);
		if (source.getList() != null && !source.getList().isEmpty()) {
			page.setList(source.getList().stream().map(bean -> {
				try {
					return mapper.apply(bean, BeanUtil.copy(bean, cls.newInstance()));
				} catch (Exception e) {
					return null;
				}
			}).filter(Objects::nonNull).collect(Collectors.toList()));
		} else {
			page.setList(Collections.emptyList());
		}
		return page;
	}

    /**
     *
     * @param source 源对象
     * @param mapper 源个体对象转成目标个体对象的操作
     * @param <S> 源个体类型
     * @param <T> 目标个体类型
     * @return
     */
    public static <S, T> Page<T> copyPage(Page<S> source, Function<S, T> mapper) {
        if (null == source) {
            return Page.emptyPage();
        }
        Page<T> page = copyPageCommon(source);
        if (source.getList() != null && !source.getList().isEmpty()) {
            page.setList(source.getList().stream().map(mapper).filter(Objects::nonNull).collect(Collectors.toList()));
        } else {
            page.setList(Collections.emptyList());
        }
        return page;
    }


	public static <S, T> List<T> copyList(List<S> source, Class<T> cls, BiFunction<S, T, T> mapper) {
		if (source == null || source.isEmpty()) {
			return Collections.emptyList();
		}
		return source.stream().map(t -> {
			try {
				return BeanUtil.copy(t, cls.newInstance(), mapper);
			} catch (Exception e) {
				return null;
			}
		}).filter(Objects::nonNull).collect(Collectors.toList());
	}

    /**
     *
     * @param source 源对象
     * @param mapper 源个体对象转成目标个体对象的操作
     * @param <S> 源个体类型
     * @param <T> 目标个体类型
     * @return
     */
    public static <S, T> List<T> copyList(List<S> source, Function<S, T> mapper) {
        if (source == null || source.isEmpty()) {
            return Collections.emptyList();
        }
        return source.stream().map(mapper).filter(Objects::nonNull).collect(Collectors.toList());
    }


}
