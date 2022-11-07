package fr.epsi.mspr.keunotor.domain;

public class Response<T>{
	private T data;
	private boolean success = true;
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
