package pool.thread.pool.executor.work.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 任务队列
 * @author lishanglei
 * @version v1.0.0
 * @date 2020/6/8
 * @Description Modification History:
 * Date                 Author          Version          Description
 * ---------------------------------------------------------------------------------*
 * 2020/6/8              lishanglei      v1.0.0           Created
 */
public class LinkedBlockingQueue {

    private static ExecutorService pool;

    public static void main(String[] args) {

        /**
         * 使用无界任务队列,线程池的任务队列可以无限制的添加新的任务队列,
         *  而线程池创建的最大线程数量就是你的corePoolSize设置的数量
         *  ,也就是说在这种情况下maximumPoolSize参数时无效的,哪怕你的任务队列中缓存了很多未执行的任务
         *  当线程池的线程数达到corePoolSize后,就不会再增加了.
         *  后续若有新的任务加入,则直接进入等待队列,当使用这种任务队列模式时,一定要主义你任务提交与处理之间的协调与控制
         *  不然会出现队列中的任务由于无法及时处理导致一直增长,直到资源耗尽
         */
        pool = new ThreadPoolExecutor(
                5,                                      //核心线程数
                10,                                  //最大线程数
                1000,                                  //线程存活时间
                TimeUnit.MILLISECONDS,                               //线程存活时间单位
                new java.util.concurrent.LinkedBlockingQueue<>(),       //无界任务队列
                Executors.defaultThreadFactory(),                    //线程工厂
                new ThreadPoolExecutor.AbortPolicy());               //拒绝策略:直接抛出异常

        for (int i = 0; i <200; i++) {
            pool.execute(new Thread(() -> {
                System.out.println(Thread.currentThread().getName());
            }));
        }
    }
}
