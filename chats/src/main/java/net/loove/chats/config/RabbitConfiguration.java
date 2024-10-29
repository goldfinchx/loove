package net.loove.chats.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@EnableRabbit
@RequiredArgsConstructor
public class RabbitConfiguration {

    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;

    @Value("${spring.rabbitmq.port}")
    private int rabbitPort;

    @Value("${spring.rabbitmq.username}")
    private String rabbitUsername;

    @Value("${spring.rabbitmq.password}")
    private String rabbitPassword;

    @Value("${rabbitmq.exchange-name}")
    private String exchangeName;

    @Bean
    public ConnectionFactory rabbitConnectionFactory() {
        final CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(this.rabbitHost);
        factory.setPort(this.rabbitPort);
        factory.setUsername(this.rabbitUsername);
        factory.setPassword(this.rabbitPassword);
        return factory;
    }

    @Bean
    public Exchange chatsExchange() {
        return new TopicExchange(this.exchangeName);
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(this.rabbitConnectionFactory());
    }

}
