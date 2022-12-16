package com.naidu.vfs.dao;

import java.util.HashMap;

import com.naidu.vfs.entities.INode;

public class INodeDao {
//We store Inode as trie data structure
	public static String PATH_SEPARATOR = "/";
	INode root;

	public INode getInode(String path) {
		String[] subdirs = path.split(PATH_SEPARATOR);
		INode fileNode = root;
		for (int i = 0; i < subdirs.length; i++) {
			String subdir = subdirs[i];
			if (fileNode.children != null && fileNode.children.get(subdir) != null) {
				fileNode = fileNode.children.get(subdir);
			} else {
				return null;
			}
		}
		return fileNode;
	}

	public INode getParentInode(String path) {
		String[] subdirs = path.split(PATH_SEPARATOR);
		INode fileNode = root;
		INode parentNode = null;
		for (int i = 0; i < subdirs.length; i++) {
			String subdir = subdirs[i];
			if (fileNode.children != null && fileNode.children.get(subdir) != null) {
				parentNode = fileNode;
				fileNode = fileNode.children.get(subdir);
			} else {
				return null;
			}
		}
		return parentNode;
	}

	public INode putInode(String path, boolean isDir) {
		String[] subdirs = path.split(PATH_SEPARATOR);
		INode fileNode = root;
		INode newFileNode = null;
		for (int i = 0; i < subdirs.length; i++) {
			String subdir = subdirs[i];
			if (fileNode.children != null && fileNode.children.get(subdir) != null) {
				fileNode = fileNode.children.get(subdir);
			} else {
				if (i == subdirs.length - 1 && !isDir) {
					newFileNode = new INode(subdir, 1024, false, null);
				} else {
					newFileNode = new INode(subdir, 0, true, null);
				}
				if (fileNode.children == null) {
					fileNode.children = new HashMap<String, INode>();
				}

				fileNode.children.put(subdir, newFileNode);
				fileNode = newFileNode;
			}
		}
		return newFileNode;
	}
}
