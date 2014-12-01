package mzvoicecontrol;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.JsonObject;

public class Vognition implements Serializable {
	private String vogHostname = "sample.whataremindsfor.com";
	private int vogPortnumber = 46900;
	private String vogAppkey;
    private String vogAppsecret;
    private String vogConkey;
    private String vogConsecret;
    private String vogTtsSpeakerType = "useenglishfemale";
    private String vogRequestType = "ASR";
    private ArrayList<String> vogHPDL_ID = new ArrayList<String>();
    private String vogLocale = "en-US";
    
    public Vognition(){}
    
    public Vognition(String param_appKey, String param_appSecret) {
    	vogAppkey = param_appKey;
    	vogAppsecret = param_appSecret;
    }
    
    public Vognition(String param_appKey, String param_appSecret, String param_conKey, String param_conSecret) {
    	vogAppkey = param_appKey;
    	vogAppsecret = param_appSecret;
    	vogConkey = param_conKey;
    	vogConsecret = param_conSecret;
    }
    
    //Setters, directly from documentation
    public void setHostname(String hostname) {vogHostname = hostname;}
    public void setPortnumber(int portnumber) {vogPortnumber = portnumber;}
    public void setAppKey(String appkey) {vogAppkey = appkey;}
    public void setAppSecret(String appsecret) {vogAppsecret = appsecret;}
    public void setConsumerKey(String conkey) {vogConkey = conkey;}
    public void setConsumerSecret(String consecret) {vogConsecret = consecret;}
    //Getters, directly from documentation
    public String getAppKey() {return vogAppkey;}
    public String getAppSecret() {return vogAppsecret;}
    public String getConsumerKey() {return vogConkey;}
    public String getConsumerSecret() {return vogConsecret;}
    public String getHostname() {return vogHostname;}
    public int getPortnumber() {return vogPortnumber;}
    
    private byte[] stringBuilder(Map<String,Object> params) throws Exception {
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
		return postDataBytes;
    }
    
    public void serialize(){
    	System.out.println("You are in serialize");
    	try{
		FileOutputStream fout = new FileOutputStream("/home/mike/Documents/repos/minecraft/vognition.ser");//TODO: Save in the right place
		ObjectOutputStream oos = new ObjectOutputStream(fout);   
		oos.writeObject(this);
		oos.close();
		System.out.println("Done");
  
 	   }catch(Exception ex){
 		   ex.printStackTrace();
 	   }
    }
    public void deserialze(){
    	
 	   Vognition vogObj;
  
 	   try{
 		   FileInputStream fin = new FileInputStream("/home/mike/Documents/repos/minecraft/vognition.ser");//TODO: Save in the right place
 		   ObjectInputStream ois = new ObjectInputStream(fin);
 		   vogObj = (Vognition) ois.readObject();
 		   ois.close();
 		   
 		   this.setHostname(vogObj.getHostname());
 		   this.setPortnumber(vogObj.getPortnumber());
 		   this.setAppKey(vogObj.getAppKey());
 		   this.setAppSecret(vogObj.getAppSecret());
 		   this.setConsumerKey(vogObj.getConsumerKey());
 		   this.setConsumerSecret(vogObj.getConsumerSecret());
 	   }catch(Exception ex){
 		   ex.printStackTrace();
 	   } 
    } 

    public String transText(String sentence, String ttsSpeakerType, String locale) throws Exception {
    	URL url = new URL("http://" + vogHostname + ":" + vogPortnumber + "/apiv1/transtext");
    	
    	System.out.println("App Key"+vogAppkey);
    	System.out.println("App Secret"+vogAppsecret);
    	System.out.println("Con Key"+vogConkey);
    	System.out.println("Con Secret"+vogConsecret);
    	
    	Map<String,Object> params = new LinkedHashMap<String,Object>();
        params.put("appkey", vogAppkey);
        params.put("appsecret", vogAppsecret);
        params.put("conkey", vogConkey);
        params.put("consecret", vogConsecret);
        params.put("ttsSpeakerType", ttsSpeakerType);
        params.put("sentence", sentence);
        params.put("requestType", "ASR");
        params.put("HPDL_ID", "TEST_HPM_ID");
        params.put("locale", locale);

        byte[] postDataBytes = stringBuilder(params);
        
        JsonObject response = HttpClient.main(url, postDataBytes, "POST");
        
        System.out.println(response.toString());
        return response.get("response").toString();
    }
    
    //TODO: Test it, and need to check if conkey already exists
    public String createUser() throws Exception{
    	URL url = new URL("http://" + vogHostname + ":" + vogPortnumber + "/apiv1/user");
    	
    	Map<String,Object> params = new LinkedHashMap<String,Object>();
        params.put("appkey", vogAppkey);
        params.put("appsecret", vogAppsecret);
        byte[] postDataBytes = stringBuilder(params);
        
        JsonObject response = HttpClient.main(url, postDataBytes, "POST");
        
        vogConkey = response.get("conkey").toString();
        vogConsecret = response.get("consecret").toString();
        
        return response.get("response_code").toString();
    }
    
    //TODO: Test it, should work
    public String createHomeProfileID() throws Exception{
    	URL url = new URL("http://" + vogHostname + ":" + vogPortnumber + "/apiv1/homeprofile");
    	
    	Map<String,Object> params = new LinkedHashMap<String,Object>();
        params.put("appkey", vogAppkey);
        params.put("appsecret", vogAppsecret);
        params.put("conkey", vogConkey);
        params.put("consecret", vogConsecret);
        params.put("HPDL_ID", vogHPDL_ID);
        params.put("roleeKey", vogConkey);//Docs say roleeKey is conkey of user being assigned a home profile
        byte[] postDataBytes = stringBuilder(params);
        
        JsonObject response = HttpClient.main(url, postDataBytes, "POST");
        
        //TODO: Check if ID exists(I think)
        vogHPDL_ID.add(response.get("HPDL_ID").toString());
        
        return response.get("response_code").toString();
    }
    //TODO: Dont think this will work
    public String uploadHomeProfile() throws Exception{
    	URL url = new URL("http://" + vogHostname + ":" + vogPortnumber + "/apiv1/homeprofile");
    	
    	Map<String,Object> params = new LinkedHashMap<String,Object>();
    	params.put("appkey", vogAppkey);
        params.put("appsecret", vogAppsecret);
        params.put("conkey", vogConkey);
        params.put("consecret", vogConsecret);
        params.put("HPDL_ID", vogHPDL_ID);
        params.put("roleeKey", vogConkey);//Docs say roleeKey is conkey of user being assigned a home profile
        byte[] postDataBytes = stringBuilder(params);
        
        JsonObject response = HttpClient.main(url, postDataBytes, "PUT");
        return response.get("response_code").toString();
    }
    //TODO: Test it, should work
    public String removeHomeProfile() throws Exception{
    	URL url = new URL("http://" + vogHostname + ":" + vogPortnumber + "/apiv1/homeprofile");
    	
    	Map<String,Object> params = new LinkedHashMap<String,Object>();
    	params.put("appkey", vogAppkey);
        params.put("appsecret", vogAppsecret);
        params.put("conkey", vogConkey);
        params.put("consecret", vogConsecret);
        params.put("HPDL_ID", vogHPDL_ID);
        byte[] postDataBytes = stringBuilder(params);
        
        JsonObject response = HttpClient.main(url, postDataBytes, "DELETE");
        vogHPDL_ID.remove(response.get("HPDL_ID").toString());
        return response.get("response_code").toString();
    }
    //TODO: Untested, were do I get role from?
    public String assignRole() throws Exception{
    	URL url = new URL("http://" + vogHostname + ":" + vogPortnumber + "/apiv1/role");
    	
    	Map<String,Object> params = new LinkedHashMap<String,Object>();
    	params.put("appkey", vogAppkey);
        params.put("appsecret", vogAppsecret);
        params.put("conkey", vogConkey);
        params.put("consecret", vogConsecret);
        params.put("HPDL_ID", vogHPDL_ID);
        params.put("role", "role");//Look here
        params.put("roleeKey", vogConkey);
        byte[] postDataBytes = stringBuilder(params);
        
        JsonObject response = HttpClient.main(url, postDataBytes, "POST");
        return response.get("response_code").toString();
    }
    //TODO: Test and check parameters
    public String changeRole() throws Exception{
    	URL url = new URL("http://" + vogHostname + ":" + vogPortnumber + "/apiv1/role");
    	
    	Map<String,Object> params = new LinkedHashMap<String,Object>();
    	params.put("appkey", vogAppkey);
        params.put("appsecret", vogAppsecret);
        params.put("conkey", vogConkey);
        params.put("consecret", vogConsecret);
        params.put("HPDL_ID", vogHPDL_ID);
        params.put("accept", 1);//1 or zero
        byte[] postDataBytes = stringBuilder(params);
        
        JsonObject response = HttpClient.main(url, postDataBytes, "PUT");
        return response.get("response_code").toString();
    }
    //TODO: Test and check parameters
    public String deleteRole() throws Exception{
    	URL url = new URL("http://" + vogHostname + ":" + vogPortnumber + "/apiv1/role");
    	
    	Map<String,Object> params = new LinkedHashMap<String,Object>();
    	params.put("appkey", vogAppkey);
        params.put("appsecret", vogAppsecret);
        params.put("conkey", vogConkey);
        params.put("consecret", vogConsecret);
        params.put("HPDL_ID", vogHPDL_ID);
        params.put("roleeKey", vogConkey);
        byte[] postDataBytes = stringBuilder(params);
        
        JsonObject response = HttpClient.main(url, postDataBytes, "DELETE");
        //TODO: Remove something
        return response.get("response_code").toString();
    }
    //TODO: Test and check parameters
    public String acceptRole() throws Exception{
    	URL url = new URL("http://" + vogHostname + ":" + vogPortnumber + "/apiv1/rolee");//TODO: role or rolee
    	
    	Map<String,Object> params = new LinkedHashMap<String,Object>();
    	params.put("appkey", vogAppkey);
        params.put("appsecret", vogAppsecret);
        params.put("conkey", vogConkey);
        params.put("consecret", vogConsecret);
        params.put("HPDL_ID", vogHPDL_ID);
        params.put("accept", 1);//TODO: Accept or deny here
        byte[] postDataBytes = stringBuilder(params);
        
        JsonObject response = HttpClient.main(url, postDataBytes, "POST");
        //TODO: Handle stuff, idk
        return response.get("response_code").toString();
    }
    //TODO: Test and check parameters
    public String unassignRole() throws Exception{
    	URL url = new URL("http://" + vogHostname + ":" + vogPortnumber + "/apiv1/rolee");//TODO: role or rolee
    	
    	Map<String,Object> params = new LinkedHashMap<String,Object>();
    	params.put("appkey", vogAppkey);
        params.put("appsecret", vogAppsecret);
        params.put("conkey", vogConkey);
        params.put("consecret", vogConsecret);
        params.put("HPDL_ID", vogHPDL_ID);
        byte[] postDataBytes = stringBuilder(params);
        
        JsonObject response = HttpClient.main(url, postDataBytes, "DELETE");
        //TODO: Learn what i need to do here
        return response.get("response_code").toString();
    }
}
