package com.xuzp.stockplayer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author za-xuzhiping
 * @Date 2018/1/10
 * @Time 20:02
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "com.xuzp")
public class StockPlayerApplication {

	private static final Logger log = LoggerFactory.getLogger(StockPlayerApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(StockPlayerApplication.class, args);
		log.info("oops");
	}
}
