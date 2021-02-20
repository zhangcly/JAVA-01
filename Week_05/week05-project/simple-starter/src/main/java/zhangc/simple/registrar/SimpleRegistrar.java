package zhangc.simple.registrar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import zhangc.simple.entity.Student;
import zhangc.simple.properties.SimpleProperties;

import java.util.Map;

public class SimpleRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        SimpleProperties properties = Binder.get(environment).bind("simple", SimpleProperties.class).get();
        for (Map.Entry<String, String> entry : properties.entrySet()){
            BeanDefinition studentBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Student.class)
                    .addPropertyValue("id", Integer.valueOf(entry.getKey()))
                    .addPropertyValue("name", entry.getValue())
                    .getBeanDefinition();
            BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(studentBeanDefinition, "student" + entry.getKey());
            BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinitionHolder, registry);
        }
    }
}
