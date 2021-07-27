package com.book.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)      // 이 어노테이션이 생성될 수 있는 위치를 지정합니다.
                                    // PARAMETER로 지정했으니 메소드의 파라미터로 서언된 객체에서만 사용 가능
                                    // 이 외에도 클래스 선언문에 쓸 수 있는 type등이 있음
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {       // @interface 이파일을 어노테이션 클래스로 지정합니다.
                                    // LoginUser 이름을 가진 어노테이션이 생성되었다고 보면됨

}
