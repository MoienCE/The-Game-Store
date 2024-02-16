package ir.ac.kntu;

import ir.ac.kntu.accessories.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Admin extends Main {
    private String name;

    private String password;

    private final int id;

    private static int focusedUserID = -1;

    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
        this.id = DataManager.getAdmins().size();
    }

    public static void signIn() {
        String name;
        String password;
        System.out.print("name: ");
        name = scanner.nextLine();
        System.out.print("your password: ");
        password = scanner.nextLine();

        for (int i = 0; i < DataManager.getAdmins().size(); i++) {
            if (name.equals(DataManager.getAdmins().get(i).name)) {
                if (password.equals(DataManager.getAdmins().get(i).password)) {
                    DataManager.setCurrentType(Type.Admin);
                    DataManager.setCurrentAdmin(DataManager.getAdmins().get(i));
                    DataManager.getAdmins().get(i).mainMenu();
                }
            }
        }
        System.out.println("failed.");
        signIn();
    }

    public void mainMenu() {
        if (DataManager.getCurrentType() == Type.Admin) {
            mainMenuP();
        } else {
            System.out.println("you do not have access to administrator panel.");
        }
    }

    private void mainMenuP() {
        DataManager.setCurrentPanel(Panels.ADMIN_MAIN_MENU);
        System.out.print("""
                    
                    
                    ╭──────────────────────╮
                    │      hello lord      │
                ┏━━━┷━━━━━━━━━━━━━━━━━━━━━━┷━━━━┓
                     
                  Where do you want to go, sir?
                  
                     1. users    2. products
                     
                          3. ADMINS
                                          
                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                >>\s""");

        userCommand = scanner.nextLine();

        switch (userCommand) {
            case "1" -> userManagement();
            case "2" -> adminProductsPanel();
            case "3" -> this.admins();
            case "back" -> Identification.mainMenu();
            default -> {
                System.out.println("command not found...\n");
                Panels.retarder(DataManager.getCurrentPanel());
            }
        }
    }

    private void admins() {
        System.out.print("""
                    
                    
                    ╭───────────────────────╮
                    │         lords         │
                ┏━━━┷━━━━━━━━━━━━━━━━━━━━━━━┷━━━━┓
                                       
                        1. ME      2. MORE
                                          
                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                >>\s""");

        userCommand = scanner.nextLine();
        userCommand = userCommand.trim();

        switch (userCommand) {
            case "1" -> this.me();
            case "2" -> more();
            case "back" -> this.mainMenu();
            default -> {
                System.out.println("command not found...\n");
                DataManager.getCurrentAdmin().admins();
            }
        }
    }

    private static void more() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━");
        for (int i = 0; i < DataManager.getAdmins().size(); i++) {
            System.out.println("- " + DataManager.getAdmins().get(i).name);
        }
        System.out.print("━━━━━━━━━━━━━━━━━━━━━━━━━\ncreate MORE? [y]\n>> ");

        userCommand = scanner.nextLine();

        if (userCommand.equals("y")) {
            create();
        } else if (userCommand.equals("back")) {
            DataManager.getCurrentAdmin().admins();
        } else {
            System.out.println("command not found...\n");
            Admin.more();
        }

    }

    private static void create() {
        System.out.print("name: ");
        String name = scanner.nextLine();
        if (nameIsValid(name)) {
            System.out.println("invalid name.");
            Admin.create();
        }
        System.out.print("new password: ");
        String password = scanner.nextLine();
        String strong = "(?=(.*[a-z]){3,})(?=(.*[A-Z]){2,})(?=(.*[0-9]){2,})(?=(.*)+).{8,}";
        Pattern pattern = Pattern.compile(strong);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.find()) {
            System.out.print(Identification.PASSWORD_IS_NOT_STRONG);
            Admin.create();
        }

        DataManager.setCurrentType(Type.Admin);
        DataManager.setCurrentAdmin(new Admin(name, password));
        DataManager.getAdmins().add(DataManager.getCurrentAdmin());
        DataManager.setCurrentAdmin(DataManager.getCurrentAdmin());
        DataManager.getCurrentAdmin().mainMenu();
    }

    private void me() {
        System.out.println("you are: " + this.name);
        System.out.println("your password: **************");
        System.out.println("delete yourself? [d]" +
                "\nedit yourself? [e]");
        System.out.print(">> ");

        userCommand = scanner.nextLine();
        switch (userCommand) {
            case "d" -> {
                System.out.print("Enter your password to continue: ");
                if (scanner.nextLine().equals(this.password)) {
                    this.remove();
                } else {
                    System.out.println("wrong password.\n");
                    this.me();
                }
            }
            case "e" -> {
                System.out.print("Enter your password to continue: ");
                if (scanner.nextLine().equals(this.password)) {
                    this.edit();
                } else {
                    System.out.println("wrong password.\n");
                    this.me();
                }
            }
            case "back" -> this.admins();
            default -> {
                System.out.println("command not found...\n");
                this.me();
            }
        }
    }

    private void remove() {
        DataManager.getAdmins().remove(this.id);
        DataManager.setCurrentAdmin(null);
        DataManager.setCurrentType(Type.Guest);
        if (DataManager.getAdmins().size() == 0) {
            DataManager.setHaveAdmin(false);
        }
        System.out.println("☺ good bye ☺");
        Identification.mainMenu();
    }

    private void edit() {
        System.out.print("new name: ");
        String name = scanner.nextLine();
        if ((nameIsValid(name) && !name.equals(this.name))) {
            System.out.println("invalid name.\n");
            this.edit();
        }
        System.out.print("new password: ");
        String password = scanner.nextLine();
        String strong = "(?=(.*[a-z]){3,})(?=(.*[A-Z]){2,})(?=(.*[0-9]){2,})(?=(.*)+).{8,}";
        Pattern pattern = Pattern.compile(strong);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.find()) {
            System.out.print(Identification.PASSWORD_IS_NOT_STRONG);
            this.edit();
        }
        this.name = name;
        this.password = password;

        System.out.println("\n");
        this.me();
    }

    public static boolean nameIsValid(String name) {
        for (int i = 0; i < DataManager.getAdmins().size(); i++) {
            if (DataManager.getAdmins().get(i).name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void adminProductsPanel() {
        DataManager.setCurrentPanel(Panels.ADMIN_PRODUCTS_PANEL);
        System.out.print("""
                    
                    
                    ╭──────────────────────╮
                    │     A_PRODUCTS_P     │
                ┏━━━┷━━━━━━━━━━━━━━━━━━━━━━┷━━━━┓
                  
                     1. create    2. manage
                     
                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                >>\s""");

        userCommand = scanner.nextLine();
        userCommand = userCommand.trim();

        switch (userCommand) {
            case "1" -> this.createProduct();
            case "2" -> {
                DataManager.setPreviousPanel(Panels.ADMIN_PRODUCTS_PANEL);
                Utilities.store();
            }
            case "back" -> this.mainMenu();
            default -> {
                System.out.println("command not found...\n");
                Panels.retarder(DataManager.getCurrentPanel());
            }
        }
    }


    public static void userManagement() {
        DataManager.setCurrentPanel(Panels.USER_MANAGEMENT);
        System.out.print("""
                    
                    
                    ╭─────────────────────╮
                    │        users        │
                ┏━━━┷━━━━━━━━━━━━━━━━━━━━━┷━━━━┓
                  
                     1. create   2. manage
                     
                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                >>\s""");
        userCommand = scanner.nextLine();
        userCommand = userCommand.trim();

        switch (userCommand) {
            case "1" -> createUser();
            case "2" -> allUsers();
            case "3" -> Identification.mainMenu();
            case "back" -> DataManager.getCurrentAdmin().mainMenu();
            default -> {
                System.out.println("command not found...\n");
                Panels.retarder(DataManager.getCurrentPanel());
            }
        }
    }

    public static void allUsers() {
        DataManager.setCurrentPanel(Panels.ALL_USERS);
        System.out.print("""
                                    
                    
                 ╭─────────╮
                 │  Users  │
                ┏┷━━━━━━━━━┷━━━━━━━━━━━━━━━━━━━━━┓
                     
                  """);
        for (int i = 0; i < DataManager.getUsers().size(); i++) {
            if (DataManager.getCurrentUser() == DataManager.getUsers().get(i) &&
                    DataManager.getCurrentType() == Type.User) {
                continue;
            }
            System.out.println("  [" + i + "] " +
                    DataManager.getUsers().get(i).getUserName());
        }
        System.out.print("""
                                
                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                Enter the number of that game.
                or you can search by the 's' command.
                >>\s""");
        userCommand = scanner.nextLine();
        if (userCommand.equals("s")) {
            searchUser();
        } else if (userCommand.equals("back")) {
            switch (DataManager.getCurrentType()) {
                case User -> DataManager.getCurrentUser().friends();
                case Admin -> userManagement();
                default -> System.out.println("visit Admin. i am here");
            }
        } else {
            focusedUserID = Integer.parseInt(userCommand);
            userProfile(DataManager.getUsers().get(focusedUserID));
        }
    }

    public static void searchUser() {
        DataManager.setCurrentPanel(Panels.SEARCH_USER);
        System.out.print("""
                                
                1. Existential search
                2. search with start with
                >>\s""");

        userCommand = scanner.nextLine();

        switch (userCommand) {
            case "1" -> inclusivelySearch();
            case "2" -> startWithSearch();
            case "back" -> allUsers();
            default -> {
                System.out.println("command not found.\n");
                searchUser();
            }
        }
    }

    static void startWithSearch() {
        int counter = 0;
        System.out.print(": ");
        userCommand = scanner.nextLine();

        for (int i = 0; i < DataManager.getUsers().size(); i++) {
            if ((DataManager.getUsers().get(i).getUserName().startsWith(userCommand) ||
                    DataManager.getUsers().get(i).getPhoneNumber().startsWith(userCommand) ||
                    DataManager.getUsers().get(i).getEmail().startsWith(userCommand)) &&
                    (DataManager.getCurrentType() == Type.User)) {
                System.out.println("[" + i + "] " +
                        DataManager.getUsers().get(i).getUserName());
                counter++;
            }
        }
        if (counter == 0) {
            System.out.println("nothing found. try again...");
            inclusivelySearch();
        }
        System.out.print(">> ");
        userCommand = scanner.nextLine();
        if (userCommand.equals("back")) {
            allUsers();
        } else {
            focusedUserID = Integer.parseInt(userCommand);
            userProfile(DataManager.getUsers().get(focusedUserID));
        }
    }

    public static void inclusivelySearch() {
        int counter = 0;
        System.out.print(": ");
        userCommand = scanner.nextLine();

        for (int i = 0; i < DataManager.getUsers().size(); i++) {
            if (DataManager.getUsers().get(i).getUserName().contains(userCommand) ||
                    DataManager.getUsers().get(i).getPhoneNumber().contains(userCommand) ||
                    DataManager.getUsers().get(i).getEmail().contains(userCommand)) {
                System.out.println("[" + i + "] " +
                        DataManager.getUsers().get(i).getUserName());
                counter++;
            }
        }
        if (counter == 0) {
            System.out.println("nothing found. try again...");
            inclusivelySearch();
        }
        System.out.print(">> ");
        userCommand = scanner.nextLine();
        if (userCommand.equals("back")) {
            allUsers();
        } else {
            focusedUserID = Integer.parseInt(userCommand);
            userProfile(DataManager.getUsers().get(focusedUserID));
        }
    }

    public static void createUser() {
        DataManager.setCurrentPanel(Panels.ADMIN_USER_CREATION);
        String userName;
        String password;
        String email;
        String phoneNumber;
        System.out.print("username: ");
        userName = scanner.nextLine();
        Identification.isUsernameValid(userName);
        System.out.print("password: ");
        password = scanner.nextLine();
        Identification.passwordCheck(password);
        System.out.print("email: ");
        email = scanner.nextLine();
        Identification.emailIsValid(email);
        System.out.print("phoneNumber: ");
        phoneNumber = scanner.nextLine();
        Identification.phoneCheck(phoneNumber);

        DataManager.addUsers(new User(userName, password, email, phoneNumber));
        System.out.println("Done.");
        userManagement();
    }

    private void createProduct() {
        System.out.print("""
                what is this?
                1. GamePad
                2. GamingChair
                3. Monitor
                4. GAME
                >>\s""");
        userCommand = scanner.nextLine();
        if (userCommand.equals("1")) {
            createGamePad();
        } else if (userCommand.equals("2")) {
            createGamingChair();
        } else if (userCommand.equals("3")) {
            createMonitor();
        } else if (userCommand.equals("4")) {
            Developer.create();
        } else if (userCommand.equals("back")) {
            this.adminProductsPanel();
        } else {
            System.out.println("command not found.");
            this.createProduct();
        }
        System.out.println("done.\n");
        this.createProduct();
    }

    private void createGamePad() {
        System.out.print("\nname: ");
        String name = scanner.nextLine();
        System.out.print(AccessoriesSeller.WHAT_CONNECTION);
        userCommand = scanner.nextLine();
        CommunicationPort communicationPort = null;
        switch (userCommand) {
            case "1" -> communicationPort = CommunicationPort.WIRELESS;
            case "2" -> communicationPort = CommunicationPort.WIRED;
            default -> {
                System.out.println("not accepted.");
                this.createGamePad();
            }
        }
        System.out.print(AccessoriesSeller.WHAT_TYPE);
        userCommand = scanner.nextLine();
        ArrayList<DeviceType> deviceTypes = new ArrayList<>();
        if (userCommand.contains("1")) {
            deviceTypes.add(DeviceType.ANDROID);
        }
        if (userCommand.contains("2")) {
            deviceTypes.add(DeviceType.IOS);
        }
        if (userCommand.contains("3")) {
            deviceTypes.add(DeviceType.WINDOWS);
        }
        if (userCommand.contains("4")) {
            deviceTypes.add(DeviceType.MAC);
        }
        if (userCommand.contains("5")) {
            deviceTypes.add(DeviceType.LINUX);
        }

        System.out.print("price: ");
        int price = Integer.parseInt(scanner.nextLine());
        System.out.print("inventory: ");
        int inventory = Integer.parseInt(scanner.nextLine());

        GamePad gamePad = new GamePad(name,
                communicationPort,
                deviceTypes,
                price,
                inventory,
                DataManager.getSellers().get(0));
        DataManager.addGamePad(gamePad);
    }

    private void createGamingChair() {
        System.out.print("name: ");
        String name = scanner.nextLine();
        System.out.print("height (cm): ");
        int height = Integer.parseInt(scanner.nextLine());
        System.out.print("color: ");
        String color = scanner.nextLine();
        System.out.print("price: ");
        int price = Integer.parseInt(scanner.nextLine());
        System.out.print("inventory: ");
        int inventory = Integer.parseInt(scanner.nextLine());
        GamingChair gamingChair = new GamingChair(name,
                height,
                price,
                color,
                inventory,
                DataManager.getSellers().get(0));
        DataManager.addGamingChair(gamingChair);
    }

    private void createMonitor() {
        System.out.print("name: ");
        String name = scanner.nextLine();
        System.out.print("refreshRate: ");
        int refreshRate = Integer.parseInt(scanner.nextLine());
        System.out.print("size (inch): ");
        int size = Integer.parseInt(scanner.nextLine());
        System.out.print("responseTime: ");
        int responseTime = Integer.parseInt(scanner.nextLine());
        System.out.print("price: ");
        int price = Integer.parseInt(scanner.nextLine());
        System.out.print("inventory: ");
        int inventory = Integer.parseInt(scanner.nextLine());
        Monitor monitor = new Monitor(name,
                refreshRate,
                size,
                responseTime,
                price,
                inventory,
                DataManager.getSellers().get(0));
        DataManager.addMonitor(monitor);
    }

    private static void userProfile(User theUser) {
        switch (DataManager.getCurrentType()) {
            case User -> userUserProfile(theUser);
            case Admin -> adminUserProfile(theUser);
            default -> System.out.println("no");
        }
    }

    private static void userUserProfile(User theUser) {
        System.out.println("userName: " + theUser.getUserName());
        System.out.println("purchasedGames{");
        for (int i = 0; i < theUser.getPurchasedGames().size(); i++) {
            System.out.println("    " + theUser.getPurchasedGames().get(i).getName());
        }
        System.out.println("}");
        if (DataManager.getCurrentUser().getFriends().contains(theUser)) {
            System.out.println(theUser.getUserName() + " is your friend!");
            allUsers();
        }
        System.out.print("\nwant to send a friend request? [y]\n>> ");
        userCommand = scanner.nextLine();
        switch (userCommand) {
            case "y" -> {
                theUser.addFriendRequests(DataManager.getCurrentUser());
                System.out.println("Done.");
                DataManager.getCurrentUser().friends();
            }
            case "back" -> allUsers();
            default -> {
                System.out.println("command not found.\n");
                userUserProfile(theUser);
            }
        }
    }

    private static void adminUserProfile(User theUser) {
        System.out.println("userName: " + theUser.getUserName());
        System.out.println("userPassword: " + theUser.getPassword());
        System.out.println("userEmail: " + theUser.getEmail());
        System.out.println("userPhoneNumber: " + theUser.getPhoneNumber());
        System.out.println("userJib-e mobarak: " + theUser.getWallet());
        System.out.println("purchasedGames{");
        for (int i = 0; i < theUser.getPurchasedGames().size(); i++) {
            System.out.println("    " + theUser.getPurchasedGames().get(i).getName());
        }
        System.out.println("}");
        //TODO FRIENDS

        System.out.print("""
                                
                1. delete the user
                2. edit the user
                >>\s""");
        userCommand = scanner.nextLine();
        switch (userCommand) {
            case "1" -> {
                DataManager.getUsers().remove(theUser.getId());
                allUsers();
            }
            case "2" -> editUser();
            case "back" -> allUsers();
            default -> {
                System.out.println("command not found.\n");
                adminUserProfile(theUser);
            }
        }
    }

    public static void editUser() {
        DataManager.setCurrentPanel(Panels.ADMIN_EDITING_USER);
        System.out.print("new username: ");
        String newUserName = scanner.nextLine();
        if (!newUserName.equals(DataManager.getUsers().get(focusedUserID).getUserName())) {
            Identification.isUsernameValid(newUserName);
        }
        System.out.print("new password: ");
        String newPassword = scanner.nextLine();
        Identification.passwordCheck(newPassword);

        System.out.print("new email: ");
        String newEmail = scanner.nextLine();
        if (!newEmail.equals(DataManager.getUsers().get(focusedUserID).getEmail())) {
            Identification.emailIsValid(newEmail);
        }

        System.out.print("new phoneNumber: ");
        String newPhoneNumber = scanner.nextLine();
        if (!newPhoneNumber.equals(DataManager.getUsers().get(focusedUserID).getPhoneNumber())) {
            Identification.phoneCheck(newPhoneNumber);
        }

        System.out.print("how much money you want to add to the wallet: ");
        DataManager.getUsers().get(focusedUserID).
                increaseWallet(Integer.parseInt(scanner.nextLine()));

        DataManager.getUsers().get(focusedUserID).setUserName(newUserName);
        DataManager.getUsers().get(focusedUserID).setPassword(newPassword);
        DataManager.getUsers().get(focusedUserID).setPhoneNumber(newPhoneNumber);
        DataManager.getUsers().get(focusedUserID).setEmail(newEmail);
        System.out.println("DONE.");
        userProfile(DataManager.getUsers().get(focusedUserID));
    }
}
