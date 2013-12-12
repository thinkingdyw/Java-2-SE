package com.think.common.utils;

import java.util.Collection;
import java.util.List;


public final class CollectionUtils {

	/**
	 * 判断集合是否为空
	 * @param <T>
	 * @param collection
	 * @return
	 */
	public static <T> boolean isEmpty(Collection<T> collection){
		
		return collection == null || collection.isEmpty();
	}
	
	/**
	 * list中每一个元素需要实现hashcode和equals方法
	 * @param <E>
	 * @param inList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> List<E> unique(List<E> inList){
		List<E> outList = null;
		if(isEmpty(inList)){
			return inList;
		}
		try {
			outList = inList.getClass().newInstance();
			for (E e : inList) {
				if(!outList.contains(e)){
					outList.add(e);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return outList;
	}
	
	public interface UniqueComparetor<E>{
		public boolean equals(E e1,E e2);
	}
}
