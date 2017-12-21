package pl.edu.agh.iosr.microservices.crud.validator;

import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = {"should.perform.validation = false"})
public class InactiveValidatorTest extends AbstractValidatorTest {
    @Override
    protected boolean isActive() {
        return false;
    }
}