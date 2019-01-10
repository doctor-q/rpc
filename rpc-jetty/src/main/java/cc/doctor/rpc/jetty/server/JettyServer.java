package cc.doctor.rpc.jetty.server;

import cc.doctor.rpc.serialize.Deserializer;
import cc.doctor.rpc.server.AbstractServer;
import com.thundersdata.rpc.registry.Registry;
import com.thundersdata.rpc.serialize.Deserializer;
import com.thundersdata.rpc.serialize.Serializer;
import com.thundersdata.rpc.server.AbstractServer;
import com.thundersdata.rpc.service.ServiceHandler;
import com.thundersdata.rpc.signature.ServerSig;
import com.thundersdata.rpc.signature.ServiceSig;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class JettyServer extends AbstractServer {
    private static final Logger logger = LoggerFactory.getLogger(JettyServer.class);

    private int port;
    private ServerConnector connector;
    private boolean isStarted = false;
    private ServiceHandler serviceHandler;
    private Registry registry;
    private Serializer serializer;
    private Deserializer deserializer;

    public JettyServer(int port) {
        this.port = port;
    }

    @Override
    public void start() {
        // HTTP connector
        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server();
        connector = new ServerConnector(server);
        connector.setPort(port);
        server.setConnectors(new Connector[]{connector});

        // Set a handler
        JettyHandler jettyHandler = new JettyHandler(serviceHandler, serializer, deserializer);
        HandlerCollection handlers = new HandlerCollection();
        handlers.setHandlers(new Handler[]{jettyHandler});
        server.setHandler(handlers);
        try {
            server.start();
            // start registry and register service
            registerService();
            Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
            isStarted = true;
            logger.info("服务启动成功！");
        } catch (Exception e) {
            logger.error("服务启动失败！", e);
        }
    }

    @Override
    public void stop() {
        if (isStarted) {
            logger.info("关闭服务");
            connector.close();
            if (registry != null) {
                registry.shutdown();
            }
            isStarted = false;
        } else {
            logger.warn("服务未启动！");
        }
    }

    @Override
    public boolean isStarted() {
        return isStarted;
    }

    @Override
    public boolean isStopped() {
        return !isStarted;
    }

    @Override
    public void registerService() {
        if (registry != null) {
            registry.start();
            Collection<ServiceSig> serviceList = serviceHandler.getServiceList();
            for (ServiceSig serviceSig : serviceList) {
                registry.register(serviceSig.lookupKey(), new ServerSig(port));
            }
        }
    }

    @Override
    public ServiceHandler serviceHandler() {
        return serviceHandler;
    }

    public void setServiceHandler(ServiceHandler serviceHandler) {
        this.serviceHandler = serviceHandler;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    public void setDeserializer(Deserializer deserializer) {
        this.deserializer = deserializer;
    }
}
