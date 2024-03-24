package reactivelearn;

/**
 * 演示join方法的使用
 * 主线程（发起线程）的调用（被发起线程）的join方法，等待乙方执行完成；如果乙方没有完成，甲方阻塞。
 * 传统的异步阻塞调用
 * 没有返回值无法让主线程知道具体情况
 */
public class JoinDemo {

    public static final int SLEEP_TIME = 1000;

    public static String getCurThreadName() {
        return Thread.currentThread().getName();
    }

    static class HotWaterThread extends Thread {
        public HotWaterThread() {
            super("HotWaterThread");
        }

        @Override
        public void run() {
            try {
                System.out.println("洗好水壶");
                System.out.println("灌上凉水");
                System.out.println("放在火上");
                Thread.sleep(SLEEP_TIME);
                System.out.println("烧开了");
            } catch (InterruptedException e) {
                System.out.println("发生异常被中断.");
            }
            System.out.println("烧水线程结束.");
        }
    }

    static class WashThread extends Thread {
        public WashThread() {
            super("WashThread");
        }

        @Override
        public void run() {
            try {
                System.out.println("洗茶壶");
                System.out.println("洗茶杯");
                System.out.println("拿茶叶");
                Thread.sleep(SLEEP_TIME);
                System.out.println("洗完了");
            } catch (InterruptedException e) {
                System.out.println("发生异常被中断.");
            }
            System.out.println("清洗线程结束.");
        }
    }


    public static void main(String[] args) {
        Thread hThread = new HotWaterThread();
        Thread wThread = new WashThread();
        hThread.start();
        wThread.start();
        try {
            hThread.join();
            wThread.join();
            Thread.currentThread().setName("主线程");
            System.out.println("泡茶喝");
        } catch (InterruptedException e) {
            System.out.println("发生异常被中断.");
        }
        System.out.println(getCurThreadName() +"线程结束.");
    }
}
