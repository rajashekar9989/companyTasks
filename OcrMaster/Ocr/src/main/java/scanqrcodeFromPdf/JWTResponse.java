package scanqrcodeFromPdf;

public class JWTResponse {

	private DecodeResponseData data;
	private String iss;

	public DecodeResponseData getData() {
		return data;
	}

	public void setData(DecodeResponseData data) {
		this.data = data;
	}

	public String getIss() {
		return iss;
	}

	public void setIss(String iss) {
		this.iss = iss;
	}

}
