DROP TABLE IF EXISTS devices;

CREATE TABLE devices (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  device_type INT NOT NULL,
  mac_address VARCHAR(250) NOT NULL,
  up_link_device_address VARCHAR(250) DEFAULT NULL
);

INSERT INTO devices (device_type, mac_address, up_link_device_address) VALUES
  (2,  '10:10:10:10:10:01',  NULL),
  (0,  '10:10:10:10:10:02',  '10:10:10:10:10:01'),
  (1,  '10:10:10:10:10:03',  '10:10:10:10:10:01'),
  (1,  '10:10:10:10:10:04',  '10:10:10:10:10:03'),
  (2,  '10:10:10:10:10:21', NULL),
  (0,  '10:10:10:10:10:22', '10:10:10:10:10:21');
