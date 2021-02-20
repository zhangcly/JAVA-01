package zhangc.springbeansassembling;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.serviceloader.ServiceLoaderFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import zhangc.springbeansassembling.entity.Entity;
import zhangc.springbeansassembling.entity.TestEntity;
import zhangc.springbeansassembling.entity.TestEntityFactoryBean;

@Configuration
@Import(BeansConfiguration.Registrar.class)
public class BeansConfiguration{

    @Bean("entity1")
    public TestEntity assembleByConstructor(){
        return new TestEntity();
    }

    @Bean(value = "entity2")
    public TestEntityFactoryBean assembleByFactoryBean(){
        return new TestEntityFactoryBean();
    }

    @Bean
    public ServiceLoaderFactoryBean assembleByFactoryServiceLoader(){
        ServiceLoaderFactoryBean serviceLoaderFactoryBean = new ServiceLoaderFactoryBean();
        serviceLoaderFactoryBean.setServiceType(Entity.class);
        return serviceLoaderFactoryBean;
    }

    public static class Registrar implements ImportBeanDefinitionRegistrar , BeanFactoryAware {

        private BeanFactory beanFactory;

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            this.beanFactory = beanFactory;
        }

        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            // 通过BeanDefinitionRegistry装配
            BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(TestEntity.class).getBeanDefinition();
            BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(beanDefinition, "entity3");
            BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinitionHolder, registry);
        }
    }
}
