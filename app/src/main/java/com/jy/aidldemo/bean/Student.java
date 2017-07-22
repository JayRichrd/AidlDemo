package com.jy.aidldemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

/*
 *   author RichardJay
 *    email jiangfengfn12@163.com
 *  created 2017/7/21 11:00
 *  version v1.0
 * modified 2017/7/21
 *     note xxx
 */

public class Student implements Parcelable {
    // 属性变量
    private String name;
    private int age;

    /**
     * 构造函数
     *
     * @param name 姓名
     * @param age  年龄
     */
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    protected Student(Parcel in) {
        // 读取属性值
        name = in.readString();
        age = in.readInt();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // 写入属性值
        dest.writeString(name);
        dest.writeInt(age);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
