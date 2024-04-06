package ar.edu.itba.paw.webapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WebConfig.class, EmailConfig.class})
public class AppConfig {
}
