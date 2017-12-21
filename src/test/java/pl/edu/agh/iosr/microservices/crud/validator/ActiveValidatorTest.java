package pl.edu.agh.iosr.microservices.crud.validator;

import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = {"should.perform.validation = true"})
public class ActiveValidatorTest extends AbstractValidatorTest {
    @Override
    protected boolean isActive() {
        return true;
    }
}
