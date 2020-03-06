package ar.edu.unnoba.poo.practica4.entities;

import java.util.Date;
import java.lang.String;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Entity

public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ManyToOne(fetch=FetchType.LAZY)
	private User owner;
	private String eventName;
	@Temporal(TemporalType.DATE)
	private Date eventDate;
	@Temporal(TemporalType.DATE)
	private Date startRegistrations;
	@Temporal(TemporalType.DATE)
	private Date endRegistrations;
	private int capacity;
	private float cost;
	private boolean privateEvent;
	private String place;
	
	//private boolean isAvailable;
	
	@Version
	protected int version;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User user) {
		this.owner = user;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public Date getStartRegistrations() {
		return startRegistrations;
	}
	public void setStartRegistrations(Date startRegistrations) {
		this.startRegistrations = startRegistrations;
	}
	public Date getEndRegistrations() {
		return endRegistrations;
	}
	public void setEndRegistrations(Date endRegistrations) {
		this.endRegistrations = endRegistrations;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	public boolean isPrivateEvent() {
		return privateEvent;
	}
	public void setPrivateEvent(boolean privateEvent) {
		this.privateEvent = privateEvent;
	}
	
	@Override
	public String toString() {
		return "Event [id=" + id + ", owner=" + owner + ", eventDate=" + eventDate + ", startRegistrations="
				+ startRegistrations + ", endRegistrations=" + endRegistrations + ", capacity=" + capacity + ", cost="
				+ cost + ", privateEvent=" + privateEvent + ", version=" + version + "]";
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	/*public boolean isAvailable() {
		return isAvailable;
	//}
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	*/
	
	
}