package com.ing.product.app;

import javax.jms.ConnectionFactory;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import com.tibco.tibjms.TibjmsConnectionFactory;

@EnableJms
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {MessageSender.class})
public class MessageConfig {

	@Bean
	public DefaultJmsListenerContainerFactory myFactory(
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory =
				new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory());
		return factory;
	}
	
	@Bean
	  public ConnectionFactory connectionFactory() {
		TibjmsConnectionFactory connectionFactory = new TibjmsConnectionFactory("tcp://localhost:7222");
		connectionFactory.setUserName("admin");
		connectionFactory.setUserPassword("admin");
	    return connectionFactory;
	  }
	
}
