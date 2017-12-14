package pl.edu.agh.iosr.microservices.crud.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.junit.rules.ExternalResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class LocalDynamoDbRule extends ExternalResource {

    private DynamoDBProxyServer server;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    public LocalDynamoDbRule() {
        System.setProperty("sqlite4java.library.path", "native-libs");
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("access", "secret")))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8088", "ap-southeast-2"))
                .build();
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(amazonDynamoDB);
    }

    @Override
    protected void before() throws Throwable {
        try {
            this.server = ServerRunner.createServerFromCommandLineArgs(new String[]{"-delayTransientStatuses", "-inMemory", "-port", "8088"});
            server.start();
            amazonDynamoDB = amazonDynamoDB();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void after() {
        if (server != null) {
            try {
                server.stop();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void createTable(Class<?> clazz) {
        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDB);
        CreateTableRequest createTableRequest = mapper.generateCreateTableRequest(clazz);
        createTableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        amazonDynamoDB.createTable(createTableRequest);
    }

    public void deleteTable(Class<?> clazz) {
        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDB);
        DeleteTableRequest deleteTableRequest = mapper.generateDeleteTableRequest(clazz);
        amazonDynamoDB.deleteTable(deleteTableRequest);
    }
}