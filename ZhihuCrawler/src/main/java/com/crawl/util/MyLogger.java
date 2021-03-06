package com.crawl.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;

public class MyLogger extends Logger{
    protected MyLogger(String name) {
        super(name);
    }
    private static Properties setLogProperty(){
        Properties p = new Properties();
        Properties ipP = new Properties();
        String ip = null;
        try {
            p.load(MyLogger.class.getResourceAsStream("/log4j.properties"));
            ipP.load(MyLogger.class.getResourceAsStream("/server.properties"));
            InetAddress addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress().toString(); //获取本机ip
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(ip.equals(ipP.getProperty("innerip"))){
            //运行在服务器上
            p.setProperty("log4j.appender.logfile.File","/alidata/server/mycrawllog.log");
            p.setProperty("log4j.rootLogger","INFO,stdout,logfile");
            p.setProperty("log4j.appender.logfile.Threshold","ERROR");
        }else{
            //运行在本地,日志只需输出到控制台
            p.setProperty("log4j.rootLogger","INFO,stdout,logfile");
            p.setProperty("log4j.appender.logfile.Threshold","INFO");
        }
        return p;
    }
    public static Logger getMyLogger(Class<?> c){
        Logger logger = Logger.getLogger(c);
        PropertyConfigurator.configure(setLogProperty());
        return logger;
    }

}
