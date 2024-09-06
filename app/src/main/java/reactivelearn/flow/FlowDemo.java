package reactivelearn.flow;

import java.util.concurrent.SubmissionPublisher;

/***
 * jdk9 引入的Reactor编程方式 Flow
 * 主线程发布者发送数据
 * 订阅者在其他线程接收 主线程并不会阻塞 异步非阻塞
 */
public class FlowDemo {

    public static void main(String[] args) throws InterruptedException {
        // 创建一个发布者
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        // 创建一个订阅者并且订阅发布者内容
        SimpleSubscriber subscriber = new SimpleSubscriber();
        publisher.subscribe(subscriber);

        System.out.println("publishing items...");

        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName()+":publish:" + i);
            publisher.submit(i);
        }

        publisher.close();

        Thread.sleep(1000);

        System.out.println(Thread.currentThread().getName() + ":Finished");
    }
}
