package cc.doctor.rpc.signature;

import java.io.Serializable;
import java.util.Objects;

public class ParameterSig implements Serializable {
    /**
     * 参数名
     */
    private String name;

    /**
     * 参数类型
     */
    private Class paramClass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getParamClass() {
        return paramClass;
    }

    public void setParamClass(Class paramClass) {
        this.paramClass = paramClass;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ParameterSig that = (ParameterSig) object;
        return Objects.equals(name, that.name) &&
                Objects.equals(paramClass, that.paramClass);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, paramClass);
    }
}
