package hh.test.dubbo.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * TODO
 *
 * @author huhui
 * @since 2018/11/29 15:59
 */
public class Consumer2 {

    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"dubbo_consumer.xml"});
        context.start();
        DemoService2 demoService = (DemoService2) context.getBean("demoService2"); // get remote service proxy

        while (true) {
            try {
                Thread.sleep(10000);
                String hello = demoService.sayHai("hai hai hai"); // call remote method
                System.out.println(hello); // get result

            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

        }

    }
}
