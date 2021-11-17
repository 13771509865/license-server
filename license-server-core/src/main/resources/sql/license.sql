/*
 Navicat Premium Data Transfer

 Source Server         : 本地mysql5.7
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : license

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 17/11/2021 14:02:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activation_exception_record
-- ----------------------------
DROP TABLE IF EXISTS `activation_exception_record`;
CREATE TABLE `activation_exception_record`  (
  `id` bigint(20) NOT NULL,
  `create_time` datetime(3) NOT NULL,
  `update_time` datetime(3) NOT NULL,
  `cdkey_id` bigint(20) NOT NULL,
  `cdkey` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ip` tinyint(3) UNSIGNED NULL DEFAULT NULL,
  `mac` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cpu_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `bios_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `exception_id` tinyint(3) UNSIGNED NOT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for activation_num
-- ----------------------------
DROP TABLE IF EXISTS `activation_num`;
CREATE TABLE `activation_num`  (
  `cdkey_id` bigint(20) NOT NULL,
  `create_time` datetime(3) NOT NULL,
  `update_time` datetime(3) NOT NULL,
  `status` tinyint(3) UNSIGNED NULL DEFAULT NULL,
  `permit_num` int(11) NOT NULL,
  `surplus` int(11) NOT NULL,
  PRIMARY KEY (`cdkey_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for authorization
-- ----------------------------
DROP TABLE IF EXISTS `authorization`;
CREATE TABLE `authorization`  (
  `id` bigint(20) NOT NULL,
  `create_time` datetime(3) NOT NULL,
  `update_time` datetime(3) NOT NULL,
  `status` tinyint(3) UNSIGNED NULL DEFAULT NULL,
  `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `customer_region` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `customer_date` datetime(3) NOT NULL,
  `customer_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `saler_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `saler_mail` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `saler_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cdkey_id` bigint(20) NOT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_cdkey`(`cdkey_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cdkey
-- ----------------------------
DROP TABLE IF EXISTS `cdkey`;
CREATE TABLE `cdkey`  (
  `id` bigint(20) NOT NULL,
  `create_time` datetime(3) NOT NULL,
  `update_time` datetime(3) NOT NULL,
  `status` tinyint(3) UNSIGNED NULL DEFAULT NULL,
  `cdkey` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `cdk_create_time` datetime(3) NULL DEFAULT NULL,
  `machine` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `producer` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `product` tinyint(3) UNSIGNED NULL DEFAULT NULL,
  `version` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `region` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cdk_type` tinyint(3) UNSIGNED NOT NULL,
  `mode` tinyint(3) UNSIGNED NOT NULL,
  `expire_num` int(11) NULL DEFAULT NULL,
  `expire_unit` tinyint(3) UNSIGNED NULL DEFAULT NULL,
  `expire_date` datetime(3) NULL DEFAULT NULL,
  `permit_num` int(11) NOT NULL,
  `heart_rate_num` int(11) NOT NULL,
  `heart_rate_unit` tinyint(3) UNSIGNED NOT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_cdkey`(`cdkey`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for client_info
-- ----------------------------
DROP TABLE IF EXISTS `client_info`;
CREATE TABLE `client_info`  (
  `id` bigint(20) NOT NULL,
  `create_time` datetime(3) NOT NULL,
  `update_time` datetime(3) NOT NULL,
  `status` tinyint(4) NULL DEFAULT NULL,
  `cdkey_id` bigint(20) NOT NULL,
  `cpu_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `bios_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `mac` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ip` tinyint(3) UNSIGNED NULL DEFAULT NULL,
  `expire_date` datetime(3) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_cdk_bios_cpu`(`bios_id`, `cpu_id`, `cdkey_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
