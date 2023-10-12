package scanqrcodeFromPdf;

import java.util.List;

public class ResponseData {

	private List<ResObj> data;
	private int status;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ResObj> getData() {
		return data;
	}

	public void setData(List<ResObj> data) {
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
