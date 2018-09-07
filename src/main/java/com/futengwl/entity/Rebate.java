package com.futengwl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonView;


/**
 * 返利表
 * @author Coco
 *
 */
@Entity
@Table(name = "rebate")
public class Rebate extends BaseEntity<Long> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3264058999815117663L;

	/**
	 * 姓名
	 */
	@Column(name = "username")
	private String name;
	
	/**
	 * 电话
	 */
	@Length(max = 11, min = 11)
	@Column(name = "mobile")
	private String mobile;
	
	//是否购买3980
	@Transient
	private Integer isBuy;
	
	/**
	 * 推荐餐厅
	 */
	@Column(name = "recommendDiningRoom")
	private String recommendDiningRoom;
	
	/**
	 * 餐厅联系人
	 */
	@Column(name = "linkman")
	private String linkman;
	
	
	/**
	 * 餐厅联系人电话
	 */
	@Length(max = 11, min = 11)
	@Column(name = "linkmanTelephone")
	private String linkmanTelephone;	
	
	/**
	 * 状态	 
	 */
	public enum Status {
		/**
		 * 等待审核
		 */
		PENDING_REVIEW,
		
		/**
		 * 已审核
		 */
		CONFIRMED,
		
		/**
		 * 已上线
		 */
		LAUNCHED
	}
	
	/**
	 * 审核状态
	 */
	@JsonView(BaseView.class)
	@Column(nullable = false)
	private Rebate.Status status;
	
	//消费地点
	@Column(name = "eataddress")
	private String eataddress;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRecommendDiningRoom() {
		return recommendDiningRoom;
	}
	public void setRecommendDiningRoom(String recommendDiningRoom) {
		this.recommendDiningRoom = recommendDiningRoom;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	
	public String getLinkmanTelephone() {
		return linkmanTelephone;
	}
	public void setLinkmanTelephone(String linkmanTelephone) {
		this.linkmanTelephone = linkmanTelephone;
	}
	

	public Rebate.Status getStatus() {
		return status;
	}
	public void setStatus(Rebate.Status status) {
		this.status = status;
	}
	public Integer getIsBuy() {
		return isBuy;
	}
	public void setIsBuy(Integer isBuy) {
		this.isBuy = isBuy;
	}
	public String getEataddress() {
		return eataddress;
	}
	public void setEataddress(String eataddress) {
		this.eataddress = eataddress;
	}
}
