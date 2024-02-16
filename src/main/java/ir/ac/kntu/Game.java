package ir.ac.kntu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Game extends Main {

    //---------------( fields )---------------
    private String name;

    private String description;

    private final float rating;

    private final int numberOfRates = 0;

    private float price;

    private final ArrayList<User> usersPurchased = new ArrayList<>();

    private String genre;

    private final int gameID;

    private final Map<User, String> comments = new HashMap<>();

    private final ArrayList<Developer> developers = new ArrayList<>();

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getGameID() {
        return gameID;
    }

    public Game(String name, String description, float price, String genre) {
        this.name = name;
        this.description = description;
        this.rating = 0;
        this.price = price;
        this.genre = genre;
        this.gameID = DataManager.getGAMES().size();
        if (DataManager.getCurrentType() == Type.Developer) {
            this.developers.add(DataManager.getCurrentDeveloper());
            DataManager.getCurrentDeveloper().getDevelopingGames().add(this);
        }
    }


    public void panel() {
        switch (DataManager.getCurrentType()) {
            case Admin -> adminPanel();
            case User -> userPanel();
            case Developer -> developerPanel();
            default -> guestPanel();
        }
    }

    private void developerPanel() {
        printInfo();
        if (this.developers.contains(DataManager.getCurrentDeveloper())) {
            System.out.print("""
                                
                1. delete the game
                2. edit the game
                >>\s""");
            userCommand = scanner.nextLine();

            switch (userCommand) {
                case "1" -> {
                    DataManager.getGAMES().remove(this.gameID);
                    System.out.println("done\n\n");
                    Panels.retarder(DataManager.getCurrentPanel());
                }
                case "2" -> {
                    editGame();
                    developerPanel();
                }
                case "back" -> Panels.retarder(DataManager.getCurrentPanel());
                default -> {
                    System.out.println("command not found.\n");
                    adminPanel();
                }
            }
        }
        System.out.print("you are not the admin of this.\npress Enter to go back...");
        scanner.nextLine();
        Utilities.store();
    }

    private void printInfo() {
        System.out.println("╭╮\n││ " + this.name + "\n││");
        System.out.println("││ Description: " + this.description);
        System.out.println("││ Genre: " + this.genre);
        System.out.println("││ developers: " + this.developers.size());
        System.out.println("││ Rating: " + this.rating);
        System.out.println("││ Price: " + this.price + "\n╰╯");
        System.out.println("--------------------------------");
        printComments();
        System.out.println("--------------------------------");
    }

    private void guestPanel() {
        printInfo();

        System.out.print("press Enter to go back...");

        scanner.nextLine();
        Utilities.store();
    }


    private void adminPanel() {
        printInfo();

        System.out.print("""
                                
                1. delete the game
                2. edit the game
                3. report
                >>\s""");
        userCommand = scanner.nextLine();

        switch (userCommand) {
            case "1" -> {
                DataManager.getGAMES().remove(this.gameID);
                System.out.println("done\n\n");
                DataManager.getCurrentAdmin().adminProductsPanel();
            }
            case "2" -> {
                editGame();
                adminPanel();
            }
            case "3" -> {
                this.report();
                this.adminPanel();
            }
            case "back" -> Utilities.store();
            default -> {
                System.out.println("command not found.\n");
                adminPanel();
            }
        }
    }

    private void userPanel() {
        printInfo();

        if (DataManager.getCurrentUser().getPurchasedGames().contains(this)) {
            userOptions();
        }
        System.out.print("want to buy it? [y]\n>> ");
        userCommand = scanner.nextLine();
        if (userCommand.equals("y")) {
            this.buy();
        } else if (userCommand.equals("back")) {
            Utilities.store();
        } else {
            System.out.println("command not found.\n");
            this.userPanel();
        }
    }

    private void buy() {
        if (DataManager.getCurrentUser().getWallet() < this.price) {
            System.out.println("""
                    you can not by this!
                    OH WRETCH.
                    Go increase your money and come back later.
                    """);
            this.userPanel();
        } else {
            this.usersPurchased.add(DataManager.getCurrentUser());
            DataManager.getCurrentUser().getPurchasedGames().add(this);
            System.out.println("done.\n\n");
            this.userPanel();
        }
    }

    private void userOptions() {
        System.out.print("1. community\n2. add comment\n3. rate\n4. report\n>> ");
        userCommand = scanner.nextLine();

        switch (userCommand) {
            case "1" -> community();
            case "2" -> addComment();
            case "3" -> rate();
            case "4" -> {
                this.report();
                this.userPanel();
            }
            case "back" -> Panels.retarder(DataManager.getPreviousPanel());
            default -> {
                System.out.println("command not found.\n");
                adminPanel();
            }
        }
    }

    private void rate() {
        System.out.print("write a number between 0 and 100\n: ");
        int userRate = Integer.parseInt(scanner.nextLine());
        if (!(userRate >= 0 && userRate <= 100)) {
            System.out.println("out of range...");
            this.rate();
        } else {
            System.out.println("to be completed");
        }
    }

    private void addComment() {
        System.out.print(": ");
        String comment = scanner.nextLine();
        this.comments.put(DataManager.getCurrentUser(), comment);

        Panels.retarder(DataManager.getPreviousPanel());
    }

    private void community() {
        for (User user : this.usersPurchased) {
            System.out.println("* " +
                    user.getUserName() + " *");
        }
        this.userPanel();
    }

    private void printComments() {
        for (Map.Entry<User, String> entry : comments.entrySet()) {
            System.out.println(entry.getKey().getUserName() + ": " + entry.getValue());
        }
    }

    private void editGame() {
        System.out.print("new Name: ");
        String newName = scanner.nextLine();
        System.out.print("new Description: ");
        String newDescription = scanner.nextLine();
        System.out.print("new Price: ");
        float newPrice = Float.parseFloat(scanner.nextLine());
        System.out.print("new Genre: ");
        String newGenre = scanner.nextLine();

        this.name = newName;
        this.price = newPrice;
        this.description = newDescription;
        this.genre = newGenre;

        System.out.println("done.\n\n");
    }

    private void report() {
        System.out.print("your message: ");
        String message = scanner.nextLine();

        if (this.developers.size() > 0) {
            for (Developer developer : this.developers) {
                developer.addFeedBack(message, this);
            }
        }
        System.out.println("done.\n");
    }
}
