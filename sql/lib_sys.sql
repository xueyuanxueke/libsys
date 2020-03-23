-- MySQL dump 10.13  Distrib 5.7.29, for Win64 (x86_64)
--
-- Host: localhost    Database: lib_sys
-- ------------------------------------------------------
-- Server version	5.7.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_book`
--

DROP TABLE IF EXISTS `tb_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_book` (
  `bookid` int(11) NOT NULL,
  `isbn` char(13) DEFAULT NULL,
  `bname` varchar(12) DEFAULT NULL,
  `price` decimal(6,2) DEFAULT NULL,
  `author` varchar(15) DEFAULT NULL,
  `publisher` varchar(15) DEFAULT NULL,
  `pubdate` date DEFAULT NULL,
  `counter` int(11) DEFAULT NULL,
  `lended` bit(1) DEFAULT NULL,
  PRIMARY KEY (`bookid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_book`
--

LOCK TABLES `tb_book` WRITE;
/*!40000 ALTER TABLE `tb_book` DISABLE KEYS */;
INSERT INTO `tb_book` VALUES (1,'1111111111111','python',66.66,'佚名','南理工出版社','2020-03-03',1,_binary ''),(2,'1111111111112','java',67.76,'佚名','南理工出版社','2020-02-02',2,_binary '');
/*!40000 ALTER TABLE `tb_book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_reader`
--

DROP TABLE IF EXISTS `tb_reader`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_reader` (
  `readerid` int(11) NOT NULL,
  `rname` varchar(20) DEFAULT NULL,
  `gender` bit(1) DEFAULT NULL,
  `tel` char(11) DEFAULT NULL,
  `regdate` date DEFAULT NULL,
  `available` bit(1) DEFAULT NULL,
  PRIMARY KEY (`readerid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_reader`
--

LOCK TABLES `tb_reader` WRITE;
/*!40000 ALTER TABLE `tb_reader` DISABLE KEYS */;
INSERT INTO `tb_reader` VALUES (1,'张明',_binary '','12312345678','2020-03-23',NULL),(3,'xly',_binary '','12311111111','2020-03-23',NULL);
/*!40000 ALTER TABLE `tb_reader` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_record`
--

DROP TABLE IF EXISTS `tb_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_record` (
  `recordid` int(11) NOT NULL AUTO_INCREMENT,
  `bid` int(11) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL,
  `lenddate` date DEFAULT NULL,
  `backdate` date DEFAULT NULL,
  `publishment` decimal(5,0) DEFAULT NULL,
  PRIMARY KEY (`recordid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_record`
--

LOCK TABLES `tb_record` WRITE;
/*!40000 ALTER TABLE `tb_record` DISABLE KEYS */;
INSERT INTO `tb_record` VALUES (1,1,1,'2020-03-23',NULL,NULL),(2,2,1,'2020-03-23','2020-03-23',0),(3,2,1,'2020-03-23',NULL,NULL);
/*!40000 ALTER TABLE `tb_record` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-23 10:24:48
