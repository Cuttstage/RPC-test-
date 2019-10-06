package protocol.netty;

import framework.Invocation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import provider.LocalRegister;

import java.lang.reflect.Method;

public class NettyHandler extends ChannelInboundHandlerAdapter {


    /**
     * 客户端远程调用时触发
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object result = new Object();
        Invocation request = (Invocation)msg;

        //当客户端建立连接时，需要从自定义协议中获取信息，拿到具体的服务和实参
        //使用反射调用
        if (LocalRegister.get(request.getInterfaceName()) != null) {
            //从当前的对应的map中获得对应的class
            Class clazz = LocalRegister.get(request.getInterfaceName());
            Method method = clazz.getMethod(request.getMethodName(), request.getParamTypes());
            result = method.invoke(clazz.newInstance(), request.getParams());
        }

        ctx.write(result);
        ctx.flush();
        ctx.close();
    }

    /**
     * 调用异常时触发
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
