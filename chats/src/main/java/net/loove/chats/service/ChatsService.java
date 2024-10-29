package net.loove.chats.service;

import lombok.RequiredArgsConstructor;
import net.loove.chats.config.RabbitProperties;
import net.loove.chats.dto.MessageDTO;
import net.loove.chats.model.Chat;
import net.loove.chats.model.Message;
import net.loove.chats.repository.ChatsRepository;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatsService {

    private final ChatsRepository repository;
    private final AmqpAdmin amqpAdmin;
    private final AmqpTemplate amqpTemplate;
    private final RabbitListenerEndpointRegistry registry;
    private final RabbitProperties properties;

    public void startChat(Long userA, Long userB) {
        final Chat chat = this.repository.findByUsers(userA, userB).orElseGet(() -> this.repository.save(new Chat(userA, userB)));
        final Queue queue = this.createChatQueue(chat);
        this.addChatListener(queue);
    }

    private Queue createChatQueue(Chat chat) {
        final String queueName = this.properties.getQueuePrefix() + chat.getId();
        final Queue queue = new Queue(queueName);
        this.amqpAdmin.declareQueue(queue);
        return queue;
    }

    private void addChatListener(Queue queue) {
        final String exchangeName = this.properties.getExchangeName();
        final AbstractMessageListenerContainer listenerContainer = this.getListenerContainer(exchangeName);
        listenerContainer.addQueues(queue);
    }

    private AbstractMessageListenerContainer getListenerContainer(String id) {
        return (AbstractMessageListenerContainer) this.registry.getListenerContainer(id);
    }

    public void handleMessage(MessageDTO dto) {
        final Chat chat = this.repository.findById(dto.getChatId()).orElseThrow();
        final Message msg = new Message(chat, dto.getSender(), dto.getContent());
        chat.getMessages().add(msg);
        this.repository.save(chat);
    }


}
