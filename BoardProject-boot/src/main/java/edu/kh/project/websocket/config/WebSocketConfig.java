package edu.kh.project.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import edu.kh.project.websocket.handler.ChattingWebsocketHandler;
import edu.kh.project.websocket.handler.TestWebsocketHandler;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer{

	private final HandshakeInterceptor handshakeInterceptor;
	
	private final TestWebsocketHandler testWebsocketHandler;
	
	private final ChattingWebsocketHandler chattingWebsocketHandler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

		registry.addHandler(testWebsocketHandler, "/testSock").addInterceptors(handshakeInterceptor)
		.setAllowedOriginPatterns("http://localhost/", "http://127.0.0.1/", "192.168.50.246").withSockJS();
		// for setAllowedOriginPatters -> put domain web path ex) www.dukdakdukdak.com
		
		registry.addHandler(chattingWebsocketHandler, "/chattingSock")
		.addInterceptors(handshakeInterceptor)
		.setAllowedOriginPatterns("http://localhost/", "http://127.0.0.1/", "192.168.50.246").withSockJS();
	}

}
