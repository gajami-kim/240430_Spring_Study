package com.ezen.www.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@EnableScheduling
@EnableTransactionManagement
@MapperScan(basePackages = {"com.ezen.www.repository"})
@Configuration
public class RootConfig {
	
	@Autowired
	ApplicationContext applicationContext;

	@Bean
	public DataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		//log4jdbc => DB의 로그를 찍을 수 있는 드라이버로 설정 변경
		hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
		hikariConfig.setJdbcUrl("jdbc:log4jdbc:mysql://localhost:3306/springtest");
		hikariConfig.setUsername("springUser");
		hikariConfig.setPassword("mysql");

		//--------여기서부터 hikari 추가 설정(connection-pool)
		//필수
		hikariConfig.setMaximumPoolSize(5); //최대 커넥션 개수
		hikariConfig.setMinimumIdle(5); //최소 유휴(비어있는) 커넥션 개수(Max와 같은 개수로 설정)
		
		//선택
		hikariConfig.setConnectionTestQuery("SELECT now()"); //connecting시 test 쿼리문
		hikariConfig.setPoolName("springHikariCP");
		
		//추가설정(기본값 존재하는 것들)
		//cachePerpStmts : cache 사용 여부 설정(받겠다고 설정하면 하단 3개도 같이 설정해줘야함)
		hikariConfig.addDataSourceProperty("dataSource.cachePerpStmts", "true");
		//mysql 드라이가 연결당 cache 사이즈 : 기본값 250~500 권장
		hikariConfig.addDataSourceProperty("dataSource.prepStmtsCacheSize", "250");
		//connection 당 캐싱할 preparedStatement의 개수 지정 옵션 : default 2556
		hikariConfig.addDataSourceProperty("dataSource.prepStmtsCacheSqlLimit", "true"); //기본값 설정(256)
		//mysql 서버에서 최신 이슈가 있을 경우 지원을 받을 것인지 설정
		hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");
		
		//설정사항을 DataSource에 삽입
		HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
		
		return hikariDataSource;
		
	}
	
	//DB : _(스네이크 표기법) / java : 카멜표기법
	//file_name => fileName로 인식할 수 있게끔 설정
	//별칭 설정
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlFactoryBean = new SqlSessionFactoryBean();
		sqlFactoryBean.setDataSource(dataSource());
		sqlFactoryBean.setMapperLocations(
				applicationContext.getResources("classpath:/mappers/*.xml"));
		sqlFactoryBean.setConfigLocation(
				applicationContext.getResource("classpath:/mybatisConfig.xml"));
		
		return sqlFactoryBean.getObject();
		
	}
	
	//트랜젝션 매니저 설정
	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	
	
}
