package util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class UnitTest {

    public static void run(Object driver) {
        Method[] methods = driver.getClass().getMethods();
        ArrayList<Method> tests = new ArrayList<>();
        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                if(annotation.annotationType() == Test.class) {
                    tests.add(method);
                }
            }
        }

        tests.forEach(method -> {
            method.setAccessible(true);
            try {
                method.invoke(driver);
            } catch (Exception e) {
                System.out.println("Test Failed On: " + method.getName());
                System.err.println("Exception Thrown: " + e);
                e.printStackTrace();
                System.exit(-1);
            }
        });
    }

}
