package com.reachgoal.postman.model;

import java.util.List;

import com.reachgoal.postman.entities.Comment;
import com.reachgoal.postman.entities.Reply;

public class CommentWrapper {
	Comment comment;
	List<Reply> replies;

	public CommentWrapper(Comment comment, List<Reply> replies) {
		this.comment = comment;
		this.replies = replies;
	}

	@Override
	public String toString() {
		return "CommentWrapper [comment=" + comment + ", replies=" + replies + "]";
	}

}
