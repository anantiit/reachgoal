package com.algo.thirdparty.services;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.spayee.webreaderapi.infrastructure.common.PropertiesUtil;

public class AWSS3Service {
	
	private static final Log LOGGER = LogFactory.getLog(PropertiesUtil.class);

	private static AWSS3Service awsS3Service;

	private AmazonS3 s3clientSingapore;
	private AmazonS3 s3clientMumbai;

	static{
		awsS3Service = new AWSS3Service();
    }
	private AWSS3Service(){
		
//		AWSCredentials cred = new AWSCredentials() {
//
//				@Override
//				public String getAWSSecretKey() {
//					return PropertiesUtil.getProperty("aws_secret_access_key");
//				}
//
//				@Override
//				public String getAWSAccessKeyId() {
//					return PropertiesUtil.getProperty("aws_access_key_id");
//				}
//			};
	     
		ClientConfiguration config = new ClientConfiguration();
		config.setMaxConnections(500);//default 50
//		config.setSocketTimeout(50000);//50 secs
//		config.setConnectionTimeout(10000);//10 secs
//		config.setMaxErrorRetry(10);//default 3
		
		s3clientMumbai = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_SOUTH_1)
				.withClientConfiguration(config).build();
	
		s3clientSingapore = AmazonS3ClientBuilder.standard()
					.withClientConfiguration(config).build();
    }

	public static AWSS3Service getInstance(){
        return awsS3Service;
    }
	
	private AmazonS3 getAmazonS3ClientBasedOnBucket(String bucket) {
		if(bucket.equals("spee-qenc-videos")) {
			return s3clientMumbai;
		}
		
		return s3clientSingapore;
	}

	public InputStream getItemFromS3(String path) throws Exception {

		String bucketName = "spayeestatic";
		try {
			
			AmazonS3 s3client = getAmazonS3ClientBasedOnBucket(bucketName);

			S3Object item = s3client.getObject(new GetObjectRequest(bucketName, path));

			InputStream objectData = item.getObjectContent();
			
			//item.close();

			return objectData;

		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		}

		return null;
	}

	public long getObjectSize(String bucket, String key)
	        throws IOException {
		try {
			
			AmazonS3 s3client = getAmazonS3ClientBasedOnBucket(bucket);
			
			return s3client.getObjectMetadata(bucket, key).getContentLength();
		}catch(Exception e) {
			
		}
		
		return 0;
	}

	public InputStream getItemFromS3Bucket(String bucketName, String path) throws Exception {

		try {
			
			AmazonS3 s3client = getAmazonS3ClientBasedOnBucket(bucketName);

			if(s3client.doesObjectExist(bucketName, path)) {
				S3Object item = s3client.getObject(new GetObjectRequest(bucketName, path));
				InputStream objectData = item.getObjectContent();
				
				//item.close();
				
				return objectData;
			}

		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		}

		return null;
	}

	public boolean doesItemExists(String bucketName, String path) throws Exception {

		try {
			
			AmazonS3 s3client = getAmazonS3ClientBasedOnBucket(bucketName);
			
			return s3client.doesObjectExist(bucketName, path);

		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		}

		return false;
	}

	public File getItemAsFileFromS3Bucket(String bucketName, String path, String fileName) throws Exception {

		try {
			
			AmazonS3 s3client = getAmazonS3ClientBasedOnBucket(bucketName);
			
			File file = new File(PropertiesUtil.getProperty("tempResourcePath") + File.separator + fileName);
			S3Object item = s3client.getObject(new GetObjectRequest(bucketName, path));
			InputStream is = item.getObjectContent();

			Files.copy(is, Paths.get(file.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
			
			is.close();
			item.close();

			return file;

		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		//in case of error
		//retry once only
		
		return getItemAsFileFromS3BucketSecondTry(bucketName, path, fileName);
	}

	public File getItemAsFileFromS3BucketSecondTry(String bucketName, String path, String fileName) throws Exception {

		try {
			
			AmazonS3 s3client = getAmazonS3ClientBasedOnBucket(bucketName);
			
			File file = new File(PropertiesUtil.getProperty("tempResourcePath") + File.separator + fileName);
			S3Object item = s3client.getObject(new GetObjectRequest(bucketName, path));
			InputStream is = item.getObjectContent();

			Files.copy(is, Paths.get(file.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
			
			is.close();
			item.close();

			return file;

		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		

		return getItemAsFileFromS3BucketThirdTry(bucketName, path, fileName);
	}

	public File getItemAsFileFromS3BucketThirdTry(String bucketName, String path, String fileName) throws Exception {

		try {
			
			AmazonS3 s3client = getAmazonS3ClientBasedOnBucket(bucketName);
			
			File file = new File(PropertiesUtil.getProperty("tempResourcePath") + File.separator + fileName);
			S3Object item = s3client.getObject(new GetObjectRequest(bucketName, path));
			InputStream is = item.getObjectContent();

			Files.copy(is, Paths.get(file.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
			
			is.close();
			item.close();

			return file;

		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		

		return null;
	}

	public boolean uploadItemToS3(String path, InputStream is, long contentLength, boolean isPublic)
			throws Exception {

		String bucketName = "spayeestatic";

		String keyName = path;

		try {
			System.out.println("Uploading item:" + path + " to S3");

			// InputStream is = new ByteArrayInputStream(payload);

			ObjectMetadata objMeta = new ObjectMetadata();
			objMeta.setContentLength(contentLength);
			
			AmazonS3 s3client = getAmazonS3ClientBasedOnBucket(bucketName);

			if (isPublic) {
				s3client.putObject(new PutObjectRequest(bucketName, keyName, is, objMeta)
						.withCannedAcl(CannedAccessControlList.PublicRead));
			} else {
				s3client.putObject(new PutObjectRequest(bucketName, keyName, is, objMeta)
						.withCannedAcl(CannedAccessControlList.Private));
			}

			return true;

		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		}

		return false;

	}

	public boolean uploadItemToS3Bucket(String bucketName, String path, InputStream is, long contentLength,
			boolean isPublic, String contentType) throws Exception {
		return uploadItemToS3Bucket(bucketName, path, is, contentLength, isPublic, contentType, false);
	}
	
	public boolean uploadItemToS3Bucket(String bucketName, String path, InputStream is, long contentLength,
			boolean isPublic, String contentType, boolean attachment) throws Exception {
		String keyName = path;

		try {
			System.out.println("Uploading item:" + path + " to S3");

			// InputStream is = new ByteArrayInputStream(payload);

			ObjectMetadata objMeta = new ObjectMetadata();
			objMeta.setContentLength(contentLength);
			File src = new File("/Users/pedaappacanaiduabotula/spayee/mime.types");

			if(contentType == null) {
				MimetypesFileTypeMap mmp = new MimetypesFileTypeMap(new FileInputStream(src));
				contentType = mmp.getContentType(path.toLowerCase());
				objMeta.setContentType(contentType);
			}
			
			if(path.contains("/coursecertificates/") || path.contains("/files/") || attachment) {
				//content disposition
				String fileName = StringUtils.substringAfterLast(path, "/");
				objMeta.setContentDisposition("attachment;filename="+(URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20")));
			}
			

			objMeta.setCacheControl("max-age=31104000");// 1 year
			
			AmazonS3 s3client = getAmazonS3ClientBasedOnBucket(bucketName);
			
			if(path.contains("downloadedCourses/")) {
				
				TransferManager xfer_mgr = TransferManagerBuilder.standard()
						  .withS3Client(s3client)
						  .withMultipartUploadThreshold((long) (5 * 1024 * 1025))
						  .build();
				
				PutObjectRequest request = new PutObjectRequest(bucketName, keyName, is, objMeta)
						.withCannedAcl(CannedAccessControlList.Private);
				request.setGeneralProgressListener(new ProgressListener() {
					@Override
					public void progressChanged(ProgressEvent progressEvent) {
						System.out.println("Transferred bytes: " + progressEvent.getBytesTransferred());
						}
					});
				Upload u = xfer_mgr.upload(request);

				try {
					// You can block and wait for the upload to finish
					u.waitForCompletion();
				} catch (AmazonClientException amazonClientException) {
					System.out.println("Unable to upload file, upload aborted.");
					amazonClientException.printStackTrace();
				}

			}else {
				if (isPublic) {
					s3client.putObject(new PutObjectRequest(bucketName, keyName, is, objMeta)
							.withCannedAcl(CannedAccessControlList.PublicRead));
				} else {
					s3client.putObject(new PutObjectRequest(bucketName, keyName, is, objMeta)
							.withCannedAcl(CannedAccessControlList.Private));
				}
			}
			
			return true;

		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		}

		return false;

	}

	public boolean deleteItemFromS3Bucket(String bucketName, String path) throws Exception {

		try {
			System.out.println("Deleting item:" + path + " from S3");
			
			AmazonS3 s3client = getAmazonS3ClientBasedOnBucket(bucketName);

			s3client.deleteObject(new DeleteObjectRequest(bucketName, path));

			return true;

		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		}

		return false;

	}

	public void copyFolderFromS3Bucket(String bucketName, String path, File folder) throws Exception {

		try {
			System.out.println("Copying folder:" + path + " from S3");
			
			if(!folder.exists()) {
				folder.mkdirs();
			}
			
			if(bucketName.equals("spayeeusrresources") && (PropertiesUtil.getProperty("prod") == null || PropertiesUtil.getProperty("prod").isEmpty())) {
				path = "staging/" + path;
			}
			
			AmazonS3 s3client = getAmazonS3ClientBasedOnBucket(bucketName);
			
			for (S3ObjectSummary file : s3client.listObjects(bucketName, path).getObjectSummaries()){
				String[] fileArr = file.getKey().split("/");
				String ofilename = fileArr[fileArr.length - 1];
				
				File ofile = new File(folder + File.separator + ofilename);
				
				if(!ofile.exists()) {
					//get from s3
					
					S3Object item = s3client.getObject(new GetObjectRequest(bucketName, file.getKey()));
					InputStream is = item.getObjectContent();

					Files.copy(is, Paths.get(ofile.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
					
					is.close();
					item.close();
				}
		    }

		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		}

	}

	public void copyFolderToAnotherBucket(String srcBucket, String src, String destBucket, String dest) throws Exception {

		try {
			System.out.println("Copying folder:" + src + " from S3 to:" + dest);
			
			if(srcBucket.equals("spayeeusrresources") && (PropertiesUtil.getProperty("prod") == null || PropertiesUtil.getProperty("prod").isEmpty())) {
				src = "staging/" + src;
			}
			
			if(destBucket.equals("spayeeusrresources") && (PropertiesUtil.getProperty("prod") == null || PropertiesUtil.getProperty("prod").isEmpty())) {
				dest = "staging/" + dest;
			}
			AmazonS3 s3client = getAmazonS3ClientBasedOnBucket(srcBucket);
			
			for (S3ObjectSummary file : s3client.listObjects(srcBucket, src).getObjectSummaries()){
				
				String[] fileArr = file.getKey().split("/");
				String ofilename = fileArr[fileArr.length - 1];
				
				s3client.copyObject(srcBucket, file.getKey(), destBucket, dest + "/" + ofilename);
				
		    }

		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		}

	}

	@Deprecated
	public void deleteFolderFromS3Bucket(String bucketName, String path) throws Exception {
		// this method will delete only 1000 objects from the path.
		// use deleteFolderListFromS3Bucket() to delete complete folder
		try {
			System.out.println("Deleting s3 folder: " + bucketName + "/" + path);
			AmazonS3 s3client = getAmazonS3ClientBasedOnBucket(bucketName);
			for (S3ObjectSummary file : s3client.listObjects(bucketName, path).getObjectSummaries()){
				s3client.deleteObject(bucketName, file.getKey());
		    }
		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		}
	}
	
	public void deleteFolderListFromS3Bucket(String bucketName, String path) throws Exception {
		try {
			System.out.println("Deleting s3 folder list: " + bucketName + "/" + path);
			AmazonS3 s3Client = getAmazonS3ClientBasedOnBucket(bucketName);
			ObjectListing listObjects = s3Client.listObjects(bucketName, path);
			while (true) {
				ArrayList<KeyVersion> keys = new ArrayList<KeyVersion>();
				for (S3ObjectSummary file : listObjects.getObjectSummaries()) {
					keys.add(new KeyVersion(file.getKey()));
				}
				if(keys.size() > 0) {
					LOGGER.info("deleting keys count: " + keys.size() + " from: "+ bucketName + "/" + path);
					// create delete request
					DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(bucketName)
							.withKeys(keys)
							.withQuiet(false);
					// delete 1000 objects from folder
					DeleteObjectsResult deleteObjects = s3Client.deleteObjects(multiObjectDeleteRequest);
					LOGGER.info("deleted count: " + deleteObjects.getDeletedObjects().size() + " from: "+ bucketName + "/" + path);
				}
				if (listObjects.isTruncated()) {
					listObjects = s3Client.listObjects(bucketName, path);
				} else {
					break;
				}
			}
		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		}
	}

	public boolean copyObjectToAnotherBucket(String fromBucket, String fromKey, String toBucket, String toKey)
			throws Exception {

		try {
			System.out.println("copying item:" + fromBucket + "/" + fromKey + " to " + toBucket + "/" + toKey);

			// InputStream is = new ByteArrayInputStream(payload);

			CopyObjectRequest cpy = new CopyObjectRequest(fromBucket, fromKey, toBucket, toKey);
			
			AmazonS3 s3client = getAmazonS3ClientBasedOnBucket(toBucket);
			
			s3client.copyObject(cpy);

			return true;

		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		}

		return false;
	}

	public String getPreSignedUrlOfBookItemInS3(String path) throws Exception {

		String bucketName = "spayeestatic";

		String keyName = "bookResources/" + path;/* PropertiesUtil.getProperty("s3BookResourcesUrl") + */

		try {
			
			AWSCredentials cred = new AWSCredentials() {
				
				@Override
				public String getAWSSecretKey() {
					return PropertiesUtil.getProperty("aws_secret_access_key");
				}

				@Override
				public String getAWSAccessKeyId() {
					return PropertiesUtil.getProperty("aws_access_key_id");
				}
			};
			
			AmazonS3 s3Client1 = new AmazonS3Client(cred);
			
			java.util.Date expiration = new java.util.Date();
			long msec = expiration.getTime();
			msec += 1000 * 60 * 60; // 1 hour.
			expiration.setTime(msec);

			GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName,
					keyName);
			generatePresignedUrlRequest.setMethod(HttpMethod.GET);
			generatePresignedUrlRequest.setExpiration(expiration);

			URL s = s3Client1.generatePresignedUrl(generatePresignedUrlRequest);

			return s.toString();

		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		}

		return null;

	}

	public String getPreSignedUrlOfVideoTSFileInS3(String bucketName, String keyName) throws Exception {

		try {
			
			AmazonS3 s3Client1 = null;
			
			if(bucketName.equals("spee-qenc-videos")) {
				s3Client1 = s3clientMumbai;
			}else {
				//client with region throwing 400 for output-test-videos
				
				AWSCredentials cred = new AWSCredentials() {
					
					@Override
					public String getAWSSecretKey() {
						return PropertiesUtil.getProperty("aws_secret_access_key");
					}
					@Override
					public String getAWSAccessKeyId() {
						return PropertiesUtil.getProperty("aws_access_key_id");
					}
				};
				s3Client1 = new AmazonS3Client(cred);
			}
			
			
			java.util.Date expiration = new java.util.Date();
			long msec = expiration.getTime();
			
			if(bucketName.equals("spayeeusrresources") || keyName.contains("/liverecordings/")) {
				//download
				msec += 1000 * 60 * 60 * 6; //6 hours
			}else {
				msec += 1000 * 60 * 60; // 1 hour.
			}
			expiration.setTime(msec);

			GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName,
					keyName);
			generatePresignedUrlRequest.setMethod(HttpMethod.GET);
			generatePresignedUrlRequest.setExpiration(expiration);

			URL s = s3Client1.generatePresignedUrl(generatePresignedUrlRequest);
			
			return s.toString();
			

		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		}

		return null;

	}

	

	public String getPreSignedUrlOfVideoTSFileInS3POST(String bucketName, String keyName) throws Exception {

		try {

			java.util.Date expiration = new java.util.Date();
			long msec = expiration.getTime();

			// upload time
			msec += 1000 * 60 * 60 * 2; // 2 hours
			expiration.setTime(msec);

			GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName,
					keyName);
			generatePresignedUrlRequest.setMethod(HttpMethod.PUT);
			generatePresignedUrlRequest.setExpiration(expiration);
			generatePresignedUrlRequest.setContentType("video/mp4");

			
			URL s = getAmazonS3ClientBasedOnBucket(bucketName).generatePresignedUrl(generatePresignedUrlRequest);
			
			String url = s.toString();
			 return url;

		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		}

		return null;

	}

	public static java.security.PrivateKey getPrivateKey(String filename) throws Exception {

        File f = new File(filename);
        java.io.FileInputStream fis = new java.io.FileInputStream(f);
        java.io.DataInputStream dis = new java.io.DataInputStream(fis);
        byte[] keyBytes = new byte[(int) f.length()];
        dis.readFully(keyBytes);
        dis.close();

        java.security.spec.PKCS8EncodedKeySpec spec = new java.security.spec.PKCS8EncodedKeySpec(keyBytes);
        java.security.KeyFactory kf = java.security.KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

	public void setItemMimeType(String bucket, String path, String mimeType) {
		
		try {
			
			AmazonS3 s3client = getAmazonS3ClientBasedOnBucket(bucket);
			
			ObjectMetadata obj = s3client.getObjectMetadata(bucket, path);
			obj.setContentType(mimeType);
			
			s3client.copyObject(new CopyObjectRequest(bucket, path, bucket, path).withNewObjectMetadata(obj));

		} catch (AmazonServiceException ase) {
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			ace.printStackTrace();
		}
		
	}
	

}

