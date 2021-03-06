package zhangc.rwseperate01.properties;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "multi-data-source")
public class MultiDataSourceProperties {
    private DataSourceProperties master;
    private Map<String, DataSourceProperties> slaves;

    public DataSourceProperties getMaster() {
        return master;
    }

    public void setMaster(DataSourceProperties master) {
        this.master = master;
    }

    public Map<String, DataSourceProperties> getSlaves() {
        return slaves;
    }

    public void setSlaves(Map<String, DataSourceProperties> slaves) {
        this.slaves = slaves;
    }
}
