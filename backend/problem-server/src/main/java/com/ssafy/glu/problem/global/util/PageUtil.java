package com.ssafy.glu.problem.global.util;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PageUtil {

	public static <T> Page<T> convertListToPage(List<T> list, Pageable pageable) {
		int start = (int)pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), list.size());

		List<T> subList = list.subList(start, end);

		return new PageImpl<T>(subList, pageable, list.size());
	}
}
