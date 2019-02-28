package com.example;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class SimpleRouteBuilder extends RouteBuilder {

	int count;

	@Override
	public void configure() throws Exception {

		//interceptFrom("*").process(new Processor() {
		intercept().process(new Processor() {
			public void process(Exchange exchange) {
				count++;
				
				System.out.println("interceptor called " + count + " times " + exchange.getIn().getBody()
						+" "+exchange.getFromEndpoint());

			}
		});

		from("file:F:/data/camel-interceptor/inbox?noop=true").split().tokenize("\r\n").to("jms:queue:queue1");
		from("jms:queue:queue1").to("jms:queue:queue2");
		from("jms:queue:queue2").to("jms:queue:queue3");
	}

}