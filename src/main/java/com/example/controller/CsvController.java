package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.domain.Member;
import com.example.helper.DownloadHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@Controller
@RequestMapping("")
public class CsvController {
	
	@Autowired
	private DownloadHelper downloadHelper;
	
	/**
     * CsvMapperで、csvを作成する。
     * @return csv(String)
     * @throws JsonProcessingException
     */
	public String getCsvText() throws JsonProcessingException{
		CsvMapper mapper = new CsvMapper();
		//文字列にダブルクオートをつける
		mapper.configure(CsvGenerator.Feature.ALWAYS_QUOTE_STRINGS, true);
		//ヘッダをつける
		CsvSchema schema = mapper.schemaFor(Member.class).withHeader();
		//メンバーデータをダウンロードするイメージ
		List<Member> members = new ArrayList<>();
		members.add(new Member(1L, "user01", "プロフィール１", new Date()));
        members.add(new Member(2L, "user02", "プロフィール２", new Date()));
        members.add(new Member(3L, "user03", "プロフィール３", new Date()));
        return mapper.writer(schema).writeValueAsString(members);
	}
	
	@RequestMapping(value="")
	public String showDownloadPage() {
		return "download";
	}
	
	@RequestMapping(value="/download/csv", method=RequestMethod.POST)
	public ResponseEntity<byte[]> download() throws IOException{
		HttpHeaders headers = new HttpHeaders();
		downloadHelper.addContentDisposition(headers, "メンバー.csv");
		return new ResponseEntity<>(getCsvText().getBytes("UTF-8"), headers, HttpStatus.OK);
	}
	
}
