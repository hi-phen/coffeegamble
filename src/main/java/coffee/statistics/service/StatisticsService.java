package coffee.statistics.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffee.gamble.domain.GambleLoser;
import coffee.gamble.service.GambleService;

@Service
public class StatisticsService {

	@Autowired
	GambleService gambleService;
	/**
	 * Desc : 해당 월과 주차별 당첨 통계 제공
	 * @Method Name : getStatistics
	 * @param year
	 * @param month
	 * @return
	 * @throws ParseException 
	 */
	public Map<String,Object> getStatistics(int year, int month) throws ParseException {
		Map<Long,Integer> monthly = new HashMap<Long,Integer>();
		List<Map<Long,Integer>> weekly = new ArrayList<Map<Long,Integer>>();
		List<String[]> weeklyText = new ArrayList<String[]>();
		Map<Long,String> gamblerMap = new HashMap<Long, String>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM W"); //주차 알아내기
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd"); //일자 알아내기
		
		for(int i=1;i<7;i++){
			Date weekStart = sdf.parse(String.valueOf(year)+"-"+String.valueOf(month)+" "+String.valueOf(i));
			Date weekEnd = DateUtils.addDays(weekStart, 7);
			
			weeklyText.add(new String[]{sdf2.format(weekStart),sdf2.format(weekEnd)});
			
			List<GambleLoser> losers = gambleService.getLoser(weekStart, weekEnd);
			
			Map<Long,Integer> weeklyItem = new HashMap<Long,Integer>();	
			for(GambleLoser loser : losers){
				String dateStr = sdf2.format(loser.getLoseDate());
				gamblerMap.put(loser.getGamblerId().getId(), loser.getGamblerName());

				// 월 당첨 내역 입력
				// 해당일이 주차에는 포함되지만 월에는 포함되지 않은 경우를 필터링
				if(Integer.valueOf(dateStr.split("-")[0]) == month){
					try{
						Integer hit = monthly.get(loser.getGamblerId().getId());
						monthly.put(loser.getGamblerId().getId(), ++hit);
					}catch (NullPointerException e) {
						monthly.put(loser.getGamblerId().getId(), 1);
					}
				}
				// 주 당첨 내역 입력
				try{
					Integer hit = weeklyItem.get(loser.getGamblerId().getId());
					weeklyItem.put(loser.getGamblerId().getId(), ++hit);
				}catch (NullPointerException e) {
					weeklyItem.put(loser.getGamblerId().getId(), 1);
				}
			}
			
			weekly.add(weeklyItem);
		}
		
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("monthly", monthly);
		returnMap.put("weekly", weekly);
		returnMap.put("weeklyText", weeklyText);
		returnMap.put("gamblerMap", gamblerMap);
		
		return returnMap;
	}
}
