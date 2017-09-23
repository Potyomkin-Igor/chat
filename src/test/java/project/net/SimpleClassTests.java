package project.net;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class SimpleClassTests {


    @Test
    public void testOne() {
        System.out.println(new BCryptPasswordEncoder().encode("password"));
    }
}
