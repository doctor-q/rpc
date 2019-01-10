package cc.doctor.rpc.serialize;

import java.lang.reflect.Type;

/**
 * 反序列化
 *
 * @param <T> 序列类型
 */
public interface Deserializer<T> {
    /**
     * 反序列化
     *
     * @param to    序列
     * @param fType 转换类型
     * @param <F>   转换类型
     * @return 反序列化结果
     */
    <F> F deserialize(T to, Type fType);
}
