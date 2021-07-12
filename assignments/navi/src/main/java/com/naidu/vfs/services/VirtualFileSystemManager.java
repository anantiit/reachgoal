package com.naidu.vfs.services;

import java.io.FileInputStream;

import com.naidu.vfs.dao.INodeDao;
import com.naidu.vfs.entities.INode;

public class VirtualFileSystemManager {
	INodeDao inodeDao;

	public VirtualFileSystemManager(INodeDao inodeDao) {
		super();
		this.inodeDao = inodeDao;
	}

	public FileInputStream fopen() {

	}

	public String fread(String path) {
		INode fileNode = inodeDao.getInode(path);
		if (fileNode == null || fileNode.isDirectory()) {
			System.out.println("File with given path not present or it is directory " + path + ". Hence skipping");
			return null;
		}
		return fileNode.getFileContent();
	}

	public boolean fwrite(String path, String content) {
		INode fileNode = inodeDao.getInode(path);
		if (fileNode == null) {
			System.out.println("File with given path not present: " + path + ". Hence creating a file");
			fileNode = createFile(path, false);
		} else if (fileNode.isDirectory()) {
			System.out.println("File with given path is directory " + path + ". Hence skipping..");
			return false;
		}

		String fileContent = (fileNode.getFileContent() == null) ? "" : fileNode.getFileContent() + content;
		fileNode.setFileContent(fileContent);
		return true;

	}

	public boolean fclose(String fileName) {

	}

	public boolean rename(String path, String modifiedName) {
		INode fileNode = inodeDao.getInode(path);
		if (fileNode == null) {
			System.out.println("File with given path not present: " + path + ". Hence cannot be renamed");
			return false;
		}
		fileNode.setFileName(modifiedName);
		INode parentNode = inodeDao.getParentInode(path);
		String[] subdirs = path.split(INodeDao.PATH_SEPARATOR);
		parentNode.getChildren().put(modifiedName, fileNode);
		parentNode.getChildren().remove(subdirs[subdirs.length - 1]);
		return true;
	}

	public boolean remove(String path) {
		INode fileNode = inodeDao.getInode(path);
		if (fileNode == null || fileNode.isDirectory()) {
			System.out.println(
					"File with given path not present or it is directory: " + path + ". Hence cannot be removed");
			return false;
		}
		INode parentNode = inodeDao.getParentInode(path);
		String[] subdirs = path.split(INodeDao.PATH_SEPARATOR);
		parentNode.getChildren().remove(subdirs[subdirs.length - 1]);
		return true;
	}

	public INode createFile(String path, boolean isDir) {
		INode fileNode = inodeDao.putInode(path, isDir);
		if (fileNode == null) {
			System.out.println("file with path given is already present:" + path);
		}
		return fileNode;
	}

}
