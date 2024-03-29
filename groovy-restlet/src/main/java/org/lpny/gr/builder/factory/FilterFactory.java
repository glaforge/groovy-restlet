/**
 * 
 */
package org.lpny.gr.builder.factory;

import groovy.lang.Closure;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.restlet.Filter;
import org.restlet.data.Request;
import org.restlet.data.Response;

/**
 * @author keke
 * @reversion $Revision$
 * @version 0.1
 * @since 0.1
 */
public class FilterFactory extends RestletFactory {
    protected static final String AFTER  = "after";
    protected static final String BEFORE = "before";

    @SuppressWarnings("unchecked")
    @Override
    protected Object newInstanceInner(final FactoryBuilderSupport builder,
            final Object name, final Object value, final Map attributes)
            throws InstantiationException, IllegalAccessException {
        final Closure before = (Closure) attributes.remove(BEFORE);
        final Closure after = (Closure) attributes.remove(AFTER);
        return new Filter(FactoryUtils.getParentRestletContext(builder)) {

            @Override
            protected void afterHandle(final Request request,
                    final Response response) {
                if (after != null) {
                    after.call(FactoryUtils.packArgs(this, after, request,
                            response));
                } else {
                    super.afterHandle(request, response);
                }
            }

            @Override
            protected void beforeHandle(final Request request,
                    final Response response) {
                if (before != null) {
                    before.call(FactoryUtils.packArgs(this, before, request,
                            response));
                } else {
                    super.beforeHandle(request, response);
                }
            }

        };
    }
}
