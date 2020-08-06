package com.yc.favorite.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisHelper {
	
	private static SqlSessionFactory sqlSessionFactory;
	static {
		try {
			 //mybatis配置文件
		  	  String resource = "mybatis.xml";
		  	  //读入配置文件
		  	  InputStream inputStream = Resources.getResourceAsStream(resource);
		  	  //构建会话工厂  ==》23设计模式  工厂模式
		  	   sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			}catch(IOException e) {
				throw new RuntimeException(e);
			}
	}
public static SqlSession openSession() {
	return sqlSessionFactory.openSession();
}
}
