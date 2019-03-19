package com.gupao.vip.pattern.prototype;

import com.sun.org.apache.xml.internal.dtm.ref.sax2dtm.SAX2DTM2;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: QuZheng
 * @Date: 2019/3/19.
 * @Description: 序列化深拷贝类必须要实现Serializable接口
 */
public class Student implements Cloneable,Serializable{

    private String name;
    private int age;
    private List<String> hobbies;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    //缺点：平时写业务逻辑赋值的时候都是一个一个赋值，遇到引用对象时需要创建一个新的实例然后在逐个赋值，代码繁琐，有新需求的时候改动多
    //解决方案：可以采用JDK的Clonable的接口重写clone方法，通过第三方工具类clone一个新的实例无需关心类中每个属性的赋值逻辑
    public Student normalClone(){
        Student s = new Student();
        s.setAge(this.getAge());
        s.setName(this.getName());
        List cloneHobbies = new ArrayList<String>();
        List target = this.getHobbies();
        for(int i = 0; i < target.size(); i++) {
            cloneHobbies.add(target.get(i));
        }
        s.setHobbies(cloneHobbies);
        return s;
    }

    //通过实现Cloneable接口并重写clone方法，将对象通过生成字节码的形式写入到内存中，然后在从内存中读取对象，并产生一个新的对象，达到一个深拷贝的过程（类似序列化和反序列化）
    @Override
    protected Object clone() throws CloneNotSupportedException {
//        return super.clone();//浅拷贝，引用类型拷贝的是引用地址指向同一个对象

        //深拷贝：写入对象到内存并生成字节码，然后读取内存中的字节码并生成新的对象
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            Student newStudent = (Student) ois.readObject();
            return newStudent;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        //序列化写入到磁盘文件流
//        try {
//            FileOutputStream fos = new FileOutputStream("Student.obj");
//            ObjectOutputStream outputStream = new ObjectOutputStream(fos);
//            outputStream.writeObject(this);
//            outputStream.flush();
//            outputStream.close();
//
//            FileInputStream fis = new FileInputStream("Student.obj");
//            ObjectInputStream inputStream = new ObjectInputStream(fis);
//            Student newStudent = (Student) inputStream.readObject();
//            inputStream.close();
//
//            return newStudent;
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
    }

    //测试方法
    public static void main(String[] args) throws CloneNotSupportedException {
        Student student = new Student();
        student.setName("test");
        student.setAge(18);
        List hobbies = new ArrayList<String>();
        hobbies.add("football");
        hobbies.add("basketball");
        student.setHobbies(hobbies);

        Student student2 = student.normalClone();
        System.out.println(student==student2);
        System.out.println(student.getHobbies());
        System.out.println(student2.getHobbies());
        System.out.println(student.getHobbies()==student2.getHobbies());

        System.out.println("-----------------------------------------------");

        Student student3 = (Student) student.clone();
        System.out.println(student==student3);
        System.out.println(student.getHobbies());
        System.out.println(student3.getHobbies());
        System.out.println(student.getHobbies()==student3.getHobbies());
    }
}
