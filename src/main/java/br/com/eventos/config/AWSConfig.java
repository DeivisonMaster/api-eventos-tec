package br.com.eventos.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {

	@Value("${aws.region}")
	private String region;
	
	@Value("${aws.accessKeyId}")
	private String accessKey;
	
	@Value("${aws.secretKey}")
	private String secretKey;
	
	
	@Bean
	public AmazonS3 createS3Instance() {
		BasicAWSCredentials credenciais = new BasicAWSCredentials(accessKey, secretKey);
		
		return AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credenciais))
				.withRegion(region)
				.build();
	}
}
