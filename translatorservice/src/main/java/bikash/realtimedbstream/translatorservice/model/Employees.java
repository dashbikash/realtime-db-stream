package bikash.realtimedbstream.translatorservice.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Employees implements Serializable {

	@SerializedName("empid")
	@Expose
	private Integer empid;
	@SerializedName("fname")
	@Expose
	private String fname;
	@SerializedName("lname")
	@Expose
	private String lname;
	@SerializedName("dept")
	@Expose
	private String dept;
	@SerializedName("email")
	@Expose
	private String email;
	@SerializedName("dob")
	@Expose
	private Long dob;
	private final static long serialVersionUID = -2923738855171726190L;

	public Integer getEmpid() {
		return empid;
	}

	public void setEmpid(Integer empid) {
		this.empid = empid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getDob() {
		return dob;
	}

	public void setDob(Long dob) {
		this.dob = dob;
	}

}
