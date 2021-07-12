package com.reachgoal.postman.entities;

public class Comment {
	String commentId;
	String commentText;
	String postId;
	String userId;

	public Comment(String commentId, String commentText, String postId, String userId) {
		super();
		this.commentId = commentId;
		this.commentText = commentText;
		this.postId = postId;
		this.userId = userId;
	}

	public Comment(String commentId) {
		super();
		this.commentId = commentId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commentId == null) ? 0 : commentId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (commentId == null) {
			if (other.commentId != null)
				return false;
		} else if (!commentId.equals(other.commentId)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", commentText=" + commentText + ", postId=" + postId + ", userId="
				+ userId + "]";
	}

}
