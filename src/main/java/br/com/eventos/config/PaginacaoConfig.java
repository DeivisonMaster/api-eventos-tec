package br.com.eventos.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PaginacaoConfig implements WebMvcConfigurer {
	
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		PageableHandlerMethodArgumentResolver paginacao = new PageableHandlerMethodArgumentResolver();
		paginacao.setFallbackPageable(PageRequest.of(0, 4));
		
		resolvers.add(paginacao);
	}
}
