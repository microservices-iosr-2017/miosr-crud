package pl.agh.edu.iosr.microservices.crud.config;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ComponentScan(basePackages = "pl.agh.edu.iosr.microservices.crud")
@EnableMongoRepositories(basePackages = "pl.agh.edu.iosr.microservices.crud.repository")
@Configuration
public class FongoConfiguration extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "test-db";
    }

    @Bean
    @Override
    public Mongo mongo() {
        return new Fongo("mongo-test").getMongo();
    }
}
