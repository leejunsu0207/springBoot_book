package com.book.springboot.config.auth;

import com.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity  // Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable().headers().frameOptions().disable()    // h2-console 화면을 사용하기 위해 해당 옵션들 disable
                .and()
                .authorizeRequests()    // URL별 권한관리를 설정 하는 옵션의 시작점
                                        // authorizeRequests가 선언 되어야만 antMatchers옵션 사용 할수 있음

                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())     // 권한 관리 대상을 지정하는 옵션
                                                                                     // URL, HTTP메소드별 관리 가능
                                                                                     // "/"등 지정된 URL들은 permitAll()옵션을 통해 전체 열람권한 을 줌
                                                                                     // "/api/v1/**" 주소를 가진 API는 USER권한을 가진 사람만 가능 하도록

                .anyRequest().authenticated()   // 설정된 값들 이외 나머지 URL들을 나타냄
                                                // 여기서는 authenticated()를 추가하여 나머지 URL들은 모드 인증된 사용자들에게 만 허용하게 함
                                                // 인증된 사용즈 즉, 로그인한 사용자들을 이야기함
                .and()
                .logout().logoutSuccessUrl("/")     // 로그아웃 기능에 대한 여러 설정의 진입점
                                                    // 로그아웃 성공 시 /(루트)주소로 이동함
                .and()
                .oauth2Login()  // OAuth2 로그인 기능에 대한 여러 설정의 진입점
                .userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올 떄의 설정들을 담당


                .userService(customOAuth2UserService);  // 소셜 로그인 성공시 후속 조치를 진행할 UserService 인터페이스 구현체를 등록 한다.
                                                        // 리소스 서버(즉, 소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시 할수 있다.
    }

}
