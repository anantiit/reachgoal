package com.reachgoal.postman.controller;

import com.reachgoal.postman.dao.CommentDao;
import com.reachgoal.postman.services.CommentManager;

/*
 * Comment: commented, commentText, postid, userid
Reply: replyid, parentCommentid, replytext, userid

HashMap<parentComment, LIst<Reply>>
 

CreateComment()
getComments() 
createReply()

 */
public class CommentSystemDriver {
	// CommentManager commentManager;
	public static void main(String args[]) {
		CommentDao commentDao = new CommentDao();
		CommentManager commentManager = new CommentManager(commentDao);
		commentManager.createComment("commentId1", "what you said is right", "post1", "user1");
		commentManager.createReply("reply1", "commentId1", "reply to commment1", "user1");
		System.out.println(commentManager.getComment("commentId1"));
	}
}
