package reactivelearn;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * CompletableFuture 是 Java 8 引入的一个类，用于表示异步计算的结果。它提供了一种异步非阻塞的编程方式。
 */
public class AysncDemo {

    public static final int SLEEP_TIME = 1000;
    static class HotWaterJob implements Supplier<Boolean> {

        @Override
        public Boolean get() {
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

    static class WashJob implements Supplier<Boolean> {

        @Override
        public Boolean get() {
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

    public static void main(String[] args){
        
        
        CompletableFuture<Boolean> ayncsfuture=CompletableFuture.supplyAsync(new HotWaterJob());
        CompletableFuture<Boolean> ayncsfuture2=CompletableFuture.supplyAsync(new WashJob());
        ayncsfuture.thenAccept((result)->{
            System.out.println("烧水完成，结果："+result);
            waterOk=result;
        });
        ayncsfuture2.thenAccept((result)->{
            System.out.println("清洗完成，结果："+result);
            cupOk=result;
        });
        System.out.println("主线程运行");
        while(true){
            try {
                Thread.sleep(SLEEP_TIME);
                System.out.println("主线程运行...");
                if(waterOk && cupOk){
                    drinkTea();
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
    }

}
