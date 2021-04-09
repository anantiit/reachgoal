package com.practice.assignments.coffeemachine.data;

import com.google.gson.annotations.SerializedName;

public class MachineWrapper {
	public MachineWrapper() {
	}

	@SerializedName("machine")
	Machine machine;

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

}
