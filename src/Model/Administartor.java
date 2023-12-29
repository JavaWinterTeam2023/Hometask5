package Model;

public class Administartor  extends User{
private int AdminType;
private String DateOfAdministration;
 public Administartor() {
	 
 }
 public int GetAdminType() {
	 return AdminType;
 }
 public void SetAdminType(int adminType) {
	 this.AdminType=adminType;
 }
 public String GetDateOfAdministration() {
	 return DateOfAdministration;
 }
 public void SetDateOfAdministration(String DAdmin) {
	 this.DateOfAdministration=DAdmin;
 }
 
}
