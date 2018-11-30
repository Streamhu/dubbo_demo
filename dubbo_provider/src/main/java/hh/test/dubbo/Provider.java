package hh.test.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * TODO
 *
 * @author huhui
 * @since 2018/11/29 15:57
 */
public class Provider {

    public static void main(String[] args) throws Exception {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"dubbo_provider.xml"});
        context.start();
        System.out.println("----------------服务已启动，按任意键结束···········--------------------");
        System.in.read(); // press any key to exit
    }

}
