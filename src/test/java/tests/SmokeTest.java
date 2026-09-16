package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SmokeTest extends BaseTest {
    @Test
    public void pageOpens(){
        String title = driver.getTitle();
        Assertions.assertEquals("Swag Labs", title);
    }
}
