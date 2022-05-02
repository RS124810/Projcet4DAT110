package no.hvl.dat110.ac.restservice;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;
import static spark.Spark.post;
import static spark.Spark.delete;

import com.google.gson.Gson;

/**
 * Hello world!
 *
 */
public class App {
	
	static AccessLog accesslog = null;
	static AccessCode accesscode = null;
	
	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8080);
		}

		// objects for data stored in the service
		
		accesslog = new AccessLog();
		accesscode  = new AccessCode();
		
		after((req, res) -> {
  		  res.type("application/json");
  		});
		
		// for basic testing purposes
		get("/accessdevice/hello", (req, res) -> {
			
		 	Gson gson = new Gson();
		 	
		 	return gson.toJson("IoT Access Control Device");
		});
		
		// TODO: implement the routes required for the access control service
		// as per the HTTP/REST operations describined in the project description
		
		post("/accessdevice/log/", (req, res) -> {
			//counters = gson.fromJson(req.body(), Counters.class);
			//return counters.toJson();
			Gson gson = new Gson();
			AccessMessage s = gson.fromJson(req.body(), AccessMessage.class);
	
			int id = accesslog.add(s.getMessage());
					
			return gson.toJson(accesslog.get(id));
		});
		
		get("/accessdevice/log/", (req, res) -> {
			Gson gson = new Gson();
			
			return gson.toJson(accesslog.log.values());
		});
		
		get("/accessdevice/log/:id",(req, res) ->{
			
			int id = 0;
			AccessEntry e = null;
			Gson gson = new Gson();
			String sid = req.params(":id"); 
			String regex = "\\d+";
			
			if (sid.matches(regex)&& sid != null) {
			id = Integer.parseInt(sid);
			}
			
			if (accesslog.size()>= id && id != 0) {
				 e = accesslog.get(id);
			}
			
			return gson.toJson(e);
		});
		
		put("/accessdevice/code", (req , res) ->{
			
			Gson gson = new Gson();
			accesscode = gson.fromJson(req.body(), AccessCode.class);
			return gson.toJson(accesscode.getAccesscode());
		});
		
		get("/accessdevice/code",(req , res) ->{
			Gson gson = new Gson();
						
			return gson.toJson(accesscode.getAccesscode());
		});
		delete("/accessdevice/log",(req , res) ->{
			
			Gson gson = new Gson();
			accesslog.clear();
			
			return gson.toJson(accesslog.log);
		});
    }
    
}
