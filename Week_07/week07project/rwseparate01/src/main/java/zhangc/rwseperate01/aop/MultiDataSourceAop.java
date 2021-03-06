package zhangc.rwseperate01.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import zhangc.rwseperate01.datasource.CustomRoutingDataSource;
import zhangc.rwseperate01.annotation.ReadOnly;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
public class MultiDataSourceAop implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private List<String> slaveNames;

    private AtomicInteger index = new AtomicInteger(0);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Pointcut(value="execution(* zhangc.rwseperate01.service.*(..))")
    public void serviceMethod(){

    }
    @Around("serviceMethod()")
    public void aroundDong(ProceedingJoinPoint joinPoint) throws Throwable{
        Method method =  ((MethodSignature)joinPoint.getSignature()).getMethod();
        CustomRoutingDataSource dataSource = applicationContext.getBean(CustomRoutingDataSource.class);
        if (method.isAnnotationPresent(ReadOnly.class)){
            dataSource.getThreadLocal().set(slaveNames.get(index.getAndIncrement()));
            if (index.get() == slaveNames.size()){
                index.set(0);
            }
        } else {
            dataSource.getThreadLocal().set("master");
        }
        joinPoint.proceed();
        dataSource.getThreadLocal().set(null);
    }

    public List<String> getSlaveNames() {
        return slaveNames;
    }

    public void setSlaveNames(List<String> slaveNames) {
        this.slaveNames = slaveNames;
        index = new AtomicInteger(slaveNames.size());
    }
}
