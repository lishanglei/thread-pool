package pool.thread.pool.executor.work.queue;

import lombok.Data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 优先任务队列
 * @author lishanglei
 * @version v1.0.0
 * @date 2020/6/8
 * @Description Modification History:
 * Date                 Author          Version          Description
 * ---------------------------------------------------------------------------------*
 * 2020/6/8              lishanglei      v1.0.0           Created
 */
public class PriorityBlockingQueue {

    private static ExecutorService pool;

    public static void main(String[] args) {

        /**
         * 除第一个任务直接创建线程执行外,其它任务都被放入优先任务队列,按照优先级进行重新排列执行,
         * 且线程数一直为corePoolSize.
         * PriorityBlockingQueue其实是一个特殊的无界队列,它其中无论添加了多少个任务,线程池创建的线程数不会
         * 超过corePoolSize的数量,只不过其它任务队列一般是按照先进先出的规则处理任务,
         * 而PriorityBlockingQueue队列可以自定义根据任务的优先级顺序先后执行
         *
         */
        pool = new ThreadPoolExecutor(
                1,                                      //核心线程数
                2,                                  //最大线程数
                1000,                                  //线程存活时间
                TimeUnit.MILLISECONDS,                               //线程存活时间单位
                new java.util.concurrent.PriorityBlockingQueue<>(),       //优先任务队列
                Executors.defaultThreadFactory(),                    //线程工厂
                new ThreadPoolExecutor.AbortPolicy());               //拒绝策略:直接抛出异常

        for (int i = 0; i <20; i++) {
            pool.execute(new ThreadTask(i));
        }
    }

}

@Data
class ThreadTask implements Runnable,Comparable<ThreadTask>{

    private int priority;

    public ThreadTask(int priority) {
        this.priority = priority;
    }


    //当前对象和其它对象比较,当前优先级大就返回-1,优先级小就返回1,值越小优先级越高
    @Override
    public int compareTo(ThreadTask o) {
        return this.priority>o.priority?-1:1;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(1000);
            System.out.println("priority:"+this.priority+"ThreadName:"+Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}