DROP TABLE IF EXISTS ticket;
DROP TABLE IF EXISTS parking_spot;
DROP TABLE IF EXISTS parking_lot;
DROP TABLE IF EXISTS fee;

CREATE TABLE parking_lot (
  id INT NOT NULL AUTO_INCREMENT,
  parking_lot_type varchar(100) NOT NULL,
  total_size INT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE parking_spot (
  id INT NOT NULL AUTO_INCREMENT,
  parking_spot_id INT NOT NULL ,
  type varchar(100) NOT NULL,
  parking_lot_id INT NOT NULL,
  is_currently_occupied BOOLEAN,
  PRIMARY KEY (id,parking_lot_id),
  FOREIGN KEY (parking_lot_id) REFERENCES parking_lot(id)
);

CREATE TABLE fee (
	id int NOT NULL AUTO_INCREMENT,
	fee_plan_id int not NULL,
	start_secs int NOT NULL,
	type varchar(30) NOT NULL,
	parking_spot_type varchar(30) NOT NULL,
	end_secs int NOT NULL,
	is_flat_fee BOOLEAN,
	isFlatPerHour BOOLEAN,
	isFlatPerDay BOOLEAN,
	isIntervalFee BOOLEAN,
	isEndInclusive BOOLEAN,
	amount DOUBLE NOT NULL,
	PRIMARY KEY (id),
	UNIQUE (fee_plan_id,start_secs,type,parking_spot_type)
);

SELECT * FROM parking_spot p WHERE p.is_Currently_Occupied = false AND p.parking_Lot_Id = 2 AND p.type = 'FOUR_WHEELER' ORDER BY p.id ASC
/*
We can have another table for mapping between fee and parkinglot
*/

/*
H2 data base does not allow to refer the non primary key ids from other table.
*/
/*
DROP TABLE IF EXISTS ticket;
CREATE TABLE ticket (
  id INT NOT NULL,
  start_time TIMESTAMP NOT NULL,
  end_time TIMESTAMP,
  vehicle_reg_no VARCHAR(30),
  parking_spot_id INT NOT NULL,
  parking_lot_id INT NOT NULL,
  parkingFee DOUBLE,
  PRIMARY KEY (id,parking_lot_id,parking_spot_id),
  FOREIGN KEY (parking_spot_id) REFERENCES parking_spot(id),
  FOREIGN KEY (parking_lot_id) REFERENCES parking_spot(parking_lot_id)
);


*/




