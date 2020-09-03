package util;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

public class UnitTest {

    public static class TestJob {
        public final Method method;
        public final Object object;
        public final Object[] parameters;

        public TestJob(Method method, Object object, Object...parameters) {
            Objects.requireNonNull(method);
            Objects.requireNonNull(object);
            this.method = method;
            this.object = object;
            this.parameters = parameters;

        }

        public void invoke(Consumer<Object> consumer)
                throws IllegalAccessException,
                InvocationTargetException {
            Test test = method.getAnnotation(Test.class);
            if(!test.enable()) {
                return;
            }
            method.setAccessible(true);
            int iteration = test.iteration();
            for(int i = 0; i < iteration; i++) {
                consumer.accept(method.invoke(object, parameters));
            }
        }
    }

    public static void run(Object driver) {
        Method[] methods = driver.getClass().getMethods();
        ArrayList<TestJob> tests = new ArrayList<>();
        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            boolean flag = false;
            for (Annotation annotation : annotations) {
                if(annotation.annotationType() == Test.class) {
                    flag = true;
                }

                if(annotation.annotationType() == Disable.class) {
                    flag = false;
                    break;
                }
            }

            if(flag) {
                tests.add(new TestJob(method, driver));
            }
        }

        tests.forEach(job -> {
            try {
                job.invoke(result -> {});
            } catch (IllegalAccessException | InvocationTargetException e) {
                System.err.println("Test Failed On: " + job.method);
                e.printStackTrace();
            }
        });
    }

    public static void equal(Object a, Object b) {
        if(!Objects.deepEquals(a, b)) {
            throw new RuntimeException("Assertion Failed!, Object: " + a + " and " + b + ", does not equal.");
        }
    }
}
