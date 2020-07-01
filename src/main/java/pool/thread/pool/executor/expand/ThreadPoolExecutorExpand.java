package pool.thread.pool.executor.expand;

import lombok.Data;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池拓展:
 *
 *  1.beforeExecute:线程池中任务运行前执行
 *  2.afterExecute:线程池中任务运行完毕后执行
 *  3.
 * 通过这三个接口我们可以监控每个任务的开始和结束时间
 * @author lishanglei
 * @version v1.0.0
 * @date 2020/6/8
 * @Description Modification History:
 * Date                 Author          Version          Description
 * ---------------------------------------------------------------------------------*
 * 2020/6/8              lishanglei      v1.0.0           Created
 */
public class ThreadPoolExecutorExpand {

    private static ThreadPoolExecutor executor ;
    /**
     * Nthreads=CPU数量
     * Ucpu=目标CPU的使用率，0<=Ucpu<=1
     * W/C=任务等待时间与任务计算时间的比率
     */
    //private static int  Nthreads = Ncpu*Ucpu*(1+W/C)
    public static void main(String[] args) {

        //实现自定义接口
        executor=new ThreadPoolExecutor(
                2,
                4,
                1000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(5),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        System.out.println("线程"+r.hashCode()+"创建");
                        Thread thread =new Thread(r,"threadPool"+r.hashCode());
                        return thread;
                    }
                },
                new ThreadPoolExecutor.CallerRunsPolicy()
        ){
            protected void beforeExecute(Thread t,Runnable r){
                System.out.println("准备执行: "+((ThreadTask)r).getTaskName());
            }

            protected void afterExecute(Runnable r,Throwable t){
                System.out.println("执行完毕: "+((ThreadTask)r).getTaskName());
            }
            protected void terminated() {
                System.out.println("线程池退出");
            }
        };

        for(int i=0;i<10;i++){
            executor.execute(new ThreadTask("Task" +i));
        }

        /**
         * 可以看到通过对beforeExecute()、afterExecute()和terminated()的实现，我们对线程池中线程的运行状态进行了监控，
         * 在其执行前后输出了相关打印信息。另外使用shutdown方法可以比较安全的关闭线程池，
         * 当线程池调用该方法后，线程池中不再接受后续添加的任务。但是，此时线程池不会立刻退出，
         * 直到添加到线程池中的任务都已经处理完成，才会退出。
         */
        executor.shutdown();
    }

}

@Data
class ThreadTask implements Runnable{

    private String taskName;

    public ThreadTask(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        //输出执行线程的名称
        System.out.println("TaskName"+this.getTaskName()+"---ThreadName:"+Thread.currentThread().getName());
    }
}
