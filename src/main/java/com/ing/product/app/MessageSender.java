package com.ing.product.app;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

	private static final String SOAP_ACTION = "ProcessDefinition";
	
	  @Autowired
	  private ConnectionFactory connectionFactory;
	  private JmsTemplate jmsTemplate;
	  private Destination destination;

	  @PostConstruct
	  public void init() {
	      this.jmsTemplate = new JmsTemplate(connectionFactory);	      
	  }

	  public Boolean sendMessage(Long id) throws Exception {

		  String messageInput = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:sch=\"http://www.tibco.com/schemas/poc/SharedResources/Schema/Schema.xsd\">\r\n" + 
			  		"   <soap:Header/>\r\n" + 
			  		"   <soap:Body>\r\n" + 
			  		"      <sch:ComplianceRequest>\r\n" + 
			  		"         <sch:CustomerID>" + id +
			  		"</sch:CustomerID>\r\n" +
			  		"      </sch:ComplianceRequest>\r\n" + 
			  		"   </soap:Body>\r\n" + 
			  		"</soap:Envelope>";


		  String corelationId = "COMPLIANCE_IN_" + new Date().getTime();
		  
	      try {
	      jmsTemplate.send("COMPLIANCE_IN", new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message inputMessage = null;
				destination = session.createQueue("COMPLIANCE_OUT");
				session.createConsumer(destination);
				try {
					inputMessage = session.createTextMessage(messageInput);
					inputMessage.setJMSCorrelationID(corelationId);
					inputMessage.setJMSReplyTo(destination);
					inputMessage.setStringProperty("SOAPAction", SOAP_ACTION);
				}catch(Exception ex) {
					ex.printStackTrace();
				}
				System.out.println("input:"+inputMessage);  
				return inputMessage;
			}
		});
	    }catch(Exception ex) {
	    	ex.printStackTrace();
	    }
	      
	   
	   
	    Message messageOutput =  jmsTemplate.receiveSelected(destination, "JMSCorrelationID = '"
                + corelationId + "'");
	    String extractBody = messageOutput.getBody(String.class);
	    System.out.println("output:"+extractBody);
	    String extractedOutput = extractBody.substring(extractBody.indexOf("<ns0:isCompliant>")+17, extractBody.indexOf("</ns0:isCompliant>"));
	    System.out.println("output:"+extractedOutput);
	    return Boolean.valueOf(extractedOutput);
	  }	
	  
	  
}
