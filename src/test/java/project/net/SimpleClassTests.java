package project.net;

import org.apache.tomcat.jni.Local;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class SimpleClassTests {
    @Autowired
   private MessageSource messages;

    @Test
    public void testOne() {
        System.out.println(new BCryptPasswordEncoder().encode("password"));
    }


    public void testLocale() {
        messages.getMessage("message.resetPassword", null, Locale.CANADA);
    }
}
