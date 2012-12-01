-- phpMyAdmin SQL Dump
-- version 3.2.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 01, 2012 at 12:53 PM
-- Server version: 5.1.44
-- PHP Version: 5.3.2

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `Accountability`
--

-- --------------------------------------------------------

--
-- Table structure for table `meta`
--

CREATE TABLE `Entity` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `name` varchar(30) DEFAULT 'anonymous',
  `score` bigint(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Entity`
--


-- --------------------------------------------------------

--
-- Table structure for table `testimonial`
--

CREATE TABLE `Testimonial` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `proof` blob NOT NULL,
  `entity_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Testimonial`
--


-- --------------------------------------------------------

--
-- Table structure for table `data`
--

CREATE TABLE `Transaction` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `from` bigint(20) NOT NULL,
  `to` bigint(20) NOT NULL,
  `amount` float NOT NULL,
  `testimonial_id` bigint(20) NOT NULL,
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Transaction`
--

