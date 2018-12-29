package rabbitcustomer.jacy;

import com.rabbitmq.client.*;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
//@EnableScheduling//启用任务调度.
//@RabbitListener(queues="foo")//启用Rabbit队列监听foo key.
//@RabbitListener(queues="news")//启用Rabbit队列监听foo key.
public class JacyApplication
{

    private final static String QUEUE_NAME = "rabbitMQ.test";

    //  接收到消息处理 接收到消息处理
//    @RabbitHandler
//    public void onMessage(@Payload String foo){
//        System.out.println(" >>> "+new Date() + ": " + foo);
//    }

    public static void main(String[] args)  throws IOException, TimeoutException {
        SpringApplication.run(JacyApplication.class, args);
        System.out.println("Customer running");

        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ地址
        factory.setHost("localhost");
        //创建一个新的连接
        Connection connection = factory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        //声明要关注的队列
        channel.queueDeclare(QUEUE_NAME, false, false, true, null);
        System.out.println("Customer Waiting Received messages");
        //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
        // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Customer Received '" + message + "'");
            }
        };
        //自动回复队列应答 -- RabbitMQ中的消息确认机制
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }

}
