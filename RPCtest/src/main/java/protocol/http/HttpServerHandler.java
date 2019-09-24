package protocol.http;


import framework.Invocation;
import org.apache.commons.io.IOUtils;
import provider.LocalRegister;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;

public class HttpServerHandler {
    public void handle(HttpServletRequest req, HttpServletResponse resp) {
        try{
            // Http请求流转为对象
            InputStream is = req.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);

            //代理实现
            Invocation invocation = (Invocation)ois.readObject();

            // 寻找实现类，通过反射执行
            Class implClass = LocalRegister.get(invocation.getInterfaceName());
            Method method = implClass.getMethod(invocation.getMethodName(), invocation.getParamTypes());

            // 执行结果
            String result = (String) method.invoke(implClass.newInstance(), invocation.getParams());
            System.out.println(result);

            // 将结果返回
            IOUtils.write(result, resp.getOutputStream());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
