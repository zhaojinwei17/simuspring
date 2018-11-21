package com.zjw;

import com.zjw.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test implements TestIn{
    public static void main(String[] args){
//        JDKProxyBean proxyBean=new JDKProxyBean();
//        TestIn bind = (TestIn)proxyBean.getInstance(Class.forName("com.zjw.Test"));
//        bind.test("hello proxy");

//        Test bean = getBean("com.zjw.Test", Test.class);
//        bean.test("hello proxy");

        BeanFactory beanFactory=new BeanFactory(Test.class.getClassLoader().getResourceAsStream("zjw.xml"));
        Test test = beanFactory.getBean("test1");
        test.test("hello spring");
    }

    public void test(String str){
        System.out.println(str);
    }
}
