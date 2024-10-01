package banquemisr.challenge05.task.management.service.configuration;


import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTransactionManager(mongoDatabaseFactory);
    }

    @NotNull
    @Override
    protected String getDatabaseName() {
        return databaseName;
    }
}
