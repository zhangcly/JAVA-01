package zhangc.simple.starter.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import zhangc.simple.aop.ISchool;
import zhangc.simple.entity.Klass;
import zhangc.simple.entity.Student;

@Component
public class SimpleCommandLineRunner implements CommandLineRunner, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) throws Exception {

        ApplicationContext context = this.applicationContext;

        Student student123 = (Student) context.getBean("student123");
        System.out.println(student123.toString());

        Student student100 = (Student) context.getBean("student100");
        System.out.println(student100.toString());

        Klass class1 = context.getBean(Klass.class);
        System.out.println(class1);
        System.out.println("Klass对象AOP代理后的实际类型："+class1.getClass());
        System.out.println("Klass对象AOP代理后的实际类型是否是Klass子类："+(class1 instanceof Klass));

        ISchool school = context.getBean(ISchool.class);
        System.out.println(school);
        System.out.println("ISchool接口的对象AOP代理后的实际类型："+school.getClass());

        ISchool school1 = context.getBean(ISchool.class);
        System.out.println(school1);
        System.out.println("ISchool接口的对象AOP代理后的实际类型："+school1.getClass());

        school1.ding();

        class1.dong();

        System.out.println("   context.getBeanDefinitionNames() ===>> "+ String.join(",", context.getBeanDefinitionNames()));


    }
}
