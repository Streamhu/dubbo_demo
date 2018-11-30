package hh.test.dubbo;

import com.alibaba.dubbo.rpc.RpcContext;
import hh.test.dubbo.demo.RpcService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO
 *
 * @author huhui
 * @since 2018/11/30 15:08
 */
public class RpcServiceImpl implements RpcService{

    @Override
    public String sayRpc(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello rpc " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello rpc" + name + ", response form provider: " + RpcContext.getContext().getLocalAddress();
    }
}
