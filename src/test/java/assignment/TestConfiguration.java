/*
package assignment;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Repository;

@Profile("test")
@Configuration
@ComponentScan(basePackages = {"intango.assignment"})
@EnableJpaRepositories(basePackages = "intango.assignment",
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Repository.class))
@EnableAutoConfiguration
public class TestConfiguration {

    @Bean(destroyMethod = "shutdown")
    public EmbeddedDatabase dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }
}
*/
