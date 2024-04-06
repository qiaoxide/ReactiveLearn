package reactivelearn;

import reactor.core.publisher.Mono;

/***
 * Reactor 实现异步非阻塞编程
 */
public class ReactorDemo {

    private static final long SLEEP_TIME = 1000;

    public static void main(String[] args) {

        // 创建两个 Mono 对象，分别表示烧水和清洗工作。

        // Mono.fromSupplier 用于创建一个 Mono 对象。Mono代表一个任务，它会在某个时刻产生一个结果。
        Mono<Boolean> boilWater = Mono.fromSupplier(() -> {
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
        });

        Mono<Boolean> washCup = Mono.fromSupplier(() -> {
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
        });

        // 非阻塞的方式同时运行两个任务异步任务，Mono.zip 是 Reactor 库中的一个方法，用于组合两个或更多的 Mono。
        Mono.zip(boilWater, washCup)
                .subscribe(tuple -> {
                    if (tuple.getT1() && tuple.getT2())
                        System.out.println("泡茶喝");
                    else
                        System.out.println("烧水失败，没有茶喝了");

                });

    }
}
