package com.barakawei.lightwork.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.barakawei.lightwork.domain.Purchasing;
import com.barakawei.lightwork.domain.Role;
import com.barakawei.lightwork.domain.SearchForm;
import com.barakawei.lightwork.domain.User;
import com.barakawei.lightwork.service.UserService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component("userContextUtil")
public class UserContextUtil {

	@Autowired
	private UserService userService;

    @Autowired
    protected TaskService taskService;

	private static UserService us;

    private static TaskService ts;

	@PostConstruct
	public void init() {
		us = this.userService;
        ts = this.taskService;

	}

    public static String notifyMessage(){
        Map notify = new HashMap();
        List<User> users = us.findAll();
        for(User u : users){

            List<Task> tasks = ts.createTaskQuery().processDefinitionKey(Purchasing.FLOW).taskCandidateGroup(u.getRoles().iterator().next().getName()).active().orderByTaskPriority().desc()
                    .orderByTaskCreateTime().desc().list();
            for (Task _task : tasks) {
                ts.claim(_task.getId(), u.getId());
            }

            long  todo = ts.createTaskQuery().taskAssignee(u.getId()).active().count();
            notify.put("userID"+u.getId(),todo);
        }
        String msg = JsonUtil.Obj2Json(notify);
        return msg;
    }

	public static UserDetails getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            return null;
        }
		return (UserDetails)authentication.getPrincipal();
	}

	public static User getCurrentUser() {
		UserDetails ud = getCurrentUserDetails();
		Assert.notNull(ud, "error_data_null");
		User user = us.findUserByAccount(ud.getUsername());
		Assert.notNull(user, "error_data_null");
		return user;

	}

	public static Role getCurrentRole() {
		User user = getCurrentUser();
		Assert.notNull(user, "error_data_null");
		List<Role> roles = user.getRoles();
		Assert.isTrue(null!=roles&&!roles.isEmpty(), "error_data_null");
		return roles.iterator().next();

	}
	
	public static boolean isCurrentUser(User user){
		Assert.notNull(user, "error_data_null");
		return user.getId().equals(getCurrentUser().getId());
	}

}
