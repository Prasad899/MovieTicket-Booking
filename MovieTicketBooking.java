import java.util.Scanner;

public class MovieTicketBooking {

    static class Movie {
        String name;
        int availableTickets;
        double ticketPrice;
        boolean[] seats; // Array to track seat availability (true for booked, false for available)

        Movie(String name, int availableTickets, double ticketPrice) {
            this.name = name;
            this.availableTickets = availableTickets;
            this.ticketPrice = ticketPrice;
            this.seats = new boolean[15]; // Initialize 15 seats (0 to 14)
        }

        void displayMovieDetails() {
            System.out.println("Movie: " + name + " | Available Tickets: " + availableTickets + " | Price: Rs. " + ticketPrice);
            displayAvailableSeats(); // Show available seats
        }

        void displayAvailableSeats() {
            System.out.print("Available Seats: ");
            for (int i = 0; i < seats.length; i++) {
                if (!seats[i]) {
                    System.out.print((i + 1) + " "); // Display seat number (1-based)
                }
            }
            System.out.println();
        }

        boolean allocateSeats(int[] seatNumbers) {
            for (int seatNumber : seatNumbers) {
                if (seatNumber < 1 || seatNumber > 15 || seats[seatNumber - 1]) {
                    System.out.println("Seat " + seatNumber + " is invalid or already booked. Please try again.");
                    return false; // Invalid seat number or seat already booked
                }
            }

            // Book the seats
            for (int seatNumber : seatNumbers) {
                seats[seatNumber - 1] = true;
            }
            availableTickets -= seatNumbers.length; // Decrease the available ticket count
            return true;
        }

        void displayBookedSeats() {
            System.out.print("Booked Seats: ");
            for (int i = 0; i < seats.length; i++) {
                if (seats[i]) {
                    System.out.print((i + 1) + " "); // Display booked seat number (1-based)
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Movie[] movies = {
            new Movie("Devara", 10, 200),
            new Movie("Visvam", 8, 150),
            new Movie("Game changer", 5, 170),
            new Movie("Pushpa", 6, 150)
        };

        System.out.println("Welcome to the Movie Ticket Booking System!");
        boolean continueBooking = true;

        while (continueBooking) {
            System.out.println("\nAvailable Movies:");
            for (int i = 0; i < movies.length; i++) {
                System.out.print((i + 1) + ". ");
                movies[i].displayMovieDetails();
            }

            System.out.print("\nEnter the movie number you want to book tickets for: ");
            int movieChoice = scanner.nextInt() - 1;

            if (movieChoice < 0 || movieChoice >= movies.length) {
                System.out.println("Invalid movie selection. Try again.");
                continue;
            }

            Movie selectedMovie = movies[movieChoice];

            System.out.print("Enter the number of tickets you want to book: ");
            int numTickets = scanner.nextInt();

            // Check if enough tickets are available
            if (numTickets <= selectedMovie.availableTickets) {
                selectedMovie.displayAvailableSeats();
                
                int[] seatNumbers = new int[numTickets];
                System.out.println("Please enter the seat numbers you want to book (e.g., 1 2 3 for seat 1, 2, 3): ");
                for (int i = 0; i < numTickets; i++) {
                    seatNumbers[i] = scanner.nextInt(); // Collect the seat numbers
                }

                // Try to allocate the selected seats
                if (selectedMovie.allocateSeats(seatNumbers)) {
                    double totalCost = numTickets * selectedMovie.ticketPrice;

                    System.out.println("\nBooking Successful!");
                    System.out.println("Movie: " + selectedMovie.name);
                    System.out.println("Seats: ");
                    for (int seatNumber : seatNumbers) {
                        System.out.print(seatNumber + " ");
                    }
                    System.out.println("\nNumber of tickets: " + numTickets);
                    System.out.println("Total cost: Rs. " + totalCost);
                    selectedMovie.displayBookedSeats(); // Show booked seats
                } else {
                    System.out.println("Booking failed due to invalid seat selection.");
                }
            } else {
                System.out.println("Sorry, only " + selectedMovie.availableTickets + " tickets are available.");
            }

            System.out.print("\nDo you want to book another movie? (yes/no): ");
            String response = scanner.next();
            continueBooking = response.equalsIgnoreCase("yes");
        }

        System.out.println("Thank you for using the Movie Ticket Booking System. Goodbye!");
        scanner.close();
    }
}