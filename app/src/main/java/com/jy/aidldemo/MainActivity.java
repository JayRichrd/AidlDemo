package com.jy.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jy.aidldemo.bean.Student;

public class MainActivity extends AppCompatActivity {
    private static int ageBase = 10;
    // 显示控件
    private TextView tvResult;
    // IBinder对象
    private IMyAidl mIMyAidl;
    // 绑定服务的intent
    private Intent intent;
    // Binder死亡通知
    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {

        @Override
        public void binderDied() {
            if (mIMyAidl==null)
                return;
            // 首先取消死亡监听
            mIMyAidl.asBinder().unlinkToDeath(deathRecipient,0);
            // 将IBinder客户端重置为空
            mIMyAidl = null;
            // 重新绑定服务
            bindService(intent, mConnection, BIND_AUTO_CREATE);
        }
    };
    // 服务连接
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            /**
             * 当与服务绑定时调用，即回调Service的onBind函数
             * service就是Service的onBind函数返回的IBinder对象
             * 将IBinder对象转换成具体的IMyAidl类型
             */
            mIMyAidl = IMyAidl.Stub.asInterface(service);
            // 设置服务绑定死亡监听
            try {
                service.linkToDeath(deathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIMyAidl = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResult = (TextView) findViewById(R.id.tv_result);
        // 绑定服务service
        // 5.0以后必须采用显示Intent的方式绑定
        intent = new Intent(getApplicationContext(), MyAidlService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    // 点击响应
    public void add(View view) {
        Student student = new Student("学生", ageBase++);
        // 调用接口函数实现进程间的通信
        try {
            // 调用函数将数据从前台Activity进程传递到后台Service进程
            mIMyAidl.add(student);
            // 调用函数从后台Service进程获取数据到前台Activity进程
            tvResult.setText(mIMyAidl.getStudents().toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
