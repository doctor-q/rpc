package cc.doctor.rpc.serialize;

public interface ByteSerializer extends Serializer<byte[]> {
    @Override
    <F> byte[] serialize(F from);
}
