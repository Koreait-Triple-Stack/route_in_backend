package triple_stack.route_in_backend.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import triple_stack.route_in_backend.security.handler.JsonTypeHandler;

@Configuration
public class MyBatisConfig {

    @Bean
    public ConfigurationCustomizer typeHandlerCustomizer() {
        return configuration ->
                configuration.getTypeHandlerRegistry()
                    .register(JsonTypeHandler.class);

    }
}
