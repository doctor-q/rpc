package cc.doctor.rpc.springboot;

import cc.doctor.rpc.server.Server;
import com.thundersdata.rpc.server.Server;
import com.thundersdata.rpc.springboot.provider.RpcProviderScan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@RpcProviderScan(basePackages = "com.thundersdata.rpc.springboot.service")
public class TestServer {

    @Autowired
    private Server server;

    @Test
    public void testServer() throws InterruptedException {
        server.start();
        server.block();
    }
}
