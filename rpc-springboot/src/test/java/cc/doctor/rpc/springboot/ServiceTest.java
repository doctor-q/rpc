package cc.doctor.rpc.springboot;

import cc.doctor.rpc.springboot.consumer.RpcConsumerScan;
import cc.doctor.rpc.springboot.service.AService;
import cc.doctor.rpc.springboot.service.impl.AServiceImpl;
import cc.doctor.rpc.springboot.utils.SpringBeanUtils;
import com.thundersdata.rpc.springboot.consumer.RpcConsumerScan;
import com.thundersdata.rpc.springboot.service.AService;
import com.thundersdata.rpc.springboot.service.impl.AServiceImpl;
import com.thundersdata.rpc.springboot.utils.SpringBeanUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@RpcConsumerScan(basePackages = "com.thundersdata.rpc.springboot.service")
public class ServiceTest {

//    @Autowired
//    private AService aService;

    @Test
    public void testAService() {
        AService aService = SpringBeanUtils.getBean(AService.class);
        String hello = aService.hello("xxx");
        Assert.assertTrue(hello.equals(new AServiceImpl().hello("xxx")));
    }
}
