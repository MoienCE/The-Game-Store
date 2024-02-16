package ir.ac.kntu;

import ir.ac.kntu.accessories.GamePad;
import ir.ac.kntu.accessories.GamingChair;
import ir.ac.kntu.accessories.Monitor;

import java.util.ArrayList;
import java.util.Objects;

public class User extends Main {

    private static final String USER_MAIN_MENU = """
                    
                    
                    ╭──────────────────────╮
                    │     welcome back     │
                ┏━━━┷━━━━━━━━━━━━━━━━━━━━━━┷━━━━┓
                     
                  Where do you want to go budy?
                  
                    1. friends    2. library
                    3. store      4. profile
                     
                           5. sign out
                     
                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                >>\s""";

    private static final String LIBRARY = """
                                    
                    
                 ╭─────────╮
                 │ library │
                ┏┷━━━━━━━━━┷━━━━━━━━━━━━━━━━━━┓
                     
                  """;

    private String userName;

    private String password;

    private String email;

    private String phoneNumber;

    private float wallet;

    private final int id;

    private final ArrayList<User> friends = new ArrayList<>();

    private final ArrayList<Game> purchasedGames = new ArrayList<>();

    private final ArrayList<User> friendRequests = new ArrayList<>();

    private final ArrayList<GamePad> gamePads = new ArrayList<>();

    private final ArrayList<GamingChair> gamingChairs = new ArrayList<>();

    private final ArrayList<Monitor> monitors = new ArrayList<>();

    public ArrayList<GamePad> getGamePads() {
        return gamePads;
    }

    public ArrayList<GamingChair> getGamingChairs() {
        return this.gamingChairs;
    }

    public ArrayList<Monitor> getMonitors() {
        return monitors;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void addFriend(User friend) {
        this.friends.add(friend);
    }

    public ArrayList<Game> getPurchasedGames() {
        return purchasedGames;
    }

    public ArrayList<User> getFriendRequests() {
        return friendRequests;
    }

    public void addFriendRequests(User theUser) {
        this.friendRequests.add(theUser);
    }

    public int getId() {
        return id;
    }


    public User(String userName, String password,
                String email, String phoneNumber) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.wallet = 0;
        this.id = DataManager.getUsers().size();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public float getWallet() {
        return wallet;
    }

    public void increaseWallet(int money) {
        this.wallet += money;
    }

    public void mainMenu() {
        DataManager.setCurrentPanel(Panels.USER_MAIN_MENU);
        System.out.print(USER_MAIN_MENU);
        userCommand = scanner.nextLine();
        switch (userCommand) {
            case "1" -> this.friends();
            case "2" -> this.library();
            case "3" -> {
                DataManager.setPreviousPanel(Panels.USER_MAIN_MENU);
                Utilities.store();
            }
            case "4" -> this.profile();
            case "5" -> Identification.mainMenu();
            case "back" -> Identification.mainMenu();
            default -> {
                System.out.println("command not found...\n");
                this.mainMenu();
            }
        }
    }

    void library() {
        printLibrary();
        userCommand = scanner.nextLine();
        switch (userCommand) {
            case "s" -> searchGame();
            case "f" -> filterGames();
            case "back" -> this.mainMenu();
            default -> {
                int gameNumber = Integer.parseInt(userCommand);
                DataManager.setPreviousPanel(Panels.USER_LIBRARY);
                this.purchasedGames.get(gameNumber).panel();
            }
        }
    }

    private void printLibrary() {
        System.out.print(LIBRARY);

        for (Game purchasedGame : this.purchasedGames) {
            System.out.println("[" + purchasedGame.getGameID() + "]: " +
                    purchasedGame.getName());
        }
        for (GamePad gamePad : this.gamePads) {
            System.out.println("[" + gamePad.getId() + "]: " +
                    gamePad.getName());
        }
        for (GamingChair gamingChair : this.gamingChairs) {
            System.out.println("[" + gamingChair.getId() + "]: " +
                    gamingChair.getName());
        }
        for (Monitor monitor : this.monitors) {
            System.out.println("[" + monitor.getId() + "]: " +
                    monitor.getName());
        }

        System.out.print("""
                
                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                Enter the number of that product.
                or you can search by the 's' command.
                or you can use filter by the 'f' command.
                >>\s""");
    }

    public void friends() {
        DataManager.setCurrentPanel(Panels.USER_FRIENDS);
        System.out.print("""
                    
                    ╭───────────────────────╮
                    │        friends        │
                ┏━━━┷━━━━━━━━━━━━━━━━━━━━━━━┷━━━━┓
                                
                     1. friends list
                     2. find friend
                     3. inbox
                                                                
                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                >>\s""");
        userCommand = scanner.nextLine();
        switch (userCommand) {
            case "1" -> this.friendsList();
            case "2" -> Admin.allUsers();
            case "3" -> this.inbox();
            case "4" -> this.profile();
            case "5" -> Identification.mainMenu();
            case "back" -> this.mainMenu();
            default -> {
                System.out.println("command not found...\n");
                this.friends();
            }
        }
    }

    private void inbox() {
        System.out.print("""
                                    
                 ╭─────────╮
                 │  inbox  │
                ┏┷━━━━━━━━━┷━━━━━━━━━━━━━━━━━━┓
                     
                """);
        for (int i = 0; i < this.friendRequests.size(); i++) {
            System.out.println("    [" + i + "] " +
                    this.friendRequests.get(i).getUserName());
        }
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.print(">> ");
        userCommand = scanner.nextLine();
        if (userCommand.equals("back")) {
            this.friends();
        }
        int userChoice = Integer.parseInt(userCommand);
        System.out.print("want to add " +
                this.friendRequests.get(userChoice).getUserName() +
                " to your friends? [y/n]\n>> ");
        userCommand = scanner.nextLine();
        switch (userCommand) {
            case "y" -> {
                this.addFriend(this.friendRequests.get(userChoice));
                this.getFriendRequests().get(userChoice).addFriend(this);
                this.getFriendRequests().remove(userChoice);
                System.out.println("Done.");
                this.friends();
            }
            case "n" -> {
                this.getFriendRequests().remove(userChoice);
                System.out.println("Done.");
                this.friends();
            }
            case "back" -> this.inbox();
            default -> {
                System.out.println("command not found.");
                this.inbox();
            }
        }
    }

    private void friendsList() {
        for (User friend : this.friends) {
            System.out.println("*" + friend.getUserName() + "*");
        }
        System.out.println("press Enter to go back...");
        scanner.nextLine();
        this.friends();
    }

    public void profile() {
        DataManager.setCurrentPanel(Panels.PROFILE);
        System.out.println("your ID: " + this.id);
        System.out.println("userName: " + this.userName);
        System.out.println("password: " + this.password);
        System.out.println("email: " + this.email);
        System.out.println("phone number: " + this.phoneNumber);
        System.out.println("jib-e mobarak: " + this.wallet);
        System.out.print("""
                
                want to change your information? [y]
                >>\s""");
        userCommand = scanner.nextLine();
        userCommand = userCommand.trim();

        if (userCommand.equals("y")) {
            changeInformation();
        } else if (userCommand.equals("back")) {
            this.mainMenu();
        } else {
            System.out.println("command not found.\n");
            Panels.retarder(DataManager.getCurrentPanel());
        }
    }

    public void changeInformation() {
        System.out.print("new username: ");
        String newUserName = scanner.nextLine();
        if (!Objects.equals(newUserName, this.userName)) {
            Identification.isUsernameValid(newUserName);
        }

        System.out.print("new password: ");
        String newPassword = scanner.nextLine();
        Identification.passwordCheck(newPassword);

        System.out.print("new email: ");
        String newEmail = scanner.nextLine();
        if (!Objects.equals(newEmail, this.email)) {
            Identification.emailIsValid(newEmail);
        }

        System.out.print("new phoneNumber: ");
        String newPhoneNumber = scanner.nextLine();
        if (!Objects.equals(newPhoneNumber, this.phoneNumber)) {
            Identification.phoneCheck(newPhoneNumber);
        }

        System.out.print("how much money you want to add to the wallet: ");
        this.wallet += scanner.nextInt();

        this.userName = newUserName;
        this.password = newPassword;
        this.phoneNumber = newPhoneNumber;
        this.email = newEmail;
        scanner.nextLine();

        System.out.println("DONE.");
        this.mainMenu();
    }

    private void searchGame() {
        System.out.print("""
                                
                1. Existential search
                2. search with start with
                >>\s""");

        userCommand = scanner.nextLine();

        switch (userCommand) {
            case "1" -> inclusivelySearch();
            case "2" -> startWithSearch();
            case "back" -> this.library();
            default -> {
                System.out.println("command not found.\n");
                searchGame();
            }
        }
    }

    private void startWithSearch() {
        int counter = 0;
        System.out.print(": ");
        userCommand = scanner.nextLine();

        for (int i = 0; i < this.purchasedGames.size(); i++) {
            if (this.purchasedGames.get(i).getName().startsWith(userCommand)) {
                System.out.println(i + ": " + this.purchasedGames.get(i).getName());
                counter++;
            }
        }
        if (counter == 0) {
            System.out.println("nothing found. try again...");
            startWithSearch();
        }
        System.out.print(">> ");
        userCommand = scanner.nextLine();
        if (userCommand.equals("back")) {
            this.library();
        } else {
            int gameNumber = Integer.parseInt(userCommand);
            DataManager.setPreviousPanel(Panels.USER_LIBRARY);
            this.purchasedGames.get(gameNumber).panel();
        }
    }

    private void inclusivelySearch() {
        int counter = 0;
        System.out.print(": ");
        userCommand = scanner.nextLine();

        for (int i = 0; i < this.purchasedGames.size(); i++) {
            if (this.purchasedGames.get(i).getName().contains(userCommand)) {
                System.out.println(i + ": " + this.purchasedGames.get(i).getName());
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
            this.library();
        } else {
            int gameNumber = Integer.parseInt(userCommand);
            DataManager.setPreviousPanel(Panels.USER_LIBRARY);
            this.purchasedGames.get(gameNumber).panel();
        }
    }

    private void filterGames() {
        int counter = 0;
        System.out.print("Price range from: ");
        int from = Integer.parseInt(scanner.nextLine());
        System.out.print("Price range to: ");
        int to = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < this.purchasedGames.size(); i++) {
            if ((this.purchasedGames.get(i).getPrice() >= from) &&
                    (this.purchasedGames.get(i).getPrice() < to)) {
                System.out.println(i + ": " + this.purchasedGames.get(i).getName());
                counter++;
            }
        }
        if (counter == 0) {
            System.out.println("nothing found. try again...");
            filterGames();
        }
        System.out.print(">> ");
        userCommand = scanner.nextLine();
        if (userCommand.equals("back")) {
            this.library();
        } else {
            int gameNumber = Integer.parseInt(userCommand);
            DataManager.setPreviousPanel(Panels.USER_LIBRARY);
            this.purchasedGames.get(gameNumber).panel();
        }
    }
}
