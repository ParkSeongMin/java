package com.seongmin.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAnnotation {

	public int number();

	public String text() default "This is first annotation";

	
	/*
	
	
* 어노테이션(Annotation) 이란 ?
- 클래스나 메소드 등의 선언 시에 @ 를 사용하는 것을 말한다.
참고로 클래스, 메소드, 변수 등 모든 요소에 선언할 수 있다.
메타 데이터(Metadata) 라고도 불리며, JDK 5 부터 등장했다고 한다.


* 언제 사용할까?
- 컴파일러에게 정보를 알려주거나
- 컴파일 할 때와 설치 시의 작업을 지정하거나
- 실행할 때 별도의 처리가 필요할 때


* 일반적으로 사용가능한 Annotation 3가지 (JDK 6 기준)

(1) @Override
- 해당 메소드가 부모 클래스에 있는 메소드를 오버라이드 했다는 것을 명시적으로 선언한다.
자식 클래스에 메소드가 여러 개 있을 때 어떤 메소드가 오버라이드 된 것인지
쉽게 알 수 있고, 깜빡하고 빼먹은 매개변수가 있는지 컴파일러에게 알려달라고 생각하면 된다.
이클립스 같이 편리한 개발툴을 사용하다 보면 자주 볼수 있는 키워드다.

(2) @Deprecated
- 더 이상 사용되지 않은 클래스나 메소드 앞에 추가해준다.
'그냥 지워버리면 되는 거 아닌가?' 라고 생각할 수 있지만 여러 사람이 개발하는 프로젝트에서
갑자기 있던 걸 훅 지워버리면... 욕 엄청 먹을 수 있다ㅋ 이런 알림을 통해 서서히 지우자.
나의 경우 안드로이드 개발할 때 API 문서에서 이 키워드를 몇 번 본 적이 있다.

(3) @SuppressWarnings
- 프로그램에는 문제가 없는데 간혹 컴파일러가 경고를 뿜을 때가 있다.
내 성격상 경고 뿜어대는 게 은근 신경쓰이고 거슬릴 때가 있다. 
그럴 때는 이 Annotation을 추가해서 컴파일러에게 
'나도 알고 있고 일부러 그런 거다 그러니 좀 닥치고 있어'
라고 말해주는 거라고 생각하면 된다. 하지만 거슬린다고 너무 남용하진 말자.

- 참고로 다른 Annotation 과 다르게 속성값을 지정해 줄 수도 있다.
ex) @SuppressWarnings("deprecation"), @SuppressWarnings("serial")
개발하다보면 특히 저 serial 어쩌구 저쩌구를 자주 보는데 그럴 때는
http://blog.naver.com/fochaerim/70105895049 여기 링크를 통해 해결하자.


* Meta Annotation
- Annotation 을 선언할 때 사용한다. 
메타 어노테이션은 다음과 같이 4가지가 존재하고 반드시 모두 사용할 필요는 없다.

(1) @Target 
- 어떤 것에 어노테이션을 적용할 지 선언할 때 사용한다. 적용 가능 대상은 아래와 같다.


요소 타입 
대상 
CONSTRUCTOR 
생성자 선언시 
FIELD 
enum 상수를 포함한 필드값 선언시 
LOCAL_VARIABLE 
지역 변수 선언시 
METHOD 
메소드 선언시 
PACKAGE 
패키지 선언시 
PARAMETER 
매개 변수 선언시 
TYPE 
클래스, 인터페이스, enum 등 선언시 

(2) @Retention
- 얼마나 오래 어노테이션 정보가 유지되는 지를 선언한다.

대상
SOURCE
어노테이션 정보가 컴파일시 사라짐 
CLASS 
 클래스 파일에 있는 어노테이션 정보가 컴파일러에 의해 참조 가능함.
 하지만 가상 머신에서는 사라짐.
RUNTIME 
실행시 어노테이션 정보가 가상 머신에 의해서 참조 가능 

(3) @Documented
- 해당 어노테이션에 대한 정보가 JavaDocs(API) 문서에 포함된다는 것을 선언한다.

(4) @Inherited
- 모든 자식 클래스가 부모 클래스의 어노테이션을 사용할 수 있다는 것을 선언한다.

+ 추가로 @interface 도 존재한다.
이 어노테이션은 어노테이션을 선언할 때 사용한다.
	
	*/
	
}
