package zhangc.springbeansassembling;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.serviceloader.ServiceLoaderFactoryBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import zhangc.springbeansassembling.entity.TestEntity;

import java.util.Iterator;
import java.util.ServiceLoader;

@Component
public class BeanTestCommandLineRunner implements CommandLineRunner, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) throws Exception {
        // 通过AutowireCapableBeanFactory装配
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        TestEntity testEntity = autowireCapableBeanFactory.createBean(TestEntity.class);
        System.out.println(testEntity);

        String[] beanNames = applicationContext.getBeanNamesForType(TestEntity.class);
        for (String beanName: beanNames){
            System.out.println(beanName);
        }

        ServiceLoader<TestEntity> serviceLoader = applicationContext.getBean(ServiceLoader.class);
        Iterator<TestEntity> iterator = serviceLoader.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

    }
}
