package com.algaworks.algafood.infrastructure.service.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.PhotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.exception.StorageException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class S3PhotoStorageService implements PhotoStorageService {
	
	@Autowired
	private AmazonS3 amazonS3;
	
	@Autowired
	private StorageProperties storageProperties;

	@Override
	public PhotoRecovered recovery(String fileName) {
		String pathFile = getPathFile(fileName);
		
		URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), pathFile);				
		
		return PhotoRecovered.builder().url(url.toString()).build();
	}

	@Override
	public void store(NewPhoto newPhoto) {
		try {
			String pathFile = getPathFile(newPhoto.getFileName());
			
			var objectMetaData = new ObjectMetadata();
			objectMetaData.setContentType(newPhoto.getContentType());
			
			var putObjectRequest = new PutObjectRequest(
					storageProperties.getS3().getBucket(),
					pathFile,
					newPhoto.getInputStream(),
					objectMetaData)
				.withCannedAcl(CannedAccessControlList.PublicRead);
			
			amazonS3.putObject(putObjectRequest);
		} catch(Exception ex) {
			throw new StorageException("Não foi possível enviar o arquivo para o AWS S3!", ex);
		}
	}	

	@Override
	public void remove(String fileName) {
		try {
			String pathFile = getPathFile(fileName);
			var deleteObjectRequest = new DeleteObjectRequest(storageProperties.getS3().getBucket(), pathFile);
			
			amazonS3.deleteObject(deleteObjectRequest);
		} catch(Exception ex) {
			throw new StorageException("Não foi possível excluir o arquivo de AWS S3!", ex);
		}
	}
	
	private String getPathFile(String fileName) {
		return String.format("%s/%s", storageProperties.getS3().getDirectoryPhotos(), fileName);
	}

}
