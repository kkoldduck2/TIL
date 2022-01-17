# Message Queue

### Message queue란 무엇이며 언제 사용되는가.

> Message queuing은 애플리케이션 간 통신에 사용된다.
Message queue는 Destination program이 바쁘거나 연결이 되지 않았을 때, 임시 메시지 저장소로 쓰인다.
> 

### 메시지 큐의 기본 구조
![image](https://user-images.githubusercontent.com/47748246/149721425-7f952f4a-c55b-4864-a4d6-7fe4a1dfd4cf.png)

- Producer : 메시지를 생산. 큐에 전송
- consumer : 큐로 부터 메시지를 가져온다.

### 메시지 큐의 기능

> A message queue provides an **asynchronous communications protocol**
this system puts a message onto a message queue and **does not require an immediate response** to continuing processing.

Email is probably the best example of asynchronous communication

This way of handling messages **decouples the producer from the consumer** so that they do not need to interact with the message queue **at the same time**
> 

**1) Decoupling and Scalability**

> **Decoupling이란** System의 한 부분이 다른 부분에 얼마나 의존하고 있는가를 나타낸다. 
또한 Decoupling을 통해 기능들을 분리하여 각 기능이 더 self-contained 되도록 한다 .
Decoupled된 system에서는 시스템들이 서로 연결되지 않고도 통신을 할 수 있다. 각 시스템들은 완전히 독립적이며 다른 시스템을 전혀 인지하고 있지 않다.
> 

만약 decoupled system에 속해있는 한 프로세스가 메시지를 처리하는데 실패했을때, 다른 메시지는 여전히 큐에 추가될 수 있으며, 시스템이 복구되었을 때 다시 처리될 수 있어야 한다. 

우리는 메시지 큐를 통해 위와 같은 process delay를 할 수 있다. 

또한 producer는 consumer가 바쁜지 안 바쁜지와 관계없이 처리할 작업들을 큐에 추가하고 바로 다음 작업을 수행할 수 있다. 

이처럼 하나의 큰 시스템을 만드는 대신, 기능 별 작은 시스템을 여러 개 만들고, 각 시스템이 메시지 큐를 통해 비동기적으로 통신할 수 있도록 하는 것이 더 좋다. 이 방법을 통해 애플리케이션의 각 부분들이 독립적으로 개발/유지보수 될 수 있기 때문이다. 

**2) Message queuing  사용 예**

- 문제
    
    만약 당신이 많은 request들을 처리하는 웹 서비스를 만든다고 생각해보자. 모든 request들은 놓치는 것 하나 없이 처리되어야 하며, 하나의 function에 의해 처리된다. (function의 throughput은 높다) 다시 말해, 이 웹 서비스는 높은 가용성을 지녀야 하며, 새로운 request들을 받을 준비가 되어있어야 하고, 이전 request들을 처리하느라 lock되어서는 안된다. 
    

- 해결 방안
    
    이런 경우 웹 서비스와 processing 서비스 간에 메시지 큐를 배치하는 것이 이상적이다. 웹 서비스는 “start processing” 메시지를 큐에 추가하고, 다른 프로세스는 해당 메시지를 가져와서 처리하도록 한다. 이 두 프로세스는 서로 decoupled되어 있으며, 다른 한쪽의 작업이 끝날 때까지 기다릴 필요가 없다. 
    
    또한, workload가 증가하여 system을 scale up 해야 할 경우, consumer들을 추가함으로써 해결할 수 있다. 
    

### Rabbit MQ와 AMQP

- RabbitMQ 는 Advanced Message Queuing Protocol (AMQP)를 구현한 message-oriented middleware 오픈소스이다.

[https://www.cloudamqp.com/blog/what-is-message-queuing.html](https://www.cloudamqp.com/blog/what-is-message-queuing.html)

# RabbitMQ Exchanges, routing keys and bindings

> 1. Exchange, Routing key, binding이란 무엇인가?
2. Exchange들과 Queue들이 어떻게 연결되는가?
3. Exchange, Routing key, binding를 언제 어떻게 사용하는지 안다.
> 

### Exchange

- Producer는 큐에 바로 메시지를 publish하지 않는다. 대신 Exchange에 메시지를 publish한다.
- Exchanges는 message routing agents(주체)이며, RabbitMQ 내부의 virtual host에 의해 정의된다.
- Exchange는 헤더 정보, binding, routing key들을 기반으로 메시지를 여러 큐에 routing한다.

**Binding** : queue와 exchange을 연결하기 위한 것

**routing key** : routing key는 메시지 attribute 중 하나로, exchange가 이를 통해 어느 큐로 메시지를 라우팅 할 지 결정한다. 

**Exchange type**

- Direct : 특정한 하나의 큐에 전달
- Fan-out : 모든 큐에 전달 (broad cast)
- Topic : rule이나 pattern이 일치하는 하나 이상의 큐에 전달

[https://www.cloudamqp.com/blog/part4-rabbitmq-for-beginners-exchanges-routing-keys-bindings.html](https://www.cloudamqp.com/blog/part4-rabbitmq-for-beginners-exchanges-routing-keys-bindings.html)
