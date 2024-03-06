package com.wilson.reactor.reactive;

import java.time.Instant;
import java.util.TreeMap;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

/**
 * flow API demo
 *
 * @author wilson
 */
public class ReactiveDemo {

    //定义中间操作,只写订阅者接口
    static class MyProcessor extends SubmissionPublisher<String> implements Flow.Processor<String, String> {
        private Flow.Subscription subscription;//保存绑定关系，发布者与订阅者之间的绑定关系

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            System.out.println("订阅绑定完成");
            this.subscription = subscription;
            subscription.request(1);

        }

        //数据到达 触发这个回调
        @Override
        public void onNext(String item) {
            System.out.println("processor拿到数据" + item);
            item = "哈哈" + item;
            //把处理过的数据 发送出去，调用了SubmissionPublisher的方法
            submit(item);
            subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onComplete() {

        }
    }

    //发布订阅模式--观察者模式的一种
    public static void main(String[] args) throws InterruptedException {
        //定义发布者
//        Flow.Publisher<String> publisher = new Flow.Publisher<String>() {
//            //订阅者会订阅这个发布者的接口
//            @Override
//            public void subscribe(Flow.Subscriber<? super String> subscriber) {
//                this.subscribe=subscriber;
//            }
//        };
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

        //定义一个中间操作
        MyProcessor myProcessor1 = new MyProcessor();
        MyProcessor myProcessor2 = new MyProcessor();
        MyProcessor myProcessor3 = new MyProcessor();


        //定义订阅者 感兴趣发布者数据
        Flow.Subscriber subscriber = new Flow.Subscriber<String>() {
            //订阅关系
            private Flow.Subscription subscription;

            //事件触发机制，onXXX表示 再XXX事件发生时执行这个回调
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println(Thread.currentThread().getName() + " 订阅开始了:" + subscription);
                this.subscription = subscription;
                //从上游请求一个数据 订阅者告诉发布者，我可以接受数据了
                //背压模式，订阅者与发布者绑定后，订阅者需要给发布者一个信号，告诉发布者可以发送数据了，这就是背压模式
                subscription.request(Long.MAX_VALUE);
            }

            //当下一个元素到达时，接受到了新数据
            @Override
            public void onNext(String item) {
                System.out.println(Thread.currentThread().getName() + "订阅者1接受数据" + item);
//                //每次都要数据
//                if (item.equals("P-2")) {
//                    //取消订阅
//                    subscription.cancel();
//                } else {
//                    subscription.request(1);
//                }
            }

            //错误发生时触发
            @Override
            public void onError(Throwable throwable) {
                System.out.println(Thread.currentThread().getName() + "订阅者接受数据出现错误" + throwable.getMessage());
            }

            //再完成时触发
            @Override
            public void onComplete() {
                System.out.println(Thread.currentThread().getName() + "数据完毕");
            }
        };


        Flow.Subscriber subscriber2 = new Flow.Subscriber<String>() {
            //订阅关系
            private Flow.Subscription subscription;

            //事件触发机制，onXXX表示 再XXX事件发生时执行这个回调
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println(Thread.currentThread().getName() + " 订阅2开始了:" + subscription);
                this.subscription = subscription;
                //从上游请求一个数据 订阅者告诉发布者，我可以接受数据了
                subscription.request(1);
            }

            //当下一个元素到达时，接受到了新数据
            @Override
            public void onNext(String item) {
                System.out.println(">>>>订阅者2《》接受数据" + item);
                subscription.request(1);
            }

            //错误发生时触发
            @Override
            public void onError(Throwable throwable) {
                System.out.println(Thread.currentThread().getName() + "订阅者2《》接受数据出现错误" + throwable.getMessage());

            }

            //再完成时触发
            @Override
            public void onComplete() {
                System.out.println(Thread.currentThread().getName() + "数据完毕2");

            }
        };

        //绑定发布者与订阅者之间订阅关系
        //先绑定关系 再发布数据
//        publisher.subscribe(subscriber);
//        publisher.subscribe(subscriber2);

        //绑定中间操作
        publisher.subscribe(myProcessor1);//发布者与中间操作绑定，此时处理器作为订阅者
        myProcessor1.subscribe(myProcessor2);
        myProcessor2.subscribe(myProcessor3);
        myProcessor3.subscribe(subscriber);//中间操作与订阅者绑定，此时处理器作为发布者

        //多个处理器绑定 形成职责链

        for (int i = 0; i < 10; i++) {
            if (i == 5) {
//                publisher.closeExceptionally(new RuntimeException("error"));
            } else {
//                //发布者发布10条数据，存到了buffer缓冲区
                publisher.submit("P-" + i);
            }
            System.out.println("发布数据线程" + Thread.currentThread().getName());
//            publisher.submit("P-" + i);
        }
//        publisher.subscribe(subscriber2);
        //保障主线程不退出，观察数据
        TimeUnit.SECONDS.sleep(2000);
    }
}
