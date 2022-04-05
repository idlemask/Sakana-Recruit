package name.sakanacatcher.recruit.auth.authorization;

import name.sakanacatcher.recruit.auth.authorization.entity.Token;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthorizationServerApplication.class)
public class TokenTest {
    @Test
    public void testEntity() {
        Token token = new Token("123456789012345678901234567890");
        System.out.println(token);
    }
}
