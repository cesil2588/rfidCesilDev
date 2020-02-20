package com.systemk.spyder.Config.MultiDataBase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ChainTransactionConfig {

    @Bean
    @Primary
    public PlatformTransactionManager chainTransactionManager(PlatformTransactionManager mainTransactionManager,
                                      PlatformTransactionManager externalTransactionManager,
                                      PlatformTransactionManager openDbTransactionManager){
        return new ChainedTransactionManager(mainTransactionManager, externalTransactionManager, openDbTransactionManager);
    }
}
