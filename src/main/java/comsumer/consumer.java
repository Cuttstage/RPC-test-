package main.java.comsumer;

import framework.ProxyFactory;
import framework.RpcProxy;
import provider.api.HelloService;

public class consumer {
    public static void main(String[] args) {
//        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
//        String result = helloService.Hello("lxx");
//        System.out.println(result);

        //基于netty的调用
        HelloService helloService = RpcProxy.create(HelloService.class);
        String result = helloService.Hello("lxx");
        System.out.println(result);
    }
}
