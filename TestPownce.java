
public class TestPownce {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Pownce myPownce = new Pownce("xml", "54191u267d1354zhz57r214evm30w850");
//		myPownce.getPublicNotes(); 				//working
//		myPownce.getUserNotes("pstallworth"); 	//working
//		myPownce.getUserProfile("pstallworth"); //working
//		myPownce.getNote("2144024"); 			//working
//		myPownce.getNoteRecipients("2144024"); 	//working
//		myPownce.getUserFans("kylebrowning");	//working
//		myPownce.getUserFansOf("pstallworth");	//working
//		myPownce.getUserFriends("pstallworth");	//working
//		myPownce.getSendToList();				//working
		myPownce.sendMessage("public", "my message");
		
		myPownce.authorize("username", "password"); //working
		myPownce.execute();
	}

}
