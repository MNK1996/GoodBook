import java.util.ArrayList;
import java.util.*;

public class BooksPlatform {
        private Map<String, Book> books;
        private Map<String, User> users;
        private User activeUser;
        private Scanner scanner;

        public BooksPlatform() {
            books = new HashMap<>();
            users = new HashMap<>();
            User user = new User("admin","admin123");
            users.put("admin",user);
            scanner = new Scanner(System.in);
        }

        public void addBook(String title, String contents) {
            // check if active user is admin (hard-coded as "admin" for now)
            if (activeUser != null && activeUser.getUsername().equals("admin")) {
                Book book = new Book(title, contents);
                books.put(title, book);
                System.out.println("Book added successfully.");
            } else {
                System.out.println("Error: only admin users can add books.");
            }
        }

        public void registerUser(String name, String password) {
            if (!users.containsKey(name)) {
                User user = new User(name, password);
                users.put(name, user);
                System.out.println("User registered successfully.");
            } else {
                System.out.println("Error: user already exists.");
            }
        }

        public void logIn(String loginUsername, String loginPassword) {
            if (users.containsKey(loginUsername)) {
                User user = users.get(loginUsername);
                if (user != null && user.getPassword().equals(loginPassword)) {
                    user.setLoggedIn(true);
                    activeUser = user;
                    System.out.println("Logged in as: " + activeUser.getUsername());
                } else {
                    System.out.println("Error: invalid username or password.");
                }
            } else {
                System.out.println("Error: user does not exist.");
            }
        }

        public void logOut() {
            if (activeUser != null && activeUser.isLoggedIn()) {
                activeUser.setLoggedIn(false);
                System.out.println("Logged out: " + activeUser.getUsername());
                activeUser = null;
            } else {
                System.out.println("Error: user must be logged in to log out.");
            }
        }


        public void purchaseBook(String bookTitle) {
            if (activeUser != null && activeUser.isLoggedIn()) {
                if (books.containsKey(bookTitle)) {
                    activeUser.getPurchasedBooks().put(bookTitle,0);
                    System.out.println("Book purchased: " + bookTitle);
                } else {
                    System.out.println("Error: invalid book title.");
                }
            } else {
                System.out.println("Error: user must be logged in to purchase a book.");
            }
        }

        public void startRead(String bookTitle) {
            if (activeUser != null && activeUser.isLoggedIn()) {
                if (books.containsKey(bookTitle)) {
                    if (activeUser.getPurchasedBooks().containsKey(bookTitle)) {
                        Book book = books.get(bookTitle);
                        Map<String, Integer> purchasedBooks = activeUser.getPurchasedBooks();
                        int currentPage = purchasedBooks.get(bookTitle);
                        System.out.println("Reading book: " + bookTitle);
                        System.out.println("Current page: " + currentPage);
                        System.out.println("Page content: " + book.getPageInfo().get(currentPage));
                        boolean finished = false;
                        while (!finished) {
                            System.out.println("Type 'next' to go to the next page, 'prev' to go to the previous page, 'goto' to jump to a specific page, 'rate' to rate the book, or 'exit' to finish reading.");
                            String input = scanner.nextLine();
                            switch (input) {
                                case "next":
                                    currentPage++;
                                    if (currentPage >= book.getPageInfo().size()) {
                                        System.out.println("Error: reached end of book.");
                                        currentPage = book.getPageInfo().size();
                                    }
                                    System.out.println("Current page: " + currentPage);
                                    System.out.println("Page content: " + book.getPageInfo().get(currentPage));
                                    purchasedBooks.put(bookTitle,currentPage);

                                    break;
                                case "prev":
                                    currentPage--;
                                    if (currentPage < 0) {
                                        System.out.println("Error: already at the beginning of the book.");
                                        currentPage = 0;
                                    }
                                    System.out.println("Current page: " + currentPage);
                                    System.out.println("Page content: " + book.getPageInfo().get(currentPage));
                                    purchasedBooks.put(bookTitle,currentPage);
                                    break;
                                case "rate":
                                    System.out.println("Enter a rating between 1 and 10:");
                                    double rating = scanner.nextDouble();
                                    scanner.nextLine(); // consume newline character
                                    if (rating >= 1 && rating <= 10) {
                                        Double newRating = book.getRating();
                                        int usersRated = book.getUsersRated();
                                        book.setRating((rating + newRating*usersRated)/(usersRated+1));
                                        book.setUsersRated(++usersRated);
                                        System.out.println("Rating set to: " + book.getRating());
                                    } else {
                                        System.out.println("Error: rating must be between 1 and 10.");
                                    }
                                    break;
                                case "goto":
                                    System.out.println("Enter a page number:");
                                    int pageNumber = scanner.nextInt();
                                    scanner.nextLine(); // consume newline character
                                    if (pageNumber >= 0 && pageNumber < book.getPageInfo().size()) {
                                        currentPage = pageNumber;
                                        System.out.println("Jumped to page: " + currentPage);
                                        System.out.println("Page content: " + book.getPageInfo().get(currentPage));
                                        purchasedBooks.put(bookTitle,currentPage);
                                    } else {
                                        System.out.println("Error: page number out of range.");
                                    }
                                    break;
                                case "exit":
                                    finished = true;
                                    break;
                                default:
                                    System.out.println("Error: invalid input.");
                                    break;
                            }
                        }
                        System.out.println("Finished reading.");
                    } else {
                        System.out.println("Book not purchased");
                    }
                } else {
                    System.out.println("Error: invalid book title.");
                }
            } else {
                System.out.println("Error: user must be logged in to read a book.");
            }
        }


        public void listMyBooks() {
            if (activeUser != null && activeUser.isLoggedIn()) {
                System.out.println("My Books: ");
                for (Map.Entry<String, Integer> myBooks : activeUser.getPurchasedBooks().entrySet()) {
                    System.out.println(myBooks.getKey());
                }
            } else {
                System.out.println("Error: user must be logged in to list their books.");
            }
        }

    public void run() {
        boolean finished = false;
        while (!finished) {
            System.out.println("Type 'addbook' to add a book, 'register' to register a new user, 'login' to log in, 'logout' to log out, 'purchase' to purchase a book, 'read' to start/resume reading a book, 'list' to list your books, or 'exit' to finish.");
            String input = scanner.nextLine();
            switch (input) {

                case "register":
                    System.out.println("Enter username:");
                    String username = scanner.nextLine();
                    System.out.println("Enter password:");
                    String password = scanner.nextLine();
                    registerUser(username, password);
                    break;

                case "login":
                    System.out.println("Enter username:");
                    String loginUsername = scanner.nextLine();
                    System.out.println("Enter password:");
                    String loginPassword = scanner.nextLine();
                    logIn(loginUsername, loginPassword);
                    break;

                case "addbook":
                    System.out.println("Enter book title:");
                    String title = scanner.nextLine();
                    System.out.println("Enter contents:");
                    String contents = scanner.nextLine();
                    scanner.nextLine();
                    addBook(title, contents);
                    break;

                case "logout":
                    logOut();
                    break;

                case "purchase":
                    System.out.println("Enter book title:");
                    String bookTitle = scanner.nextLine();
                    System.out.println("Enter book author:");
                    purchaseBook(bookTitle);
                    break;
                case "read":
                    //This is used to start/resume read
                    System.out.println("Enter book title:");
                    String titleOfTheBook = scanner.nextLine();
                    startRead(titleOfTheBook);
                    break;
                case "list":
                    listMyBooks();
                    break;
                case "exit":
                    finished = true;
                    break;
                default:
                    System.out.println("Error: invalid input.");
                    break;
            }
        }
    }

    }
