package com.ufcg.psoft.mercadofacil.exception;

public class ClienteAlredyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public ClienteAlredyExistsException(String msg) {
		super(msg);
	}
}
