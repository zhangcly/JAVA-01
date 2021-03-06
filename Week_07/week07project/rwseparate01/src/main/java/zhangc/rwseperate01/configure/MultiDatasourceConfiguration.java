package zhangc.rwseperate01.configure;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import zhangc.rwseperate01.datasource.CustomRoutingDataSource;
import zhangc.rwseperate01.aop.MultiDataSourceAop;
import zhangc.rwseperate01.properties.MultiDataSourceProperties;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(MultiDataSourceProperties.class)
public class MultiDatasourceConfiguration {

    @Bean
    public DataSource dataSource(MultiDataSourceProperties multiDataSourceProperties){
        if (multiDataSourceProperties.getMaster() == null){
            throw new RuntimeException("master dataSource cannot be null");
        }
        Map<Object, Object> dataSourceMap = new HashMap<>();
        HikariDataSource master = new HikariDataSource();
        master.setDriverClassName(multiDataSourceProperties.getMaster().getDriverClassName());
        master.setJdbcUrl(multiDataSourceProperties.getMaster().getUrl());
        master.setUsername(multiDataSourceProperties.getMaster().getUsername());
        master.setPassword(multiDataSourceProperties.getMaster().getPassword());
        dataSourceMap.put("master", master);

        Map<String, DataSourceProperties> slaves = multiDataSourceProperties.getSlaves();
        if (!CollectionUtils.isEmpty(slaves)){
            for (Map.Entry<String, DataSourceProperties> entry : slaves.entrySet()){
                if ("master".equals(entry.getKey())){
                    throw new RuntimeException("slave dataSource cannot be named 'master'");
                }
                HikariDataSource slave = new HikariDataSource();
                slave.setDriverClassName(entry.getValue().getDriverClassName());
                slave.setJdbcUrl(entry.getValue().getUrl());
                slave.setUsername(entry.getValue().getUsername());
                slave.setPassword(entry.getValue().getPassword());
                dataSourceMap.put(entry.getKey(), slave);
            }
        }

        CustomRoutingDataSource dataSource = new CustomRoutingDataSource();
        dataSource.setTargetDataSources(dataSourceMap);
        return dataSource;
    }

    @Bean
    public MultiDataSourceAop aop(MultiDataSourceProperties multiDataSourceProperties){
        MultiDataSourceAop aop = new MultiDataSourceAop();
        if (!CollectionUtils.isEmpty(multiDataSourceProperties.getSlaves())){
            List<String> slaveNames = new ArrayList<>();
            slaveNames.addAll(multiDataSourceProperties.getSlaves().keySet());
            aop.setSlaveNames(slaveNames);
        }
        return aop;
    }
}
