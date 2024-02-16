package ir.ac.kntu.accessories;

import ir.ac.kntu.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GamePad extends Main {

    //---------------( fields )---------------
    private String id;

    private String name;

    private CommunicationPort communicationPort;

    private ArrayList<DeviceType> deviceTypes = new ArrayList<>();

    private int price;

    private int numberOf;

    private final Map<User, String> comments = new HashMap<>();

    private final ArrayList<User> usersPurchased = new ArrayList<>();

    private float rating;

    private final AccessoriesSeller seller;


    //-----------( managing data )-----------
    public GamePad(String name,
                   CommunicationPort communicationPort,
                   ArrayList<DeviceType> deviceTypes,
                   int price,
                   int numberOf,
                   AccessoriesSeller accessoriesSeller) {
        this.name = name;
        this.communicationPort = communicationPort;
        this.deviceTypes = deviceTypes;
        this.price = price;
        this.numberOf = numberOf;
        this.id = "GP" + DataManager.getGamePads().size();
        this.seller = accessoriesSeller;
    }

    public void addComment() {
        System.out.print(": ");
        String comment = scanner.nextLine();
        this.comments.put(DataManager.getCurrentUser(), comment);
        this.panel();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CommunicationPort getCommunicationPort() {
        return communicationPort;
    }

    public void setCommunicationPort(CommunicationPort communicationPort) {
        this.communicationPort = communicationPort;
    }

    public ArrayList<DeviceType> deviceTypes() {
        return deviceTypes;
    }

    public void setDeviceType(ArrayList<DeviceType> deviceTypes) {
        this.deviceTypes = deviceTypes;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumberOf() {
        return numberOf;
    }

    public void setNumberOf(int numberOf) {
        this.numberOf = numberOf;
    }

    public Map<User, String> getComments() {
        return comments;
    }

    public ArrayList<User> getUsersPurchased() {
        return usersPurchased;
    }

    public void addUsersPurchased(User user) {
        this.usersPurchased.add(user);
    }


    //--------------( panels )--------------

    private void printInfo() {
        System.out.println("╭╮\n││ " + this.name + "\n││");
        System.out.println("││ Communication Port: " + this.communicationPort);
        System.out.println("││ Devices Supports: " + this.deviceTypes);
        System.out.println("││ Seller: " + this.seller.getSellerName());
        System.out.println("││ Rating: " + this.rating);
        System.out.println("││ Price: " + this.price);
        System.out.println("││ Inventory: " + this.numberOf + "\n╰╯");
        System.out.println("--------------------------------");
        printComments();
        System.out.println("--------------------------------");
    }

    private void printComments() {
        for (Map.Entry<User, String> entry : comments.entrySet()) {
            System.out.println(entry.getKey().getUserName() + ": " +
                    entry.getValue());
        }
    }

    public void panel() {
        switch (DataManager.getCurrentType()) {
            case Admin -> adminPanel();
            case User -> userPanel();
            case Developer -> developerPanel();
            case AccessorySeller -> sellerPanel();
            default -> guestPanel();
        }
    }

    private void userPanel() {
        printInfo();

        if (DataManager.getCurrentUser().getGamePads().contains(this)) {
            userOptions();
        }
        if (this.numberOf > 0) {
            System.out.print("want to buy it? [y]\n>> ");
        } else {
            System.out.print("""
                    you can't buy because there is no more of this
                    ask the seller to increase the inventory.
                    press Enter to go back...""");
            scanner.nextLine();
            Utilities.store();
        }

        userCommand = scanner.nextLine();

        if (userCommand.equals("y") && this.numberOf > 0) {
            buy();
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
            DataManager.getCurrentUser().getGamePads().add(this);
            this.numberOf--;
            System.out.println("done.\n\n");
            this.userPanel();
        }
    }

    private void userOptions() {
        System.out.println("1. community\n2. add comment\n3. rate");
        if (this.numberOf > 0) {
            System.out.print("want to buy it again? [y]\n>> ");
        } else {
            System.out.print("""
                    you can't buy because there is no more of this
                    ask the seller to increase the inventory.
                    >>\s""");
        }

        userCommand = scanner.nextLine();

        if (userCommand.equals("1")) {
            community();
        } else if (userCommand.equals("2")) {
            addComment();
        } else if (userCommand.equals("3")) {
            rate();
        } else if (userCommand.equals("y") && this.numberOf > 0) {
            buy();
        } else if (userCommand.equals("back")) {
            Utilities.store();
        } else {
            System.out.println("command not found.\n");
            this.userPanel();
        }
    }

    private void community() {
        System.out.println();
        for (User user : this.usersPurchased) {
            System.out.println("* " +
                    user.getUserName() + " *");
        }
        System.out.println("\n");
        this.userPanel();
    }

    private void rate() {
        //TODO rate
    }

    private void developerPanel() {
        System.out.println("you are developer. you can not buy any accessory." +
                "\njust take a look at it :)");
        printInfo();
        System.out.print("\npress Enter to go back...");
        scanner.nextLine();
        Utilities.store();
    }

    private void sellerPanel() {
        printInfo();

        if (this.seller == DataManager.getCurrentSeller()) {
            System.out.print("""
                                    
                    1. delete the GamePad
                    2. edit the GamePad
                    >>\s""");
            userCommand = scanner.nextLine();

            switch (userCommand) {
                case "1" -> {
                    DataManager.getGamePads().remove(Integer.parseInt(this.id.
                            replace("GP", "")));
                    System.out.println("done\n\n");
                    Panels.retarder(DataManager.getCurrentPanel());
                }
                case "2" -> edit();
                case "back" -> Panels.retarder(DataManager.getCurrentPanel());
                default -> {
                    System.out.println("command not found.\n");
                    this.sellerPanel();
                }
            }
        } else {
            System.out.print("this is NOT yours.\npress Enter to go back...");
            scanner.nextLine();
            Utilities.store();
        }
    }

    private void adminPanel() {
        printInfo();

        System.out.print("""
                                
                1. delete the game
                2. edit the game
                >>\s""");
        userCommand = scanner.nextLine();

        switch (userCommand) {
            case "1" -> {
                DataManager.getGamePads().remove(Integer.parseInt(this.id.
                        replace("GP", "")));
                System.out.println("done\n\n");
                Panels.retarder(DataManager.getCurrentPanel());
            }
            case "2" -> edit();
            case "back" -> Utilities.store();
            default -> {
                System.out.println("command not found.\n");
                this.adminPanel();
            }
        }
    }

    private void guestPanel() {
        printInfo();

        System.out.print("press Enter to go back...");
        scanner.nextLine();
        Utilities.store();
    }

    private void edit() {
        editName();
        editConnection();
        editType();
        editPrice();

        addInventory();

        Utilities.store();
    }

    private void editName() {
        System.out.print("\nnew name: ");
        this.name = scanner.nextLine();
    }

    private void editConnection() {
        System.out.print(AccessoriesSeller.WHAT_CONNECTION);
        userCommand = scanner.nextLine();
        switch (userCommand) {
            case "1" -> this.communicationPort = CommunicationPort.WIRELESS;
            case "2" -> this.communicationPort = CommunicationPort.WIRED;
            default -> {
                System.out.println("not accepted.");
                this.edit();
            }
        }
    }

    private void editType() {
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
        this.deviceTypes = deviceTypes;
    }

    private void editPrice() {
        System.out.print("new price: ");
        this.price = Integer.parseInt(scanner.nextLine());
    }

    private void addInventory() {
        System.out.print("""

                how much you want to add to the inventory?
                (you can also decrease):\s""");
        this.numberOf += Integer.parseInt(scanner.nextLine());
    }
}
