package com.testtask.clinic.soap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    // bean definitions
	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
	    MessageDispatcherServlet servlet = new MessageDispatcherServlet();
	    servlet.setApplicationContext(applicationContext);
	    servlet.setTransformWsdlLocations(true);
	    return new ServletRegistrationBean<>(servlet, "/makeShedule/*");
	}
	
	@Bean(name = "shedule")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema sheduleSchema) {
	    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
	    wsdl11Definition.setPortTypeName("ShedulePort");
	    wsdl11Definition.setLocationUri("/makeShedule");
	    wsdl11Definition.setTargetNamespace("http://www.testtask.com/clinic/soap/gen");
	    wsdl11Definition.setSchema(sheduleSchema);
	    return wsdl11Definition;
	}

	@Bean
	public XsdSchema sheduleSchema() {
	    return new SimpleXsdSchema(new ClassPathResource("sheduleRequestResponce.xsd"));
	}

}