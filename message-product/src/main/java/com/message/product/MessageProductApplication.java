package com.message.product;

import com.message.product.dao.UserDao;
import com.message.product.redis.RedisStatus;
import com.message.product.rocketmq.SendMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessageProductApplication implements CommandLineRunner{

    @Autowired
    UserDao userDao;
    @Autowired
    RedisStatus redisStatus;
    @Autowired
    SendMQ sendMQ;

    @Override
    public void run(String... strings) throws Exception {
        int id=userDao.getId();

        if(redisStatus.isExists(id)){
            System.out.println("已存在");
        }else {
            sendMQ.sendRocketMQ(id);
            redisStatus.setIntoRedis(id);
        }

    }

    public static void main(String[] args) {
		SpringApplication.run(MessageProductApplication.class, args);
	}

}
