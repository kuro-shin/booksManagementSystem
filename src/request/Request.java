package request;

public class Request {
 private String requestStatus;
 private String requestId;
 private String requestEmployeeId;
 private String requestTitle;
 private String requestAuthor;
 private String requestPublisher;
 private String requestApplicantName;
 private String requestApplicantDate;
 private String requestUpdaterName;
 private String requestUpdateDate;
 private String requestRejectReason;
 private String requestUrl;


public Request() {

}

public String getRequestStatus() {
	return requestStatus;
}

public static String requestId(String a){
	return "select \n" +
			"RE.REQUEST_BOOK_ID, \n" +
			"RE.EMPLOYEE_ID, \n" +
			"EM.NAME, \n" +
			"RE.TITLE, \n" +
			"RE.PUBLISHER, \n" +
			"RE.AUTHER, \n" +
			"RE.URL, \n" +
			"RE.STATUS, \n" +
			"RE.REJECTED_REASON, \n" +
			"RE.REQUEST_DATE, \n" +
			"RE.UPDATED_DATE, \n" +
			"EMP.NAME as UPDATE_NAME \n" +
			"from \n" +
			"REQUEST_BOOKS RE, \n" +
			"EMPLOYEES EM, \n" +
			"EMPLOYEES EMP \n" +
			"where 1=1 \n" +
			"and RE.EMPLOYEE_ID=EM.EMPLOYEE_ID \n" +
			"and RE.UPDATER_ID=EMP.EMPLOYEE_ID(+) \n" +
			"and EM.REQUEST_BOOK_ID = '"+a+"' " ;
}
public void setRequestStatus(String requestStatus) {
	this.requestStatus = requestStatus;
}

public String getRequestId() {
	return requestId;
}

public void setRequestId(String requestId) {
	this.requestId = requestId;
}

public String getRequestEmployeeId() {
	return requestEmployeeId;
}

public void setRequestEmployeeId(String requestEmployeeId) {
	this.requestEmployeeId = requestEmployeeId;
}

public String getRequestTitle() {
	return requestTitle;
}

public void setRequestTitle(String requestTitle) {
	this.requestTitle = requestTitle;
}

public String getRequestAuthor() {
	return requestAuthor;
}

public void setRequestAuthor(String requestAuthor) {
	this.requestAuthor = requestAuthor;
}

public String getRequestPublisher() {
	return requestPublisher;
}

public void setRequestPublisher(String requestPublisher) {
	this.requestPublisher = requestPublisher;
}

public String getRequestApplicantName() {
	return requestApplicantName;
}

public void setRequestApplicantName(String requestApplicantName) {
	this.requestApplicantName = requestApplicantName;
}

public String getRequestApplicantDate() {
	return requestApplicantDate;
}

public void setRequestApplicantDate(String requestApplicantDate) {
	this.requestApplicantDate = requestApplicantDate;
}

public String getRequestUpdaterName() {
	return requestUpdaterName;
}

public void setRequestUpdaterName(String requestUpdaterName) {
	this.requestUpdaterName = requestUpdaterName;
}

public String getRequestUpdateDate() {
	return requestUpdateDate;
}

public void setRequestUpdateDate(String requestUpdateDate) {
	this.requestUpdateDate = requestUpdateDate;
}

public String getRequestRejectReason() {
	return requestRejectReason;
}

public void setRequestRejectReason(String requestRejectReason) {
	this.requestRejectReason = requestRejectReason;
}

public String getRequestUrl() {
	return requestUrl;
}

public void setRequestUrl(String requestUrl) {
	this.requestUrl = requestUrl;
}


}
