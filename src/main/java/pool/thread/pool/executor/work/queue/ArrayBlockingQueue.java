package pool.thread.pool.executor.work.queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 有界任务队列:
 *
 * @author lishanglei
 * @version v1.0.0
 * @date 2020/6/8
 * @Description Modification History:
 * Date                 Author          Version          Description
 * ---------------------------------------------------------------------------------*
 * 2020/6/8              lishanglei      v1.0.0           Created
 */
public class ArrayBlockingQueue {

    private static ExecutorService pool;

    public static void main(String[] args) {

        /**
         * 使用ArraysBlockingQueue有界任务队列,若有新的任务需要执行时,线程池会创建新的线程
         * 知道创建数量达到最大corePoolSize,则会将新的任务加入到等待队列中,若等待队列已满,则超过
         * ArrayBlockingQueue初始化的数量,则继续创建线程,直到线程数达到maximumPoolSize设置的组大线程数
         * 若大于maximumPoolSize,则执行拒绝策略,在这种情况下,线程数量的上限与有接任务队列的状态有直接关系
         * ,如果有界任务队列初始容量比较大或者没有达到超负荷的状态,线程数将一直维持在corePoolSize以下,
         * 反之当任务队列已满时,则会以maximumPoolSize为最大线程数上线
         */
        pool = new ThreadPoolExecutor(
                5,                                      //核心线程数
                10,                                  //最大线程数
                1000,                                  //线程存活时间
                TimeUnit.MILLISECONDS,                               //线程存活时间单位
                new java.util.concurrent.ArrayBlockingQueue<>(3),       //有界任务队列
                Executors.defaultThreadFactory(),                    //线程工厂
                new ThreadPoolExecutor.AbortPolicy());               //拒绝策略:直接抛出异常

        for (int i = 0; i <20; i++) {
            pool.execute(new Thread(() -> {
                System.out.println(Thread.currentThread().getName());
            }));
        }
    }

}
