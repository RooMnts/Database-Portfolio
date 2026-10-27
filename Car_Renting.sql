-- Create the database
Create database Car_Renting;

-- Show all databases
show databases;

-- Select the database to use
use Car_Renting;

-- Create Client table with constraints
create table if not exists Client (
Client_ID int auto_increment primary key,
Name varchar(50) not null,
DOB date not null,
Age int check (Age >= 0),
Client_address varchar(100) not null
);

-- Describe table structure
desc Client;

-- Create CarCare table
create table if not exists CarCare (
Car_type varchar(30) primary key,
Base_rent decimal(6,2) check (Base_rent > 0)
);

desc CarCare;

-- Create ServiceInformation table
create table if not exists ServiceInformation (
Service_name varchar(50) primary key,
Service_charge decimal(6,2) check (Service_charge >= 0)
);

desc ServiceInformation;

-- Create CarHire table with foreing keys
create table if not exists CarHire (
Reservation_ID int auto_increment primary key,
Client_ID int not null,
Car_type varchar(30) not null,
Date_of_Reservation datetime default current_timestamp,
foreign key (Client_ID) references Client(Client_ID) on delete cascade,
foreign key	(Car_type) references CarCare(Car_type) on delete cascade
);

desc CarHire;

-- Create Reservation table
create table if not exists Reservation (
Reservation_ID int not null,
Service_name varchar(50) not null,
primary key (Reservation_ID, Service_name),
foreign key (Reservation_ID) references CarHire(Reservation_ID) on delete cascade,
foreign key (Service_name) references ServiceInformation(Service_name) on delete cascade
);

desc Reservation;

-- Show all tables
show tables;

-- Inser dummy data into Client, CarCare, ServiceInformation, CarHire and Reservation
insert into Client (Client_ID, Name, DOB, Age, Client_address) values
(101, 'John', '1990-05-15', 33, '123 Main St, City'),
(102, 'Sarah', '1985-07-21', 38, '456 Elm St, Town'),
(103, 'Michael', '1995-03-10', 28, '789 Oak St, Village'),
(104, 'Emily', '1988-11-30', 34, '101 Pine Rd, County'),
(105, 'Daniel', '1980-09-05', 43, '222 Cedar Ln, City'),
(106, 'Jessica', '1992-04-20', 31, '333 Birch Ave, Town'),
(107, 'Brian', '1987-08-12', 36, '444 Oak Rd, City'),
(108, 'Amanda', '1993-01-25', 30, '555 Elm St, Village'),
(109, 'Robert', '1997-06-03', 26, '666 Maple Ln, County'),
(110, 'Olivia', '1996-12-18', 26, '777 Pine St, Town'),
(111, 'Mateo', '1994-02-11', 31, '12 Harbour St, Dublin'),
(112, 'Sofia', '1972-06-19', 53, '45 Greenway Rd, Cork'),
(113, 'Andres', '2000-09-25', 25, '78 Riverbank Ave, Galway'),
(114, 'Valeria', '1983-03-14', 42, '90 Hilltop Dr, Limerick'),
(115, 'Hector', '1978-12-01', 47, '21 Oakridge Ln, Waterford'),
(116, 'Camila', '2010-05-07', 15, '33 Maple Grove, Kilkenny'),
(117, 'Diego', '1999-08-30', 26, '56 Birchwood St, Sligo'),
(118, 'Fernanda', '1985-11-22', 40, '67 Chestnut Rd, Wexford'),
(119, 'Leonardo', '1992-04-05', 33, '88 Willow Park, Athlone'),
(120, 'Paula', '1996-07-18', 29, '99 Sycamore Ave, Dundalk');

-- Retrive all clients, car types, services, car hires and reservations
select * from Client;
describe Client;

insert into CarCare (Car_type, Base_rent) values
('Sedan', 50),
('SUV', 80),
('Hatchback', 40),
('Convertible', 70),
('Truck', 90);

select * from CarCare;
describe CarCare;

insert into ServiceInformation (Service_name, Service_charge) values
('GPS', 10),
('Child Seat', 15),
('Extra Driver', 15),
('Wash & Vacuum', 20);

select * from ServiceInformation;
describe ServiceInformation;

insert into CarHire (Reservation_ID, Client_ID, Car_type, Date_of_Reservation) values
(1, 101, 'Sedan', '2023-10-17'),
(2, 102, 'SUV', '2023-10-16'),
(3, 103, 'Hatchback', '2023-10-15'),
(4, 104, 'Convertible', '2023-10-14'),
(5, 105, 'Truck', '2023-10-13'),
(6, 106, 'Sedan', '2023-10-12'),
(7, 107, 'Hatchback', '2023-10-11'),
(8, 108, 'SUV', '2023-10-10'),
(9, 109, 'Convertible', '2023-10-09'),
(10, 110, 'Sedan', '2023-10-08'),
(11, 111, 'Sedan', '2023-10-02'),
(12, 112, 'SUV', '2023-10-30'),
(13, 113, 'Hatchback', '2023-10-26'),
(14, 114, 'Convertible', '2023-10-18'),
(15, 115, 'Truck', '2023-10-11'),
(16, 116, 'Sedan', '2023-10-07'),
(17, 117, 'Hatchback', '2023-10-09'),
(18, 118, 'SUV', '2023-10-31'),
(19, 119, 'Convertible', '2023-10-01'),
(20, 120, 'Sedan', '2023-10-22');

select * from CarHire;
describe CarHire;

insert into Reservation (Reservation_ID, Service_name) values
(1, 'GPS'),
(1, 'Child Seat'),
(2, 'Extra Driver'),
(3, 'Wash & Vacuum'),
(4, 'GPS'),
(4, 'Wash & Vacuum'),
(5, 'GPS'),
(6, 'Child Seat'),
(7, 'Wash & Vacuum'),
(8, 'Child Seat'),
(9, 'GPS'),
(9, 'Extra Driver'),
(10, 'Wash & Vacuum'),
(11, 'Child Seat'),
(12, 'GPS'),
(12, 'Extra Driver'),
(13, 'Wash & Vacuum'),
(14, 'Extra Driver'),
(15, 'Wash & Vacuum'),
(16, 'GPS'),
(16, 'Wash & Vacuum'),
(17, 'GPS'),
(18, 'Child Seat'),
(19, 'Wash & Vacuum'),
(20, 'Wash & Vacuum');

select * from Reservation;
describe Reservation;

show tables;

-- Select clients older than 30
select Name, Age, Client_address
from Client
where Age > 30;

-- Order clients by DOB ascending
select Name, DOB
from Client
order by DOB asc;

-- Inner join Client and CarHire
select c.Name, h.Car_type, h.Date_of_Reservation
from Client c
inner join CarHire h on c.Client_ID = h.Client_ID;

-- Group by car type and count reservations
select Car_type, count(*) as Total_Reservations
from CarHire
group by Car_type;

-- Group by car type with having clause
select Car_type, count(*) as Total_Reservations
from CarHire
group by Car_type
having count(*) > 3;

-- Join CarHire, Client and CarCare
select h.Reservation_ID, c.Name, h.Car_type, cc.Base_rent
from CarHire h
join Client c on h.Client_ID = c.Client_ID
join CarCare cc on h.Car_type = cc.Car_type;

-- Join Reservation, CarHire, Client, ServiceInformation 
select h.Reservation_ID, c.Name, r.Service_name, s.Service_charge
from Reservation r
join CarHire h on r.Reservation_ID = h.Reservation_ID
join Client c on h.Client_ID = c.Client_ID
join ServiceInformation s on r.Service_name = s.Service_name;

-- Aggregate sum of service charges per client
select c.Name, sum(s.Service_charge) as Total_Reservations
from Client c
join CarHire h on c.Client_ID = h.Client_ID
join Reservation r on h.Reservation_ID = r.Reservation_ID
join ServiceInformation s on r.Service_name = s.Service_name
group by c.Name;

-- Filter reservations by date range
select Reservation_ID, Client_ID, Car_type, Date_of_Reservation
from CarHire
Where Date_of_Reservation between '2023-10-01' and '2023-10-31';
    
-- Left join CarHire with Reservation
select h.Reservation_ID, c.Name, r.Service_name
from CarHire h
left join Reservation r on h.Reservation_ID = r.Reservation_ID
left join Client c on h.Client_ID = c.Client_ID;

-- Right join Reservation with ServiceInformation
select r.Reservation_ID, s.Service_name, s.Service_charge
from Reservation r
right join ServiceInformation s on r.Service_name = s.Service_name;

-- Update example
update Client
set Age = Age + 1
where Client_ID = 101;

-- Delete example, typo corrected: Service_name
delete from Reservation
where Reservation_ID = 20 and Service_name = 'Wash & Vacuum';

-- Case statement to categorize clients by age
select Name, Age,
	case
		when Age < 18 then 'Minor'
        when Age between 18 and 40 then 'Young Adult'
        else 'Adult'
	end as Category
from client;

-- Subquery to find clients who booked SUVs
select Name, Client_ID
	from Client
    where Client_ID in (
    select Client_ID
    from CarHire
    where Car_type = 'SUV'
    );

-- Full join simulation using union
select r.Reservation_ID, r.Service_name, s.Service_charge
from Reservation r
left join ServiceInformation s on r.Service_name = s.Service_name
union
select r.Reservation_ID, r.Service_name, s.Service_charge
from Reservation r
right join ServiceInformation s on r.Service_name = s.Service_name
