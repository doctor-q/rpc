package cc.doctor.rpc.server;

public abstract class AbstractServer implements Server {
    private final Object lock = new Object();

    @Override
    public void block() throws InterruptedException {
        synchronized (lock) {
            while (!isStopped()) {
                lock.wait();
            }
        }
    }
}
