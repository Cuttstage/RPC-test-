package provider;

import framework.URL;
import protocol.http.HttpServer;
import protocol.netty.NettyRegistry;
import provider.api.HelloService;
import provider.impl.HelloServiceImpl;
import register.RemoteMapRegister;

public class Provider {
    //服务提供方提供服务
    public static void main(String[] args) {
        //本地注册对应的服务
        provider.LocalRegister.register(HelloService.class.getName(), HelloServiceImpl.class);

//        //(远程注册)注册中心注册对应的服务
//        URL url = new URL("localhost", 8080);
//        RemoteMapRegister.register(HelloService.class.getName(), url);
//
//        //启动对应的tomcat服务器
//        HttpServer httpServer = new HttpServer();
//        httpServer.start("localhost", 8080);

        //基于netty启动
        new NettyRegistry(8080).start();
    }
}
