-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.10.2-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for stat_db
-- CREATE DATABASE IF NOT EXISTS `stat_db` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci */;
-- USE `stat_db`;

-- Dumping structure for table stat_db.black_list_refresh_token
CREATE TABLE IF NOT EXISTS `black_list_refresh_token` (
  `token` varchar(255) NOT NULL,
  `black_listed_date` datetime DEFAULT NULL,
  `blocked_by_acces_token` varchar(255) DEFAULT NULL,
  `blocked_by_username` varchar(255) DEFAULT NULL,
  `expired` datetime DEFAULT NULL,
  `generated_from_ip` varchar(255) DEFAULT NULL,
  `generated_on` datetime DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`token`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table stat_db.black_list_refresh_token: 0 rows
/*!40000 ALTER TABLE `black_list_refresh_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `black_list_refresh_token` ENABLE KEYS */;

-- Dumping structure for table stat_db.privileges
CREATE TABLE IF NOT EXISTS `privileges` (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table stat_db.privileges: 18 rows
/*!40000 ALTER TABLE `privileges` DISABLE KEYS */;
INSERT INTO `privileges` (`id`, `name`) VALUES
	('2b4472e9-68ec-49b8-94d2-8b9c27443c8e', 'ACCESS-DASHBOARD-SCREEN'),
	('a99715d1-67a3-45a3-94ec-e762b159e0e9', 'ACCESS-PROFILE-SCREEN'),
	('819487af-8a40-4512-86f2-b297f72b5068', 'ACCESS-SDP-SCREEN'),
	('ab405c24-c871-42a0-ae49-746f4d15ae4f', 'ACCESS-SDR-SCREEN'),
	('5ade6a8a-90ef-41d9-adb7-99fed2a3e27d', 'ACCESS-CM-SCREEN'),
	('03d24389-0284-4c95-99bc-293105db91d3', 'ACCESS-SDF-SCREEN'),
	('336dd6cd-b6fe-4c15-80e3-978b968d5036', 'ADD-SDR-STAT'),
	('8e1500ba-aef8-4267-8670-00cac0df726f', 'UPDATE-SDR-STAT'),
	('9edbcbc2-2974-4b17-a2fd-d17ea57f8079', 'DELETE-SDR-STAT'),
	('2fa7d0be-596d-43dd-bfa6-b1c3a5102c91', 'ADD-SDP-STAT'),
	('65df31d5-5b38-4572-98e0-58d6112f3fde', 'UPDATE-SDP-STAT'),
	('0a0d7577-3798-471e-9792-4d82f0636018', 'DELETE-SDP-STAT'),
	('7f46a401-8a3c-4561-8b42-fceb3caf6119', 'ADD-CM-STAT'),
	('ff8be1e3-3937-4c32-9104-dde8d6712860', 'UPDATE-CM-STAT'),
	('4a605d8f-4de7-40a9-836c-52a113386e28', 'DELETE-CM-STAT'),
	('0cd0801b-2602-492d-9b42-dfee04166976', 'ADD-SDF-STAT'),
	('d2e4a1fd-6cd0-4cff-aeda-28c36b509c8f', 'UPDATE-SDF-STAT'),
	('a3a6ab34-85dc-4155-a7d9-72d86f311556', 'DELETE-SDF-STAT');
/*!40000 ALTER TABLE `privileges` ENABLE KEYS */;

-- Dumping structure for table stat_db.refresh_token
CREATE TABLE IF NOT EXISTS `refresh_token` (
  `username` varchar(255) NOT NULL,
  `expired` datetime DEFAULT NULL,
  `generated_from_ip` varchar(255) DEFAULT NULL,
  `generated_on` datetime DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table stat_db.refresh_token: 0 rows
/*!40000 ALTER TABLE `refresh_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `refresh_token` ENABLE KEYS */;

-- Dumping structure for table stat_db.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `id` varchar(36) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table stat_db.roles: 7 rows
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id`, `name`) VALUES
	('01f0f74b-0046-444e-8133-4088c05ea816', 'SDP'),
	('3fc96aa8-0dc5-44de-944a-f1bbbcae6ae9', 'SDF'),
	('c5d027d2-1181-44cb-ae12-abe180156484', 'ADMIN'),
	('cc81babc-04d3-4efd-a783-a2f2ee9eaef6', 'SUPERADMIN'),
	('a9133cf8-b8ff-4849-8bf6-2cb208659081', 'SDR'),
	('215ef598-20b2-4133-95c1-91a015c0b2b0', 'SDCM'),
	('93cb30f5-f35c-496c-82cb-c6b48eece341', 'SUPERVISOR');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

-- Dumping structure for table stat_db.roles_privileges
CREATE TABLE IF NOT EXISTS `roles_privileges` (
  `role_id` varchar(36) NOT NULL,
  `privilege_id` varchar(36) NOT NULL,
  KEY `FK5duhoc7rwt8h06avv41o41cfy` (`privilege_id`),
  KEY `FK629oqwrudgp5u7tewl07ayugj` (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table stat_db.roles_privileges: 51 rows
/*!40000 ALTER TABLE `roles_privileges` DISABLE KEYS */;
INSERT INTO `roles_privileges` (`role_id`, `privilege_id`) VALUES
	('01f0f74b-0046-444e-8133-4088c05ea816', '2fa7d0be-596d-43dd-bfa6-b1c3a5102c91'),
	('01f0f74b-0046-444e-8133-4088c05ea816', '0a0d7577-3798-471e-9792-4d82f0636018'),
	('01f0f74b-0046-444e-8133-4088c05ea816', '65df31d5-5b38-4572-98e0-58d6112f3fde'),
	('01f0f74b-0046-444e-8133-4088c05ea816', '819487af-8a40-4512-86f2-b297f72b5068'),
	('215ef598-20b2-4133-95c1-91a015c0b2b0', '5ade6a8a-90ef-41d9-adb7-99fed2a3e27d'),
	('215ef598-20b2-4133-95c1-91a015c0b2b0', 'ff8be1e3-3937-4c32-9104-dde8d6712860'),
	('215ef598-20b2-4133-95c1-91a015c0b2b0', '4a605d8f-4de7-40a9-836c-52a113386e28'),
	('215ef598-20b2-4133-95c1-91a015c0b2b0', '7f46a401-8a3c-4561-8b42-fceb3caf6119'),
	('3fc96aa8-0dc5-44de-944a-f1bbbcae6ae9', '03d24389-0284-4c95-99bc-293105db91d3'),
	('3fc96aa8-0dc5-44de-944a-f1bbbcae6ae9', 'd2e4a1fd-6cd0-4cff-aeda-28c36b509c8f'),
	('3fc96aa8-0dc5-44de-944a-f1bbbcae6ae9', 'a3a6ab34-85dc-4155-a7d9-72d86f311556'),
	('3fc96aa8-0dc5-44de-944a-f1bbbcae6ae9', '0cd0801b-2602-492d-9b42-dfee04166976'),
	('a9133cf8-b8ff-4849-8bf6-2cb208659081', 'ab405c24-c871-42a0-ae49-746f4d15ae4f'),
	('a9133cf8-b8ff-4849-8bf6-2cb208659081', '8e1500ba-aef8-4267-8670-00cac0df726f'),
	('a9133cf8-b8ff-4849-8bf6-2cb208659081', '9edbcbc2-2974-4b17-a2fd-d17ea57f8079'),
	('a9133cf8-b8ff-4849-8bf6-2cb208659081', '336dd6cd-b6fe-4c15-80e3-978b968d5036'),
	('c5d027d2-1181-44cb-ae12-abe180156484', '2b4472e9-68ec-49b8-94d2-8b9c27443c8e'),
	('c5d027d2-1181-44cb-ae12-abe180156484', 'ab405c24-c871-42a0-ae49-746f4d15ae4f'),
	('c5d027d2-1181-44cb-ae12-abe180156484', '8e1500ba-aef8-4267-8670-00cac0df726f'),
	('c5d027d2-1181-44cb-ae12-abe180156484', '9edbcbc2-2974-4b17-a2fd-d17ea57f8079'),
	('c5d027d2-1181-44cb-ae12-abe180156484', '336dd6cd-b6fe-4c15-80e3-978b968d5036'),
	('c5d027d2-1181-44cb-ae12-abe180156484', '5ade6a8a-90ef-41d9-adb7-99fed2a3e27d'),
	('c5d027d2-1181-44cb-ae12-abe180156484', 'ff8be1e3-3937-4c32-9104-dde8d6712860'),
	('c5d027d2-1181-44cb-ae12-abe180156484', '4a605d8f-4de7-40a9-836c-52a113386e28'),
	('c5d027d2-1181-44cb-ae12-abe180156484', '7f46a401-8a3c-4561-8b42-fceb3caf6119'),
	('c5d027d2-1181-44cb-ae12-abe180156484', '03d24389-0284-4c95-99bc-293105db91d3'),
	('c5d027d2-1181-44cb-ae12-abe180156484', 'd2e4a1fd-6cd0-4cff-aeda-28c36b509c8f'),
	('c5d027d2-1181-44cb-ae12-abe180156484', 'a3a6ab34-85dc-4155-a7d9-72d86f311556'),
	('c5d027d2-1181-44cb-ae12-abe180156484', '0cd0801b-2602-492d-9b42-dfee04166976'),
	('c5d027d2-1181-44cb-ae12-abe180156484', '819487af-8a40-4512-86f2-b297f72b5068'),
	('c5d027d2-1181-44cb-ae12-abe180156484', '65df31d5-5b38-4572-98e0-58d6112f3fde'),
	('c5d027d2-1181-44cb-ae12-abe180156484', '0a0d7577-3798-471e-9792-4d82f0636018'),
	('c5d027d2-1181-44cb-ae12-abe180156484', '2fa7d0be-596d-43dd-bfa6-b1c3a5102c91'),
	('cc81babc-04d3-4efd-a783-a2f2ee9eaef6', '2b4472e9-68ec-49b8-94d2-8b9c27443c8e'),
	('cc81babc-04d3-4efd-a783-a2f2ee9eaef6', 'ab405c24-c871-42a0-ae49-746f4d15ae4f'),
	('cc81babc-04d3-4efd-a783-a2f2ee9eaef6', '8e1500ba-aef8-4267-8670-00cac0df726f'),
	('cc81babc-04d3-4efd-a783-a2f2ee9eaef6', '9edbcbc2-2974-4b17-a2fd-d17ea57f8079'),
	('cc81babc-04d3-4efd-a783-a2f2ee9eaef6', '336dd6cd-b6fe-4c15-80e3-978b968d5036'),
	('cc81babc-04d3-4efd-a783-a2f2ee9eaef6', '5ade6a8a-90ef-41d9-adb7-99fed2a3e27d'),
	('cc81babc-04d3-4efd-a783-a2f2ee9eaef6', 'ff8be1e3-3937-4c32-9104-dde8d6712860'),
	('cc81babc-04d3-4efd-a783-a2f2ee9eaef6', '4a605d8f-4de7-40a9-836c-52a113386e28'),
	('cc81babc-04d3-4efd-a783-a2f2ee9eaef6', '7f46a401-8a3c-4561-8b42-fceb3caf6119'),
	('cc81babc-04d3-4efd-a783-a2f2ee9eaef6', '03d24389-0284-4c95-99bc-293105db91d3'),
	('cc81babc-04d3-4efd-a783-a2f2ee9eaef6', 'd2e4a1fd-6cd0-4cff-aeda-28c36b509c8f'),
	('cc81babc-04d3-4efd-a783-a2f2ee9eaef6', 'a3a6ab34-85dc-4155-a7d9-72d86f311556'),
	('cc81babc-04d3-4efd-a783-a2f2ee9eaef6', '0cd0801b-2602-492d-9b42-dfee04166976'),
	('cc81babc-04d3-4efd-a783-a2f2ee9eaef6', '819487af-8a40-4512-86f2-b297f72b5068'),
	('cc81babc-04d3-4efd-a783-a2f2ee9eaef6', '65df31d5-5b38-4572-98e0-58d6112f3fde'),
	('cc81babc-04d3-4efd-a783-a2f2ee9eaef6', '0a0d7577-3798-471e-9792-4d82f0636018'),
	('cc81babc-04d3-4efd-a783-a2f2ee9eaef6', '2fa7d0be-596d-43dd-bfa6-b1c3a5102c91'),
	('93cb30f5-f35c-496c-82cb-c6b48eece341', '2b4472e9-68ec-49b8-94d2-8b9c27443c8e');
/*!40000 ALTER TABLE `roles_privileges` ENABLE KEYS */;

-- Dumping structure for table stat_db.rubrique
CREATE TABLE IF NOT EXISTS `rubrique` (
  `id` varchar(36) NOT NULL,
  `name_ar` varchar(255) DEFAULT NULL,
  `name_en` varchar(255) DEFAULT NULL,
  `name_fr` varchar(255) DEFAULT NULL,
  `id_service` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt5wa5230ss2fw4ttd26kvhtv6` (`id_service`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table stat_db.rubrique: 0 rows
/*!40000 ALTER TABLE `rubrique` DISABLE KEYS */;
/*!40000 ALTER TABLE `rubrique` ENABLE KEYS */;

-- Dumping structure for table stat_db.rubrique_value
CREATE TABLE IF NOT EXISTS `rubrique_value` (
  `id` varchar(36) NOT NULL,
  `insertion_date` datetime DEFAULT NULL,
  `value` double NOT NULL,
  `id_rubrique` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh0g5aa0d7ti4urvsh8n4yjprk` (`id_rubrique`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table stat_db.rubrique_value: 0 rows
/*!40000 ALTER TABLE `rubrique_value` DISABLE KEYS */;
/*!40000 ALTER TABLE `rubrique_value` ENABLE KEYS */;

-- Dumping structure for table stat_db.service
CREATE TABLE IF NOT EXISTS `service` (
  `id` varchar(36) NOT NULL,
  `service` varchar(255) DEFAULT NULL,
  `service_ar` varchar(255) DEFAULT NULL,
  `service_lettre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Dumping data for table stat_db.service: 0 rows
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
/*!40000 ALTER TABLE `service` ENABLE KEYS */;

-- Dumping structure for table stat_db.tentative_acces
CREATE TABLE IF NOT EXISTS `tentative_acces` (
  `ip_request` varchar(255) NOT NULL,
  `date_tentative` datetime NOT NULL,
  `tentative` int(11) DEFAULT NULL,
  PRIMARY KEY (`ip_request`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table stat_db.tentative_acces: 0 rows
/*!40000 ALTER TABLE `tentative_acces` DISABLE KEYS */;
/*!40000 ALTER TABLE `tentative_acces` ENABLE KEYS */;

-- Dumping structure for table stat_db.traitement
CREATE TABLE IF NOT EXISTS `traitement` (
  `id` varchar(36) NOT NULL,
  `id_traitement` varchar(255) NOT NULL,
  `traitement` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table stat_db.traitement: 0 rows
/*!40000 ALTER TABLE `traitement` DISABLE KEYS */;
/*!40000 ALTER TABLE `traitement` ENABLE KEYS */;

-- Dumping structure for table stat_db.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` varchar(36) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `is_account_non_expired` bit(1) NOT NULL,
  `is_account_non_locked` bit(1) NOT NULL,
  `is_credentials_non_expired` bit(1) NOT NULL,
  `is_enabled` bit(1) NOT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `password` varchar(120) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `id_service` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  KEY `FKhx5f4iq2mkmcuiq2pi9mcmslt` (`id_service`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table stat_db.users: 7 rows
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `email`, `firstname`, `is_account_non_expired`, `is_account_non_locked`, `is_credentials_non_expired`, `is_enabled`, `lastname`, `password`, `username`, `id_service`) VALUES
	('01a14e33-b530-4cf6-851c-da338c0889dd', 'esselamia@cnas.dz', 'ABDELLATIF', b'1', b'1', b'1', b'1', 'ESSELAMI', '$2a$12$TrDJwYYx3ivqkPYaDFNojOkQJ/Naiqj5Q66tah3NdW7FjqUMGCDm.', 'esselami', NULL),
	('29b7440f-9789-49fd-a44f-51c7b0e9808a', 'dr_aindefla@cnas.dz', 'AHMED', b'1', b'1', b'1', b'1', 'ESSELAMI', '$2a$12$TrDJwYYx3ivqkPYaDFNojOkQJ/Naiqj5Q66tah3NdW7FjqUMGCDm.', 'dr_aindefla', NULL),
	('e23d0813-33b4-4915-bfa9-c0e004e13c09', 'sdf_aindefla@cnas.dz', 'SDF', b'1', b'1', b'1', b'1', 'AIN DEFLA', '$2a$12$TrDJwYYx3ivqkPYaDFNojOkQJ/Naiqj5Q66tah3NdW7FjqUMGCDm.', 'sdf_aindefla', NULL),
	('31bb1f1f-b8b6-4705-a942-0312ba096b80', 'sdr_aindefla@cnas.dz', 'SDR', b'1', b'1', b'1', b'1', 'AIN DEFLA', '$2a$12$TrDJwYYx3ivqkPYaDFNojOkQJ/Naiqj5Q66tah3NdW7FjqUMGCDm.', 'sdr_aindefla', NULL),
	('08afb782-6a73-4d41-990b-90de7faad0e1', 'sdp_aindefla@cnas.dz', 'SDP', b'1', b'1', b'1', b'1', 'AIN DEFLA', '$2a$12$TrDJwYYx3ivqkPYaDFNojOkQJ/Naiqj5Q66tah3NdW7FjqUMGCDm.', 'sdp_aindefla', NULL),
	('5129e22b-6a84-4a3d-9475-97f11e096694', 'cm_aindefla@cnas.dz', 'CM', b'1', b'1', b'1', b'1', 'AIN DEFLA', '$2a$12$TrDJwYYx3ivqkPYaDFNojOkQJ/Naiqj5Q66tah3NdW7FjqUMGCDm.', 'cm_aindefla', NULL),
	('cdff885b-3d49-4660-a2b8-34a048e6476a', 'ccaindefla@cnas.dz', 'AHMED', b'1', b'1', b'1', b'1', 'NEMAR', '$2a$12$TrDJwYYx3ivqkPYaDFNojOkQJ/Naiqj5Q66tah3NdW7FjqUMGCDm.', 'admin_aindefla', NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- Dumping structure for table stat_db.user_roles
CREATE TABLE IF NOT EXISTS `user_roles` (
  `user_id` varchar(36) NOT NULL,
  `role_id` varchar(36) NOT NULL,
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
  KEY `FKhfh9dx7w3ubf1co1vdev94g3f` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table stat_db.user_roles: 7 rows
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
	('01a14e33-b530-4cf6-851c-da338c0889dd', 'cc81babc-04d3-4efd-a783-a2f2ee9eaef6'),
	('cdff885b-3d49-4660-a2b8-34a048e6476a', 'c5d027d2-1181-44cb-ae12-abe180156484'),
	('29b7440f-9789-49fd-a44f-51c7b0e9808a', '93cb30f5-f35c-496c-82cb-c6b48eece341'),
	('08afb782-6a73-4d41-990b-90de7faad0e1', '01f0f74b-0046-444e-8133-4088c05ea816'),
	('31bb1f1f-b8b6-4705-a942-0312ba096b80', 'a9133cf8-b8ff-4849-8bf6-2cb208659081'),
	('e23d0813-33b4-4915-bfa9-c0e004e13c09', '3fc96aa8-0dc5-44de-944a-f1bbbcae6ae9'),
	('5129e22b-6a84-4a3d-9475-97f11e096694', '215ef598-20b2-4133-95c1-91a015c0b2b0');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
