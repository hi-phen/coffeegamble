package coffee.gambler.domain;

import java.util.ArrayList;
import java.util.Date;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class Gambler{
	private Key gamblerId; // 아이디
	private String name; // 이름
	private boolean active = true; //활성화 여부
	private float chance; // 기본 확률 
	private ArrayList<String> weightLog = new ArrayList<String>(); // 가중치 로그
	private Date addDate = new Date();

	
	public Gambler(){}
	
	public Gambler(String name,boolean isActive) {
		super();
		this.name = name;
		this.setActive(isActive);
	}
	
	public Gambler(Entity entity){
		super();
		this.gamblerId = entity.getKey();
		this.name = (String) entity.getProperty("name");
		this.addDate = (Date) entity.getProperty("addDate");
		this.active = (Boolean) entity.getProperty("active");
	}
	
	public Gambler(long key){
		super();
		this.gamblerId = KeyFactory.createKey("gambler", key);
	}
	
	public Entity toEntity(){
		Entity entity;
		if(gamblerId != null){ 
			entity = new Entity("gambler",gamblerId.getId());
		}else{
			entity = new Entity("gambler");
		}
		entity.setProperty("name", name);
		entity.setProperty("active", active);
		entity.setProperty("addDate", addDate);
		return entity;
	}
	
	public Key getGamblerId() {
		return gamblerId;
	}
	public void setGamblerId(Key gamblerId) {
		this.gamblerId = gamblerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public float getChance() {
		return chance;
	}

	public void setChance(float baseChance) {
		this.chance = baseChance;
	}

	public ArrayList<String> getWeightLog() {
		return weightLog;
	}

	public void setWeightLog(ArrayList<String> weightLog) {
		this.weightLog = weightLog;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
	public boolean isActive() {
		return active;
	}
}
