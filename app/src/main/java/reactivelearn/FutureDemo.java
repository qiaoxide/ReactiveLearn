package reactivelearn;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Callable和Future式的异步阻塞
 */
public class FutureDemo {

    public static final int SLEEP_TIME = 1000;

    public static String getCurThreadName() {
        return Thread.currentThread().getName();
    }

    static class HotWaterJob implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            try {
                System.out.println("洗好水壶");
                System.out.println("灌上凉水");
                System.out.println("放在火上");
                Thread.sleep(SLEEP_TIME);
                System.out.println("烧开了");
            } catch (InterruptedException e) {
                System.out.println("发生异常被中断.");
                return false;
            }
            System.out.println("烧水线程结束.返回运行结果");
            return true;
        }

    }

    static class WashJob implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            try {
                System.out.println("洗茶壶");
                System.out.println("洗茶杯");
                System.out.println("拿茶叶");
                Thread.sleep(SLEEP_TIME);
                System.out.println("洗完了");
            } catch (InterruptedException e) {
                System.out.println("发生异常被中断.");
                return false;
            }
            System.out.println("清洗线程结束.返回运行结果");
            return true;
        }
    
    }

    static boolean waterOk=false;
    static boolean cupOk=false;

    public static void drinkTea(){
        if(waterOk && cupOk)
            System.out.println("泡茶喝");
        else if(!waterOk)
            System.out.println("烧水失败，没有茶喝了");
        else if(!cupOk)
            System.out.println("杯子洗不了，没有茶喝了");

    }

    public static void main(String[] args) {
        Callable<Boolean> hJob = new HotWaterJob();
        Callable<Boolean> wJob = new WashJob();
        FutureTask<Boolean> hFuture = new FutureTask<Boolean>(hJob);
        FutureTask<Boolean> wFuture = new FutureTask<Boolean>(wJob);
        Thread hThread = new Thread(hFuture, "烧水线程");
        Thread wThread = new Thread(wFuture, "清洗线程");
        hThread.start();
        wThread.start();
        Thread.currentThread().setName("主线程");
        try {
            waterOk = hFuture.get();
            cupOk = wFuture.get();
            drinkTea();
        } catch (Exception e) {
            System.out.println("发生异常被中断.");
        }
    }
}
