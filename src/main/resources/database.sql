
-- Creation of Schema and tables --
CREATE SCHEMA IF NOT EXISTS `MobileDevicesSystem`;

CREATE TABLE `MobileDevicesSystem`.`MobileDevices` (
  `modelName` char(36) NOT NULL,
  PRIMARY KEY (`modelName`));

CREATE TABLE `MobileDevicesSystem`.`Features` (
  `id` char(36) NOT NULL,
  `feature` char(36) NOT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `MobileDevicesSystem`.`MobileDevicesFeatures` (
  `mobileName` char(36) NOT NULL,
  `featureId` char(36) NOT NULL,
  PRIMARY KEY (`mobileName`,`featureId`),
  KEY `mobileName_Index` (`mobileName`),
  KEY `featureId_FK_idx` (`featureId`),
  CONSTRAINT `featureId_FK` FOREIGN KEY (`featureId`) REFERENCES `Features` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `mobileName_FK` FOREIGN KEY (`mobileName`) REFERENCES `MobileDevices` (`modelName`) ON DELETE NO ACTION ON UPDATE NO ACTION);

-- Populating the tables --
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('25CCB7C5-92F9-4799-913A-80AFC67FC887','Tablet');
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('348F6922-430B-47EB-92A8-FCA6071FD3D8','Apple');
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('36CB7D2F-CE3D-466B-B49B-80C5FE128484','RES_1080x1920');
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('3C7029BD-2204-476B-A445-4C4B3E57DCA7','SmartPhone');
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('3DDE21D3-6188-46A6-B0B1-3B92EC4E3515','Samsung');
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('52F11580-0375-4947-99F7-5305711A64E3','RES_720x1280');
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('57153D74-17C9-4661-88A3-D9E8AA6AB081','RES_750x1334');
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('5BB2406B-837A-45F1-9D74-47883EB1E9B6','Front camera');
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('6E2D6427-3F82-42A7-AF04-14ABE3F538EE','Small_Screen_Size');
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('7A76FEA0-D2B5-4B1F-9200-E1126190D68C','Gyroscope');
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('8A6D9E5D-AD12-4774-8681-5AF0D172E31D','iOS');
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('8F905D28-297C-460F-94E0-B7E1A8D57CBA','Mobile phone');
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('986B0F30-B1C1-49D3-B442-E6EAA04B536F','RES_640x1136');
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('A3E56F66-8773-4A9C-A262-A3C51415F0A2','LG');
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('AA0F3BA2-22F6-4347-8170-57292C7BFF51','Smart TV');
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('AE1C4577-F18A-4689-899E-9DE1A254C165','Android');
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('B3548E10-1C54-4C36-AB8B-2C7E9FC806AE','RES_1440x2560');
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('B81A5EA1-212D-402B-B1AC-8D673B91084A','Medium_Screen_Size');
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('C4BF86A5-1FD4-4A17-97A8-F10E63F96377','Back camera');
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('C680DA27-6144-4827-A757-CEE972B20A00','Big_Screen_Size');
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('F43C62AD-DCB7-43D2-8719-1CBC08FFCB2E','XL_Screen_Size');
INSERT INTO `MobileDevicesSystem`.`Features` (`id`,`feature`) VALUES ('F4E0F5A4-9768-4AE0-9958-F06457CE154B','FeaturePhone');
