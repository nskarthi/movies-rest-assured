package api.contants;

public enum StatusCode {
	CODE_200(200, ""), 
	CODE_201(201, ""), 
	CODE_400(400, "Bad Request"), 
	CODE_401(401, "Invalid access token"),
	CODE_404(404, "Not Found");

	public final int code;
	public final String message;

	StatusCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
}
