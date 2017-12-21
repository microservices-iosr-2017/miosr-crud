package pl.edu.agh.iosr.microservices.crud.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.agh.iosr.microservices.crud.CrudApplication;

import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrudApplication.class)
@ActiveProfiles("test")
public abstract class AbstractValidatorTest {

    @Autowired
    private Validator validator;

    private static final String CORRECT_TEST_USER = "TestUser";
    private static final String CORRECT_TEST_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJUZXN0VXNlciJ9.1KD4gItK8r8i3w7IlqAifl85tgzAy3nGSG3btvdtVBA";

    private static final String INCORRECT_TEST_USER = "BadUser";
    private static final String INCORRECT_TEST_TOKEN = "BadToken";


    @Test
    public void shouldAcceptCorrectCredentials() {
        MockHttpServletRequest request = prepareRequest(CORRECT_TEST_USER, CORRECT_TEST_TOKEN);
        boolean response = validator.validateHttpRequest(request);
        assertTrue(response);
    }

    @Test
    public void shouldNotAcceptIncorrectUsername() {
        MockHttpServletRequest request = prepareRequest(INCORRECT_TEST_USER, CORRECT_TEST_TOKEN);
        boolean response = validator.validateHttpRequest(request);
        assert(response != isActive());
    }

    @Test
    public void shouldNotAcceptIncorrectToken() {
        MockHttpServletRequest request = prepareRequest(CORRECT_TEST_USER, INCORRECT_TEST_TOKEN);
        boolean response = validator.validateHttpRequest(request);
        assert(response != isActive());
    }

    @Test
    public void shouldNotAcceptIncorrectCredentials() {
        MockHttpServletRequest request = prepareRequest(INCORRECT_TEST_USER, INCORRECT_TEST_TOKEN);
        boolean response = validator.validateHttpRequest(request);
        assert(response != isActive());
    }

    @Test
    public void shouldNotAcceptMissingUsername() {
        MockHttpServletRequest request = prepareRequest(null, CORRECT_TEST_TOKEN);
        boolean response = validator.validateHttpRequest(request);
        assert(response != isActive());
    }

    @Test
    public void shouldNotAcceptMissingToken() {
        MockHttpServletRequest request = prepareRequest(CORRECT_TEST_USER, null);
        boolean response = validator.validateHttpRequest(request);
        assert(response != isActive());
    }

    @Test
    public void shouldNotAcceptMissingCredentials() {
        MockHttpServletRequest request = prepareRequest(null, null);
        boolean response = validator.validateHttpRequest(request);
        assert(response != isActive());
    }

    protected abstract boolean isActive();

    private MockHttpServletRequest prepareRequest(String username, String token) {
        MockHttpServletRequest request = new MockHttpServletRequest();

        if (username != null)
            request.addHeader("username", username);

        if (token != null)
            request.addHeader("token", token);

        return request;
    }
}
