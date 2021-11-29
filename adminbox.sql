/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : adminbox

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 29/11/2021 16:32:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tkadmin_groups
-- ----------------------------
DROP TABLE IF EXISTS `tkadmin_groups`;
CREATE TABLE `tkadmin_groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `rule_ids` longtext,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tkadmin_groups
-- ----------------------------
BEGIN;
INSERT INTO `tkadmin_groups` VALUES (1, '超级管理员', 'administrator', '1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,28,27,29,30,31,32', 1, '2021-11-19 16:02:58', '2021-11-26 13:46:35');
COMMIT;

-- ----------------------------
-- Table structure for tkadmin_logs
-- ----------------------------
DROP TABLE IF EXISTS `tkadmin_logs`;
CREATE TABLE `tkadmin_logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `user_type` tinyint(2) NOT NULL DEFAULT '0',
  `content` longtext,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tkadmin_member
-- ----------------------------
DROP TABLE IF EXISTS `tkadmin_member`;
CREATE TABLE `tkadmin_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `realname` varchar(255) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `salt` varchar(10) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `group_ids` longtext,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tkadmin_member
-- ----------------------------
BEGIN;
INSERT INTO `tkadmin_member` VALUES (1, 'admin', '13333333333', 'Admin', '2E977AA4BB33B6E93B13F221B62DD8EB18E6B4D8', '037799', '2131321', '1', 1, '2021-05-03 18:17:14', '2021-11-23 21:34:43');
COMMIT;

-- ----------------------------
-- Table structure for tkadmin_rules
-- ----------------------------
DROP TABLE IF EXISTS `tkadmin_rules`;
CREATE TABLE `tkadmin_rules` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL DEFAULT '0',
  `is_layout` tinyint(1) NOT NULL DEFAULT '0',
  `is_router` tinyint(1) NOT NULL DEFAULT '1',
  `title` varchar(255) DEFAULT NULL,
  `title_en` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `path` longtext,
  `component` longtext,
  `redirect` longtext,
  `alway_show` tinyint(1) NOT NULL DEFAULT '0',
  `icon` varchar(255) DEFAULT NULL,
  `cache` tinyint(1) NOT NULL DEFAULT '0',
  `hide_tab` tinyint(1) NOT NULL DEFAULT '0',
  `hide_menu` tinyint(1) NOT NULL DEFAULT '0',
  `hide_close` tinyint(1) NOT NULL DEFAULT '0',
  `weight` int(11) NOT NULL DEFAULT '0',
  `page_type` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tkadmin_rules
-- ----------------------------
BEGIN;
INSERT INTO `tkadmin_rules` VALUES (1, 0, 1, 1, '首页', 'Home', 'homeTitle', '/', 'Layout', '/dashboard', 0, 'HomeFilled', 0, 0, 0, 0, 0, NULL, '2021-11-12 17:51:00', '2021-11-24 14:22:37');
INSERT INTO `tkadmin_rules` VALUES (2, 1, 0, 1, '仪表盘', 'DashBoard', 'dashboardTitle', '/dashboard', '/views/main/dashboard/index.vue', '', 0, 'HomeFilled', 0, 0, 0, 1, 10000, NULL, '2021-11-12 17:51:49', '2021-11-21 22:02:14');
INSERT INTO `tkadmin_rules` VALUES (3, 0, 0, 1, '系统设置', 'System Config', 'sysConfigTitle', '/system/config', 'Layout', '/rules', 0, 'Setting', 0, 0, 0, 0, 0, NULL, '2021-11-12 22:46:11', '2021-11-23 18:11:15');
INSERT INTO `tkadmin_rules` VALUES (4, 3, 0, 1, '规则管理', 'Rules Manage', 'sysRulesTitle', 'rules', '/thinker/system/rules.vue', '', 0, '', 0, 0, 0, 0, 0, NULL, '2021-11-23 18:09:47', NULL);
INSERT INTO `tkadmin_rules` VALUES (5, 4, 0, 0, '规则管理Restful', 'Rules Manage Restful', 'sysRulesTitleRestful', NULL, '/restful/thinker/system/rules', NULL, 0, NULL, 0, 0, 0, 0, 0, NULL, '2021-11-23 18:09:47', NULL);
INSERT INTO `tkadmin_rules` VALUES (6, 4, 0, 0, '规则管理编辑', 'Rules Manage Edit', 'sysRulesTitleEdit', 'rulesEdit', '/thinker/system/rulesEdit.vue', NULL, 0, NULL, 0, 0, 0, 0, 1, NULL, '2021-11-23 18:09:47', NULL);
INSERT INTO `tkadmin_rules` VALUES (7, 4, 0, 0, '规则管理新增按钮', 'Rules Manage Add Button', 'sysRulesTitleAddButton', NULL, NULL, NULL, 0, NULL, 0, 0, 0, 0, 2, 'addBtn', '2021-11-23 18:09:47', NULL);
INSERT INTO `tkadmin_rules` VALUES (8, 4, 0, 0, '规则管理批量删除按钮', 'Rules Manage All Delete Button', 'sysRulesTitleAllDeleteButton', '', '', '', 0, '', 0, 0, 0, 0, 3, 'allDelBtn', '2021-11-23 18:09:47', '2021-11-23 19:08:54');
INSERT INTO `tkadmin_rules` VALUES (9, 4, 0, 0, '规则管理编辑按钮', 'Rules Manage Edit Button', 'sysRulesTitleEditButton', NULL, NULL, NULL, 0, NULL, 0, 0, 0, 0, 4, 'editBtn', '2021-11-23 18:09:47', NULL);
INSERT INTO `tkadmin_rules` VALUES (10, 4, 0, 0, '规则管理删除按钮', 'Rules Manage Delete Button', 'sysRulesTitleDeleteButton', NULL, NULL, NULL, 0, NULL, 0, 0, 0, 0, 5, 'delBtn', '2021-11-23 18:09:47', NULL);
INSERT INTO `tkadmin_rules` VALUES (11, 3, 0, 1, '角色管理', 'Roles Manage', 'sysRolesTitle', 'roles', '/thinker/system/roles.vue', '', 0, '', 0, 0, 0, 0, 1, NULL, '2021-11-23 18:10:49', NULL);
INSERT INTO `tkadmin_rules` VALUES (12, 11, 0, 0, '角色管理Restful', 'Roles Manage Restful', 'sysRolesTitleRestful', NULL, '/restful/thinker/system/roles', NULL, 0, NULL, 0, 0, 0, 0, 0, NULL, '2021-11-23 18:10:49', NULL);
INSERT INTO `tkadmin_rules` VALUES (13, 11, 0, 0, '角色管理编辑', 'Roles Manage Edit', 'sysRolesTitleEdit', 'rolesEdit', '/thinker/system/rolesEdit.vue', NULL, 0, NULL, 0, 0, 0, 0, 1, NULL, '2021-11-23 18:10:49', NULL);
INSERT INTO `tkadmin_rules` VALUES (14, 11, 0, 0, '角色管理新增按钮', 'Roles Manage Add Button', 'sysRolesTitleAddButton', NULL, NULL, NULL, 0, NULL, 0, 0, 0, 0, 2, 'addBtn', '2021-11-23 18:10:49', NULL);
INSERT INTO `tkadmin_rules` VALUES (15, 11, 0, 0, '角色管理批量删除按钮', 'Roles Manage All Delete Button', 'sysRolesTitleAllDeleteButton', '', '', '', 0, '', 0, 0, 0, 0, 3, 'allDelBtn', '2021-11-23 18:10:49', '2021-11-23 19:09:00');
INSERT INTO `tkadmin_rules` VALUES (16, 11, 0, 0, '角色管理编辑按钮', 'Roles Manage Edit Button', 'sysRolesTitleEditButton', NULL, NULL, NULL, 0, NULL, 0, 0, 0, 0, 4, 'editBtn', '2021-11-23 18:10:49', NULL);
INSERT INTO `tkadmin_rules` VALUES (17, 11, 0, 0, '角色管理删除按钮', 'Roles Manage Delete Button', 'sysRolesTitleDeleteButton', NULL, NULL, NULL, 0, NULL, 0, 0, 0, 0, 5, 'delBtn', '2021-11-23 18:10:49', NULL);
INSERT INTO `tkadmin_rules` VALUES (18, 3, 0, 1, '成员管理', 'Members Manage', 'sysMembersTitle', 'members', '/thinker/system/members.vue', '', 0, '', 0, 0, 0, 0, 2, NULL, '2021-11-23 18:11:50', NULL);
INSERT INTO `tkadmin_rules` VALUES (19, 18, 0, 0, '成员管理Restful', 'Members Manage Restful', 'sysMembersTitleRestful', NULL, '/restful/thinker/system/members', NULL, 0, NULL, 0, 0, 0, 0, 0, NULL, '2021-11-23 18:11:50', NULL);
INSERT INTO `tkadmin_rules` VALUES (20, 18, 0, 0, '成员管理编辑', 'Members Manage Edit', 'sysMembersTitleEdit', 'membersEdit', '/thinker/system/membersEdit.vue', NULL, 0, NULL, 0, 0, 0, 0, 1, NULL, '2021-11-23 18:11:50', NULL);
INSERT INTO `tkadmin_rules` VALUES (21, 18, 0, 0, '成员管理新增按钮', 'Members Manage Add Button', 'sysMembersTitleAddButton', NULL, NULL, NULL, 0, NULL, 0, 0, 0, 0, 2, 'addBtn', '2021-11-23 18:11:50', NULL);
INSERT INTO `tkadmin_rules` VALUES (22, 18, 0, 0, '成员管理批量删除按钮', 'Members Manage All Delete Button', 'sysMembersTitleAllDeleteButton', '', '', '', 0, '', 0, 0, 0, 0, 3, 'allDelBtn', '2021-11-23 18:11:50', '2021-11-23 19:08:30');
INSERT INTO `tkadmin_rules` VALUES (23, 18, 0, 0, '成员管理编辑按钮', 'Members Manage Edit Button', 'sysMembersTitleEditButton', NULL, NULL, NULL, 0, NULL, 0, 0, 0, 0, 4, 'editBtn', '2021-11-23 18:11:50', NULL);
INSERT INTO `tkadmin_rules` VALUES (24, 18, 0, 0, '成员管理删除按钮', 'Members Manage Delete Button', 'sysMembersTitleDeleteButton', NULL, NULL, NULL, 0, NULL, 0, 0, 0, 0, 5, 'delBtn', '2021-11-23 18:11:50', NULL);
INSERT INTO `tkadmin_rules` VALUES (25, 0, 1, 1, '系统监控', 'System Monitor', 'sysMonitorTitle', '/thinker/monitor', 'Layout', '/loginLog', 0, 'Monitor', 0, 0, 0, 0, 10000, NULL, '2021-11-23 18:15:09', NULL);
INSERT INTO `tkadmin_rules` VALUES (26, 25, 0, 0, '监控日志Restful', 'Monitor Logs Restful', 'sysMonitorLogTitleRestful', '', '/restful/thinker/monitor/systemLogs', '', 0, '', 0, 0, 0, 0, 0, NULL, '2021-11-23 18:16:32', '2021-11-24 21:17:13');
INSERT INTO `tkadmin_rules` VALUES (27, 25, 0, 1, '请求日志', 'Http Logs', 'sysHttpLogTitle', 'httpLogs', '/thinker/monitor/httpLogs.vue', '', 0, '', 0, 0, 0, 0, 2, NULL, '2021-11-23 18:55:17', '2021-11-24 18:44:31');
INSERT INTO `tkadmin_rules` VALUES (28, 25, 0, 1, '登录日志', 'Login Logs', 'sysLoginLogTitle', 'loginLogs', '/thinker/monitor/loginLogs.vue', '', 0, '', 0, 0, 0, 0, 0, NULL, '2021-11-23 18:16:32', NULL);
INSERT INTO `tkadmin_rules` VALUES (29, 25, 0, 1, '系统日志', 'System Logs', 'systemLogTitle', 'sysLogs', '/thinker/monitor/sysLogs.vue', '', 0, '', 0, 0, 0, 0, 3, NULL, '2021-11-24 21:00:43', NULL);
INSERT INTO `tkadmin_rules` VALUES (30, 25, 0, 1, '错误及告警日志', 'Error And Warn Logs', 'sysErrWarnLogTitle', 'fileLogs', '/thinker/monitor/fileLogs.vue', '', 0, '', 0, 0, 0, 0, 4, NULL, '2021-11-24 21:13:06', '2021-11-24 21:13:41');
INSERT INTO `tkadmin_rules` VALUES (31, 25, 0, 1, '服务器监控', 'Server Moniitor', 'sysServerMonitorTitle', 'server', '/thinker/monitor/server.vue', NULL, 0, NULL, 0, 0, 0, 0, 5, NULL, '2021-11-25 01:11:18', NULL);
INSERT INTO `tkadmin_rules` VALUES (32, 25, 0, 1, '库表文件生成', 'Generate Sql Files', 'moGenerateSqlTitle', 'generateSql', '/thinker/monitor/generateSql.vue', '', 0, '', 0, 0, 0, 0, 7, NULL, '2021-11-25 15:42:49', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
