package cc.doctor.rpc.jetty.server;

import cc.doctor.rpc.serialize.Deserializer;
import cc.doctor.rpc.service.ServiceHandler;
import com.thundersdata.rpc.client.RemoteCall;
import com.thundersdata.rpc.serialize.ByteDeserializer;
import com.thundersdata.rpc.serialize.ByteSerializer;
import com.thundersdata.rpc.serialize.Deserializer;
import com.thundersdata.rpc.serialize.Serializer;
import com.thundersdata.rpc.service.MethodInvoker;
import com.thundersdata.rpc.service.ServiceHandler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class JettyHandler extends AbstractHandler {
    private static final Logger logger = LoggerFactory.getLogger(JettyHandler.class);

    private ServiceHandler serviceHandler;
    private ByteSerializer byteSerializer;
    private ByteDeserializer deserializer;

    public JettyHandler(ServiceHandler serviceHandler, Serializer serializer, Deserializer deserializer) {
        this.serviceHandler = serviceHandler;
        this.byteSerializer = (ByteSerializer) serializer;
        this.deserializer = (ByteDeserializer) deserializer;
    }

    @Override
    public void handle(String target, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();
        RemoteCall remoteCall = parseRemoteCall(httpServletRequest);
        if (remoteCall == null) {
            outputStream.write(-1);
            return;
        }
        Object service = serviceHandler.getService(remoteCall.getService());
        if (service == null) {
            outputStream.write(-1);
            return;
        }
        Object response = MethodInvoker.handle(remoteCall, service);
        byte[] bytes = byteSerializer.serialize(response);
        outputStream.write(bytes);
        outputStream.flush();
    }

    public RemoteCall parseRemoteCall(HttpServletRequest request) {
        try {
            byte[] bytes = readBytes(request);
            return deserializer.deserialize(bytes, RemoteCall.class);
        } catch (IOException e) {
            logger.error("", e);
        }
        return null;
    }

    private byte[] readBytes(HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        int contentLen = request.getContentLength();
        InputStream is = request.getInputStream();
        if (contentLen > 0) {
            int readLen = 0;
            int readLengthThisTime = 0;
            byte[] message = new byte[contentLen];
            try {
                while (readLen != contentLen) {
                    readLengthThisTime = is.read(message, readLen, contentLen - readLen);
                    if (readLengthThisTime == -1) {
                        break;
                    }
                    readLen += readLengthThisTime;
                }
                return message;
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return new byte[]{};
    }
}
