package com.ing.message;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ing.product.app.MessageConfig;
import com.ing.product.app.MessageSender;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessageConfig.class)
public class MessageSenderTest {

   @Autowired 
   MessageSender messageSender;

    @Test
    public void test() throws Exception{
    	//messageSender.sendMessage("ING-111111");
    }

}
