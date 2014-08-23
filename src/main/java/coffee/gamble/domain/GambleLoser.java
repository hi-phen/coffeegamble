package coffee.gamble.domain;

import java.util.Date;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class GambleLoser {
	private Key loserId;
	private Key gamblerId;
	private Date loseDate;
	private String gamblerName;
	
	public GambleLoser(Key gamblerId, Date loseDate,String gamblerName) {
		super();
		this.gamblerId = gamblerId;
		this.loseDate = loseDate;
		this.gamblerName = gamblerName;
	}
	
	public GambleLoser(Entity loser) {
		super();
		this.gamblerId = (Key) loser.getProperty("gamblerId");
		this.loseDate = (Date) loser.getProperty("loseDate");
		this.gamblerName = (String) loser.getProperty("gamblerName");
		this.loserId = (Key) loser.getKey();
	}

	public GambleLoser(long key){
		super();
		this.loserId = KeyFactory.createKey("gambleloser", key);
	}
	
	public Entity toEntity(){
		Entity entity;
		if(loserId == null){
			entity = new Entity("gambleloser");
		}else{
			entity = new Entity("gambleloser", loserId.getId());
		}
		entity.setProperty("gamblerId", gamblerId);
		entity.setProperty("loseDate", loseDate);
		entity.setProperty("gamblerName", gamblerName);
		return entity;
	}
	
	public Key getLoserId() {
		return loserId;
	}

	public void setLoserId(Key loserId) {
		this.loserId = loserId;
	}

	public Key getGamblerId() {
		return gamblerId;
	}

	public void setGamblerId(Key gamblerId) {
		this.gamblerId = gamblerId;
	}

	public Date getLoseDate() {
		return loseDate;
	}

	public void setLoseDate(Date loseDate) {
		this.loseDate = loseDate;
	}

	public String getGamblerName() {
		return gamblerName;
	}

	public void setGamblerName(String gamblerName) {
		this.gamblerName = gamblerName;
	}
	
}
