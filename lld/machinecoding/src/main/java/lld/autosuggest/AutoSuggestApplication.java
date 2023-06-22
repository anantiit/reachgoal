package lld.autosuggest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AutoSuggestApplication {
	public static void main(String args) {
		@Autowired
		private static ApplicationContext context;
		SpringApplication.run(AutoSuggestApplication.class, args);

	}
}
