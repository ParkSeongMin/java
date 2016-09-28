package com.seongmin.test.xml.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(namespace = "")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "usesFeature", "usesSdk", "usesPermission",
		"permission", "application" })
public class Manifest {

	@XmlAttribute(name = "package", namespace = "")
	private String packageName;

	@XmlAttribute
	private int versionCode;

	@XmlAttribute
	private String versionName;

	@XmlElement
	private Application application;
	@XmlElement
	private Permission permission;
	@XmlElement(name="uses-permission")
	private List<UsesPermission> usesPermission;
	@XmlElement(name="uses-feature")
	private UsesFeature usesFeature;
	@XmlElement(name="uses-sdk")
	private UsesSdk usesSdk;
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	public List<UsesPermission> getUsesPermission() {
		return usesPermission;
	}
	public void setUsesPermission(List<UsesPermission> usesPermission) {
		this.usesPermission = usesPermission;
	}
	public UsesFeature getUsesFeature() {
		return usesFeature;
	}
	public void setUsesFeature(UsesFeature usesFeature) {
		this.usesFeature = usesFeature;
	}
	public UsesSdk getUsesSdk() {
		return usesSdk;
	}
	public void setUsesSdk(UsesSdk usesSdk) {
		this.usesSdk = usesSdk;
	}
	@Override
	public String toString() {
		return "Manifest [packageName=" + packageName + ", versionCode="
				+ versionCode + ", versionName=" + versionName
				+ ", application=" + application + ", permission=" + permission
				+ ", usesPermission=" + usesPermission + ", usesFeature="
				+ usesFeature + ", usesSdk=" + usesSdk + "]";
	}


	
	
}
