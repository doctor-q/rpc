package cc.doctor.rpc.serialize;

import java.lang.reflect.Type;

public interface ByteDeserializer extends Deserializer<byte[]> {
    @Override
    <F> F deserialize(byte[] to, Type fType);
}
