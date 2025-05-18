    package com.end2end.ansimnuri.util.config;

    import com.end2end.ansimnuri.note.endpoint.NoteEndpoint;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.socket.WebSocketHandler;
    import org.springframework.web.socket.config.annotation.EnableWebSocket;
    import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
    import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

    @EnableWebSocket
    @Configuration
    public class WebSocketConfig implements WebSocketConfigurer {
        @Override
        public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            registry.addHandler(myHandler(), "/ws/note")
                    .setAllowedOrigins("*");
        }

        @Bean
        public WebSocketHandler myHandler() {
            return new NoteEndpoint();
        }
    }
