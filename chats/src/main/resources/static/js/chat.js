'use strict';

const user = 1;

const chatsList = document.querySelector('#chats-list');
const chatTitle = document.querySelector('#chat-title');
const chatForm = document.querySelector('#chat-form');
const chatInput = document.querySelector('#chat-input');
const chatMessages = document.querySelector('#chat-messages');

let stompClient = null;
let activeChat = null;

function onConnection() {
  stompClient.subscribe(`/user/${user}/queue/messages`, onMessageReceived);
}

function onError() {
  stompClient = null;
  console.log('Error connecting to chat');
}

async function connectChat() {
  const socket = new SockJS('/ws');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, onConnection, onError);
}

function sendMessage(event) {
  const input = chatInput.value.trim();
  if (!input) {
    return;
  }

  const chatMessage = {
    chatId: activeChat.id,
    sender: user,
    content: input,
    at: new Date().getTime(),
  };

  stompClient.send('/app/chats', {}, JSON.stringify(chatMessage));
  displayMessage(chatMessage);
  chatInput.value = '';

  event.preventDefault();
}


async function onMessageReceived(payload) {
  const message = JSON.parse(payload.body);
  displayMessage(message);
}

async function loadChats() {
  const response = await fetch(`/api/v1/users/${user}/chats`);
  const chats = await response.json();
  chatsList.innerHTML = '';

  for (const chat of chats) {
    const chatElement = document.createElement('li');
    const chatName = document.createElement('p');
    chatName.textContent = await getChatName(chat);

    chatElement.appendChild(chatName);
    chatElement.classList.add('chat-item');
    chatElement.onclick = () => openChat(chat);
    chatsList.appendChild(chatElement);
  }
}

function displayMessage(message) {
  const messageContainer = document.createElement('div');
  messageContainer.classList.add('message');

  if (message.sender === user) {
    messageContainer.classList.add('sender');
  } else {
    messageContainer.classList.add('receiver');
  }

  const messageBody = document.createElement('p');
  messageBody.textContent = message.content;
  messageContainer.appendChild(messageBody);
  chatMessages.appendChild(messageContainer);
}

async function loadChatMessages() {
  const response = await fetch(`/api/v1/chats/${activeChat.id}/messages`);
  const messages = await response.json();
  chatMessages.innerHTML = '';

  messages.forEach(displayMessage);
}

async function getChatName(chat) {
  const otherUserId = chat.users.find(u => u.id !== user);
  const response = await fetch(`/api/v1/profiles/${otherUserId}`);
  const profile = await response.json();
  return profile.name;
}

async function openChat(chat) {
  activeChat = chat;

  chatForm.classList.remove('hidden');
  chatTitle.textContent = await getChatName(chat);
  chatMessages.innerHTML = '';

  loadChatMessages().then(connectChat)
}

loadChats().then();
chatForm.onsubmit = sendMessage;