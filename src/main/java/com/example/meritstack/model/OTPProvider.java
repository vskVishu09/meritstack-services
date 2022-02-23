package com.example.meritstack.model;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Entity
@Table(name = "OTPProvider", uniqueConstraints = {
		@UniqueConstraint(columnNames = "email")
})
@Configuration
@Getter
@Setter
@NoArgsConstructor
public class OTPProvider {

	@Transient
	@Value("${otp.expire.time.in.minute}")
	private int expiryTimeInMinutes;

	@Id
	private String email;

	private Long otp;

	private Date genratedAt;

	private Date expireAt;

	public OTPProvider(String email, Long otp) {
		this.email = email;
		this.otp = otp;
		calculateExpiryDate();
	}

	public void calculateExpiryDate() {
		Calendar cal = Calendar.getInstance();
		setGenratedAt(cal.getTime());
		Calendar calExpire = (Calendar) cal.clone();
		calExpire.add(Calendar.MINUTE, 1440);
		setExpireAt(calExpire.getTime());
	}
}
