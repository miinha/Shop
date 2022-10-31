package kh.study.shop.intercepter;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class IntercepterConfig implements WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getCategoryIntercepter())
				.addPathPatterns("/**/**")
//				.excludePathPatterns("/admin/changeStatus"
//									, "/admin/selectCategoryListInUseAjax"
//									, "/admin/updateStock");
				.excludePathPatterns("/**/**Ajax");
	}
 
	@Bean
	public CategoryIntercepter getCategoryIntercepter() {
		return new CategoryIntercepter();
	}
	
}
