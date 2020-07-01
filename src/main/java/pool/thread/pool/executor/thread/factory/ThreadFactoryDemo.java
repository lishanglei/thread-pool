package pool.thread.pool.executor.thread.factory;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池中的线程就是通过ThreadPoolExecutor中的ThreadFactory线程工厂创建的,
 * 那么通过自定义ThreadFactory,可以按需要对线程池中创建的线程进行一些特殊的设置,如命名,优先级等
 *
 * @author lishanglei
 * @version v1.0.0
 * @date 2020/6/8
 * @Description Modification History:
 * Date                 Author          Version          Description
 * ---------------------------------------------------------------------------------*
 * 2020/6/8              lishanglei      v1.0.0           Created
 */
public class ThreadFactoryDemo {


    private static ThreadPoolExecutor executor;

    public static void main(String[] args) {

        //自定义线程工厂
        executor = new ThreadPoolExecutor(2, 4, 1000, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(5),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        System.out.println("线程" + r.hashCode() + "创建");
                        Thread thread = new Thread(r, "threadPool" + r.hashCode());
                        return thread;
                    }
                });

        for (int i = 0; i < 10; i++) {
            executor.execute(new Thread(() -> {
                System.out.println("ThreadName:" + Thread.currentThread().getName());
            }));
        }
    }
}
