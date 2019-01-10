package cc.doctor.rpc.serialize.protostuff;

import cc.doctor.rpc.serialize.ByteSerializer;
import com.thundersdata.rpc.serialize.ByteSerializer;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;


public class ProtostuffSerializer implements ByteSerializer {

    @Override
    public <F> byte[] serialize(F from) {
        Class<F> cls = (Class<F>) from.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<F> schema = SchemaCache.getSchema(cls);
            return ProtostuffIOUtil.toByteArray(from, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }
}
