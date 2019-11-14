package com.zj.caoshangfei.common.thymeleaf;


import com.zj.caoshangfei.utils.PaginationUtils;

import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by jin.zhang@fuwo.com on 2017/12/29.
 */
public class PageDialect extends AbstractDialect implements IExpressionObjectDialect {

    private final IExpressionObjectFactory EXPRESSION_OBJECTS_FACTORY = new PageExpressionFactory();

    public static final String NAME = "pageUtils";


    public PageDialect() {
        super(NAME);
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return EXPRESSION_OBJECTS_FACTORY;
    }

    public static class PageExpressionFactory implements IExpressionObjectFactory {

        public static final String PAGE_UTILS_EXPRESSION_OBJECT_NAME = "pageUtils";
        private static final PaginationUtils PAGEUTILS_EXPRESSION_OBJECT = new PaginationUtils();
        public static final Set<String> ALL_EXPRESSION_OBJECT_NAMES;

        static {
            final Set<String> allExpressionObjectNames = new LinkedHashSet();
            allExpressionObjectNames.add(PAGE_UTILS_EXPRESSION_OBJECT_NAME);
            ALL_EXPRESSION_OBJECT_NAMES = Collections.unmodifiableSet(allExpressionObjectNames);
        }

        public PageExpressionFactory() {
            super();
        }

        @Override
        public Set<String> getAllExpressionObjectNames() {
            return ALL_EXPRESSION_OBJECT_NAMES;
        }

        @Override
        public Object buildObject(IExpressionContext context, String expressionObjectName) {
            return PAGE_UTILS_EXPRESSION_OBJECT_NAME.equals(expressionObjectName) ? PAGEUTILS_EXPRESSION_OBJECT : null;
        }

        public boolean isCacheable(String expressionObjectName) {
            return expressionObjectName != null && PAGE_UTILS_EXPRESSION_OBJECT_NAME.equals(expressionObjectName);
        }
    }
}
