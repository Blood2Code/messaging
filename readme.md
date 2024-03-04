# Description:

*This project provides a foundation for building real-time, interactive communication applications using Spring Boot WebSockets. It enables you to establish connections between clients and servers, facilitating efficient message exchange with user-specific destination patterns.* 

## Key Features:

WebSocket Integration: Leverages Spring Boot WebSockets for low-latency, bidirectional communication.
User-Specific Channels: Allows messages to be directed to and received by individual users, enhancing personalization and organization.
Customizable: Offers flexibility to tailor the messaging logic to specific project requirements.
Easy Setup: Provides clear instructions for getting started and using the project effectively.
Getting Started:

### 1. Prerequisites:

+ ***Java 17 or higher***
+ ***Maven (or similar build tool)***

### 2. Clone the Repository:
    git clone https://github.com/Blood2Code/messaging.git
    cd messaging

### 3. Install Dependencies:

    mvn install

### 4. Run the Application:

    mvn spring-boot:run

### 5. Connect to the Server:

#### Use a WebSocket client, such as StompJS in your frontend application, to connect to the server at 
    ws://localhost:8080/websocket

# Example Usage:

### Frontend (JavaScript):

    // Establish a WebSocket connection
    const stompClient = new StompJs.Client({
        brokerURL: 'ws://localhost:8080/websocket'
    });
    
    // Subscribe to a user-specific channel
    stompClient.subscribe(`/user/${username}`, (greeting) => {
        showGreeting(JSON.parse(greeting.body).content);
    });
    
    // Send a message to another user
    stompClient.send(
        "/app/hello",
        JSON.stringify({ name: 'John', message: 'Hello from John!' }),
        { destination: '/user/umar' }
    );
(Adjust the URL if necessary)

# Backend (Java):

    
    @Component
    public class EventProvider implements ApplicationListener<SessionConnectEvent> {

        @Autowired
        private SimpMessagingTemplate messagingTemplate;
    
        @Override
        public void onApplicationEvent(SessionConnectEvent event) {
            StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(event.getMessage());
            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
            headerAccessor.setSessionId(stompHeaderAccessor.getSessionId());
            headerAccessor.setLeaveMutable(true);
    
            String username = Objects.requireNonNull(event.getUser()).getName();
        }
    
        public void sendMessageToUser(String username, Greeting message) {
            messagingTemplate.convertAndSend("/user/" + username, message);
        }
    
        public void sendMessageToTopic(String topic, String message) {
            messagingTemplate.convertAndSend("/topic/" + topic, message);
        }
    }