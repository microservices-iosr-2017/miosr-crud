package pl.edu.agh.iosr.microservices.crud.config;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.testing.LocalDatastoreHelper;
import org.junit.rules.ExternalResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.threeten.bp.Duration;

@Configuration
@Profile("test")
public class LocalDatastoreRule extends ExternalResource {

    @Value("${google.cloud.datastore.kind}")
    private String datastoreKind;

    public static LocalDatastoreHelper localDatastoreHelper = LocalDatastoreHelper.create(1.0);

    @Bean
    public Datastore datastore() {
        return localDatastoreHelper.getOptions().getService();
    }

    @Bean
    public KeyFactory keyFactory() {
        return datastore().newKeyFactory().setKind(datastoreKind);
    }

    @Override
    protected void before() throws Throwable {
        localDatastoreHelper.start();
    }

    @Override
    protected void after() {
        try {
            localDatastoreHelper.stop(Duration.ofSeconds(8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}