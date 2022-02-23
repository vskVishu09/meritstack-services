package com.example.meritstack;

import com.example.meritstack.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class MeritStackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeritStackApplication.class, args);
	}

	static List<Employee> createEmployeeList() {
		List<Employee> list = new ArrayList<>();
		Employee e1 = new Employee();
		e1.setAge(21);
		e1.setName("name21");
		Employee e2 = new Employee();
		e2.setAge(25);
		e2.setName("name25");
		Employee e3 = new Employee();
		e3.setAge(22);
		e3.setName("name22");
		list.add(e3);
		list.add(e2);
		list.add(e1);

		return list;
	}
}


class Employee {
	int age;
	String name;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}