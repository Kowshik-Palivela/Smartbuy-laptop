package laptopcompare.in;

import java.util.InputMismatchException; // For handling non-integer input
import java.util.Scanner; // For user input

// Main class to run the SmartBuy application
public class laptopcomparisionApp {
    private static Scanner scanner = new Scanner(System.in); // Global scanner for input
    private static User[] users = new User[5]; // Array to store registered users (fixed size)
    private static int userCount = 0; // Current number of registered users
    private static User loggedInUser; // Currently logged-in user

    public static void main(String[] args) {
        // Display welcome banner
        System.out.println("                     SmartBuy");
        System.out.println();
        System.out.println("                   ----------------------                                 ");
        System.out.println("                   |                    |                                 ");
        System.out.println("                   |    Laptop Deals    |                                 ");
        System.out.println("                   |        at          |                                 ");
        System.out.println("                   |    Lightning Speed |                                 ");
        System.out.println("                   |                    |                                 ");
        System.out.println("                   ---------------------                                  ");
        System.out.println("                 /                     /                                  ");
        System.out.println("                /                     /                                   ");
        System.out.println("               /                     /                                    ");
        System.out.println("              /                     /                                     ");
        System.out.println("            ----------------------                                        ");
        System.out.println();

        // Main application loop for registration/login
        while (true) {
            System.out.println("             1. Register\n             2. Login\n             3. Exit");
            System.out.print("Choose an option: ");
            int choice = -1; // Initialize choice to an invalid value

            try {
                choice = scanner.nextInt(); // Read integer choice
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number (1, 2, or 3).");
            } finally {
                scanner.nextLine(); // Consume the rest of the line (including invalid input)
            }

            switch (choice) {
                case 1:
                    register(); // Handle user registration
                    break;
                case 2:
                    if (login()) { // If login is successful
                        startShopping(); // Start the shopping experience
                    }
                    break;
                case 3:
                    System.out.println("             Thank you for visiting SmartBuy!");
                    System.exit(0); // Exit the application
                    break;
                default:
                    if (choice != -1) { // Only show invalid choice if it wasn't an InputMismatchException
                        System.out.println("             Invalid choice. Please try again.");
                    }
            }
            System.out.println(); // Add a newline for better readability between prompts
        }
    }

    /**
     * Handles the user registration process.
     */
    private static void register() {
        if (userCount >= users.length) {
            System.out.println("             User limit reached. Cannot register more users.");
            return;
        }

        System.out.print("             Enter username: ");
        String username = scanner.nextLine();

        // Basic validation for empty username
        if (username.trim().isEmpty()) {
            System.out.println("             Username cannot be empty.");
            return;
        }

        // Check if username already exists
        for (int i = 0; i < userCount; i++) {
            if (users[i] != null && users[i].getUsername().equalsIgnoreCase(username)) {
                System.out.println("             Username already taken. Please choose a different one.");
                return;
            }
        }

        System.out.print("             Enter password: ");
        String password = scanner.nextLine();

        // Basic validation for password length
        if (password.length() < 4) { // Example: minimum 4 characters
            System.out.println("             Password must be at least 4 characters long.");
            return;
        }

        String mobileNumber;
        while (true) {
            System.out.print("             Enter 10-digit mobile number: ");
            mobileNumber = scanner.nextLine();

            // Validate mobile number format and length
            if (mobileNumber.length() == 10 && mobileNumber.matches("[0-9]+")) {
                break; // Valid mobile number
            } else {
                System.out.println("             Please enter a valid 10-digit mobile number (digits only).");
            }
        }

        users[userCount++] = new User(username, password, mobileNumber);
        System.out.println("             Registration successful!");
        System.out.println();
    }

    /**
     * Handles the user login process.
     * @return true if login is successful, false otherwise.
     */
    private static boolean login() {
        if (userCount == 0) {
            System.out.println("             No users registered. Please register first.");
            return false;
        }

        System.out.print("             Enter username: ");
        String username = scanner.nextLine();
        System.out.print("             Enter password: ");
        String password = scanner.nextLine();

        for (int i = 0; i < userCount; i++) { // Iterate only up to userCount
            User user = users[i];
            if (user != null && user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;
                System.out.println("             Login successful!");
                System.out.println();
                return true;
            }
        }

        System.out.println("             Invalid username or password.");
        System.out.println("             1. Forgot Password\n             2. Back to Main Menu");
        System.out.print("Choose an option: ");
        int choice = -1;

        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number (1 or 2).");
        } finally {
            scanner.nextLine(); // Consume newline
        }

        if (choice == 1) {
            forgotPassword();
        }
        return false; // Login failed or chose to go back
    }

    /**
     * Handles the forgot password functionality using OTP.
     */
    private static void forgotPassword() {
        System.out.print("             Enter your registered mobile number: ");
        String mobileNumber = scanner.nextLine();

        User userToReset = null;
        for (int i = 0; i < userCount; i++) { // Iterate only up to userCount
            if (users[i] != null && users[i].getMobileNumber().equals(mobileNumber)) {
                userToReset = users[i];
                break;
            }
        }

        if (userToReset != null) {
            String otp = OTPService.generateOTP();
            System.out.println("             OTP sent to mobile: " + otp); // In a real app, this would send SMS
            System.out.print("             Enter OTP: ");
            String enteredOTP = scanner.nextLine();

            if (otp.equals(enteredOTP)) {
                System.out.print("             Enter new password: ");
                String newPassword = scanner.nextLine();
                // Basic validation for new password length
                if (newPassword.length() < 4) {
                    System.out.println("             New password must be at least 4 characters long.");
                    return;
                }
                userToReset.setPassword(newPassword);
                System.out.println("             Password reset successfully! Please login with your new password.");
            } else {
                System.out.println("             Invalid OTP.");
            }
        } else {
            System.out.println("             Mobile number not registered.");
        }
        System.out.println();
    }

    /**
     * Starts the laptop shopping and comparison process.
     */
    private static void startShopping() {
        BankAccount account = new BankAccount(); // Create a new bank account for the session
        LaptopBrand[] brands = initializeLaptopBrands(); // Initialize available laptop brands and specs

        while (true) {
            System.out.println("             Select a brand:");
            for (int i = 0; i < brands.length; i++) {
                System.out.println("             " + (i + 1) + ". " + brands[i].getName());
            }
            System.out.println("             " + (brands.length + 1) + ". Exit Shopping");
            System.out.print("Choose an option: ");

            int brandChoice = -1;
            try {
                brandChoice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
                continue; // Restart the loop
            } finally {
                scanner.nextLine(); // Consume newline
            }

            if (brandChoice == brands.length + 1) {
                System.out.println("             Thank you for shopping with us!");
                break; // Exit shopping loop
            }

            if (brandChoice < 1 || brandChoice > brands.length) {
                System.out.println("             Invalid choice. Please select a valid brand number.");
                continue; // Restart the loop
            }

            // Get the selected brand
            LaptopBrand selectedBrand = brands[brandChoice - 1];
            System.out.println("             Specifications for " + selectedBrand.getName() + ":");

            LaptopSpecification[] specs = selectedBrand.getSpecifications();
            for (int i = 0; i < specs.length; i++) {
                System.out.println("             " + (i + 1) + ". " + specs[i].getSpecification());
            }

            System.out.print("             Select a specification: ");
            int specChoice = -1;
            try {
                specChoice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
                continue; // Restart the loop
            } finally {
                scanner.nextLine(); // Consume newline
            }

            if (specChoice < 1 || specChoice > specs.length) {
                System.out.println("             Invalid choice. Please select a valid specification number.");
                continue; // Restart the loop
            }

            // Get the chosen specification
            LaptopSpecification chosenSpec = specs[specChoice - 1];
            System.out.println("             Amazon price: $" + String.format("%.2f", chosenSpec.getPriceAmazon()));
            System.out.println("             Flipkart price: $" + String.format("%.2f", chosenSpec.getPriceFlipkart()));
            System.out.println("             Tata Neu price: $" + String.format("%.2f", chosenSpec.getPriceTataNeu()));
            System.out.println("             Cheapest price: $" + String.format("%.2f", chosenSpec.getCheapestPrice()) +
                                 " (from " + chosenSpec.getCheapestPlatform() + ")");

            System.out.println("             Do you want to purchase this laptop? (yes/no): ");
            String purchaseChoice = scanner.nextLine();

            if (purchaseChoice.equalsIgnoreCase("yes")) {
                double price = chosenSpec.getCheapestPrice();
                account.deductBalance(price); // Attempt to deduct balance

                // If balance was insufficient, deductBalance will have already prompted to add funds.
                // No need for a separate if (account.getBalance() < 0) block here.

                System.out.println("             Do you want to continue shopping? (yes/no): ");
                String continueShopping = scanner.nextLine();
                if (!continueShopping.equalsIgnoreCase("yes")) {
                    break; // Exit shopping loop
                }
            }
            System.out.println(); // Add a newline for better readability
        }
    }

    /**
     * Initializes and returns an array of LaptopBrand objects with their respective specifications.
     * This acts as our mock database for available laptops.
     * @return An array of LaptopBrand objects.
     */
    private static LaptopBrand[] initializeLaptopBrands() {
        // Dell Specifications
        LaptopSpecification dellSpec1 = new LaptopSpecification("Dell XPS 13, i7, 16GB RAM, 512GB SSD", 999.99, 950.00, 980.00);
        LaptopSpecification dellSpec2 = new LaptopSpecification("Dell Inspiron 15, i5, 8GB RAM, 256GB SSD", 799.99, 760.00, 780.00);
        LaptopSpecification dellSpec3 = new LaptopSpecification("Dell G5, i7, 16GB RAM, 1TB HDD", 1050.00, 1020.00, 1040.00);
        LaptopSpecification dellSpec4 = new LaptopSpecification("Dell Latitude 14, i5, 8GB RAM, 512GB SSD", 850.00, 830.00, 840.00);
        LaptopSpecification dellSpec5 = new LaptopSpecification("Dell Alienware, i9, 32GB RAM, 1TB SSD", 2200.00, 2100.00, 2150.00);
        LaptopBrand dell = new LaptopBrand("Dell", new LaptopSpecification[]{dellSpec1, dellSpec2, dellSpec3, dellSpec4, dellSpec5});

        // Lenovo Specifications
        LaptopSpecification lenovoSpec1 = new LaptopSpecification("Lenovo ThinkPad X1, i7, 16GB RAM, 512GB SSD", 1200.00, 1150.00, 1180.00);
        LaptopSpecification lenovoSpec2 = new LaptopSpecification("Lenovo IdeaPad, i5, 8GB RAM, 256GB SSD", 650.00, 620.00, 640.00);
        LaptopSpecification lenovoSpec3 = new LaptopSpecification("Lenovo Legion, i7, 16GB RAM, 1TB SSD", 1350.00, 1300.00, 1320.00);
        LaptopSpecification lenovoSpec4 = new LaptopSpecification("Lenovo Yoga 9i, i7, 16GB RAM, 512GB SSD", 1300.00, 1250.00, 1270.00);
        LaptopSpecification lenovoSpec5 = new LaptopSpecification("Lenovo ThinkPad P Series, i9, 32GB RAM, 1TB SSD", 2500.00, 2400.00, 2450.00);
        LaptopBrand lenovo = new LaptopBrand("Lenovo", new LaptopSpecification[]{lenovoSpec1, lenovoSpec2, lenovoSpec3, lenovoSpec4, lenovoSpec5});

        // Asus Specifications
        LaptopSpecification asusSpec1 = new LaptopSpecification("Asus ZenBook, i7, 16GB RAM, 512GB SSD", 1050.00, 1020.00, 1030.00);
        LaptopSpecification asusSpec2 = new LaptopSpecification("Asus VivoBook, i5, 8GB RAM, 256GB SSD", 550.00, 510.00, 530.00);
        LaptopSpecification asusSpec3 = new LaptopSpecification("Asus ROG Strix, i7, 16GB RAM, 1TB SSD", 1500.00, 1450.00, 1480.00);
        LaptopSpecification asusSpec4 = new LaptopSpecification("Asus TUF Gaming, i5, 8GB RAM, 512GB SSD", 800.00, 770.00, 790.00);
        LaptopSpecification asusSpec5 = new LaptopSpecification("Asus ExpertBook, i7, 16GB RAM, 512GB SSD", 1200.00, 1150.00, 1180.00);
        LaptopBrand asus = new LaptopBrand("Asus", new LaptopSpecification[]{asusSpec1, asusSpec2, asusSpec3, asusSpec4, asusSpec5});

        // HP Specifications
        LaptopSpecification hpSpec1 = new LaptopSpecification("HP Spectre x360, i7, 16GB RAM, 512GB SSD", 1300.00, 1250.00, 1280.00);
        LaptopSpecification hpSpec2 = new LaptopSpecification("HP Pavilion, i5, 8GB RAM, 256GB SSD", 700.00, 680.00, 690.00);
        LaptopSpecification hpSpec3 = new LaptopSpecification("HP Omen, i7, 16GB RAM, 1TB SSD", 1450.00, 1400.00, 1420.00);
        LaptopSpecification hpSpec4 = new LaptopSpecification("HP Elite Dragonfly, i5, 8GB RAM, 256GB SSD", 1600.00, 1550.00, 1570.00);
        LaptopSpecification hpSpec5 = new LaptopSpecification("HP Envy, i7, 16GB RAM, 512GB SSD", 1200.00, 1150.00, 1180.00);
        LaptopBrand hp = new LaptopBrand("HP", new LaptopSpecification[]{hpSpec1, hpSpec2, hpSpec3, hpSpec4, hpSpec5});

        return new LaptopBrand[]{dell, lenovo, asus, hp};
    }
    
}
