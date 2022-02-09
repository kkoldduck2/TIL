# NoSql

### what are NoSQL databases?

NoSql 데이터 베이스는

- 관계형 데이터베이스(표 형태로 저장됨)보다 더 다양한 모델을 이용하는 데이터의 저장 및 조회를 위한 메커니즘을 제공한다.
- big data와 real-time web 애플리케이션에서 자주 사용된다

### How Does a NoSql(non-relational) Database work?

- NoSql databases는 데이터에 접근하고 관리하는데 있어 **다양한 데이터 모델**들을 사용한다.
- 따라서 (관계형 데이터 베이스처럼 data consistency 제한 사항들이 없기 때문에) flexible한 데이터 모델, **low latency, large data volume**을 필요로 하는 애플리케이션들에게 NoSql은 최적의 선택이다.
- **관계형 데이터베이스와의 비교**를 통해 NoSql이 어떤 식으로 저장되는지 알아보자
    - 관계형 DB에서는 book 레코드는 종종 normlized되어 여러 분리된 테이블에 저장된다. 그 테이블 간의 관계는 PK와 FK로 정의된다.
        
        예를 들어, **Books** 테이블에는 ISBN, BookTitle, Edition Number 컬럼이 존재하며, **Authors** 테이블은 AuthorID, Author Name 컬럼으로 구성된다. 그리고 **Author-ISBN** 테이블은 AuthorID와 ISBN 컬럼으로 구성된다. 이처럼 관계형 모델은 데이터베이스 내의 테이블간에 참조 통일성 (referential integrity)을 강제하고 data의 redundancy를 줄임으로써 storage를 최적화한다. 
        
    - NoSql 데이터베이스에서는 하나의 book record는 **JSON document 형태로 저장**된다. 각각의 책은 **ISBN**, **Book Title**, **Edition Number**, **Author Name**, and **AuthorID가** 하나의 document에 속성으로써 저장된다.
        
        따라서 이 모델은 직관적인 개발과 horizontal scalability에 최적화 되어있다. 
        

### Why should you use a NoSql datatbase?

- **Flexibility**
- **Scalability**
    
    : NoSql database는 일반적으로 distributed clusters of hardware를 사용함으로써 스케일 아웃을 하기위해 설계되었다. 
    
- **High-performance**
    
    : NoSql database는 더 높은 performance를 가능하게 하는 특정 데이터 모델들과 특정 access pattern들에 최적화 되어 있다. 
    
- **Highly functional**
    
    : NoSql database는 유용한 API들과 data type들을 제공한다. 
    

### Types of NoSQL Databases

1. **Key-value**
    - Key-value 방식으로 데이터를 저장한다. 여기서 key는 unique identifier로써 동작한다. key와 value는 어떤 형태든 가능하다. 단순한 object일 수도 있고 복잡한 compound object일 수도 있다.
    - key-value 데이터베이스는 highly partitionable하고 수평적 스케일링이 가능하다.
    - 예: **Dynamo DB**
    
    ![image](https://user-images.githubusercontent.com/47748246/153099822-87acc68f-ba8f-4bdb-af99-fcbea801008b.png)
    

2. **Document**
    - document database는 데이터를 JSON 형태의 document로 저장하거나 질의한다.
    
    ```java
    [
        {
            "year" : 2013,
            "title" : "Turn It Down, Or Else!",
            "info" : {
                "directors" : [ "Alice Smith", "Bob Jones"],
                "release_date" : "2013-01-18T00:00:00Z",
                "rating" : 6.2,
                "genres" : ["Comedy", "Drama"],
                "image_url" : "http://ia.media-imdb.com/images/N/O9ERWAU7FS797AJ7LU8HN09AMUP908RLlo5JF90EWR7LJKQ7@@._V1_SX400_.jpg",
                "plot" : "A rock band plays their music at high volumes, annoying the neighbors.",
                "actors" : ["David Matthewman", "Jonathan G. Neff"]
            }
        },
        {
            "year": 2015,
            "title": "The Big New Movie",
            "info": {
                "plot": "Nothing happens at all.",
                "rating": 0
            }
        }
    ]
    ```
    
3. **Graph**
    - Graph database는 relationships을 저장하거나 탐색하기 위해 사용한다.
    - Graph database는 data entities를 저장하기 위해 node를 사용하고, 그들간의 관계를 edges로 저장한다. edge는 항상 start node, end node, type, direction을 포함한다. 그 뿐만 아니라 edge는 부모-자식 관계, actions, ownership 등을 표현할 수 있다.
    - social networking, recommendation engines, fraud detection의 상황에서 자주 사용된다.
    

![image](https://user-images.githubusercontent.com/47748246/153099850-cee4f761-6fc2-483c-8369-cda841b6e216.png)

4. **In-memory**
    - 일반적인 데이터베이스가 데이터를 Disk나 SSD에 저장하는 것과 달리, In-memory database는 메모리에 저장한다. 따라서 응답시간이 매우 빠르나, 프로세스나 서버가 재기동될 때마다 데이터를 잃어버릴 수 있는 risk가 존재한다.
    - 예 : Redis

5. **Search**
    - search-engine 데이터베이스는 데이터를 찾는 용도로만 사용된다. 비슷한 특징의 데이터들을 카테고라이징하기 위해 indexes를 사용한다.
    

참고

[https://aws.amazon.com/nosql/?nc1=h_ls](https://aws.amazon.com/nosql/?nc1=h_ls)
