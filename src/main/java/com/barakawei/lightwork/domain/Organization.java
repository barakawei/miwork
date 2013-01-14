package com.barakawei.lightwork.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author qianxiaowei
 * @date 2012-11-25
 */
@Entity
public class Organization {

	@Id
	@GeneratedValue(generator = "system_uuid")
	@GenericGenerator(name = "system_uuid", strategy = "uuid")
	private String id;

	private String name;

	private int level;

	@ManyToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "parent_id")
	private Organization parent;

	@ManyToMany(cascade = { CascadeType.REFRESH })
	@JoinTable(name = "organization_role", inverseJoinColumns = @JoinColumn(name = "role_id"), joinColumns = @JoinColumn(name = "org_id"))
	Set<Role> roles = new HashSet<Role>();

	@OneToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY, mappedBy = "org")
	Set<User> users = new HashSet<User>();

	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	Set<Organization> children = new HashSet<Organization>();

	@Column(name = "create_date")
	private Date createDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Organization getParent() {
		return parent;
	}

	public void setParent(Organization parent) {
		this.parent = parent;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Organization> getChildren() {
		return children;
	}

	public void setChildren(Set<Organization> children) {
		this.children = children;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
