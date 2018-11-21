package com.zjw.dom;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

public class DomAnalysis {

    public static Document load(String filename) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(new File(filename)); // 读取XML文件,获得document对象
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }
    public static Document load(File file) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(file); // 读取XML文件,获得document对象
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }
    public static Document load(InputStream inputStream) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(inputStream); // 读取XML文件,获得document对象
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }
    public static Document load(Reader reader) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(reader); // 读取XML文件,获得document对象
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }

    public static Document load(URL url) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(url); // 读取XML文件,获得document对象
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }
}
