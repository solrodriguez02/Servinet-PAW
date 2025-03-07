package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.model.Categories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan({
        "ar.edu.itba.paw.webapp.controller",
        "ar.edu.itba.paw.services",
        "ar.edu.itba.paw.persistance"
})
@Import(EmailConfig.class)
public class WebConfig extends WebMvcConfigurerAdapter {

    @Value("classpath:schema.sql")
    private Resource schemaSql;

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.postgresql.Driver.class);
        ds.setUrl("jdbc:postgresql://localhost/paw");
        ds.setUsername("root");
        ds.setPassword("root");
        return ds;
    }
    @Bean
    public CharacterEncodingFilter characterEncodingFilter(){
        CharacterEncodingFilter cef = new CharacterEncodingFilter();
        cef.setEncoding("UTF-8");
        cef.setForceEncoding(true);
        return cef;
    }
    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();

        ms.addBasenames("classpath:i18n/messages", "classpath:mail/i18n/email-messages");
        ms.setDefaultEncoding(StandardCharsets.UTF_8.name());
        ms.setCacheSeconds((int) TimeUnit.MINUTES.toSeconds(5));

        return ms;
    }
    @Bean
    public PlatformTransactionManager transactionManager(final DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }
    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource ds) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(ds);
        initializer.setDatabasePopulator(dsPopulator());
        return initializer;
    }
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver mpr = new CommonsMultipartResolver();
        mpr.setMaxUploadSize(1024 * 1024 * 10);
        mpr.setMaxUploadSizePerFile(1024 * 1024 * 2);
        return mpr;
    }
    private DatabasePopulator dsPopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(schemaSql);
        return populator;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);

        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

}
