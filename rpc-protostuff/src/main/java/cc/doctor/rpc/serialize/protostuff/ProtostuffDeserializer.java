package cc.doctor.rpc.serialize.protostuff;

import com.thundersdata.rpc.serialize.ByteDeserializer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ProtostuffDeserializer implements ByteDeserializer {
    private static Objenesis objenesis = new ObjenesisStd(true);

    @Override
    public <F> F deserialize(byte[] to, Type fType) {
        try {
            Class<F> cls = null;
            if (fType instanceof Class) {
                cls = (Class) fType;
            } else if (fType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) fType;
                cls = (Class) parameterizedType.getRawType();
            } else {
                throw new IllegalStateException();
            }
            F message = objenesis.newInstance(cls);
            Schema<F> schema = SchemaCache.getSchema(cls);
            ProtostuffIOUtil.mergeFrom(to, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
