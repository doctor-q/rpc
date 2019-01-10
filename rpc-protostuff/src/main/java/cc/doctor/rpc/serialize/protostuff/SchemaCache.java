package cc.doctor.rpc.serialize.protostuff;

import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SchemaCache {
    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

    private SchemaCache() {
    }

    public static <T> Schema<T> getSchema(Class<T> cls) {
        return (Schema<T>) cachedSchema.computeIfAbsent(cls, aClass -> RuntimeSchema.createFrom(cls));
    }
}
