package hh.test.dubbo;

import com.alibaba.dubbo.rpc.RpcContext;
import hh.test.dubbo.demo.DemoService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO
 *
 * @author huhui
 * @since 2018/11/29 15:51
 */
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", response form provider: " + RpcContext.getContext().getLocalAddress();
    }

}
