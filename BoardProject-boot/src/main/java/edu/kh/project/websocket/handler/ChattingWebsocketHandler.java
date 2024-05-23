package edu.kh.project.websocket.handler;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.kh.project.chatting.model.dto.Message;
import edu.kh.project.chatting.model.service.ChattingService;
import edu.kh.project.member.model.dto.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChattingWebsocketHandler extends TextWebSocketHandler{

	private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());
	
	private final ChattingService service;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		sessions.add(session);
		log.info("{} Connected", session.getId());
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

		sessions.remove(session);
	
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		
//		change JSON to DTO (readValue) 1 : obj to change -> 2 : to what
		Message msg = objectMapper.readValue(message.getPayload(), Message.class); 
		
		int result = service.insertMessage(msg);
		
		if(result > 0) {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm");
			
			// Date -> java.util
			msg.setSendTime(sdf.format(new Date()));
			
			for (WebSocketSession s : sessions) {
				
//				intercepted session
				HttpSession temp = (HttpSession) s.getAttributes().get("session");
				
//				loginMember - get loginMember Number
				int loginMemberNo = ((Member) temp.getAttribute("loginMember")).getMemberNo();
				
//				loginmember is the sender || loginmember is the receiver 
				if (loginMemberNo == msg.getTargetNo() || loginMemberNo == msg.getSenderNo()) {
//					Java DTO now convert it to JSON to send it to JS
					String jsonData = objectMapper.writeValueAsString(msg);
					s.sendMessage(new TextMessage(jsonData));
				}
				
			}
			
		}
		
	}
	
}
