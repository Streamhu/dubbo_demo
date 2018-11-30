package hh.test.dubbo.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * TODO
 *
 * @author huhui
 * @since 2018/11/29 15:59
 */
public class Consumer {

    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"dubbo_consumer.xml"});
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoService"); // get remote service proxy
        RpcService rpcService = (RpcService) context.getBean("rpcService"); // get remote service proxy

        while (true) {
            try {
                Thread.sleep(10000);
                String hello = demoService.sayHello("world"); // call remote method
                System.out.println(hello); // get result

                String rpc = rpcService.sayRpc("rpc"); // call remote method
                System.out.println(rpc); // get result
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

        }

    }
}
