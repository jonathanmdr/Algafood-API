package com.algaworks.algafood.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.core.storage.StorageProperties.StorageType;
import com.algaworks.algafood.domain.service.PhotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.LocalPhotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.S3PhotoStorageService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class StorageConfig {
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Bean
	@ConditionalOnProperty(name = "algafood.storage.type", havingValue = "s3")
	public AmazonS3 amazonS3() {
		var credentials = new BasicAWSCredentials(storageProperties.getS3().getAccessKey(), storageProperties.getS3().getSecretKey());
		
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(storageProperties.getS3().getRegion())
				.build();
	}
	
	@Bean
	public PhotoStorageService photoStorageService() {
		if (StorageType.S3.equals(storageProperties.getType())) {
			return new S3PhotoStorageService();
		} else {
			return new LocalPhotoStorageService();
		}
	}

}
