package framework;

import protocol.http.HttpClient;
import register.RemoteMapRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory<T> {
    public static <T> T getProxy(final Class interfaceClass){
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        // 调用哪个方法
                        Invocation invocation = new Invocation(
                                interfaceClass.getName(),
                                method.getName(),
                                args,
                                new Class[]{String.class});

                        // 获取服务器
                        URL url = RemoteMapRegister.get(interfaceClass.getName()).get(0);

                        // 调用
                        HttpClient httpClient = new HttpClient();
                        return httpClient.post(url.getHostname(),url.getPort(),invocation);
                    }
                });
    }
}
