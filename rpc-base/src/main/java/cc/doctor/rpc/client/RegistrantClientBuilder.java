package cc.doctor.rpc.client;

import com.thundersdata.rpc.registry.Registry;

/**
 * client use registrant
 *
 * @param <T> 序列化类型
 * @param <R> 注册中心存储类型
 */
public abstract class RegistrantClientBuilder<T, R> extends ClientBuilder<T> {
    private Registry<R> registry;

    /**
     * client of registry
     */
    public RegistrantClientBuilder<T, R> registry(Registry<R> registry) {
        this.registry = registry;
        return this;
    }

    @Override
    public Client<T> build() {
        registry.start();
        return new RegistryClient<T, R>(registry) {
            @Override
            public Client<T> createRealClient(R ipPort) {
                return createRealClientBuilder(ipPort).build();
            }
        };
    }

    public abstract ClientBuilder<T> createRealClientBuilder(R ipPort);
}
