package xyz.panyi.imserver.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class DatabaseManager {
    private static final String CONFIG_FILE = "mybatis-config.xml";

    private SqlSession mSqlSession;

    private SectionDao mSessionDao;

    public void BatisDao(){
    }

    public void initDao(){
        try {
            InputStream inputStream = Resources.getResourceAsStream(CONFIG_FILE);
            final SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

            addMappers(sqlSessionFactory);
            mSqlSession = sqlSessionFactory.openSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addMappers(final SqlSessionFactory sqlSessionFactory){
        if(sqlSessionFactory == null)
            return;

        sqlSessionFactory.getConfiguration().addMapper(SectionDao.class);
    }


    public SectionDao getSessionDao(){
        if(mSessionDao  == null){
            mSessionDao = mSqlSession.getMapper(SectionDao.class);
        }
        return mSessionDao;
    }

}
