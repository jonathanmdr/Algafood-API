package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface PhotoStorageService {
	
	PhotoRecovered recovery(String fileName);
	
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
	public class NewPhoto {
		
		private String fileName;
		private String contentType;
		private InputStream inputStream;
		
	}
	
	@Getter
	@Builder
	public class PhotoRecovered {
		
		private InputStream inputStream;
		private String url;
		
		public boolean hasUrl() {
			return url != null;
		}
		
		public boolean hasInputStream() {
			return inputStream != null;
		}
		
	}

}
