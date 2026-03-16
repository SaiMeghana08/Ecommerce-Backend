package com.JWT.demo.Service;

import com.JWT.demo.Configuration.WebSecurityConfig;
import com.JWT.demo.Model.Role;
import com.JWT.demo.Model.RolesList;
import com.JWT.demo.Repository.RoleRepo;
import com.JWT.demo.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepo repo;
    @Autowired
    RoleRepo repo1;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public void createUsers(){
        Role admin=new Role();
        admin.setRoleName("Admin");
        admin.setRoleDesc("Admin Role Access");
        repo1.save(admin);

        Role user=new Role();
        user.setRoleName("User");
        user.setRoleDesc("User Role");
        repo1.save(user);

        RolesList role1=new RolesList();
        role1.setUser("User1");
        role1.setPassword(passwordEncoder.encode("123"));
        Set<Role> userRoles=new HashSet<>();
        userRoles.add(admin);
        role1.setRoles(userRoles);
        repo.save(role1);

        RolesList role2=new RolesList();
        role2.setUser("User2");
        role2.setPassword(passwordEncoder.encode("mypassword123"));
        Set<Role> userRole=new HashSet<>();
        userRole.add(user);
        role2.setRoles(userRole);
        repo.save(role2);
    }
    public void registerUser(RolesList User){
        Role role = repo1.findById("User").get();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User.setRoles(roles);
        User.setPassword(passwordEncoder.encode(User.getPassword()));
        repo.save(User);
    }
}
