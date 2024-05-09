
USE master
IF EXISTS (SELECT * FROM SYS.DATABASES WHERE NAME = 'MoviesApp')
BEGIN
	ALTER DATABASE MoviesApp SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
	DROP DATABASE MoviesApp;
END
GO

CREATE DATABASE MoviesApp
GO

USE MoviesApp
GO

Create Table Movies
(
	mId int IDENTITY(1,1) PRIMARY KEY,
	mImage varchar(400),
	mName varchar(400),
	mDate smalldatetime,
	mGenre varchar(400),
	mRating int,
	mDescription varchar(500)
)

Insert into Movies( mImage, mName, mDate, mGenre, mRating, mDescription)
Values ('Image','Name','04-04-2004','Action',7,'Khong co');

SELECT * FROM Movies