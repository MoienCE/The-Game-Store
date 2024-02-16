package ir.ac.kntu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Identification extends Main {

    private static final String MAIN_MENU = """
                
                
                ╭──────────────────────╮
                │ identification panel │
            ┏━━━┷━━━━━━━━━━━━━━━━━━━━━━┷━━━━┓

                 Select the access level:
                 1. Admin
                 2. User
                 3. Developer
                 4. Accessories Seller
                 5. guest
                
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            >>\s""";

    private static final String BE_MY_ADMIN_Y = """
                                
            there is no admin.
            do you like to be MY ADMIN?!!! [y]
            >>\s""";

    static final String PASSWORD_IS_NOT_STRONG = """
                                
            password is not strong.
            - At least one lowercase letter
            - At least one uppercase letter
            - At least one digit
            - At least one special character (@$!%*?&)
            - A minimum length of 8 characters
                                
            try again...
            """;

    private static final String CREATE_ADMIN_WARMING = """
            \u001B[31mremember,
            Admin has the highest level of access.
            If you forget the password,
            it is not possible to change it.
            Unless you clear the entire database!\u001B[0m
            """;

    private static final String USER_CREATED = """
            
            done.
            now sign in again...
            Yes, I know it's pointless,
            but "they" said that after creating an account,
            the user must go back to the sign-in page
            sorry :(
                                
            """;

    private static final String SIGN_IN_FAILED = """
                            
            the userName or password is wrong.
            try again...
            """;

    private static final String DUPLICATED_NAME = """
                                
            This is a duplicate name.
            try again...
            """;

    private static final String UNACCEPTED_EMAIL = """
                                
            email is unacceptable.
            try again...
            """;

    private static final String UNACCEPTED_PHONE_NUMBER = """
                                
            phone is unacceptable.
            try again...
            """;


    //____________________ panels ____________________
    public static void mainMenu() {
        DataManager.setCurrentPanel(Panels.IDENTIFICATION_MAIN_MENU);
        System.out.print(MAIN_MENU);
        userCommand = scanner.nextLine();

        switch (userCommand) {
            case "1" -> adminIdentification();
            case "2" -> userIdentification();
            case "3" -> developerIdentification();
            case "4" -> sellerIdentification();
            case "5" -> {
                DataManager.setCurrentType(Type.Guest);
                Guest.mainMenu();
            }
            case "back" -> {
                switch (DataManager.getCurrentType()) {
                    case Admin -> DataManager.getCurrentAdmin().mainMenu();
                    case User -> DataManager.getCurrentUser().mainMenu();
                    case AccessorySeller -> DataManager.getCurrentSeller().mainMenu();
                    case Developer -> DataManager.getCurrentDeveloper().mainMenu();
                    default -> Guest.mainMenu();
                }
            }
            default -> {
                System.out.println("command not found.\n");
                Panels.retarder(DataManager.getCurrentPanel());
            }
        }
    }

    public static void sellerIdentification() {
        DataManager.setCurrentPanel(Panels.SELLER_SIGN);
        System.out.print("""
                                
                sign in or sign up? [i/u]
                >>\s""");
        userCommand = scanner.nextLine();
        userCommand = userCommand.trim();

        if (userCommand.equals("i")) {
            sellerSignIn();
        } else if (userCommand.startsWith("u")) {
            sellerSignUp();
        } else if (userCommand.equals("back")) {
            mainMenu();
        } else {
            System.out.println("command not found.\n");
            sellerIdentification();
        }
    }

    public static void sellerSignUp() {
        DataManager.setCurrentPanel(Panels.SELLER_SIGN_UP);
        String sellerName;
        String password;
        String email;
        String phoneNumber;
        System.out.print("sellerName: ");
        sellerName = scanner.nextLine();
        isSellerNameValid(sellerName);
        System.out.print("password: ");
        password = scanner.nextLine();
        passwordCheck(password);
        System.out.print("email: ");
        email = scanner.nextLine();
        sellerEmailIsValid(email);
        System.out.print("phoneNumber: ");
        phoneNumber = scanner.nextLine();
        sellerPhoneCheck(phoneNumber);

        DataManager.addSeller(new AccessoriesSeller(sellerName, password, email, phoneNumber));
        System.out.print(USER_CREATED);
        sellerIdentification();
    }

    public static void sellerSignIn() {
        String sellerName;
        String password;
        System.out.print("your sellerName: ");
        sellerName = scanner.nextLine();
        System.out.print("your password: ");
        password = scanner.nextLine();

        for (int i = 0; i < DataManager.getSellers().size(); i++) {
            if (sellerName.equals(DataManager.getSellers().get(i).getSellerName())) {
                if (password.equals(DataManager.getSellers().get(i).getPassword())) {
                    DataManager.setCurrentType(Type.AccessorySeller);
                    DataManager.setCurrentSeller(DataManager.getSellers().get(i));
                    DataManager.getSellers().get(i).mainMenu();
                }
            }
        }
        System.out.print(SIGN_IN_FAILED);
        sellerSignIn();

    }


    public static void developerIdentification() {
        System.out.print("""
                                
                sign in or sign up? [i/u]
                >>\s""");

        userCommand = scanner.nextLine();

        if (userCommand.equals("i")) {
            developerSignIn();
        } else if (userCommand.startsWith("u")) {
            developerSignUp();
        } else if (userCommand.equals("back")) {
            mainMenu();
        } else {
            System.out.println("command not found.\n");
            developerIdentification();
        }
    }

    private static void developerSignIn() {
        String username;
        String password;
        System.out.print("your name: ");
        username = scanner.nextLine();
        System.out.print("your password: ");
        password = scanner.nextLine();

        for (int i = 0; i < DataManager.getDevelopers().size(); i++) {
            if (username.equals(DataManager.getDevelopers().get(i).getName())) {
                if (password.equals(DataManager.getDevelopers().get(i).getPassword())) {
                    DataManager.setCurrentType(Type.Developer);
                    DataManager.setCurrentDeveloper(DataManager.getDevelopers().get(i));
                    DataManager.getDevelopers().get(i).mainMenu();
                }
            }
        }
        System.out.print(SIGN_IN_FAILED);
        userSignIn();

    }

    public static void developerSignUp() {
        DataManager.setCurrentPanel(Panels.DEVELOPER_SIGN_UP);
        String userName;
        String password;
        String email;
        String phoneNumber;
        System.out.print("name: ");
        userName = scanner.nextLine();
        isUsernameValid(userName);
        System.out.print("password: ");
        password = scanner.nextLine();
        passwordCheck(password);
        System.out.print("email: ");
        email = scanner.nextLine();
        emailIsValid(email);
        System.out.print("phoneNumber: ");
        phoneNumber = scanner.nextLine();
        phoneCheck(phoneNumber);

        DataManager.addDeveloper(new Developer(userName, password, email, phoneNumber));
        System.out.print(USER_CREATED);
        developerIdentification();
    }

    static void adminIdentification() {
        if (!DataManager.isHaveAdmin()) {
            System.out.print(BE_MY_ADMIN_Y);
            userCommand = scanner.nextLine();
            userCommand = userCommand.trim();

            if (userCommand.equals("y")) {
                createAdmin();
            } else if (userCommand.equals("back")) {
                mainMenu();
            } else {
                System.out.println("command not found.\n");
                adminIdentification();
            }
        } else {
            if (DataManager.getCurrentType() == Type.Admin) {
                System.out.println("you ARE in admin level.");
                DataManager.getCurrentAdmin().mainMenu();
            } else {
                Admin.signIn();
            }
        }
    }

    static void userIdentification() {
        System.out.print("""
                                
                sign in or sign up? [i/u]
                >>\s""");
        userCommand = scanner.nextLine();

        if (userCommand.equals("i")) {
            userSignIn();
        } else if (userCommand.startsWith("u")) {
            userSignUp();
        } else if (userCommand.equals("back")) {
            mainMenu();
        } else {
            System.out.println("command not found.\n");
            userIdentification();
        }
    }

    public static void createAdmin() {
        DataManager.setCurrentPanel(Panels.CREATE_ADMIN);
        System.out.print(CREATE_ADMIN_WARMING);
        System.out.print("your password: ");
        String adminPassword = scanner.nextLine();
        passwordCheck(adminPassword);

        DataManager.setHaveAdmin(true);
        DataManager.setCurrentType(Type.Admin);
        DataManager.setCurrentAdmin(new Admin("│ FATHER │", adminPassword));
        DataManager.getAdmins().add(DataManager.getCurrentAdmin());
        DataManager.setCurrentAdmin(DataManager.getCurrentAdmin());
        DataManager.getCurrentAdmin().mainMenu();
    }

    static void userSignUp() {
        DataManager.setCurrentPanel(Panels.USER_SIGN_UP);
        String userName;
        String password;
        String email;
        String phoneNumber;
        System.out.print("username: ");
        userName = scanner.nextLine();
        isUsernameValid(userName);
        System.out.print("password: ");
        password = scanner.nextLine();
        passwordCheck(password);
        System.out.print("email: ");
        email = scanner.nextLine();
        emailIsValid(email);
        System.out.print("phoneNumber: ");
        phoneNumber = scanner.nextLine();
        phoneCheck(phoneNumber);

        DataManager.addUsers(new User(userName, password, email, phoneNumber));
        System.out.print(USER_CREATED);
        userIdentification();
    }

    static void userSignIn() {
        String username;
        String password;
        System.out.print("your userName: ");
        username = scanner.nextLine();
        System.out.print("your password: ");
        password = scanner.nextLine();

        for (int i = 0; i < DataManager.getUsers().size(); i++) {
            if (username.equals(DataManager.getUsers().get(i).getUserName())) {
                if (password.equals(DataManager.getUsers().get(i).getPassword())) {
                    DataManager.setCurrentType(Type.User);
                    DataManager.setCurrentUser(DataManager.getUsers().get(i));
                    DataManager.getUsers().get(i).mainMenu();
                }
            }
        }
        System.out.print(SIGN_IN_FAILED);
        developerSignIn();

    }


    //____________________ tools ____________________

    public static void passwordCheck(String password) {
        String strong = "(?=(.*[a-z]){3,})(?=(.*[A-Z]){2,})(?=(.*[0-9]){2,})(?=(.*)+).{8,}";
        Pattern pattern = Pattern.compile(strong);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.find()) {
            System.out.print(PASSWORD_IS_NOT_STRONG);
            Panels.retarder(DataManager.getCurrentPanel());
        }
    }

    public static void emailIsValid(String email) {
        String strong = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        Pattern pattern = Pattern.compile(strong);
        Matcher matcher = pattern.matcher(email);
        boolean emailIsUnique = true;
        for (int i = 0; i < DataManager.getUsers().size(); i++) {
            if (email.equals(DataManager.getUsers().get(i).getEmail())) {
                emailIsUnique = false;
                break;
            }
        }
        if (!matcher.find() || !emailIsUnique) {
            System.out.print(UNACCEPTED_EMAIL);
            Panels.retarder(DataManager.getCurrentPanel());
        }
    }

    public static void phoneCheck(String phoneNumber) {
        String strong = "[0-9]{11,}";
        Pattern pattern = Pattern.compile(strong);
        Matcher matcher = pattern.matcher(phoneNumber);
        boolean phoneIsUnique = true;
        for (int i = 0; i < DataManager.getUsers().size(); i++) {
            if (phoneNumber.equals(DataManager.getUsers().get(i).getPhoneNumber())) {
                phoneIsUnique = false;
                break;
            }
        }
        if (!matcher.find() || !phoneIsUnique) {
            System.out.print(UNACCEPTED_PHONE_NUMBER);
            Panels.retarder(DataManager.getCurrentPanel());
        }
    }

    public static void isUsernameValid(String username) {
        for (int i = 0; i < DataManager.getUsers().size(); i++) {
            if (username.equals(DataManager.getUsers().get(i).getUserName())) {
                System.out.print(DUPLICATED_NAME);
                Panels.retarder(DataManager.getCurrentPanel());
            }
        }
    }

    public static void isSellerNameValid(String sellerName) {
        for (int i = 0; i < DataManager.getSellers().size(); i++) {
            if (sellerName.equals(DataManager.getSellers().get(i).getSellerName())) {
                System.out.print(DUPLICATED_NAME);
                Panels.retarder(DataManager.getCurrentPanel());
            }
        }
    }

    public static void sellerEmailIsValid(String email) {
        String strong = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        Pattern pattern = Pattern.compile(strong);
        Matcher matcher = pattern.matcher(email);
        boolean emailIsUnique = true;
        for (int i = 0; i < DataManager.getSellers().size(); i++) {
            if (email.equals(DataManager.getSellers().get(i).getEmail())) {
                emailIsUnique = false;
                break;
            }
        }
        if (!matcher.find() || !emailIsUnique) {
            System.out.print(UNACCEPTED_EMAIL);
            Panels.retarder(DataManager.getCurrentPanel());
        }
    }

    public static void sellerPhoneCheck(String phoneNumber) {
        String strong = "[0-9]{11,}";
        Pattern pattern = Pattern.compile(strong);
        Matcher matcher = pattern.matcher(phoneNumber);
        boolean phoneIsUnique = true;
        for (int i = 0; i < DataManager.getSellers().size(); i++) {
            if (phoneNumber.equals(DataManager.getSellers().get(i).getPhoneNumber())) {
                phoneIsUnique = false;
                break;
            }
        }
        if (!matcher.find() || !phoneIsUnique) {
            System.out.print(UNACCEPTED_PHONE_NUMBER);
            Panels.retarder(DataManager.getCurrentPanel());
        }
    }
}
