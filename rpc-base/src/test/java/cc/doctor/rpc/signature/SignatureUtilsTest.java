package cc.doctor.rpc.signature;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.*;

public class SignatureUtilsTest {

    @Test
    public void getParameterSig() {
        for (Method method : SignatureUtilsTest.class.getMethods()) {
            MethodSig methodSig = SignatureUtils.getMethodSig(method);
            System.out.println(methodSig);
        }
    }
}