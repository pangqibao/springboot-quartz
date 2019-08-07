package com.boo.quartz.config.quartz;

import com.alibaba.druid.pool.DruidDataSource;
import com.boo.quartz.constants.JobConst;
import com.boo.quartz.util.ApplicationUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.sql.SQLException;
import java.util.Properties;

/**
 * @ClassName QuartzConfig
 * @Description quartz 默认配置文件
 * @Author boo
 * @Date 2019/8/7 9:30
 * @Version 1.0.0
 */
@Configuration
public class QuartzConfig {
    @Value("${job.mysql.url}")
    private String url;

    @Value("${job.mysql.username}")
    private String username;

    @Value("${job.mysql.password}")
    private String password;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws SQLException {
        SchedulerFactoryBean schedulerFactoryBean=new SchedulerFactoryBean();
        schedulerFactoryBean.setSchedulerName(JobConst.SCHEDULER);
        schedulerFactoryBean.setDataSource(getDruidDataSource());
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContextKey");
        schedulerFactoryBean.setApplicationContext(ApplicationUtil.getApplicationContext());
        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setStartupDelay(15);
        schedulerFactoryBean.setQuartzProperties(getProperties());
        return schedulerFactoryBean;
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        properties.put("org.quartz.scheduler.instanceName", JobConst.SCHEDULER);
        properties.put("org.quartz.scheduler.instanceId", "AUTO");
        properties.put("org.quartz.scheduler.skipUpdateCheck", "true");
        properties.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        properties.put("org.quartz.threadPool.threadCount", "32");
        properties.put("org.quartz.threadPool.threadPriority", "5");
        properties.put("org.quartz.jobStore.misfireThreshold", "60000");
        properties.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        properties.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        properties.put("org.quartz.jobStore.useProperties", "true");
        properties.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        properties.put("org.quartz.jobStore.isClustered", "true");
        properties.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
        properties.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");
        return properties;
    }

    private DruidDataSource getDruidDataSource() throws SQLException {
        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(1);
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(20);
        // 获取连接等待超时的时间
        dataSource.setMaxWait(60000);
        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(30000);
        dataSource.setValidationQuery("SELECT * FROM QRTZ_LOCKS");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxOpenPreparedStatements(20);
        dataSource.setFilters("stat");
        dataSource.setDefaultAutoCommit(true);
        return dataSource;
    }
}
