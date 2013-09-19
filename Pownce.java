import java.io.*;
import java.net.URLEncoder;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.*;
import org.apache.commons.httpclient.methods.multipart.*;
public class Pownce {

	private String appKey;
	private String apiUrl = "";
	private String apiBase = "http://api.pownce.com/2.0";
	private String format = "";
	private HttpClient client;
	private HttpMethod method;
	private String requestType = "";
	private String authentication = "";
	private MultipartRequestEntity mpre;

	public Pownce(String format, String appKey) {
		
		this.format = format;
		this.appKey = appKey;
	}
	
	public void execute() {
		
		client = new HttpClient();
//		if (requestType.equals("GET")) {
			//Instantiate a GET HTTP method
//	        HttpMethod method = new GetMethod(apiUrl);
	
//	        if (!authentication.equals("")) {
//	        	method.setRequestHeader("Authorization", "basic " + authentication);
//	        }
	        try{
	        	
	            int statusCode = client.executeMethod(method);

	            System.out.println("QueryString>>> "+apiUrl);
	            System.out.println("Status Text>>>"
	                  +HttpStatus.getStatusText(statusCode));

	            //Get data as a String
	            System.out.println(method.getResponseBodyAsString());

	            //OR as a byte array
	            byte [] res  = method.getResponseBody();

	            //write to file
	            FileOutputStream fos= new FileOutputStream("donepage.html");
	            fos.write(res);

	            //release connection
	            method.releaseConnection();
	        }
	        catch(IOException e) {
	            e.printStackTrace();
	        }
		//}
	}
	
	public void getPublicNotes() {
		
		apiUrl = apiBase + "/note_lists." + format;
		method = new GetMethod(apiUrl);

	}
	
	/**
	 * Get a list of notes for a user. To get your "inbox" of notes, the typical Pownce stream, you'll need to call this method with your own username and proper authentication. 
	 * An authenticated call with a friend's username will return all the notes that they've sent you (public and private). Without authentication, this method returns a list of public notes sent by the user.
	 * 
	 * @param username Username of a Pownce user
	 * @param type Filter the notes by type. Options are messages, links, or events. Authenticated users may also filter by files.
	 * @param limit Limit the number of notes returned. Default is 20 and max is 100.
	 * @param page Page number. Starts at 0.
	 * @param since_id Limit the notes returned to those greater than the specified note id.
	 * <p> Optional url parameters (authenticated user):
	 * @param filter Get a subset of the authenticated user's note list. Options are notes (no replies), replies, sent, public, private, nonpublic (private and friends-only notes), and all. 
	 * If no filter is specified, the notes will be returned according to the user's preferences.
	 * @param set Filter the authenticated user's notes by a particular set. Only available for the authenticated user's own notes.
	 */
	public void getUserNotes(String username) {
		
		apiUrl = apiBase + "/note_lists/" + username + "." + format + "?" + "app_key=" + appKey;
		method = new GetMethod(apiUrl);

	}
	
	public void getUserProfile(String username) {

		apiUrl = apiBase + "/users/" + username + "." + format + "?" + "app_key=" + appKey;
		method = new GetMethod(apiUrl);

	}
	
	public void getNote(String noteId) {
		
		apiUrl = apiBase + "/notes/" + noteId + "." + format + "?" + "app_key=" + appKey;
		method = new GetMethod(apiUrl);

	}
	
	public void getNoteRecipients(String noteId) {
		
		apiUrl = apiBase + "/notes/" + noteId + "/" + "recipients." + format + "?" + "app_key=" + appKey;
		method = new GetMethod(apiUrl);
	}
	
	/**
	 * Get an individual user's fan relationship data
	 * @param username Username of a Pownce user
	 * <p>Optional Parameters
	 * @param limit Limit the number of users returned. Default is 20 and max is 100.
	 * @param page Page number.  Starts at 0.
	 */
	public void getUserFans(String username) {
		
		apiUrl = apiBase + "/users/" + username + "/fans." + format + "?" + "app_key=" + appKey;
		method = new GetMethod(apiUrl);
	}
	
	/**
	 * Get an individual user's fans_of relationship data
	 * @param username of a Pownce user
	 * <p>Optional Parameters
	 * @param limit Limit the number of users returned. Default is 20 and max is 100.
	 * @param page Page number.  Starts at 0.
	 */
	public void getUserFansOf(String username) {
		
		apiUrl = apiBase + "/users/" + username + "/fan_of." + format + "?" + "app_key=" + appKey;
		method = new GetMethod(apiUrl);
	}
	
	/**
	 * Get an individual user's friend relationship data
	 * @param username
	 * <p>Optional Parameters
	 * @param limit Limit the number of users returned. Default is 20 and max is 100.
	 * @param page Page number.  Starts at 0.
	 */
	public void getUserFriends(String username) {

		apiUrl = apiBase + "/users/" + username + "/friends." + format + "?" + "app_key=" + appKey;
		method = new GetMethod(apiUrl);
	}
	
	/**
	 * Get a the list of potential recipients for the authenticated user. Includes options for the public, all friends, sets, and individual friends.
	 * The selected parameter that is returned is the user's preference for their default "send to" option and developers should respect this preference.
	 */
	public void getSendToList() {
		
		apiUrl = apiBase + "/send/" + "send_to." + format + "?" + "app_key=" + appKey;
		method = new GetMethod(apiUrl);
	}
	
	/**
	 * Post a note that contains only a message.
	 * @param noteTo The recipient(s) of the note. Options are public, all, friend_x, or set_x. Available options for the authenticated user can be found from the Send To List endpoint.
	 * @param noteBody The main text body of the note.
	 */
	public void sendMessage(String noteTo, String noteBody) {
		String messageData = "";
		if (!noteTo.equals("public") && !noteTo.equals("all") && !noteTo.startsWith("friend") && !noteTo.startsWith("set"))
			return; //bail
		try {
			messageData = URLEncoder.encode("note_to", "UTF-8") + "=" + URLEncoder.encode(noteTo, "UTF-8");
			messageData += "&" + URLEncoder.encode("note_body", "UTF-8") + "=" + URLEncoder.encode(noteBody, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		apiUrl = apiBase + "/send/" + "message." + format + "?" + "app_key=" + appKey;// + "&" + messageData;
		method = new PostMethod(apiUrl);
		System.out.println("url used in method:" + apiUrl);
		/*HttpMethodParams appParams = new HttpMethodParams();
		appParams.setParameter("app_key", appKey);
		NameValuePair[] data = { new NameValuePair("note_to", noteTo), new NameValuePair("app_key", appKey), new NameValuePair("note_body", URLEncoder.encode(noteBody)) };
		method.setQueryString(data);
		method.setParams(appParams);
		*/
//		method.setQueryString(data);
//		method.setRequestHeader(data);
	}

	public void authorize(String username, String password) {
		
		authentication = Base64Converter.encode(username + ":" + password);
		method.addRequestHeader("Authorization", "basic " + authentication);
	}
	
	
}
