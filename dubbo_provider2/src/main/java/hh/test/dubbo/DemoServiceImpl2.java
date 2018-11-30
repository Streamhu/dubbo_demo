package hh.test.dubbo;

import com.alibaba.dubbo.rpc.RpcContext;
import hh.test.dubbo.demo.DemoService;
import hh.test.dubbo.demo.DemoService2;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO
 *
 * @author huhui
 * @since 2018/11/30 14:22
 */
public class DemoServiceImpl2 implements DemoService2 {

    @Override
    public String sayHai(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello2 " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", response form provider2: " + RpcContext.getContext().getLocalAddress();
    }
}
