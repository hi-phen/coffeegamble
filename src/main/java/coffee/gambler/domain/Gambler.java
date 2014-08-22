package coffee.gambler.domain;

import java.util.ArrayList;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class Gambler{
	private Key gamblerId; // 아이디
	private String name; // 이름
	private boolean isActive = true; //활성화 여부
	private float chance; // 기본 확률 
	private ArrayList<String> weightLog = new ArrayList<String>(); // 가중치 로그
	
	public Gambler(){}
	
	public Gambler(String name,boolean isActive) {
		super();
		this.name = name;
		this.isActive = isActive;
	}
	
	public Gambler(Entity entity){
		super();
		this.gamblerId = entity.getKey();
		this.name = (String) entity.getProperty("name");
		this.isActive = (Boolean) entity.getProperty("isActive");
	}
	
	public Entity toEntity(){
		Entity entity;
		if(gamblerId != null){ 
			entity = new Entity("gambler",gamblerId.getId());
		}else{
			entity = new Entity("gambler");
		}
		entity.setProperty("name", name);
		entity.setProperty("isActive", isActive);
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
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
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

}
