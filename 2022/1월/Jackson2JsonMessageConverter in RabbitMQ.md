### Jackson2JsonMessageConverter

**1) Jackson2JsonMessageConverter 사용 전**

- RabbitMQ를 통해 주고 받는 메시지는 반드시 String 형식이어야 한다.
- 왜냐하면 메시지를 전달할 때 사용하는 RabbitTemplate의 default message converter (SimpleMessageConverter)가 input data type으로 string을 받기 때문이다.
- 따라서 Object  메시지를 보내고 싶으면 ObjectMapper를 사용해서 object를 string 형식으로 변환한 뒤 보내야 했다.
    
    ```java
    private ObjectMapper objectMapper = new ObjectMapper();
    
    public void sendDummy(DummyMessage message){
        String s = objectMapper.writeValueAsString(message);
        rabbitTemplate.convertAndSend("exchange", "routingkey", s);
    }
    ```
    

    

**2) Jackson2JsonMessageConverter 사용 후** 

- Jaskson2JsonMessageConverter는 SimpleMessageConverter와는 다르게 input data type으로 object를 받는다.
- 따라서 rabbitTemplate의 default converter를 Jackson2JsonMessageConverter로 변경하면 더 이상 object를 string으로 변환하지 않아도 된다.
- 또한, Jackson2JsonMessageConveter로 변환한 메세지는 body의 형식이 JSON message 형식으로 변환된다.


**3) How to use Jackson2JsonMessageConverter**

- Jackson2JsonMessageConverter를 사용하기위해선 rabbittemplate의 default message converter를 변경해야 한다.

```java
@Configuration
public class RabbitmqConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());

        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
```

**4) 변경 전, 후 Message Sender 코드 비교**

- 변경 전
    
    ```java
    private ObjectMapper objectMapper = new ObjectMapper();
    
    public void sendDummy(DummyMessage message){
        String s = objectMapper.writeValueAsString(message);
        rabbitTemplate.convertAndSend("exchange", "routingkey", s);
    }
    ```
    

- 변경 후
    
    ```java
    public void sendDummy(DummyMessage message){
        rabbitTemplate.convertAndSend("x.dummy", "", message);
    }
    ```
