## Servlet Authentication Architecture

### SecurityContextHolder

- `SecurityContextHolder`는 Spring Security의 authentication 모델의 핵심임.
- `SecurityContextHolder`는 SecurityContext를 포함한다.

![image](https://user-images.githubusercontent.com/47748246/166604637-5dc8fa69-0623-4541-9739-b394d393da68.png)

- `SecurityContextHolder`는 Spring Security가 **인증된 사용자 정보를 저장**하는 곳임
- 사용자가 authenticated 되었는지 나타내는 가장 간단한 방법은 `SecurtiyContextHolder`를 세팅하는 것이다.

```java

SecurityContext context = SecurityContextHolder.createEmptyContext();

Authentication authentication =
    new **TestingAuthenticationToken**("username", "password", "ROLE_USER");
// 일반적으로 사용되는 것은 
// UsernamePasswordAuthenticationToken(userDetails, password, authorities)이다.

context.setAuthentication(authentication);

SecurityContextHolder.setContext(context);
```

- 만약 Authenticated principal 정보가 필요하다면, `SecurityContextHolder`에 접근해서 가져오면 된다.

```java
SecurityContext context = SecurityContextHolder.getContext();
Authentication **authentication** = context.getAuthentication();

String username = authentication.getName();
Object principal = authentication.getPrincipal();

Collection<? extends GrantedAuthority> authorities = **authentication**.getAuthorities();
```

- **SecurityContextHolder는 ThreadLocal을 사용하여 사용자 정보(detail)를 저장한다**. 따라서 `SecurityContext`는 같은 스레드 내의 모든 methods에서 접근 가능하다.

### Authentication

- **principal** : identifies the user. username/password 방식으로 authenticating할 때, Principal은 UserDetails의 인스턴스이다.
- **credentials** : often password. 사용자가 authenticated되면, 보안을 위해 이 정보는 삭제된다.
- **authorities** : the `GrantedAuthorities` are high level permissions the user is granted. A few examples are roles or scopes.

### GrantedAuthority

- 사용자에게 부여된 high level 권한 = principal에게 부여된 권한 = roles
- GrantedAuthority는 `Authentication.getAuthorities()` 메서드로 얻을 수 있다.
    
    ```java
    Collection<? extends GrantedAuthority> authorities = **authentication**.getAuthorities();
    ```
    
- username/password 기반의 authentication을 사용할 경우, GrantedAuthority는 주로 `UserDetailsService`에 의해 load 된다.

### AuthenticationManager

- AuthenticationManager는 API이다.
- Spring Security의 필터들이 authentication을 수행하기 위해 **어떻게 동작하는지를 정의**한다.
- ~~AuthenticationManager를 호출한 Controller에 의해 Authentication은 SecurityContextHolder에 세팅된다. 이게 뭔소리지?~~
- AuthenticationManager의 구현체는 무엇이든 될 수 있지만, 주된 구현체는 ProviderManager이다.

### ProviderManager

![image](https://user-images.githubusercontent.com/47748246/166604686-82846222-d344-4193-a48c-ba458f29345b.png)

- ProviderManager는 가장 보편적으로 사용되는 AuthenticationManager 구현체이다.
- 그림에서 보다시피, ProviderManager는 AuthenticationProvider 리스트에 위임한다.
