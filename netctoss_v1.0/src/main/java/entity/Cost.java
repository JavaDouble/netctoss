package entity;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * java.sql包下的日期类型：
 * java.sql.Date 年月日
 * java.sql.Time 时分秒
 * java.sql.Timestmap 年月日时分秒
 * 它们都是java.util.Date的子类
 *
 * @author Double
 *
 */
public class Cost implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	//主键
	private Integer costId;
	//资费名
	private String name;
	//基本时长
	private Integer baseDuration;
	//基本费用
	private Double baseCost;
	//单位费用
	private Double unitCost;
	//状态:枚举 0-开通;1-暂停
	private String status;
	//描述
	private String descr;
	//创建时间
	private Timestamp createTime;
	//开通时间
	private Timestamp startTime;
	//费用类型:枚举 1-包月; 2-套餐; 3-计时;
	private String costType;
	public Cost() {
		super();
	}
	public Cost(Integer costId, String name, Integer baseDuration, Double baseCost, Double unitCost, String status,
			String descr, Timestamp createTime, Timestamp startTime, String costType) {
		super();
		this.costId = costId;
		this.name = name;
		this.baseDuration = baseDuration;
		this.baseCost = baseCost;
		this.unitCost = unitCost;
		this.status = status;
		this.descr = descr;
		this.createTime = createTime;
		this.startTime = startTime;
		this.costType = costType;
	}

	public Integer getCostId() {
		return costId;
	}
	public void setCostId(Integer costId) {
		this.costId = costId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getBaseDuration() {
		return baseDuration;
	}
	public void setBaseDuration(Integer baseDuration) {
		this.baseDuration = baseDuration;
	}
	public Double getBaseCost() {
		return baseCost;
	}
	public void setBaseCost(Double baseCost) {
		this.baseCost = baseCost;
	}
	public Double getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}
	@Override
	public String toString() {
		return "资费[编号:"+costId+",资费名称:"+name+",基本时长:"+baseDuration+",基本资费:"+baseCost
				+ ",单位费用:"+unitCost+",状态:"+status+",描述:"+descr+",创建时间:"+createTime
				+ ",开通时间:"+startTime+",资费类型:"+costType+"]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseCost == null) ? 0 : baseCost.hashCode());
		result = prime * result + ((baseDuration == null) ? 0 : baseDuration.hashCode());
		result = prime * result + ((costId == null) ? 0 : costId.hashCode());
		result = prime * result + ((costType == null) ? 0 : costType.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((descr == null) ? 0 : descr.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((unitCost == null) ? 0 : unitCost.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cost other = (Cost) obj;
		if (baseCost == null) {
			if (other.baseCost != null)
				return false;
		} else if (!baseCost.equals(other.baseCost))
			return false;
		if (baseDuration == null) {
			if (other.baseDuration != null)
				return false;
		} else if (!baseDuration.equals(other.baseDuration))
			return false;
		if (costId == null) {
			if (other.costId != null)
				return false;
		} else if (!costId.equals(other.costId))
			return false;
		if (costType == null) {
			if (other.costType != null)
				return false;
		} else if (!costType.equals(other.costType))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (descr == null) {
			if (other.descr != null)
				return false;
		} else if (!descr.equals(other.descr))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (unitCost == null) {
			if (other.unitCost != null)
				return false;
		} else if (!unitCost.equals(other.unitCost))
			return false;
		return true;
	}

}
