package edu.kh.project.websocket.interceptor;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import jakarta.servlet.http.HttpSession;

/** WebSocketHandler before/after -> intercept connected client session 
 * 
 */
@Component
public class SessionHandshakeInterceptor implements HandshakeInterceptor{

	
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {

		
//		if request -> downcast to ServletServerHttpRequest is possible
		if(request instanceof ServletServerHttpRequest) {
			
//			downcast
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			
//			get session from client
			
			HttpSession session = servletRequest.getServletRequest().getSession();
			
//			session -> prepare to send to Handler 
			attributes.put("session", session);
			
		}
		
		return true; // true -> send session to handler
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			@Nullable Exception exception) {
		// TODO Auto-generated method stub
		
	}

	
	
}
