package no.hvl.dat110.aciotdevice.client;

import com.google.gson.Gson;

public class AccessMessage {

	private String message;

	public AccessMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String toJson() {

		Gson gson = new Gson();

		String json = gson.toJson(this);

		return json;
	}
}
