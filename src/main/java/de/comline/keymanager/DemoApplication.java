package de.comline.keymanager;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;

@SpringBootApplication
public class DemoApplication {
	
	@Autowired
	@Qualifier("springSecurityFilterChain")
	private Filter springSecurityFilterChain;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@PostConstruct
	public void addMissing() {
		FilterChainProxy filterChainProxy = (FilterChainProxy) springSecurityFilterChain;
	    List<SecurityFilterChain> list = filterChainProxy.getFilterChains();
	    SecurityFilterChain securityFilterChain = list.get(0);
	    for(Filter f : securityFilterChain.getFilters()) {
	    	if(f instanceof DefaultLoginPageGeneratingFilter) {
	    		DefaultLoginPageGeneratingFilter filter = (DefaultLoginPageGeneratingFilter)securityFilterChain.getFilters().get(6);
	    		filter.setLogoutSuccessUrl("/login?logout");
	    	}
	    }
	}

}
