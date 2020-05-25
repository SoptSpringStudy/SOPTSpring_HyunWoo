# SOPT 충무로 스터디
26기 YB 안드로이드 이현우의 스프링 스터디 다이어리

## 2주차 스터디
### 스프링 설정 파일(XML) 분리
Q) 왜 XML 파일을 분리하려 하는가?
A) XML 파일이 길어지면 관리가 힘들어지고 가독성이 떨어져

```src/main/resource``` 파일 내에 xml 파일을 "기능"별로 쪼개서
각 기능별로 이름 지어서 만드는게 좋음

여러 개의 XML 파일을 호출하려 할 때
1) 파일 이름을 ```String[]```에 저장해 ```GenericXmlApplicationContext```의 Argument에 대입

```main``` 메소드 중 일부
```
String[] arguments = {"classpath:appCtx1.xml", "classpath:appCtx2.xml", "classpath:appCtx3.xml"};
GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(arguments);
```

2) XML 파일에 ```<import>``` 태그를 삽입 한 XML 파일로 전부 불러온다

```appCtx1.xml``` 중 일부
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

  <import resource = "appCtx2.xml"/>
  <import resource = "appCtx3.xml"/>
  
</beans>
```

위와 같이 ```appCtx1```을 읽어올 때 ```appCtx2.xml```, ```appCtx3.xml```을 모두 불러올 수 있음

### Bean(객체)의 범위
+ 싱글톤(Default)
컨테이너를 통해 객체가 생성될 때 어떠한 설정을 건들지 않을 시, 싱글톤 패턴으로 객체가 생성
컨테이너에서 각 정의마다 한 객체만 생성
따라서 ```main``` 메소드에서 동일 클래스의 인스턴스에 대해서 ```getBean()``` 메소드를 호출하더라도
오로지 "한 번"만 생성
=> 생성자는 오직 한 번만 호출

+ 프로토타입(Prototype)
xml 파일 내의 ```bean```태그 내 ```scope``` 설정을 ```scope = "prototype"```으로 하면
싱글톤 패턴과는 달리 ```getBean()```을 호출할 시 일반적인 Java 인스턴스를 생성하는 것과 마찬가지로
여러번 생성자가 호출됨

### 의존객체 자동 주입
의존 대상 객체를 명시하지 않아도 컨테이너가 자동으로 필요한 대상 객체를 찾아 객체를 주입시켜주는 기능
+ @Autowired
주입하려고 하는 객체의 타입이 일치하는 객체를 자동으로 주입한다
