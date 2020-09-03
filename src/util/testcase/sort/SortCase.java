package util.testcase.sort;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class SortCase {

    public static void test(Class<?> clazz,
                            int size,
                            int low,
                            int high) {
        Constructor<?>[] constructors = clazz.getConstructors();
        Constructor<?> constructor = getDefaultConstructor(constructors);
        assert constructor != null;
        Object instance = null;
        try {
            instance = constructor.newInstance();
            boolean result = doTest(instance, size, low, high);
            if(!result) {
                System.err.println("Test Failed!");
            } else {
                System.out.println("Test Success!");
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

    private static Constructor<?> getDefaultConstructor(Constructor<?>[] constructors) {
        Constructor<?> defaultConstructor = null;

        for(Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() == 0) {
                defaultConstructor = constructor;
            }
        }

        return defaultConstructor;
    }

    private static Method getTestMethod(Object object) {
        Method[] methods = object.getClass().getMethods();
        Method candidate = null;

        for(Method method : methods) {
            if(method.getParameterCount() == 1
                    && method.getParameterTypes()[0] == int[].class) {
                if(method.getName().contains("sort")) {
                    candidate = method;
                    continue;
                }

                candidate = method;
            }
        }

        return candidate;
    }

    private static boolean doTest(Object instance,
                                  int size,
                                  int low,
                                  int high) throws InvocationTargetException, IllegalAccessException {
        Method method = getTestMethod(instance);
        if(method == null) {
            throw new RuntimeException("Cannot find a test method! Test method must have following signature: void .*sort.*(int[] name)");
        }
        int[] array = generateRandomArray(size, low, high);
        int[] copy = Arrays.copyOf(array, array.length);
        method.setAccessible(true);
        try {
            method.invoke(instance, (Object)array);
        } catch (Exception e) {
            throw new RuntimeException("Exception Thrown while doing test on object: \n" + instance + "\n With method: " + method, e);
        }
        System.out.println("\nOriginal Array: " + Arrays.toString(copy));
        System.out.println("Sorted Array:   " + Arrays.toString(array));
        return verify(array, copy);
    }

    private static int[] generateRandomArray(int size, int low, int high) {
        Random random = new Random();
        return random.ints(size, low, high).toArray();
    }

    private void sort(int[] array) {

    }

    private static boolean verify(int[] array, int[] original) {
        return isAscending(array) && isElementsEqual(array, original);
    }

    private static boolean isAscending(int[] array) {
        for(int i = 1; i < array.length; i++) {
            if(array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }

    private static boolean isElementsEqual(int[] array, int[] that) {
        boolean[] mark = new boolean[array.length];

        for(int a : that) {
            for(int i = 0; i < array.length; i++) {
                if(!mark[i] && array[i] == a) {
                    mark[i] = true;
                }
            }
        }

        for(boolean b : mark) {
            if(!b) {
                return false;
            }
        }

        return true;
    }
}