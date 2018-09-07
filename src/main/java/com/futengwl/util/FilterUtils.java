package com.futengwl.util;

import com.futengwl.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * @title: FilterUtils
 * @package: com.futengwl.util
 * @description:
 * @author: hanbin
 * @date: 2018-06-05  20:05
 */
public class FilterUtils {
    private FilterUtils() {
    }

    public static Filters wrapper() {
        Filters filters = new Filters();
        return filters;
    }


    public static class Filters {

        private List<Filter> list;

        public Filters() {
            list = new ArrayList<>();
        }

        public Filters addFilter(Filter filter) {
            list.add(filter);
            return this;
        }
        public List<Filter> done() {
            return list;
        }

    }
}
