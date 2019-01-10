package cc.doctor.rpc.serialize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class ObjectStreamSerializer implements ByteSerializer {
    private static final Logger log = LoggerFactory.getLogger(ObjectStreamSerializer.class);

    @Override
    public byte[] serialize(Object from) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(from);
            outputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            log.error("", e);
        }
        return new byte[0];
    }
}
