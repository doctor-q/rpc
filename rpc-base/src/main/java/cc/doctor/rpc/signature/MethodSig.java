package cc.doctor.rpc.signature;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 一个方法的签名，能够唯一确定一个方法
 */
public class MethodSig implements Serializable {
    /**
     * 方法名
     */
    private String name;

    /**
     * 方法参数
     */
    private List<ParameterSig> parameterTypeSigs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ParameterSig> getParameterTypeSigs() {
        return parameterTypeSigs;
    }

    public void setParameterTypeSigs(List<ParameterSig> parameterTypeSigs) {
        this.parameterTypeSigs = parameterTypeSigs;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        MethodSig sig = (MethodSig) object;
        if (this.parameterTypeSigs.size() != sig.parameterTypeSigs.size()) {
            return false;
        }
        for (int i = 0; i < sig.parameterTypeSigs.size(); i++) {
            if (!sig.parameterTypeSigs.get(i).equals(this.parameterTypeSigs.get(i))) {
                return false;
            }
        }
        return Objects.equals(name, sig.name);
    }

    @Override
    public int hashCode() {
        List<Object> objects = new LinkedList<>();
        objects.add(name);
        objects.addAll(parameterTypeSigs);
        return Objects.hash(objects.toArray());
    }
}
