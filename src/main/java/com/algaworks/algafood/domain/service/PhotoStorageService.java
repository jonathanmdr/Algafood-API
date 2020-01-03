package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface PhotoStorageService {
	
	InputStream recovery(String fileName);
	
	void store(NewPhoto newPhoto);
	
	void remove(String fileName);
	
	default void replace(String oldFileName, NewPhoto newPhoto) {
		this.store(newPhoto);
		
		if (oldFileName != null) {
			this.remove(oldFileName);
		}
	}
	
	default String generateFileName(String originalFileName) {
		return UUID.randomUUID().toString().concat("_").concat(originalFileName);
	}
	
	@Getter
	@Builder
	class NewPhoto {
		
		private String fileName;
		private InputStream inputStream;
		
	}

}
