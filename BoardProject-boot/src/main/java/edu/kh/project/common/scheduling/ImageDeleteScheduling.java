package edu.kh.project.common.scheduling;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.kh.project.board.model.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
public class ImageDeleteScheduling {

	private final BoardService service;
	
	/*
	* @Scheduled
	*
	* * Spring에서 제공하는 스케줄러 - 스케줄러 : 시간에 따른 특정 작업(Job)의 순서를 지정하는 방법.
	*
	* 설정 방법
	* 1) XXXSSAPPlication.java 파일에 @EnableScheduling 어노테이션 추가
	* 2) 스케쥴링 동작을 위한 클래스 작성
	*
	*
	* @Scheduled 속성
	*  - fixedDelay : 이전 작업이 끝난 시점으로 부터 고정된 시간(ms)을 설정.
	*    @Scheduled(fixedDelay = 10000) // 이전 작업이 시작된 후 10초 뒤에 실행
	*   
	*  - fixedRate : 이전 작업이 수행되기 시작한 시점으로 부터 고정된 시간(ms)을 설정.
	*    @Scheduled(fixedRate  = 10000) // 이전 작업이 끝난 후 10초 뒤에 실행
	*   
	*   
	* * cron 속성 : UNIX계열 잡 스케쥴러 표현식으로 작성 - cron="초 분 시 일 월 요일 [년도]" - 요일 : 1(SUN) ~ 7(SAT)
	* ex) 2019년 9월 16일 월요일 10시 30분 20초 cron="20 30 10 16 9 2 " // 연도 생략 가능
	*
	* - 특수문자
	* * : 모든 수.
	* - : 두 수 사이의 값. ex) 10-15 -> 10이상 15이하
	* , : 특정 값 지정. ex) 3,4,7 -> 3,4,7 지정
	* / : 값의 증가. ex) 0/5 -> 0부터 시작하여 5마다
	* ? : 특별한 값이 없음. (월, 요일만 해당)
	* L : 마지막. (월, 요일만 해당)
	* @Scheduled(cron="0 * * * * *") // 모든 0초 마다 -> 매 분마다 실행
	*
	*
	* * 주의사항 - @Scheduled 어노테이션은 매개변수가 없는 메소드에만 적용 가능.
	*
	*/

	@Value("${my.profile.folder-path}")
	private String profileFolderPath;
	
//	board image
	@Value("${my.board.folder-path}")
	private String boardFolderPath;
	
	@Scheduled(cron = "0 0 0 1 * *")
	public void scheduling() {
//		log.info("hi");
		
		File boardFolder = new File(boardFolderPath);
		File memberFolder = new File(profileFolderPath);
		
		File[] boardArr = boardFolder.listFiles();
		File[] memArr = memberFolder.listFiles();
		
		File[] imgArr = new File[boardArr.length+memArr.length];
		
//		1) what to copy, what index to start, to where to copy, what index to start, length of the copy)
		System.arraycopy(memArr, 0, imgArr, 0, memArr.length);
		System.arraycopy(boardArr, 0, imgArr, 0, boardArr.length);
		
		List<File> serverImageList = Arrays.asList(imgArr);
		
		List<String> dbImageList = service.selectDbImageList();
		
		if(!serverImageList.isEmpty()) {
			
			for(File serverImage : serverImageList) {
				
				if(dbImageList.indexOf(serverImage.getName()) == -1 ) {
					log.info(serverImage.getName() + "삭제");
					serverImage.delete();
				}
				
			}
			
			
		}
		
	}
	
	
}
