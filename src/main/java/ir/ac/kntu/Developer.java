package ir.ac.kntu;

import ir.ac.kntu.accessories.GamePad;
import ir.ac.kntu.accessories.GamingChair;
import ir.ac.kntu.accessories.Monitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Developer extends Main {
    private static final String MAIN_MENU = """
                
                
                ╭──────────────────────╮
                │     welcome back     │
            ┏━━━┷━━━━━━━━━━━━━━━━━━━━━━┷━━━━┓
                 
                    WDYWTG my friend?
              
                1. profile      2. inbox
                3. feedbacks    4. others
                 
                    5. create/manage
                        
                        6. store
                 
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            >>\s""";

    private static final String GAMES = """
                                
                
             ╭─────────╮
             │  works  │
            ━┷━━━━━━━━━┷━━━━━━━━━━━━━━━━━━━
                 
              """;

    //**********( fields )**********
    private String name;

    private String password;

    private String email;

    private String phoneNumber;

    private int id;

    private final ArrayList<Game> developingGames = new ArrayList<>();

    private final Map<String, Game> feedBacks = new HashMap<>();

    private final Map<Game, Developer> inbox = new HashMap<>();

    //TODO schedules


    //**********( manage Data )**********

    public Developer(String name, String password, String email, String phoneNumber) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.id = DataManager.getDevelopers().size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Game> getDevelopingGames() {
        return developingGames;
    }

    public Map<String, Game> getFeedBacks() {
        return feedBacks;
    }

    public Map<Game, Developer> getInbox() {
        return inbox;
    }

    public void addGame(Game game) {
        this.developingGames.add(game);
    }

    public void addFeedBack(String message, Game game) {
        this.feedBacks.put(message, game);
    }

    public void addInbox(Game game, Developer developer) {
        this.inbox.put(game, developer);
    }


    //********************( Panels )********************
    public void mainMenu() {
        System.out.print(MAIN_MENU);
        userCommand = scanner.nextLine();
        switch (userCommand) {
            case "1" -> this.profile();
            case "5" -> this.games();
            case "6" -> {
                DataManager.setPreviousPanel(Panels.DEVELOPER_MAIN_MENU);
                Utilities.store();
            }
            case "back" -> Identification.mainMenu();
            default -> {
                System.out.println("command not found...\n");
                this.mainMenu();
            }
        }
    }

    private void feedBacks() {
        int i = 0;
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        for (Map.Entry<String, Game> entry : feedBacks.entrySet()) {
            i++;
            System.out.print(i + ": ");
            System.out.println(entry.getKey() + ": " + entry.getValue().getName());
        }
        if (i > 0) {
            System.out.print("want to delete some? enter its number\n>> ");
            userCommand = scanner.nextLine();

            if (userCommand.equals("back")) {
                this.mainMenu();
            } else {
                int j = 0;
                for (Map.Entry<String, Game> entry : feedBacks.entrySet()) {
                    j++;
                    if (Integer.parseInt(userCommand) == j) {
                        this.feedBacks.remove(entry.getKey());
                        this.feedBacks();
                    }
                }
            }
        } else {
            System.out.println("there is no feedbacks...");
            this.mainMenu();
        }
    }

    private void profile() {
        DataManager.setCurrentPanel(Panels.PROFILE);
        System.out.println("\n\nyour ID: " + this.id);
        System.out.println("name: " + this.name);
        System.out.println("password: " + this.password);
        System.out.println("email: " + this.email);
        System.out.println("phone number: " + this.phoneNumber);
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

    private void changeInformation() {
        System.out.print("new name: ");
        String newUserName = scanner.nextLine();
        if (!Objects.equals(newUserName, this.name)) {
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

        this.name = newUserName;
        this.password = newPassword;
        this.phoneNumber = newPhoneNumber;
        this.email = newEmail;

        System.out.println("DONE.");
        this.profile();
    }

    public void games() {
        DataManager.setCurrentPanel(Panels.DEVELOPER_GAMES);
        printGames();

        userCommand = scanner.nextLine();
        if (userCommand.equals("c")) {
            create();
            this.games();
        } else if (userCommand.equals("back")) {
            this.mainMenu();
        } else {
            int gameNumber = Integer.parseInt(userCommand);
            this.developingGames.get(gameNumber).panel();
        }
    }

    public static void create() {
        System.out.print("name of the game: ");
        String name = scanner.nextLine();
        System.out.print("description: ");
        String description = scanner.nextLine();
        System.out.print("price: ");
        float price = Float.parseFloat(scanner.nextLine());
        System.out.print("genre: ");
        String genre = scanner.nextLine();

        DataManager.getGAMES().add(new Game(name, description, price, genre));
    }

    private void printGames() {
        System.out.print(GAMES);

        for (Game purchasedGame : this.developingGames) {
            System.out.println("[" + purchasedGame.getGameID() + "]: " +
                    purchasedGame.getName());
        }

        System.out.print("""
                                
                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                you can create by 'c' command.
                or enter the number that game.
                >>\s""");
    }
}
