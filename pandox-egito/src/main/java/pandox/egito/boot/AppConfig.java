package pandox.egito.boot;

import java.util.HashMap;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseConfigurer;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactoryBean;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import pandox.egito.boot.util.BasePropertyConfigurer;
import pandox.egito.boot.util.NameGenerator;
import pandox.egito.model.Usuario;

/**
 * Classe de configuração Spring MVC 3
 */
@Configuration
@PropertySource("classpath:config.properties")
@ComponentScan(nameGenerator = NameGenerator.class, basePackages = "pandox.egito", excludeFilters = @Filter(Configuration.class))
public class AppConfig {

	private static Logger log = Logger.getLogger(AppConfig.class);

	private static HashMap<String, Object> velocityProperties = new HashMap<String, Object>();

	@Bean
	public static final BasePropertyConfigurer voucherPropertyConfigurer() {
		BasePropertyConfigurer config = new BasePropertyConfigurer();
		config.setIgnoreResourceNotFound(false);
		Resource location = new ClassPathResource("config.properties");
		config.setLocation(location);
		return config;
	}

	@Bean
	public DriverManagerDataSource dataSource() {
		// EmbeddedDatabaseFactoryBean bean = new EmbeddedDatabaseFactoryBean();
		// bean.s
//		DataSourceFactory aaa = new SimpleDriverDataSource();
		
		log.info("Configurando [dataSource]...");
		DriverManagerDataSource ds = new DriverManagerDataSource();
		// jdbc:mysql://kmweb.ckx7dqi7gxfq.us-east-1.rds.amazonaws.com/kmweb />
		ds.setUrl("jdbc:mysql://localhost/egito");
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUsername("root");
		ds.setPassword("");
		log.info("Datasource configurado: " + ds.toString());
		return ds;
	}

	@Bean
	public SessionFactory sessionFactory() {
		log.info("Configurando [sessionFactory]...");
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.hbm2ddl.auto", "create");
		hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		hibernateProperties.put("format_sql", true);
		hibernateProperties.put("showSql", true);
		
		
		LocalSessionFactoryBuilder bean = new LocalSessionFactoryBuilder(dataSource());
//		bean.setProperties(hibernateProperties);
		bean.setProperty("hibernate.hbm2ddl.auto", "create");
		bean.addAnnotatedClass(Usuario.class);
		
		log.info(bean);
		return bean.buildSessionFactory();
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		log.info("Configurando [transactionManager]...");
		log.info(sessionFactory());
		return new HibernateTransactionManager(sessionFactory());
	}

	@Bean
	public static final VelocityConfigurer velocityConfig() {
		String path = "file:/export/htdocs/pandox.base.intranet";
		log.info("Configurando Velocity...");
		log.info("Diretorio dos templates:" + path);

		velocityProperties.put("input.encoding", "UTF-8");
		velocityProperties.put("output.encoding", "UTF-8");
		velocityProperties.put("directive.foreach.counter.name", "velocityCount");
		velocityProperties.put("directive.foreach.counter.initial.value ", "1");
		VelocityConfigurer config = new VelocityConfigurer();
		config.setVelocityPropertiesMap(velocityProperties);
		config.setResourceLoaderPath(path);
		return config;
	}

	@Bean(name = "resourceBundleMessageSource")
	public static final ReloadableResourceBundleMessageSource resourceBundleMessageSource() {
		ReloadableResourceBundleMessageSource resourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("classpath:Messages");
		resourceBundleMessageSource.setDefaultEncoding("UTF-8");
		return resourceBundleMessageSource;
	}

	@Bean
	public static final ReloadableResourceBundleMessageSource config() {
		ReloadableResourceBundleMessageSource resourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("classpath:config");
		resourceBundleMessageSource.setDefaultEncoding("UTF-8");
		return resourceBundleMessageSource;
	}

	@Bean
	public static final VelocityViewResolver viewResolver() {
		VelocityViewResolver viewResolver = new VelocityViewResolver();
		viewResolver.setToolboxConfigLocation("/WEB-INF/toolbox.xml");
		HashMap<String, Object> velocityProperties = new HashMap<String, Object>();
		viewResolver.setCache(true);
		viewResolver.setSuffix(".vm");
		viewResolver.setAttributesMap(velocityProperties);
		return viewResolver;
	}

}
