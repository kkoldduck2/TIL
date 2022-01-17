# RabbitMQ Spring boot

### Producer

- Configuration
    - producer 측에서는 exchange 정보와 connection 정보만 알면 된다. (큐, binding 정보 필요 없음)
    - producer는 exchange에만 메시지를 보내기 때문
    - exchange는 메시지에 담긴 routing key를 기반으로  라우트한다.
    
    ```java
    package com.cis.rabbitmq.conf;
    
    @Configuration
    public class RabbitMqPubConfiguration {
    	
    //	public static final String topicExchangeName = "spring-boot-topicexchange";
    	public static final String directExchangeName = "amslogmq_exchange";
    	
    	static final String queueName = "amslogmq";
    	static final String host = "localhost";		// ip
    	static final String virtualHost = "test";
    	static final String username = "guest";
    	static final String password = "guest";
    	static final int port = 5672;
    	static final String routingKey = "foo.bar.baz"; 
    	
    	
    	/**
    	 * producer 측에서는 exchange 정보와 connection 정보만 알면 된다. (큐, binding 정보 필요 없음)
    	 * producer는 exchange에만 메시지를 보내기 때문
    	 * exchange는 메시지에 담긴 routing key를 기반으로  라우트한다. 
    	 */
    	
    	@Bean
    	DirectExchange exchange() {
    		return new DirectExchange(directExchangeName);
    	}
    	
    	@Bean
    	ConnectionFactory connectionFactory() {
    		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
    		connectionFactory.setVirtualHost(virtualHost);
    		connectionFactory.setPort(port);
    		connectionFactory.setUsername(username);
    		connectionFactory.setPassword(password);
    		
    		return connectionFactory;
    	}
    }
    ```
    

- 실행 Bean
    
    ```java
    @Component
    public class Publisher implements CommandLineRunner{
    	
    	private final RabbitTemplate rabbitTemplate;
    	
    	public Publisher(RabbitTemplate rabbitTemplate) {
    		this.rabbitTemplate = rabbitTemplate;
    	}
    	
    	@Override
    	public void run(String... args) throws Exception {
    		System.out.println("Sending message...");
    		rabbitTemplate.convertAndSend(RabbitMqPubConfiguration.directExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
    	}
    }
    ```
    

### Consumer

- Configuration
    - Consumer 측은 queue, exchange, binding 정보를 모두 configuration 한다.
    - 그리고 SimpleMessageListenerContainer와 MessageListenerAdapter를 등록하여 메시지를 받는 클래스를 등록한다.
    
    ```java
    @Configuration
    public class RabbitMqSubConfiguration {
    
    	public static final String directExchangeName = "amslogmq_exchange";
    	
    	static final String queueName = "amslogmq";
    	static final String host = "localhost";		
    	static final String virtualHost = "test";
    	static final String username = "guest";
    	static final String password = "guest";
    	static final int port = 5672;
    	static final String routingKey = "foo.bar.baz"; 
    	
    	
    	@Bean
    	Queue queue() {
    		return new Queue(queueName, false);
    	}
    	
    //	@Bean
    //	TopicExchange exchange() {
    //		return new TopicExchange(topicExchangeName);
    //	}
    	
    	@Bean
    	DirectExchange exchange() {
    		return new DirectExchange(directExchangeName);
    	}
    	
    //	@Bean
    //	Binding binding(Queue queue, TopicExchange exchange) {
    //		return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");	// "foo.bar.#" : foo.bar. 로 시작하는 routing key와 함께 보내지는 메시지는 해당 queue로 보내진다. 
    //	}
    	
    	@Bean
    	Binding binding(Queue queue, DirectExchange exchange) {
    		return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    	}
    	
    	@Bean
    	ConnectionFactory connectionFactory() {
    		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
    		connectionFactory.setVirtualHost(virtualHost);
    		connectionFactory.setPort(port);
    		connectionFactory.setUsername(username);
    		connectionFactory.setPassword(password);
    		
    		return connectionFactory;
    	}
    	
    	
    	// listener container -> spring에서 메시지 받아 보려고 하는듯
    	@Bean
    	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
    		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    		container.setConnectionFactory(connectionFactory);
    		container.setQueueNames(queueName);
    		container.setMessageListener(listenerAdapter);
    		return container;
    	}
    	
    	@Bean
    	MessageListenerAdapter listenerAdapter(Receiver receiver) {
    		return new MessageListenerAdapter(receiver, "receiveMessage");
    	}
    }
    ```
    

- 실행 Bean
    
    ```java
    @Component
    public class Subscriber implements CommandLineRunner{
    	
    	private final Receiver receiver;	// ?
    	
    	public Subscriber(Receiver receiver) {
    		this.receiver = receiver;
    	}
    	
    	@Override
    	public void run(String... args) throws Exception {
    		System.out.println("Receiving message...");
    		receiver.getLatch().await(1000, TimeUnit.MILLISECONDS);
    	}
    }
    ```
