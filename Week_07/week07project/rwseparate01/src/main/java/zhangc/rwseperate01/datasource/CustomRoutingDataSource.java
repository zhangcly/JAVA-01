package zhangc.rwseperate01.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class CustomRoutingDataSource extends AbstractRoutingDataSource {
    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public ThreadLocal<String> getThreadLocal() {
        return threadLocal;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return threadLocal.get();
    }
}
