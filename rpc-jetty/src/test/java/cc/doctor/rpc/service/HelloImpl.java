package cc.doctor.rpc.service;

public class HelloImpl implements Hello {
    @Override
    public String sayHello(String name) {
        return "hello, " + name;
    }
}
