CREATE TABLE `user` (
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`username`)
);

CREATE TABLE `user_roles` (
  `username` varchar(50) NOT NULL,
  `role_name` varchar(50) NOT NULL,
  UNIQUE KEY `username_roleName` (`username`,`role_name`),
  CONSTRAINT `fk_user` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
);

CREATE TABLE `roles_permissions` (
  `role_name` varchar(50) NOT NULL,
  `permission` varchar(255) NOT NULL,
  UNIQUE KEY `roleName_permission` (`role_name`,`permission`)
);

insert into users values('admin', 'admin');
insert into user_roles values('admin', 'SYSTEM_ADMIN');