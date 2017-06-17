package cuit.model;

import java.sql.Date;

public class UserBean {
	private int utId;
	private String utName;
	private String utPass;
	private String utFeatureUri;
	private String utMail;
	private Date utDate;
	private String utAddr;
	private String utPro;
	private String utEdu;
	private String utIntro;
	private String utSkill;
	private String uPic;
	private String utInterest;

	public int getUtId() {
		return utId;
	}

	public void setUtId(int utId) {
		this.utId = utId;
	}

	public String getUtName() {
		return utName;
	}

	public void setUtName(String utName) {
		this.utName = utName;
	}

	public String getUtPass() {
		return utPass;
	}

	public void setUtPass(String utPass) {
		this.utPass = utPass;
	}

	public String getUtFeatureUri() {
		return utFeatureUri;
	}

	public void setUtFeatureUri(String utFeatureUri) {
		this.utFeatureUri = utFeatureUri;
	}

	public String getUtMail() {
		return utMail;
	}

	public void setUtMail(String utMail) {
		this.utMail = utMail;
	}

	public Date getUtDate() {
		return utDate;
	}

	public void setUtDate(Date utDate) {
		this.utDate = utDate;
	}

	public String getUtAddr() {
		return utAddr;
	}

	public void setUtAddr(String utAddr) {
		this.utAddr = utAddr;
	}

	public String getUtPro() {
		return utPro;
	}

	public void setUtPro(String utPro) {
		this.utPro = utPro;
	}

	public String getUtEdu() {
		return utEdu;
	}

	public void setUtEdu(String utEdu) {
		this.utEdu = utEdu;
	}

	public String getUtIntro() {
		return utIntro;
	}

	public void setUtIntro(String utIntro) {
		this.utIntro = utIntro;
	}

	public String getUtSkill() {
		return utSkill;
	}

	public void setUtSkill(String utSkill) {
		this.utSkill = utSkill;
	}

	public String getuPic() {
		return uPic;
	}

	public void setuPic(String uPic) {
		this.uPic = uPic;
	}

	public String getUtInterest() {
		return utInterest;
	}

	public void setUtInterest(String utInterest) {
		this.utInterest = utInterest;
	}
}
