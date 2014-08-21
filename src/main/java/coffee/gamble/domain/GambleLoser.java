package coffee.gamble.domain;

import java.util.Date;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class GambleLoser {
	private Key loserId;
	private Key gamblerId;
	private Date loseDate;
	
	public GambleLoser(Key gamblerId, Date loseDate) {
		super();
		this.setGamblerId(gamblerId);
		this.setLoseDate(loseDate);
	}
	
	public GambleLoser(Entity loser) {
		super();
		this.setGamblerId((Key) loser.getProperty("gamblerId"));
		this.setLoseDate((Date) loser.getProperty("loseDate"));
	}

	public Entity toEntity(){
		Entity entity = new Entity("gambleloser",gamblerId);
		entity.setProperty("gamblerId", gamblerId);
		entity.setProperty("loseDate", loseDate);
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
	
}
