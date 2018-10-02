package au.com.goed.starter.web;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model to be used in test app.
 * @author Goed Bezig
 *
 */
public class AppModel {

	@Length(min = 1, max = 10, message ="{test.message}")
	private String str;
	
	@Min(18)
	private Integer age;

	@JsonCreator
	public AppModel(@JsonProperty("str") String str, @JsonProperty("age") Integer age) {
		this.str = str;
		this.age = age;
	}
	
	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
}
