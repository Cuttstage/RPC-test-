package provider.impl;

import provider.api.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public String Hello(String userName) {
        return "Hello" + userName;
    }
}
