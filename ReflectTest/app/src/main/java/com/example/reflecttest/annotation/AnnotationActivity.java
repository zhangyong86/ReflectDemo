package com.example.reflecttest.annotation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.reflecttest.R;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnnotationActivity extends AppCompatActivity {

    private static final String TAG = AnnotationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);

        try {
            initReflectAnnotation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initReflectAnnotation() throws Exception {
        Car car = new Car();
        Class clazz = car.getClass();//Class对象
        Method method = clazz.getMethod("defaultCar", new Class[]{String.class, int.class});
        method.invoke(car, new Object[]{"Audi", 20});
        iteratorAnnotation(method);
    }

    public static void iteratorAnnotation(Method method){
        if (method.isAnnotationPresent(MyAnnotation.class)){//Method是否包含MyAnnotation注解
            MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
            String[] value = myAnnotation.value();//可以直接获取注解的value
            for (String s: value)
                Log.i(TAG, "iteratorAnnotation: " + s);
        }

        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation: annotations){
            Log.i(TAG, "iteratorAnnotation: " + annotation.getClass());//最终是代理类的实现$Proxy1
        }
    }
}
