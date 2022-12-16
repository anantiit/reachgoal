package com.naidu.vfs.entities;

import java.util.Map;

public class INode {
	public String fileName;
	public int fileSize;
	public boolean isDirectory;
	public Map<String, INode> children;
	String fileContent;

	public INode(String fileName, int fileSize, boolean isDirectory, Map<String, INode> children) {
		super();
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.isDirectory = isDirectory;
		this.children = children;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isDirectory() {
		return isDirectory;
	}

	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	public Map<String, INode> getChildren() {
		return children;
	}

	public void setChildren(Map<String, INode> children) {
		this.children = children;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

}
