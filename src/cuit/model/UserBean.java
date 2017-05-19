package cuit.model;

import cuit.util.ConstantDeclare;
import javafx.util.converter.TimeStringConverter;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

public class UserBean {
	private int id;
	private String name;
	private String email;
	private String profession;
	private String location;
	private String education;
	private String intro;
	private Timestamp date;
	private String pass;
	private String skills;
	private byte[] pic;

	public UserBean(int id, String name, String email, String location, String profession,
                    String education, String intro, String date, String skills, byte[] pic) throws IOException
	{
		initObject(id, name, email, location, profession, education, intro, date);
		setSkills(skills);
		setPic(pic);
	}
	
	public UserBean() throws IOException{
		initObject(0, null, null, null, null, null, null, null);
		pic = new byte[1];
		//readUserPic();
	}
	
	private void initObject(int id, String name, String email, String location, String profession,
			String education, String intro, String skills){
		this.id = id;
		this.name = fillEmptyString(name);
		this.email = fillEmptyString(email);
		this.location = fillEmptyString(location);
		this.profession = fillEmptyString(profession);
		this.education = fillEmptyString(education);
		this.intro = fillEmptyString(intro);
		this.date = new Timestamp(System.currentTimeMillis());
		this.skills = fillEmptyString(skills);
	}
	
	private String fillEmptyString(String str){
		return str != null && !str.isEmpty() ? str : ConstantDeclare.DEFAULT_STRING;
	}
	
	private void readUserPic() throws IOException{
		FileInputStream file = null;
		try {
			file = new FileInputStream(ConstantDeclare.DEFAULT_USER_IMAGE_PATH);
			int pos = 0, all = 0;
			while(pos != -1){
				pos = file.read(this.pic, all, ConstantDeclare.MAX_FILE_READ_COUNT);
				all += pos;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			file.close();
		}
	}


	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	public int getId(){return id;}
	public String getName(){return name;}
	public String getEmail(){return email;}
	public String getProfession(){return profession;}
	public String getLocation(){return location;}
	public String getEducation(){return education;}
	public String getIntro(){return intro;}
	public Timestamp getDate(){return date;}
	public byte[] getPic(){return pic;}
	public String getSkills(){
		return skills;
	}
	
	public void setId(int newId){this.id = newId;}
	public void setName(String newName){this.name = newName;}
	public void setEmail(String newEmail){this.email = newEmail;}
	public void setProfession(String newProfession){this.profession = newProfession;}
	public void setLocation(String newLocation){this.location = newLocation;}
	public void setEducation(String newEducation){this.education = newEducation;}
	public void setIntro(String newIntro){this.intro = newIntro;}
	public void setDate(Timestamp date){this.date = date;}
	public void setPic(byte[] newPic){
		if (pic == null){
			//readUserPic();
		}else{
			this.pic = newPic;
		}
	}
	
	public void setSkills(String skills){
		this.skills = skills;
	}
	
//	public void addSkill(String newSkill){skills.add(newSkill);}
//	public void removeSkill(String oldSkill){skills.remove(oldSkill);}
}
