package spring.custom.security;

import org.junit.Test;

import java.util.Base64;

public class AppTest {

    @Test
    public void base64Test() {
        String credential = "username:password";
        String token = Base64.getEncoder().encodeToString(credential.getBytes());
        System.out.println("token: " + token);

        byte[] decode = Base64.getDecoder().decode(token);
        String decodedString = new String(decode);
        System.out.println("decoded: " + decodedString);
    }
}
