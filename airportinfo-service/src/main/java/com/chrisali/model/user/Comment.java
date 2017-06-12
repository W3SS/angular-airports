package com.chrisali.model.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "comments")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@RequiredArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Version
	@JsonIgnore
	private Long version;
	
	@Length(min = 5, max = 200)
	private String text;
	
	@ManyToOne
	@JoinColumn(name = "users_id")
	private final BaseUser user;
	
	@ManyToOne
	@JoinColumn(name = "reviews_id")
	private final Review review;
	
	private Date datePosted;
	
	public Comment() {
		user = new User();
		review = new Review();
	}
}
