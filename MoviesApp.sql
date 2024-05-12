
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

Insert into Movies( mImage, mName, mDate, mGenre, mRating,  mDescription)
Values	('https://moviesapi.ir/images/tt0111161_poster.jpg','The Shawshank Redemption','1994','Crime',9.3,'Khong co'),
		('https://moviesapi.ir/images/tt0068646_poster.jpg','The Godfather','1972','Crime',9.2,'Khong co'),
		('https://moviesapi.ir/images/tt0071562_poster.jpg','The Godfather: Part II','1974','Crime',9,'Khong co'),
		('https://moviesapi.ir/images/tt0468569_poster.jpg','The Dark Knight','2008','Action',9,'Khong co'),
		('https://moviesapi.ir/images/tt0050083_poster.jpg','12 Angry Men','1957','Crime',8.9,'Khong co'),
		('https://moviesapi.ir/images/tt0108052_poster.jpg','Schindlers List','1993','Biography',8.9,'Khong co'),
		('https://moviesapi.ir/images/tt0110912_poster.jpg','Pulp Fiction','1994','Crime',8.9,'Khong co'),
		('https://moviesapi.ir/images/tt0167260_poster.jpg','The Lord of the Rings: The Return of the King','2003','Adventure',8.9,'Khong co'),
		('https://moviesapi.ir/images/tt0060196_poster.jpg','The Good, the Bad and the Ugly','1966','Western',8.9,'Khong co'),
		('https://moviesapi.ir/images/tt0137523_poster.jpg','Fight Club','1999','Drama',8.8,'Khong co'),
		('https://moviesapi.ir/images/tt0120737_poster.jpg','The Lord of the Rings: The Fellowship of the Ring','2001','Adventure',8.8,'Khong co'),
		('https://moviesapi.ir/images/tt0080684_poster.jpg','Star Wars: Episode V - The Empire Strikes Back','1980','Action',8.8,'Khong co'),
		('https://moviesapi.ir/images/tt0109830_poster.jpg','Forrest Gump','1994','Comedy',8.8,'Khong co'),
		('https://moviesapi.ir/images/tt1375666_poster.jpg','Inception','2010','Drama',8.8,'Khong co'),
		('https://moviesapi.ir/images/tt0167261_poster.jpg','The Lord of the Rings: The Two Towers','2002','Drama',8.7,'Khong co'),
		('https://moviesapi.ir/images/tt0073486_poster.jpg','One Flew Over the Cuckoos Nest','1975','Drama',8.7,'Khong co'),
		('https://moviesapi.ir/images/tt0099685_poster.jpg','Goodfellas','1990','Biography',8.7,'Khong co'),
		('https://moviesapi.ir/images/tt0133093_poster.jpg','The Matrix','1999','Action',8.7,'Khong co'),
		('https://moviesapi.ir/images/tt0047478_poster.jpg','Seven Samurai','1954','Action',8.8,'Khong co'),
		('https://moviesapi.ir/images/tt0076759_poster.jpg','Star Wars: Episode IV - A New Hope','1977','Action',8.7,'Khong co');

SELECT * FROM Movies

