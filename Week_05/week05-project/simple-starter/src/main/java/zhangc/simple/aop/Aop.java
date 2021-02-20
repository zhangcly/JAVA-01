package zhangc.simple.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class Aop {
    
    @Pointcut(value="execution(* zhangc.simple.*.Klass.*dong(..))")
    public void dingPoint(){
        
    }
    
    @Before(value="dingPoint()")
    public void beforeDong(){
        System.out.println("========>begin klass dong...");
    }
    
    @AfterReturning(value = "dingPoint()")
    public void afterDong(){
        System.out.println("========>after klass dong...");
    }
    
    @Around("dingPoint()")
    public void aroundDong(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("========>around begin klass dong");
        joinPoint.proceed();
        System.out.println("========>around after klass dong");
        
    }

    @Pointcut(value="execution(* zhangc.simple.*.*.*ding(..))")
    public void dongPoint(){

    }

    @Before(value="dongPoint()")
    public void startTransaction(){
        System.out.println("    ====>begin ding... ");
    }

    @AfterReturning(value = "dongPoint()")
    public void commitTransaction(){
        System.out.println("    ====>finish ding... ");
    }

    @Around("dongPoint()")
    public void aroundDing(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("    ====>around begin ding");
        //调用process()方法才会真正的执行实际被代理的方法
        joinPoint.proceed();

        System.out.println("    ====>around finish ding");
    }
    
}
