package cc.doctor.rpc.springboot.service.impl;

import com.thundersdata.rpc.springboot.service.BService;

public class BServiceImpl implements BService {
    @Override
    public String hello(String name) {
        return "B: hello, " + name;
    }
}
