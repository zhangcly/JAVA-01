package zhangc;

import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

/**
 * 从新建的线程中拿到结果的方法
 */
public class WaysToGetResultFromAnotherThread {

    public static void main(String[] args) throws InterruptedException {
        useWaitNotify();
        useJoin();
        useCountDownLauth();
        useCyclicBarrier();
        useSpinlock();
        useExchanger();
        useLockSupport();
        useFuture();
        useThreadPoolAndCallable();
    }

    public static String demoMethod(){
        return "zhangc";
    }


    /**
     * 方式1 使用wait/notify 控制主线程和新建的线程的次序 使用一个数组（也可以使用对象的一个属性或者List、Map等）
     * 来接收返回结果 如果返回的是基本类型可以不用数组，直接改变值即可，因为基本类型是值传递，对象是引用传递，在匿名内部类中不能修改局部变量引用的地址
     */
    public static void useWaitNotify() throws InterruptedException {

        final String[] arr = new String[1];
        Thread thread = new Thread(new Runnable() {
            public void run() {
                synchronized (WaysToGetResultFromAnotherThread.class){
                    arr[0] = demoMethod();
                    WaysToGetResultFromAnotherThread.class.notify();
                }
            }
        });
        thread.start();
        synchronized (WaysToGetResultFromAnotherThread.class){
            WaysToGetResultFromAnotherThread.class.wait();
        }
        System.out.println("useWaitNotify:" + arr[0]);
    }

    /**
     * 方式2 使用join 控制主线程和新建的线程的次序 使用一个数来接收返回结果
     */
    public static void useJoin() throws InterruptedException {

        final String[] arr = new String[1];
        Thread thread = new Thread(new Runnable() {
            public void run() {
                arr[0] = demoMethod();
            }
        });
        thread.start();
        thread.join();
        System.out.println("useJoin:" + arr[0]);
    }

    /**
     * 方式3 使用CountDownLauth 控制主线程和新建的线程的次序 使用一个数来接收返回结果
     */
    public static void useCountDownLauth() throws InterruptedException {

        final String[] arr = new String[1];
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread thread = new Thread(new Runnable() {
            public void run() {
                arr[0] = demoMethod();
                countDownLatch.countDown();
            }
        });
        thread.start();
        countDownLatch.await();
        System.out.println("useCountDownLauth:" + arr[0]);
    }

    /**
     * 方式4 使用CyclicBarrier 控制主线程和新建的线程的次序 使用一个数来接收返回结果
     */
    public static void useCyclicBarrier(){

        final String[] arr = new String[1];
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        Thread thread = new Thread(new Runnable() {
            public void run() {
                arr[0] = demoMethod();
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("useCyclicBarrier:" + arr[0]);
    }

    /**
     * 方式5 使用自旋锁 控制主线程和新建的线程的次序 使用一个数来接收返回结果
     */
    public static void useSpinlock(){

        final String[] arr = new String[1];
        Thread thread = new Thread(new Runnable() {
            public void run() {
                arr[0] = demoMethod();
            }
        });
        thread.start();
        while (arr[0] == null){

        }
        System.out.println("useSpinlock:" + arr[0]);
    }

    /**
     * 方式6 使用Exchanger 控制主线程和新建的线程的次序
     */
    public static void useExchanger(){
        final Exchanger<String> exchanger = new Exchanger<String>();
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    exchanger.exchange(demoMethod());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        String res = null;
        try {
            res = exchanger.exchange("");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("useLockSupport:" + res);
    }

    /**
     * 方式7 使用LockSupport 控制主线程和新建的线程的次序 使用一个数来接收返回结果
     */
    public static void useLockSupport(){
        final String[] arr = new String[1];
        final Thread main = Thread.currentThread();
        Thread thread = new Thread(new Runnable() {
            public void run() {
                arr[0] = demoMethod();
                LockSupport.unpark(main);
            }
        });
        thread.start();
        LockSupport.park();
        System.out.println("useExchanger:" + arr[0]);
    }

    /**
     * 方式8 使用Future的get方法阻塞获取线程的返回值
     */
    public static void useFuture() {

        FutureTask<String> future = new FutureTask<String>(new Callable<String>() {
            public String call() throws Exception {
                return demoMethod();
            }
        });
        new Thread(future).start();
        try {
            System.out.println("useFuture:" + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    /**
     * 方式9 使用线程池的submit方法阻塞获取线程的返回值
     */
    public static void useThreadPoolAndCallable() {

        Callable<String> callable = new Callable<String>() {
            public String call() throws Exception {
                return demoMethod();
            }
        };
        String res = null;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            res = executorService.submit(callable).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        System.out.println("useThreadPoolAndCallable:" + res);
    }
}
