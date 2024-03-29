package bikash.realtimedbstream.syncservice.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

@Document
public class Employees implements Serializable {

	@Id
	@SerializedName("empid")
	private Integer empid;
	@SerializedName("fname")
	private String fname;
	@SerializedName("lname")
	private String lname;
	@SerializedName("dept")
	private String dept;
	@SerializedName("email")
	private String email;
	@SerializedName("dob")
	@JsonAdapter(UnixTimestampAdapter.class)
	private Date dob;

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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

}
