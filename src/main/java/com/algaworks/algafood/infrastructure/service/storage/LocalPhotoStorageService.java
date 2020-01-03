package com.algaworks.algafood.infrastructure.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafood.domain.service.PhotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.exception.StorageException;

public class LocalPhotoStorageService implements PhotoStorageService {
	
	@Value("${algafood.storage.local.directory}")
	private Path directoryPhotos;
	
	@Override
	public PhotoRecovered recovery(String fileName) {
		try {
			Path filePath = getFilePath(fileName);
			
			PhotoRecovered photoRecovered = PhotoRecovered.builder()
					.inputStream(Files.newInputStream(filePath))
					.build();
			
			return photoRecovered;
		} catch (Exception ex) {
            throw new StorageException("Não foi possível recuperar o arquivo no sistema de armazenagem local!", ex);
		}
	}

	@Override
	public void store(NewPhoto newPhoto) {		
		try {
			Path filePath = getFilePath(newPhoto.getFileName());
			
			FileCopyUtils.copy(newPhoto.getInputStream(), Files.newOutputStream(filePath));
		} catch (Exception ex) {
            throw new StorageException("Não foi possível armazenar o arquivo no sistema de armazenagem local!", ex);
		}
	}
	
	@Override
	public void remove(String fileName) {
		try {
			Path filePath = getFilePath(fileName);
		
			Files.deleteIfExists(filePath);
		} catch (Exception ex) {
            throw new StorageException("Não foi possível excluir o arquivo no sistema de armazenagem local!", ex);
		}
	}
	
	private Path getFilePath(String fileName) {
		return directoryPhotos.resolve(Path.of(fileName));
	}

}
