package com.barakawei.lightwork.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author qianxiaowei
 * @date 2012-11-25
 */
@Entity
public class Role {

    public static final String ROLE_ADMINI= "role_admin";
    public static final String ROLE_PURCHASING= "role_purchasing";
    public static final String ROLE_PLANNING= "role_planning";
    public static final String ROLE_WAREHOUSE = "role_warehouse";
    public static final String ROLE_QUALITY= "role_quality";
    public static final String ROLE_TECHNOLOG= "role_technolog";
    public static final String ROLE_LEADER= "role_leader";
    public static final String ROLE_PRODUCT = "role_product";


	@Id
	@GeneratedValue(generator = "system_uuid")
	@GenericGenerator(name = "system_uuid", strategy = "uuid")
	private String id;

	private String name;

	private String type;

    private String description;

	private boolean enable;

	@Column(name = "create_date")
	private Date createDate;

	@ManyToMany(cascade = { CascadeType.REFRESH })
	@JoinTable(name = "role_privilege", inverseJoinColumns = @JoinColumn(name = "privilege_id"), joinColumns = @JoinColumn(name = "role_id"))
	@JsonIgnore
	List<Privilege> privileges = new ArrayList<Privilege>();

	@ManyToMany(cascade = { CascadeType.REFRESH }, mappedBy = "roles")
	@JsonIgnore
	List<User> users = new ArrayList<User>();

	@ManyToMany(cascade = { CascadeType.REFRESH }, mappedBy = "roles")
	@JsonIgnore
	List<Organization> orgs = new ArrayList<Organization>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the enable
	 */
	public boolean isEnable() {
		return enable;
	}

	/**
	 * @param enable
	 *            the enable to set
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the privileges
	 */
	public List<Privilege> getPrivileges() {
		return privileges;
	}

	/**
	 * @param privileges
	 *            the privileges to set
	 */
	public void setPrivileges(List<Privilege> privileges) {
		this.privileges = privileges;
	}

	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * @param users
	 *            the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	/**
	 * @return the orgs
	 */
	public List<Organization> getOrgs() {
		return orgs;
	}

	/**
	 * @param orgs
	 *            the orgs to set
	 */
	public void setOrgs(List<Organization> orgs) {
		this.orgs = orgs;
	}

}
