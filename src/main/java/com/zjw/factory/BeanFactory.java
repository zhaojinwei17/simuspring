package com.zjw.factory;

import com.zjw.dom.DomAnalysis;
import com.zjw.proxy.CglibProxyBean;
import com.zjw.proxy.JDKProxyBean;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {
    private CglibProxyBean cglibProxyBean = new CglibProxyBean();
    private JDKProxyBean jdkProxyBean = new JDKProxyBean();
    private Document document = null;
    private Map<String, Object> zbeans = new HashMap<String, Object>();
    Map<String,String> doc=new HashMap<String, String>();

    public BeanFactory(String filename) {
        document = DomAnalysis.load(filename);
        createBean();
    }

    public BeanFactory(InputStream inputStream) {
        document = DomAnalysis.load(inputStream);
        createBean();
    }

    public BeanFactory(File file) {
        document = DomAnalysis.load(file);
        createBean();
    }

    public BeanFactory(Reader reader) {
        document = DomAnalysis.load(reader);
        createBean();
    }

    public BeanFactory(URL url) {
        document = DomAnalysis.load(url);
        createBean();
    }

    private void createBean() {                                 //创建zbean
        Element root = document.getRootElement();
        List<Element> elements = root.elements("zbean");
        for (Element element : elements) {
            String bname = element.attribute("bname").getValue();
            String bclass = element.attribute("bclass").getValue();
            doc.put(bname,bclass);
            try {
                Class<?> clazz = Class.forName(bclass);
                Class<?>[] interfaces = clazz.getInterfaces();
                if (interfaces!=null && interfaces.length>0){           //如果zbean有父接口则使用jdk动态代理
                    Object zbean = jdkProxyBean.getInstance(clazz);
                    zbeans.put(bname,zbean);
                }else {                                                 //如果zbean没有父接口则使用cglib动态代理
                    Object zbean = cglibProxyBean.getInstance(clazz);
                    zbeans.put(bname,zbean);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("zbean：" + bname + "创建异常，类" + bclass + "找不到");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public  <T> T getBean(String zbeanName,Class<T> clazz){
        Constructor<?>[] constructors = clazz.getConstructors();
        if (constructors!=null && constructors.length>0){
            try {

                Object zbean=cglibProxyBean.getInstance(clazz);
                zbeans.put(zbeanName,zbean);
                return (T) zbean;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }else {
            return (T) zbeans.get(zbeanName);
        }
        return null;
    }
    public  <T> T getBean(String zbeanName) {
        Object zbean = zbeans.get(zbeanName);
        Constructor<?>[] constructors = zbean.getClass().getConstructors();
        if (constructors!=null && constructors.length>0){

            try {
                zbean=cglibProxyBean.getInstance(Class.forName(doc.get(zbeanName)));
                zbeans.put(zbeanName,zbean);
                return (T) zbean;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }else {
            return (T) zbean;
        }
    }
}
