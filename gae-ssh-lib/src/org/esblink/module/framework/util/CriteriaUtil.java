package org.esblink.module.framework.util;


public class CriteriaUtil {

//	/**
//	 * 当离线查询中in的个数超过1000时 ，拆分成几个不同的in分句中
//	 * 
//	 * @param source
//	 *            满足条件的集合
//	 * @param dc
//	 *            DetachedCriteria对象
//	 * @param inFlag
//	 *            true:in false:not
//	 * @param field
//	 *            字段名称
//	 * @param size
//	 *            拆分每个in中放多少个
//	 * @return DetachedCriteria
//	 */
//	public static DetachedCriteria splitSourceInDc(List<?> source,
//			DetachedCriteria dc, boolean inFlag, String field, int size) {
//		if (source != null && source.size() > 0) {
//			if (source.size() > size) {
//				if (inFlag) {
//					int k = source.size() / size
//							+ (source.size() % size > 0 ? 1 : 0);
//					Criterion criterion = null;
//					for (int i = 0; i < k; i++) {
//						if (i == 0) {
//							List<?> sublist = source.subList(0, size > source
//									.size() ? source.size() : size);
//							criterion = Restrictions.in(field, sublist);
//						} else {
//							List<?> sublist = source.subList(i * size, (i + 1)
//									* size > source.size() ? source.size()
//									: (i + 1) * size);
//							criterion = Restrictions.or(criterion, Restrictions
//									.in(field, sublist));
//						}
//					}
//					dc.add(criterion);
//				} else {
//					int pageTotal = source.size() / size
//							+ (source.size() % size > 0 ? 1 : 0);
//					for (int i = 0; i < pageTotal; i++) {
//						int start = i * size;
//						int end = (i + 1) * size > source.size() ? source
//								.size() : (i + 1) * size;
//						List<?> sublist = source.subList(start, end);
//						dc.add(Restrictions
//								.not(Restrictions.in(field, sublist)));
//					}
//				}
//			} else {
//				if (inFlag) {
//					dc.add(Restrictions.in(field, source));
//				} else {
//					dc.add(Restrictions.not(Restrictions.in(field, source)));
//				}
//			}
//		}
//		return dc;
//	}

}
