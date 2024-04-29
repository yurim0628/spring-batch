package com.example.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.database.support.DataFieldMaxValueIncrementerFactory;
import org.springframework.batch.item.database.support.DefaultDataFieldMaxValueIncrementerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.SqlServerMaxValueIncrementer;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    private static final String ISOLATION_REPEATABLE_READ = "ISOLATION_REPEATABLE_READ";

    @Autowired
    private DataSource dataSource;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Bean
    public JobRepository jobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(platformTransactionManager);
        factory.setValidateTransactionState(true);
        factory.setIsolationLevelForCreate(ISOLATION_REPEATABLE_READ);
        factory.setIncrementerFactory(customIncrementerFactory());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public SimpleJobLauncher jobLauncher(JobRepository jobRepository) {
        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(jobRepository);
        return simpleJobLauncher;
    }

    private DataFieldMaxValueIncrementerFactory customIncrementerFactory() {
        return new CustomDataFieldMaxValueIncrementerFactory(dataSource);
    }

    private class CustomDataFieldMaxValueIncrementerFactory extends DefaultDataFieldMaxValueIncrementerFactory {

        CustomDataFieldMaxValueIncrementerFactory(DataSource dataSource) {
            super(dataSource);
        }

        @Override
        public DataFieldMaxValueIncrementer getIncrementer(String incrementerType, String incrementerName) {
            DataFieldMaxValueIncrementer incrementer = super.getIncrementer(incrementerType, incrementerName);
            if (incrementer instanceof SqlServerMaxValueIncrementer) {
                ((SqlServerMaxValueIncrementer) incrementer).setCacheSize(20);
            }
            return incrementer;
        }
    }
}
