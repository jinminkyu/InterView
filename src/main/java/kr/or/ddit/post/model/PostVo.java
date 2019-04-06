package kr.or.ddit.post.model;

import java.util.Date;

public class PostVo {

	private String post_code;		// 글 코드
	private String mem_id; 			// 작성자
	private String post_contents;	// 내용
	private Date post_date; 		// 작성일
	private String writer_name;		// 작성자 이름
	private String commentcount;	// 댓글 수
	private String goodcount;		// 좋아요 수
	
	public PostVo() {
		// TODO Auto-generated constructor stub
	}
	
	public String getCommentcount() {
		return commentcount;
	}

	public void setCommentcount(String commentcount) {
		this.commentcount = commentcount;
	}

	public String getGoodcount() {
		return goodcount;
	}

	public void setGoodcount(String goodcount) {
		this.goodcount = goodcount;
	}

	public String getWriter_name() {
		return writer_name;
	}

	public void setWriter_name(String writer_name) {
		this.writer_name = writer_name;
	}

	public String getPost_code() {
		return post_code;
	}

	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getPost_contents() {
		return post_contents;
	}

	public void setPost_contents(String post_contents) {
		this.post_contents = post_contents;
	}

	public Date getPost_date() {
		return post_date;
	}

	public void setPost_date(Date post_date) {
		this.post_date = post_date;
	}

	@Override
	public String toString() {
		return "PostVo [post_code=" + post_code + ", mem_id=" + mem_id + ", post_contents=" + post_contents
				+ ", post_date=" + post_date + ", writer_name=" + writer_name + ", commentcount=" + commentcount
				+ ", goodcount=" + goodcount + "]";
	}

}
