package pl.edu.agh.iosr.microservices.crud.config;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.KeyFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("production")
public class DatastoreConfig {

    @Value("${google.cloud.datastore.kind}")
    private String datastoreKind;

    @Bean
    public Datastore datastore() {
        return DatastoreOptions.getDefaultInstance().getService();
    }

    @Bean
    public KeyFactory keyFactory() {
        return datastore().newKeyFactory().setKind(datastoreKind);
    }
}
