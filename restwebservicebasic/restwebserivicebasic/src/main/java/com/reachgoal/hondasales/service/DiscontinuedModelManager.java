package com.reachgoal.hondasales.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.reachgoal.hondasales.model.VehicleModel;
import com.reachgoal.hondasales.model.VehicleModelWrapper;

public class DiscontinuedModelManager {
	HondaSalesFetcher hondaSales;

	public Set<Integer> getDiscontinuedModels(String startYear, String endYearStr) {
		hondaSales = new HondaSalesFetcher();
		HashMap<Integer, HashSet<Integer>> yearlySales = new HashMap<Integer, HashSet<Integer>>();
		int curYear = Integer.parseInt(startYear);
		int endYear = Integer.parseInt(endYearStr);
		while (curYear < endYear) {
			VehicleModelWrapper vehicleModelWrapper = hondaSales.getHondaSalesInformationPerYear(curYear + "");
			putSalesInfoIntoMap(curYear, vehicleModelWrapper, yearlySales);
			curYear++;
		}
		Set<Integer> allModels = new HashSet<Integer>();
		for (Entry<Integer, HashSet<Integer>> entry : yearlySales.entrySet()) {
			allModels.addAll(entry.getValue());
		}
		Set<Integer> last2YearModels = new HashSet<Integer>();
		last2YearModels.addAll(yearlySales.get(endYear));
		last2YearModels.addAll(yearlySales.get(endYear - 1));
		allModels.removeAll(last2YearModels);
		return allModels;
	}

	private void putSalesInfoIntoMap(Integer curYear, VehicleModelWrapper vehicleModelWrapper,
			HashMap<Integer, HashSet<Integer>> yearlySales) {
		VehicleModel[] sales = vehicleModelWrapper.getResults();
		for (VehicleModel sale : sales) {
			HashSet<Integer> curYearSales = yearlySales.get(curYear);
			if (curYearSales == null) {
				curYearSales = new HashSet<Integer>();
			}
			curYearSales.add(sale.getModel_ID());
		}

	}
}