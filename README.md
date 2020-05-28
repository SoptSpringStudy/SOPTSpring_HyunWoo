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
컨테이너 내의 객체들 타입을 확인 -> 일치하는 타입의 객체가 있으면 주입
생성자 주입(construct-arg), 세터 주입(property)를 사용하지 않아도 자동으로 주입할 수가 있다.
생성자에 @Autowired 어노테이션을 붙여서 컨테이너 내의 객체 중에 찾아서 주입

```<context:annotation-config/>``` : 어노테이션을 사용하기 위해서는 이 태그를 추가

+ @Resource
주입하려고 하는 객체의 이름이 일치하는 객체를 자동으로 주입
Bean의 id를 확인하면서 주입
@Resource는 생성자에 사용하지 못함, Property와 메서드에만 사용 가능
이때도 디폴트 생성자는 만들어 줘야함

### 의존객체 선택
다수의 빈 객체 중 의존 객체의 대상이 되는 객체를 선택하는 방법
eg) 동일한 타입의 객체가 다수 정의되어 있을 때 예외가 발생하지 않고 하나만 주입시킨다.

#### <qualifier value = “아이디”/>
#### @Qualifier(“아이디”)
태그와 어노테이션으로 구분 가능
변수 이름과 빈 객체의 id가 동일하면 의존 객체가 위치에 맞게 주입됨
의존 객체를 자동 주입하려 하는데 xml에 객체를 정의하지 않는 경우 에러가 발생, 그러나 에러를 안 뜨게 하고 싶으면

#### @Autowired(required = false)
이용하면 해당 부분에 객체가 주입이 안 되어도 에러가 발생 안 함

#### @Inject
@Autowired 속성과 동일하다고 생각하면 됨
@Named(value = “아이디”): value 값에 bean의 id를 적으면 해당 객체를 주입한다

## 3주차 스터디
### 스프링 컨테이너 생명주기
#### 스프링 컨테이너 생성
```GenericXmlApplicationCotext xml = new GenericXmlApplicationCotext(“classpath: ~.xml”);```
컨테이너 생성 시점과 빈 객체의 생성시점(의존관계 형성)은 동일

#### 스프링 컨테이너 소멸
```ctx.close()```
메모리에서 해제, 내부 Bean 객체들도 소멸

### Bean 객체 생명주기
Bean 객체의 생명주기는 컨테이너의 생명주기와 같이 한다. 즉 스프링 컨테이너가 생성될 시 빈이 생성되고 소멸될 시 같이 소멸된다.

인터페이스 InitializingBean, DisposableBean들을 통해 afterPropertiesSet, destroy 메서드를 구현할 수 있고 Bean 객체를 생성할 때 어떠한 로직을 처리하고 싶으면 afterPropertiesSet, 소멸될 때 처리하고 싶으면 destroy를 구현해서 사용하면 된다.

DB 연결을 위한 id, pw 인증 등의 작업을 이 때 처리

### init-method, destroy-method 속성
```<bean init-method = “생성시점 호출함수 이름” destroy-method = “소멸시점 호출함수”>```
InitializingBean, DisposableBean 구현하는 것과 동일한 기능

### XML 파일 대신 Java 파일을 이용해서 Bean을 제작하자!
Motivation) 여태까지 XML 파일을 이용해서 객체를 생성했는데 그보다 더 직관적으로(개발자스럽게) 객체를 만들어볼 수 있지 않을까?
스프링: 그런 당신을 위해 준비했습니다

#### @Congiguration 어노테이션 클래스
```@Configuration```은 클래스 상단에 작성하여 해당 클래스가 외부 설정 클래스임을 확인시킨다.
```
@Configuration
public class MemberConfig(){
}
```

#### 일반적인 싱글턴 객체 생성
```@Bean```어노테이션을 활용해서 Bean 생성, 생성 메서드의 이름은 xml bean 태그에서 id 역할
```
@Bean
public ClassName className(){
       return new ClassName();
}
```

#### [심화] 만약 싱글턴으로 객체를 생성하고 싶지 않다면?
```@Scope``` 어노테이션을 사용하여 해당 Bean의 범위를 제어할 수 있다
```
@Bean
@Scope(value = "prototype")
public ClassName className(){
       return new ClassName();
}
```

위와 같이 생성한다면 일반 Java 객체처럼 Bean을 생성할 수 있다.

#### DI by Constructor, Setter
+ 생성자 주입 방법
```
@Bean
public ClassName service(){
       return new ClassName(new ClassName2());
}
```

+ Setter 주입 방법
```
@Bean
public ClassName infoDev(){
       ClassName info = new ClassName();
       info.setInfo("~");
       return info;
}
```

클래스 내부에서 객체를 생성하고 Setter로 멤버 변수 값을 설정하고 객체를 반환한다

+ List, Map 타입 Setter로 주입
```
public ClassName service(){
ClassName info = new ClassName();
ArrayList<T> property = new ArrayList<T>();
	property.add(“A”);
	property.add(“B”);
	property.add(“C”);
	info.setProperty(property);

	Map<T1, T2> property = new HashMap<T1, T2>();
	property.put(“A”, “Answer”);
	property.put(“B”, “Bnswer”);
	info.setProperty(property);
	
	return info;
}
```

#### @Configuration 클래스를 이용한 Application Context 제작
XML 파일을 이용할 때는 ```GenericXmlApplicationContext``` 클래스를 활용했다면, 어노테이션 클래스를 활용할 때는 ```AnnotationConfigurationApplicationContext```를 활용한다

```
AnnotationConfigurationApplicationContext ctx = 
new AnnotationConfigurationApplicationContext(“Classname.class”);
```

이외에는 
