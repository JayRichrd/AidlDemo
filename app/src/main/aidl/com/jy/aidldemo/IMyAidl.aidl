// IMyAidl.aidl
package com.jy.aidldemo;
import com.jy.aidldemo.bean.Student;

interface IMyAidl {

    // 添加数据
    void add(in Student student);

    // 获取数据
    List<Student> getStudents();

}
