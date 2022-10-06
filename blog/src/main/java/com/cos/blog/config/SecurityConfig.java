package com.cos.blog.config;

import com.cos.blog.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// 빈 등록: 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것

@Configuration // 빈 등록 (IoC로 관리가 됨)
@EnableWebSecurity // 시큐리티 필터 추가 -> 스프링 시큐리티가 활성화가 되어 있는데 어떤 설정을 이 파일에서 하겠다는 얘기. 시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근하면 권한 및 인증을 미리 체크하겠다는 뜻.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean // IoC가 된다.
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    // 시큐리티가 대신 로그인해 주는데 password를 가로채기를 한다.
    // 이 때 해당 password가 뭘로 해시가 되서 회원가입이 되었는지 알아야 같은 해시로 암호화해서 DB에 있는 해시랑 비교 가능.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD()); // principalDetailService 에게 해시가 encodePWD() 방식으로 되었다고 알려줌.
    }

//    @Bean
//    protected AuthenticationManager configure(AuthenticationConfiguration auth) throws Exception {
//        return auth.getAuthenticationManager(); // principalDetailService 에게 해시가 encodePWD() 방식으로 되었다고 알려줌.
//    }

    // 인증이 필요한 요청이 오면 /auth/loginForm 페이지가 자동으로 뜨게 된다.
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable() // csrf 토큰 비활성화 (테스트시 걸어두는게 좋음. 테스트가 끝나면 활성화시킴)
//                .authorizeRequests()
//                .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/auth/loginForm")
//                .loginProcessingUrl("/auth/loginProc")
//                .defaultSuccessUrl("/"); // 스프링 시큐리티가 loginProcessingUrl 로 요청오는 로그인을 가로채서 대신 로그인 후 정상적으로 완료됐을 때 defaultSuccessUrl()로 이동한다.
//        return http.build();
//    }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()  // csrf 토큰 비활성화 (테스트시 걸어두는 게 좋음)
                    .authorizeRequests()
                    .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/auth/loginForm")
                    .loginProcessingUrl("/auth/loginProc")
                    .defaultSuccessUrl("/"); // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 해준다.
        }
}
