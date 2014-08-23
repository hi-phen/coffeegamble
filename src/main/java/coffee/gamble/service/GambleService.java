package coffee.gamble.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.gamble.dao.GambleDAO;
import coffee.gamble.domain.GambleLoser;
import coffee.gambler.domain.Gambler;
import coffee.gambler.service.GamblerService;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

/**
* <pre>
* coffee.gamble.service
*   |_ GambleService.java
* </pre>
* 
* Desc : 내기 서비스 
* @Author  : Min seob Kim
* @Version :
 */
@Service
public class GambleService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	GamblerService gamblerService;
	
	@Autowired
	GambleDAO gambleDAO;

	private final int GAMBLE_SIZE = 10; // 1일때 100개로 돌림
	
	/**
	 * Desc : 내기 결과 조회 
	 * @return 
	 * @Method Name : getGambleResult
	 */
	public Gambler getGambleResult(List<Gambler> gambleEntry) {
		HashMap<Long, Integer[]> bucket = new HashMap<Long, Integer[]>();
		HashMap<Long, Gambler> gamblerMap = new HashMap<Long, Gambler>();
		
		int from = 0;
		for(Gambler gambler : gambleEntry){
			int chance = (int) (gambler.getChance()*GAMBLE_SIZE);
			if(chance>0){
				bucket.put(gambler.getGamblerId().getId(),new Integer[]{from,from+=chance});
				gamblerMap.put(gambler.getGamblerId().getId(),gambler);
			}
		}
		
		//추첨자가 나올때까지 무한루프
		while(true){
			int rndNum = RandomUtils.nextInt(0, 100*GAMBLE_SIZE);
			for(Entry<Long,Integer[]> entry : bucket.entrySet()){
				long id = entry.getKey();
				Integer[] range = entry.getValue();
				if(range[0] <= rndNum && rndNum < range[1]){ //당첨자
					Gambler gambler = gamblerMap.get(id);
					addLoser(gambler);
					return gambler;
				}
			}
		}
	}
	
	/**
	 * Desc : 당첨 확률이 계산된 실제 게임 참가자를 조회 
	 * @return 
	 * @Method Name : getGambleEntry
	 */
	public List<Gambler> getGambleEntry() {
		List<Gambler> activeGamblerList = gamblerService.getActiveGamblerList(true);
		List<GambleLoser> loserOnThisWeek = getLoserOnThisWeek();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(activeGamblerList.size() > 0){//게임참가자가 0명 이상일경우만 계산
			if(activeGamblerList.size() == 1){ //혼자일때..
				activeGamblerList.get(0).setChance(100);
			}
			
			HashMap<Long, Gambler> gamblermap = new HashMap<Long, Gambler>(); //키로 참가자를 찾기 위한 맵
			float baseChance = ((int)(100f/activeGamblerList.size()*100f))/100f; //소수점 두번째 자리 까지만
			for(Gambler gambler : activeGamblerList){
				gambler.setChance(baseChance); //기본 확률 세팅
				gamblermap.put(gambler.getGamblerId().getId(), gambler);
			}
			

			for(GambleLoser loser : loserOnThisWeek){
				//당첨자는 현재 자신의 당첨확률 모두 반납
				long loserId = loser.getGamblerId().getId();
				Gambler gambler = gamblermap.get(loserId);
				if(gambler == null) continue; // 당첨자가 현재 참가자에 없음
				float chance = gambler.getChance(); //당첨자의 확률
				gambler.setChance(0); //당첨자는 확률을 반납
				gambler.getWeightLog().add(sdf.format(loser.getLoseDate())+"   당첨 > "+String.valueOf(chance*-1)+"%("+gambler.getChance()+"%)"); //로그 기록
				
				if(activeGamblerList.size() == 1){ //혼자일경우는..
					gambler.setChance(chance);
					gambler.getWeightLog().add(sdf.format(loser.getLoseDate())+" 미당첨 > "+ String.valueOf(chance)+"%("+gambler.getChance()+"%)"); //로그 기록
				}else{
					//나머지 참가자들은 확률을 나눠 가진다
					float divChance = ((int)(chance/(activeGamblerList.size()-1)*100f))/100f;
					for(Gambler winner : activeGamblerList){
						if(winner.getGamblerId().getId() != loserId){
							winner.setChance(((int)((winner.getChance()+divChance)*100f))/100f);
							winner.getWeightLog().add(sdf.format(loser.getLoseDate())+" 미당첨 > "+String.valueOf(divChance)+"%("+winner.getChance()+"%)"); //로그 기록		
						}
					}
				}
			}
		}
		
		return activeGamblerList;
	}

	/**
	 * Desc : 당첨자 입력 
	 * @Method Name : addLoser
	 * @param gambler
	 * @return 
	 */
	public Key addLoser(Gambler gambler) {
		return gambleDAO.addLoser(new GambleLoser(gambler.getGamblerId(),new Date()).toEntity());
	}
	
	/**
	 * Desc : 당첨자 입력 (날짜 지정) 
	 * @Method Name : addLoser
	 * @param gambler
	 * @param date
	 * @return
	 */
	public Key addLoser(Gambler gambler,Date date) {
		return gambleDAO.addLoser(new GambleLoser(gambler.getGamblerId(),date).toEntity());
	}

	/**
	 * Desc : 이번주 당첨자 명단을 받아옴 
	 * @return 
	 * @Method Name : getLoserOnThisWeek
	 */
	public List<GambleLoser> getLoserOnThisWeek() {
		//이번주의 시작(일요일) 끝(토요일)을 계산
		Date now = new Date();
		int day = DateUtils.toCalendar(now).get(Calendar.DAY_OF_WEEK);
		Date sunday = DateUtils.addDays(now, 1-day);
		Date saterday = DateUtils.addDays(now, 7-day);
		
		return getLoser(sunday, saterday);
	}
	
	/**
	 * Desc : 당첨자 명단을 받아옴 
	 * @Method Name : getLoser
	 * @param start
	 * @param end
	 * @return
	 */
	public List<GambleLoser> getLoser(Date start, Date end){
		Iterable<Entity> losers = gambleDAO.getLoser(start,end);
		ArrayList<GambleLoser> loserArray = new ArrayList<GambleLoser>();
		for(Entity loser : losers){
			loserArray.add(new GambleLoser(loser));
		}
		return loserArray;
	}
	
}
