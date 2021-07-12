package com.reachgoal.postman.services;

import java.util.ArrayList;

import com.reachgoal.postman.dao.CommentDao;
import com.reachgoal.postman.entities.Comment;
import com.reachgoal.postman.entities.Reply;
import com.reachgoal.postman.model.CommentWrapper;

public class CommentManager {
	CommentDao commentdao;

	public CommentManager(CommentDao commentdao) {
		super();
		this.commentdao = commentdao;
	}

	public int createComment(String commentText, String commentId, String postId, String userId) {
		Comment comment = new Comment(commentText, commentId, postId, userId);
		commentdao.putComment(comment, new ArrayList<Reply>());
		return 0;
	}

	public CommentWrapper getComment(String commentId) {
		CommentWrapper commentWrapper = commentdao.getCommentWrapper(commentId);
		return commentWrapper;
	}

	public int createReply(String replyid, String parentCommentid, String replytext, String userid) {
		if (!commentdao.checkIfCommentPresent(parentCommentid)) {
			return -1;
		}
		Reply reply = new Reply(replyid, parentCommentid, replytext, userid);
		commentdao.putReply(reply);
		return 0;
	}
}
