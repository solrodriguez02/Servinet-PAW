package ar.edu.itba.paw.persistance.config;

import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import javax.sql.DataSource;

@ComponentScan({"ar.edu.itba.paw.persistance"})
@Configuration
public class TestConfig {

    @Value("classpath:sql/pgsql.sql")
    private Resource pgsqlSyntax;

    @Value("classpath:sql/schema.sql")
    private Resource schemaSql;

    @Bean
    public DataSource dataSource() {
        final SingleConnectionDataSource ds = new SingleConnectionDataSource();

        ds.setDriverClassName(JDBCDriver.class.getName());
        ds.setUrl("jdbc:hsqldb:mem:paw");
        ds.setUsername("ha");
        ds.setPassword("");
        return ds;
    }

    @Bean
    public DataSourceInitializer dsInitializer(DataSource ds) {
        final DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(ds);
        initializer.setDatabasePopulator(dsPopulator());
        return initializer;
    }


    private DatabasePopulator dsPopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(pgsqlSyntax);
        populator.addScript(schemaSql);
        return populator;
    }
}
