# File Management Service

A file management service for uploading and downloading files, with support for both local storage and AWS S3.

## Features

- **Local Upload**: Upload files to the local file system.
- **Local Download**: Download files from the local file system.
- **AWS S3 Upload**: Upload files to an AWS S3 bucket.
- **AWS S3 Download**: Download files from an AWS S3 bucket.

## Requirements

Before getting started, make sure you have the following:
- Java 17 or higher 
- [AWS account](https://aws.amazon.com/)
- IntelliJ IDEA 2024.1 or newer
## Getting Started

1. Clone the repository:
   ```shell
    git clone https://github.com/jeremiasadriano/files-uploader.git
    cd  files-uploader
   ```
2. Set Up Your Environment

   Rename the `.env.example` to `.env`
   ```shell
    mv .env.example .env
   ```
   Open the `.env` and fill it with the correct values:
   ```ini
    STORAGE_DIRECTORY=/path/to/storage/directory
    AWS_SECRET_KEY_ID=your-secret-key-id
    AWS_SECRET_ACCESS_KEY=your-access-key
    AWS_BUCKET_S3_NAME=your-bucket-name
   ``` 
3. Configure S3 Permissions

   Make sure that your S3 bucket is set up with the right permissions to allow file uploads and downloads.

4. Run the Application

## Endpoints

### Local File Upload

- Endpoint: POST /files 
- Parameters: ``file`` (file to upload)
- Response: 200 OK with the message "Success!"

### Local File Download

- Endpoint: GET /files 
- Parameters: ``filename`` (**the absolute** name of the file to download)
- Response: The requested file as an attachment.

### AWS S3 Upload
- Endpoint: POST /files/aws 
- Parameters: ``file`` (file to upload)
- Response: 200 OK with the message "Success!"

### AWS S3 Download
- Endpoint: GET /files/aws 
- Parameters: ``filename`` (**the absolute name** of the file to download)
- Response: 200 OK with the message "Success!"