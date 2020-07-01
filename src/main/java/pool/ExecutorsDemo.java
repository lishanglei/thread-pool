package pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lishanglei
 * @version v1.0.0
 * @date 2020/6/5
 * @Description Modification History:
 * Date                 Author          Version          Description
 * ---------------------------------------------------------------------------------*
 * 2020/6/5              lishanglei      v1.0.0           Created
 */
public class ExecutorsDemo implements Runnable{

    public static void main(String[] args) {
        ExecutorService executorService =Executors.newCachedThreadPool();
        ExecutorsDemo demo=new ExecutorsDemo();
        new Thread(demo).start();

    }

    @Override
    public void run() {
        for(int i=0;i<20;i++){
            System.out.println(Thread.currentThread().getName()+"i");
        }
    }
}
