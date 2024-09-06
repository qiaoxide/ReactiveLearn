package reactivelearn.flow;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;

public class SimpleSubscriber implements Flow.Subscriber<Integer>{

    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription=subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Integer item) {
        System.out.println(Thread.currentThread().getName()+":Received:"+item);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("Error:"+throwable.getMessage());
    }

    @Override
    public void onComplete() {
       System.out.println("All items received.");
    }

}
