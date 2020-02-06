package com.example.reflecttest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.reflecttest.annotation.AnnotationActivity;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getName();

    private String mClassName;
    private String dexPath = Environment.getExternalStorageDirectory() + File.separator + "test/a.apk" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                launchTargetActivity();
                Intent intent = new Intent(MainActivity.this, AnnotationActivity.class);
                startActivity(intent);
            }
        });
        initPeople();
    }

    protected void launchTargetActivity(){
        Log.i(TAG, "launchTargetActivity: dexpath= " + dexPath);
        //根据sd卡的路径获取未安装的apk的信息
        PackageInfo packageInfo = getPackageManager().getPackageArchiveInfo(dexPath, 1);//getPackageArchiveInfo、getPackageInfo的区别？
        if ((packageInfo.activities != null) && (packageInfo.activities.length > 0)){
            String activityName = packageInfo.activities[0].name;//获取插件中第一个activity
            mClassName = activityName;
            launchTargetActivity(mClassName);
        }
    }

    protected void launchTargetActivity(String className){
        Log.i(TAG, "launchTargetActivity: start launchTargetActivity, className= " + className);
        File dexOutputDir = this.getDir("dex", 0);//?
        final String dexOutputPath = dexOutputDir.getAbsolutePath();
        ClassLoader localClassLoader = ClassLoader.getSystemClassLoader();//PathClassLoader
        DexClassLoader dexClassLoader = new DexClassLoader(dexPath,
                dexOutputPath, null, localClassLoader);

        try {
            //创建插件Activity的实例
            Class<?> loadClass = dexClassLoader.loadClass(className);//?问号的作用？
            //调用插件Activity的构造函数，将当前activity的上下文传递到插件activity中去
            Constructor<?> localConstructor = loadClass.getConstructor(new Class[]{Activity.class});//参数?
            Object instance = localConstructor.newInstance(new Object[]{this});//参数？
            Log.i(TAG, "launchTargetActivity: instance " + instance.toString());
            Method method = loadClass.getDeclaredMethod("onCreate", new Class[]{Bundle.class});
            method.setAccessible(true);//设置可进入
            Bundle bundle = new Bundle();
            //调用插件activity的onCreate方法
            method.invoke(instance, bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPeople(){
        try {
            Class clazz = Class.forName("com.example.reflecttest.People");
            //获取构造方法
            Constructor[] publicConstructors = clazz.getConstructors();
            for (Constructor constructor: publicConstructors){
                Log.i(TAG, "initPeople: " + constructor.toString());
            }
            //获取全部构造方法
            Constructor[] declaredConstructors = clazz.getDeclaredConstructors();
            //获取公开方法
            Method[] methods = clazz.getMethods();
            //获取全部方法
            Method[] declaredMethods = clazz.getDeclaredMethods();
            //获取公开属性
            Field[] publicFields = clazz.getFields();
            //获取全部属性
            Field[] declaredFields = clazz.getDeclaredFields();
            Object clsObject = clazz.newInstance();
            Method method = clazz.getDeclaredMethod("initName");
//            Object object = method.invoke(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }  catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
