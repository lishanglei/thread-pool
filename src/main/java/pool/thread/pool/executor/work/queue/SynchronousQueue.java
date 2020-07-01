package pool.thread.pool.executor.work.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * workQueue任务队列
 * 一般分为直接提交队列,有界任务队列,无界任务队列,优先任务队列
 * <p>
 * 直接提交队列:
 * 设置为synchronousQueue队列,SynchronousQueue是一个特殊的BlockingQueue
 * 它没有容量,每执行一个插入操作就会堵塞,需要再执行一个删除操作才会被唤醒
 * 反之,每一个删除操作也要等待对应的插入操作
 *
 * @author lishanglei
 * @version v1.0.0
 * @date 2020/6/8
 * @Description Modification History:
 * Date                 Author          Version          Description
 * ---------------------------------------------------------------------------------*
 * 2020/6/8              lishanglei      v1.0.0           Created
 */
public class SynchronousQueue {

    private static ExecutorService pool;

    public static void main(String[] args) {
        /**
         * 当任务队列为synchronousQueue,创建的线程数大于maximumPoolSize时,直接执行了拒绝策列抛出异常
         * 使用synchronousQueue队列,提交的任务不会被保存,总是会马上提交执行,如果用于执行任务的线程数少于maximumPoolSize时
         * 则尝试创建新的线程,如果达到maximumPoolSize设置的最大值,则根据你设置的handler执行拒绝策略
         * 因此这种方式提交的任务不会被缓存起来,而是会被马上执行,在这何种情况下,你需要对你程序的并发量有一个准确的评估
         * 才能设置适合的maximumPoolSize数量
         */
        pool = new ThreadPoolExecutor(
                1,                                      //核心线程数
                2,                                  //最大线程数
                1000,                                  //线程存活时间
                TimeUnit.MILLISECONDS,                               //线程存活时间单位
                new java.util.concurrent.SynchronousQueue<>(),       //直接提交队列
                Executors.defaultThreadFactory(),                    //线程工厂
                new ThreadPoolExecutor.AbortPolicy());               //拒绝策略:直接抛出异常

        for (int i = 0; i < 3; i++) {
            pool.execute(new Thread(() -> {
                System.out.println(Thread.currentThread().getName());
            }));
        }
    }
}
