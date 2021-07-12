package com.reachgoal.postman.entities;

public class Reply {
	String replyid;
	String parentCommentid;
	String replytext;
	String userid;

	public Reply(String replyid, String parentCommentid, String replytext, String userid) {
		super();
		this.replyid = replyid;
		this.parentCommentid = parentCommentid;
		this.replytext = replytext;
		this.userid = userid;
	}

	public String getParentCommentId() {
		return parentCommentid;
	}

	@Override
	public String toString() {
		return "Reply [replyid=" + replyid + ", parentCommentid=" + parentCommentid + ", replytext=" + replytext
				+ ", userid=" + userid + "]";
	}

}
