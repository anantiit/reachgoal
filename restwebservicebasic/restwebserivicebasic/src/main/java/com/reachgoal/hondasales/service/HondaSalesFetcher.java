package com.reachgoal.hondasales.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.reachgoal.hondasales.model.VehicleModelWrapper;

public class HondaSalesFetcher {
	static String HONDA_SALES_API = "http://vpic.nhtsa.dot.gov/api/vehicles/getmodelsformakeyear/make/honda/modelyear";
	private final Client client = ClientBuilder.newClient();

	public VehicleModelWrapper getHondaSalesInformationPerYear(String year) {
		try {
			WebTarget webTarget = client.target(HONDA_SALES_API).path(year).queryParam("format", "json");
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
			Response response = invocationBuilder.get();

			VehicleModelWrapper salesInfo = response.readEntity(VehicleModelWrapper.class);
			return salesInfo;
		} catch (final Exception e) {
			System.out.println("Error occured" + e);
			return null;
		}
	}
}