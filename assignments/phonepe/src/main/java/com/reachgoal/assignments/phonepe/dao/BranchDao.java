package com.reachgoal.assignments.phonepe.dao;

import java.util.HashMap;
import java.util.Map;

import com.reachgoal.assignments.phonepe.entities.Branch;

public class BranchDao {
	Map<String, Branch> branchMap = new HashMap<>();

	public void addBranch(String branchName) {
		// branchMap.containsKey(St)
		branchMap.put(branchName, new Branch(branchName));
	}

	public void getBranch(String branchName) {
		branchMap.get(branchName);
	}

}
