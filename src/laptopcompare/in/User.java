package laptopcompare.in;

//Represents a user of the SmartBuy application
class User {
 private String username;
 private String password;
 private String mobileNumber;

 /**
  * Constructor for the User class.
  * @param username The user's chosen username.
  * @param password The user's chosen password.
  * @param mobileNumber The user's registered mobile number.
  */
 public User(String username, String password, String mobileNumber) {
     this.username = username;
     this.password = password;
     this.mobileNumber = mobileNumber;
 }

 // Getters for user properties
 public String getUsername() {
     return username;
 }

 public String getPassword() {
     return password;
 }

 public String getMobileNumber() {
     return mobileNumber;
 }

 /**
  * Sets a new password for the user.
  * @param newPassword The new password to set.
  */
 public void setPassword(String newPassword) {
     this.password = newPassword;
 }
}
