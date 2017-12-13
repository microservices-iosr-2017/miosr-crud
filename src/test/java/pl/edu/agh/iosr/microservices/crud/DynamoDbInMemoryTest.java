package pl.edu.agh.iosr.microservices.crud;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.edu.agh.iosr.microservices.crud.model.Note;
import pl.edu.agh.iosr.microservices.crud.service.NotesService;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CrudApplication.class)
@ActiveProfiles("local")
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=http://localhost:8000/",
        "amazon.aws.accesskey=access8",
        "amazon.aws.secretkey=secret14"
})
public abstract class DynamoDbInMemoryTest {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    protected AmazonDynamoDB amazonDynamoDB;

    @Autowired
    NotesService notesService;

    @Before
    public void setup() throws Exception {
        try {
            dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
            CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(Note.class);
            tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
            amazonDynamoDB.createTable(tableRequest);
        } catch (ResourceInUseException e) {
            // Do nothing, table already created
        }
        dynamoDBMapper.batchDelete(notesService.getNotes());
    }
}
