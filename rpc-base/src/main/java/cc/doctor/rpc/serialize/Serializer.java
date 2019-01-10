package cc.doctor.rpc.serialize;

/**
 * 序列化对象为一个序列
 *
 * @param <T> 序列类型
 */
public interface Serializer<T> {

    /**
     * 序列化
     *
     * @param from 序列化对象
     * @param <F>  对象类型（任意类型）
     * @return 序列
     */
    <F> T serialize(F from);
}
