package banquemisr.challenge05.task.management.service.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

@Configuration
public class MessageSourceConfig {

    @Value("${messages.basename}")
    private String messageBaseName;

    @Value("${messages.defaultEncoding}")
    private String defaultEncoding;

    @Value("${properties.bean.location.path}")
    private String propertiesBeanLocationPath;

    @Bean("messages_bundle")
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(messageBaseName);
        messageSource.setDefaultEncoding(defaultEncoding);
        return messageSource;
    }

    @Bean("messages")
    public PropertiesFactoryBean properties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        propertiesFactoryBean.setLocations(resolver.getResources(propertiesBeanLocationPath));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean;
    }

}
