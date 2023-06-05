package com.example.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.blog.config.AppConstants;
import com.example.blog.entities.Role;
import com.example.blog.repositories.RoleRepo;



@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner {

//		@Autowired
//		private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}

	@Bean
	public ModelMapper modelmapper() {
		return new ModelMapper();
	}


	@Override
	public void run(String... args) throws Exception {

		//System.out.println(this.passwordEncoder.encode("1234"));
		//System.out.println(this.passwordEncoder.encode("abhi15"));
		try {
			Role role=new Role();
			role.setRoleId(AppConstants.NORMAL_USER);
			role.setName("ROLE_NORMAL");
			
			Role role1=new Role();
			role1.setRoleId(AppConstants.ADMIN_USER);
			role1.setName("ROLE_ADMIN");
				
			List<Role> roles=List.of(role,role1);
			
			List<Role> result= this.roleRepo.saveAll(roles);
			result.forEach(r->{System.out.println(r.getName());});
					
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
