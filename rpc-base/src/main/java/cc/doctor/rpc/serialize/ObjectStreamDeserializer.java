package cc.doctor.rpc.serialize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;

public class ObjectStreamDeserializer implements ByteDeserializer {
    private static final Logger log = LoggerFactory.getLogger(ObjectStreamDeserializer.class);

    @Override
    public <F> F deserialize(byte[] to, Type type) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(to));
            return (F) inputStream.readObject();
        } catch (IOException e) {
            log.error("", e);
        } catch (ClassNotFoundException e) {
            log.error("未定义类字节", e);
        }
        return null;
    }
}
