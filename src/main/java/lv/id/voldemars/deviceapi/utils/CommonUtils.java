package lv.id.voldemars.deviceapi.utils;

import java.util.function.Supplier;

public interface CommonUtils {

    /**
     * Checks nested call if not Null (Call example "() -> class.getter1().getter2().getter3()...")
     *
     * @param method method nested call passed as Lambda
     * @return true if non of nested calls null
     * false if any of nested calls null
     */
    static boolean isNotNull(Supplier<Object> method) {
        try {
            return method.get() != null;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Checks nested call if is Null (Call example "() -> class.getter1().getter2().getter3()...")
     * @param method method nested call passed as Lambda
     * @return false if non of nested calls null
     * true if any of nested calls null
     */
    static boolean isNull(Supplier<Object> method) {
        return !isNotNull(method);
    }
}
