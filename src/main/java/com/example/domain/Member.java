package com.example.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;

@JsonPropertyOrder({"ID", "名前", "プロフィール", "更新日時"})
@Data
@AllArgsConstructor
public class Member {
	@JsonProperty("ID")
	private Long id;
	@JsonProperty("名前")
	private String name;
	@JsonProperty("プロフィ―ル")
	private String desc;
	@JsonProperty("更新日時")
	private Date modified;
}
