package cc.doctor.rpc.signature;

import java.io.Serializable;
import java.util.Objects;

/**
 * 服务签名
 */
public class ServiceSig implements Serializable {
    /**
     * 服务名
     */
    private String name;

    /**
     * 服务版本号
     */
    private String version = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ServiceSig that = (ServiceSig) object;
        return Objects.equals(name, that.name) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, version);
    }

    /**
     * key for registering service or lookup service
     */
    public String lookupKey() {
        return String.format("%s#%s", name, version);
    }
}
