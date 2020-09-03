package introduction.to.algorithms.chapter1;

public class A {

    public A() {
        print();
    }
    public void print() {
        System.out.println("A");
    }
}

class B extends A {
    private int a;
    private int b = 10;

    public B() {
        a = 100;
    }

    public void print() {
        System.out.println("A: " + a + ", B: " + b);
    }

    public static void main(String[] args) {
        B b = new B();
    }
}