package db_hib;

// Generated Dec 1, 2012 3:05:25 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Transaction generated by hbm2java
 */
public class Transaction implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3470717241307007968L;
	private long id;
	private long from;
	private long to;
	private float amount;
	private long testimonialId;
	private Date date;

	public Transaction() {
	}

	public Transaction(long id, long from, long to, float amount,
			long testimonialId, Date date) {
		this.id = id;
		this.from = from;
		this.to = to;
		this.amount = amount;
		this.testimonialId = testimonialId;
		this.date = date;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getFrom() {
		return this.from;
	}

	public void setFrom(long from) {
		this.from = from;
	}

	public long getTo() {
		return this.to;
	}

	public void setTo(long to) {
		this.to = to;
	}

	public float getAmount() {
		return this.amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public long getTestimonialId() {
		return this.testimonialId;
	}

	public void setTestimonialId(long testimonialId) {
		this.testimonialId = testimonialId;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
