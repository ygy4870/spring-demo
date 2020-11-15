package com.ygy.study.aopdemo.proxy;

public class CglibSimple {

//    private String name;
//    private String age;
//
//    public CglibSimple(){}
//    public CglibSimple(String name, String age) {
//        this.name = name;
//        this.age = age;
//    }

    private void testPrivate() {
        System.out.println("-------testPrivate-------");
    }

    protected void testProtected() {
        System.out.println("-------testProtected-------");
    }

    public void testPublic() {
        System.out.println("-------testPublic------");
    }

    public void testPublic2() {
        System.out.println("-------testPublic2------");
        testPublic();
    }

    static void testStatic() {
        System.out.println("-------testStatic-------");
    }

    final void testFinal() {
        System.out.println("-----testFinal---------");
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getAge() {
//        return age;
//    }
//
//    public void setAge(String age) {
//        this.age = age;
//    }
}
