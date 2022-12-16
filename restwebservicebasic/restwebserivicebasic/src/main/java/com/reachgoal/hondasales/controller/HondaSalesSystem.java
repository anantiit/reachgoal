package com.reachgoal.hondasales.controller;

import com.reachgoal.hondasales.service.DiscontinuedModelManager;

public class HondaSalesSystem {
	public static void main(String args[]) {
		DiscontinuedModelManager discontinuedModelManager = new DiscontinuedModelManager();
		System.out.println(discontinuedModelManager.getDiscontinuedModels("2010", "2020"));
	}
}