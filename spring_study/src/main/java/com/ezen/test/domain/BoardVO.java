package com.ezen.test.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardVO {
	
	private int bno;
	private String title;
	private String writer;
	private String content;
	private String isDel;
	private String reg_date;
	private int read_count;
	private int cmt_qty;
	private int has_file;
	
}
