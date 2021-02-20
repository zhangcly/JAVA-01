package zhangc.simple.configure;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import zhangc.simple.aop.Aop;
import zhangc.simple.entity.Klass;
import zhangc.simple.entity.School;
import zhangc.simple.entity.Student;
import zhangc.simple.registrar.SimpleRegistrar;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Import({SimpleRegistrar.class, Aop.class})
public class SimpleAutoConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public Klass klass(){
        List<Student> students = new ArrayList<>();
        String[] studentNames = this.applicationContext.getBeanNamesForType(Student.class);
        for (String name: studentNames){
            Student student = this.applicationContext.getBean(name, Student.class);
            students.add(student);
        }
        Klass klass = new Klass();
        klass.setStudents(students);
        return klass;
    }

    @Bean
    public School school(){
        return new School();
    }

}
