package com.YoutubeAccount.Manager;

import com.mongodb.client.MongoDatabase;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableMongoAuditing
@EnableTransactionManagement
public class ManagerApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		System.setProperty("MONGODB_URI", dotenv.get("MONGODB_URI"));
		SpringApplication.run(ManagerApplication.class, args);
	}

	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}

}
