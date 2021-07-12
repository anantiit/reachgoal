package com.reachgoal.postman.dao;

import java.util.HashMap;
import java.util.List;

import com.reachgoal.postman.entities.Comment;
import com.reachgoal.postman.entities.Reply;
import com.reachgoal.postman.model.CommentWrapper;

public class CommentDao {
	HashMap<Comment, List<Reply>> commentsDB = new HashMap<Comment, List<Reply>>();

	public List<Reply> getReplies(String CommentId) {
		Comment comment = new Comment(CommentId);
		return commentsDB.get(comment);
	}

	public CommentWrapper getCommentWrapper(String commentId) {
		Comment comment = new Comment(commentId);
		List<Reply> replies = commentsDB.get(comment);
		System.out.println(commentsDB);
		return new CommentWrapper(comment, replies);

	}

	public void putComment(Comment comment, List<Reply> replies) {
		commentsDB.put(comment, replies);
		System.out.println(commentsDB);
	}

	public boolean checkIfCommentPresent(String commentId) {
		Comment comment = new Comment(commentId);
		return commentsDB.containsKey(comment);

	}

	public void putReply(Reply reply) {
		Comment comment = new Comment(reply.getParentCommentId());
		List<Reply> replies = commentsDB.get(comment);
		replies.add(reply);
		System.out.println(replies);
	}
}
