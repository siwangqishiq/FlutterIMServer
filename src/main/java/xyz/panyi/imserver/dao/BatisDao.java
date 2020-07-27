package xyz.panyi.imserver.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class BatisDao {

    public void BatisDao(){

    }

    public void initDao(){
        String resource = "mybatis-config.xml";
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

            sqlSessionFactory.openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
