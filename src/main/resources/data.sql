DROP TABLE IF EXISTS devices;

CREATE TABLE devices (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  device_type INT NOT NULL,
  mac_address VARCHAR(250) NOT NULL,
  up_link_device_address VARCHAR(250) DEFAULT NULL
);

INSERT INTO devices (device_type, mac_address, up_link_device_address) VALUES
  (2,  '1',  NULL),
  (0,  '2',  '1'),
  (1,  '3',  '1'),
  (1,  '4',  '3'),
  (2,  '21', NULL),
  (0,  '22', '21');
