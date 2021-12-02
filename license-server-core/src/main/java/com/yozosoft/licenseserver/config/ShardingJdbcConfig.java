package com.yozosoft.licenseserver.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.core.strategy.keygen.SnowflakeShardingKeyGenerator;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * shardingjdbc配置类
 */
@Configuration
@Slf4j
public class ShardingJdbcConfig {

    @Autowired
    @Qualifier("masterDataSource")
    DataSource masterDataSource;

//    @Primary
//    @Bean
//    public DataSource initShardingJdbcDataSource() throws SQLException {
//        Map<String, DataSource> dataSourceMap = createDataSourceMap();
//        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
//        Properties props = new Properties();
//        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, props);
//        return dataSource;
//    }

    @Bean
    public SnowflakeShardingKeyGenerator keyGenerator() {
        SnowflakeShardingKeyGenerator snowflakeShardingKeyGenerator = new SnowflakeShardingKeyGenerator();
        Properties properties = new Properties();
        properties.setProperty("worker.id", getWorkerId());
        snowflakeShardingKeyGenerator.setProperties(properties);
        return snowflakeShardingKeyGenerator;
    }

    private Map<String, DataSource> createDataSourceMap() {
        final Map<String, DataSource> result = new HashMap<>();
        result.put("ds_master", masterDataSource);
        return result;
    }

    private String getWorkerId() {
        long workerId = 0L;
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
            byte[] ipAddressByteArray = address.getAddress();
            //如果是IPV4，计算方式是遍历byte[]，然后把每个IP段数值相加得到的结果就是workerId
            if (ipAddressByteArray.length == 4) {
                for (byte byteNum : ipAddressByteArray) {
                    workerId += byteNum & 0xFF;
                }
                //如果是IPV6，计算方式是遍历byte[]，然后把每个IP段后6位（& 0B111111 就是得到后6位）数值相加得到的结果就是workerId
            } else if (ipAddressByteArray.length == 16) {
                for (byte byteNum : ipAddressByteArray) {
                    workerId += byteNum & 0B111111;
                }
            } else {
                throw new IllegalStateException("初始化获取workId失败,错误的ip地址!");
            }
        } catch (Exception e) {
            log.error("初始化获取workId失败", e);
            throw new IllegalStateException("初始化获取workId失败!");
        }
        return workerId + "";
    }
}
