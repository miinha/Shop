package kh.study.shop.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean // 메소드의 리턴 타입에 대한 객체를 생성, 메소드 위에서 정의
	public SecurityFilterChain filterChain(HttpSecurity security) throws Exception{ //예외 처리를 해주어야 한다.
		security.csrf().disable() 
				.authorizeRequests()
				.antMatchers("/item/list"
							, "/item/itemDetail"
							, "/member/join"
							, "/member/login").permitAll() //"/member/**"-> member안에 있는 모든 경로를 허용하겠다.
				.antMatchers("/admin/**").hasRole("ADMIN") // -> ADMIN 권한이 있는 사람만 모든 admin 경로를 허용.
				.anyRequest().authenticated() 
			.and()
				.formLogin() //인증이 되어있지 않으면 로그인 페이지로 보내겠다.
				.loginPage("/member/login") //이 경로의 로그인 페이지로 보낸다.
				.defaultSuccessUrl("/member/loginResult") //로그인 성공시 경로
				.failureUrl("/member/loginResult") //로그인 실패시 경로
				.loginProcessingUrl("/member/login") //실제 로그인을 진행할 요청 정보
				.usernameParameter("memberId")
				.passwordParameter("memberPw")
			.and()
				.exceptionHandling()
				.accessDeniedPage("/member/accessDenied")//인증은 했지만 권한이 없어서 못가는 페이지는 이 경로로 보낸다.
			.and()
				.logout()
				.invalidateHttpSession(true)
				.logoutSuccessUrl("/item/list");
		
		return security.build(); //이 객체를 넘기겠다~
				
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {//비밀번호 암호화하는 객체
		return new BCryptPasswordEncoder();
	}
	
	//security로 접근 권한을 설정하면 .js, .css 등
	//정적인 리소스에 접근도 권한을 체크
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
       return (web) -> web.ignoring().antMatchers("/js/**",  "/css/**", "/images/**"); //경로를 무시하겠다.
    }
	
}

