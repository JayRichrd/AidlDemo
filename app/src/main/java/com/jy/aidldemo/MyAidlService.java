package com.jy.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.jy.aidldemo.bean.Student;

import java.util.ArrayList;
import java.util.List;

public class MyAidlService extends Service {
    private ArrayList<Student> mStudents;
    // 本地Binder对象，实现AIDL中声明的方法
    private IBinder mIBinder = new IMyAidl.Stub() {
        @Override
        public void add(Student student) throws RemoteException {
            mStudents.add(student);
        }

        @Override
        public List<Student> getStudents() throws RemoteException {
            return mStudents;
        }
    };

    public MyAidlService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        // 初始化
        mStudents = new ArrayList<>();
        // 返回IBinder对象到服务绑定者，这里就会前台Activity进程
        return mIBinder;
    }
}
