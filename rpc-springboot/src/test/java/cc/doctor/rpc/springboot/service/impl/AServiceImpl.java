package cc.doctor.rpc.springboot.service.impl;

import cc.doctor.rpc.annotation.RpcService;
import cc.doctor.rpc.springboot.service.AService;
import com.thundersdata.rpc.annotation.RpcService;
import com.thundersdata.rpc.springboot.service.AService;

@RpcService
public class AServiceImpl implements AService {
    @Override
    public String hello(String name) {
        return "A: hello, " + name;
    }
}
