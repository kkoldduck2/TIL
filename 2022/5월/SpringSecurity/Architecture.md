## DelegatingFilterProxy

![image](https://user-images.githubusercontent.com/47748246/166604488-8d532cfe-409e-4a30-90b4-a2cdb81d9e72.png)


- `DelegatingFilterProxy` : Spring에서 제공하는 (Servlet) Filter 구현체
- Servlet Container는 자신의 standard를 사용한 Filter들을 등록함. 그러나 Spring 빈은 전혀 인지하지 못한다.
- `DelegatingFilterProxy`는 표준 서블릿 컨테이너 매카니즘을 통해 Filter처럼 등록되는 동시에, Filter를 구현한 스프링 빈에 모든 작업을 위임한다.
- 따라서 DelegatingFilterProxy는 Servlet container의 life cycle과 Spring ApplicationContext를 잇는 다리라고 할 수있다.

**동작 방식**

- DelegatingFilterProxy는 ApplicationContext에서 *Bean Filter0*을 찾아 호출한다.
- DelegatingFilterProxy의 pseudo code는 다음과 같다.

```java
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
	// Lazily get Filter that was registered as a Spring Bean
	// For the example in DelegatingFilterProxy, delegate is an instance of Bean Filter0
	Filter delegate = getFilterBean(someBeanName);
	// delegate work to the Spring Bean
	delegate.doFilter(request, response);
}
```

- DelegatingFilterProxy의 또 다른 장점은 Filter bean 인스턴스를 찾는 것을 딜레이 시킨다는 것이다.
    - 서블릿 컨테이너는 컨테이너가 시작되기 전에 Filter 인스턴스를 등록해야하는데, 스프링이 스프링 빈을 등록하는데 사용하는 ContextLoaderListener가 Filter 인스턴스가 등록되어야 하는 시점에 스프링 빈을 등록하지 못하기 때문이다.

## FilterChainProxy

![image](https://user-images.githubusercontent.com/47748246/166604515-46027c50-25c1-4cf1-a2de-7c6a2bad8ea0.png)


FilterChainProxy

- `FilterChainProxy`는 Spring security에 의해 제공되는 특별한 `Filter`이다.
- 이는 `SecurityFilterChain`을 통해 많은 `Filter` 인스턴스들에게 위임되도록 한다.
- `FilterChainProxy` 역시 빈이기 때문에, `DelegatingFilterProxy`에 의해 감싸져야 한다.
- FilterChainProxy는 Spring Security의 서블릿 지원의 시작점이 된다.

## SecurityFilterChain

![image](https://user-images.githubusercontent.com/47748246/166604536-bb89ad1e-e139-4588-a8d2-aa5c90751bcf.png)

SecurityFilterChain

- `FilterChainProxy`는 `**SecurityFilterChain` 를 사용**하여 특정 request에 **어떤 Spring Security `Filter`들이 사용되어야 하는지를 결정**함
- `SecurityFilterChain` 안의 `Security Filter`들은 일반적으로 Bean이다.
- 이 빈들은 `DelegatingFilterProxy`에 의해 등록되는 것이 아니라, **FilterChainProxy에 의해 등록**된다.

![image](https://user-images.githubusercontent.com/47748246/166604558-559cbc42-9c21-4e93-a7b7-fdf0703b567e.png)

Multiple SecurityFilterChain

- 위 그림에서 보다시피, FilterChainProxy(Bean Filter0)는 어떤 SecurityFilterChain을 사용할지를 결정한다.
- 가장 첫번째로 매치되는 SecurityFilterChain 만이 호출된다.
