# G.R.O.U.P. F.I.V.E. Hotel Project

Usage
-----

Cloning this project:

```zsh
git clone https://github.com/Baylor-G-R-O-U-P-F-I-V-E/HotelProject.git
```

See [docs/INSTALLATION.md](docs/INSTALLATION.md) for help with git.


-----


Using the `make` utility:

```zsh
# List make commands
make help

# Package the project
make build

# Run the project
make run

# Update dependencies
# This allows us to pull dependencies without compiling our project
make update

# Generate javadoc and open in browser
make jvd

# Run test cases (no target for this one lol)
mvn test
```

-----


Using Maven commands:

```zsh
# Package the project
mvn package

# Package the project, skipping test cases
mvn package -Dmaven.test.skip=true

# Run the project
java -jar target/HotelProject-0.0.1-SNAPSHOT.jar

# Update local dependencies by pulling from remote
# This allows us to pull dependencies without compiling our project
mvn dependency:copy-dependencies

# Generate javadoc and open in browser
mvn site
open target/site/index.html

# Run test cases
mvn test
```

-----


Developer References
--------------------

1.  [`HotelProjectException`](./docs/EXCEPTIONS_README.md)
1.  [Setting up Git and Cloning This Repository](./docs/INSTALLATION.md)


-----


About This Project
------------------

This project is a 3-layered application with Java SE. The data models contain 
at least seven data entities/concepts with one-to-many and many-to-many 
association mapping. Morever, the project must demonstrate the use of JUnit 
tests, git, JavaDoc, polymorphism, and Maven.  

### The Stay & Shop Reservation System

You have been contracted to create a system that automates the reservation 
process for a combined store and hotel. The establishment offers a unique 
experience where guests can stay in comfortable rooms and also have access to 
an exclusive shopping experience within the same premises!  

### Hotel Section

The establishment has a total of three floors with hotel rooms. Each floor has 
a unique theme:  

1. **Nature Retreat**: Single, double, and family rooms available. Each room 
   has a nature-inspired design.
2. **Urban Elegance**: Suite and deluxe rooms available. Modern and elegant 
   room designs.  
3. **Vintage Charm**: Standard and deluxe rooms available. Rooms with a vintage 
   touch. Each room has standard hotel features like a unique number, type of 
   beds (single, double, queen), and smoking/non-smoking status.  

Each room is assigned a quality level:  

1. Executive level
2. Business level
3. Comfort level
4. Economy level  

Each room also has the following:  

1. A unique room number
2. A certain number and type of beds (i.e. twin, full, queen, king)
3. A smoking/non-smoking status  

Each quality level has a maximum daily rate, although the rate that a guest 
pays may be less (e.g., through promotion rate, group rate, or non-refundable 
rate). When a guest wishes to make a reservation, the travel agency asks the 
guest which nights they want to stay and the type of room desired. The system 
must verify if any room is available on those nights before allowing a 
reservation. The system needs to record basic information about a guest who has 
made a reservation: name, address, credit card number, and date of expiration. 
A reservation can be canceled at any time. There is no charge for cancellation 
if it is canceled within 2 days of the reservation date. The guest is charged 
80% of the cost of a single-night stay (at the reservation rate) if the 
cancellation is made after 2 days from the date on which the reservation was 
made. A reservation can be modified at any time. When a guest checks in, a room 
is allocated to the guest until the guest checks out. The system must keep track 
of all reservations and payments made and be able to provide a summary to the 
Cruice's manager when it is requested. Corporate guests are not directly billed; 
rather, the corporations they work for are billed, and payments are made sometime 
later.  

**Store Section**: The store offers a variety of products ranging from clothing 
and accessories to local artisanal goods. Guests have the option to explore and 
shop during their stay.  
