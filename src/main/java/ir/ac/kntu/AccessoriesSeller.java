package ir.ac.kntu;

import ir.ac.kntu.accessories.*;

import java.util.ArrayList;
import java.util.Objects;

public class AccessoriesSeller extends Main {

    private static final String MAIN_MENU = """
                 
                 ╭──────────────────────╮
                 │ $ Accessory Seller $ │
            ┏━━━━┷━━━━━━━━━━━━━━━━━━━━━━┷━━━━━┓
                            
              How much is money really worth?
                 
                 1. Accessories
                 2. Profile
                 3. $ calculator $
                 4. $ Bestselling $
                 5. ↑ active users ↓
                            
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            >>\s""";

    private static final String BUSINESS_IS_BOOMING = """
                
                ╭─────────────────────╮
                │   $ Business is $   │
                │       BOOMING       │
            ┏━━━┷━━━━━━━━━━━━━━━━━━━━━┷━━━━┓
                            
                 1. create     2. mange
                        
                        3. STORE
                                                   
            ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
            >>\s""";

    private static final String WHAT_ACCESSORY = """
            what kind of accessory it is?
            1. GamePad
            2. GamingChair
            3. Monitor
            >>\s""";

    public static final String WHAT_CONNECTION = """
                    
            WIRELESS[1]
            WIRED[2]
            :\s""";

    public static final String WHAT_TYPE = """
                            
            ANDROID[1]
            IOS[2]
            WINDOWS[3]
            MAC[4]
            LINUX[5]
            (you can use multiple answers) :\s""";


    private String sellerName;

    private String password;

    private String email;

    private String phoneNumber;

    private final ArrayList<GamePad> gamePads = new ArrayList<>();

    private final ArrayList<GamingChair> gamingChairs = new ArrayList<>();

    private final ArrayList<Monitor> monitors = new ArrayList<>();

    public AccessoriesSeller(String sellerName,
                             String password,
                             String email,
                             String phoneNumber) {
        this.sellerName = sellerName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
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

    public ArrayList<GamePad> getGamePads() {
        return gamePads;
    }

    public void addGamePads(GamePad gamePad) {
        this.gamePads.add(gamePad);
    }

    public ArrayList<GamingChair> getGamingChairs() {
        return gamingChairs;
    }

    public void addGamingChairs(GamingChair gamingChair) {
        this.gamingChairs.add(gamingChair);
    }

    public ArrayList<Monitor> getMonitors() {
        return monitors;
    }

    public void addMonitors(Monitor monitor) {
        this.monitors.add(monitor);
    }

    public void mainMenu() {

        System.out.print(MAIN_MENU);
        userCommand = scanner.nextLine();

        switch (userCommand) {
            case "1" -> this.accessories();
            case "2" -> this.profile();
            case "3" -> calculator();
            case "4" -> bestSelling();
            case "5" -> {
                activeUsers();
                Guest.mainMenu();
            }
            case "back" -> Identification.mainMenu();
            default -> {
                System.out.println("command not found.\n");
                Panels.retarder(DataManager.getCurrentPanel());
            }
        }
    }

    private static void activeUsers() {
        //TODO activeUsers
    }

    private static void bestSelling() {
        //TODO bestSelling
    }

    private static void calculator() {
        //TODO calculator
    }

    public void accessories() {
        DataManager.setCurrentPanel(Panels.SELLER_ACCESSORY);
        System.out.print(BUSINESS_IS_BOOMING);
        userCommand = scanner.nextLine();

        switch (userCommand) {
            case "1" -> this.create();
            case "2" -> this.manage();
            case "3" -> {
                DataManager.setPreviousPanel(Panels.SELLER_ACCESSORY);
                Utilities.store();
            }
            case "back" -> this.mainMenu();
            default -> {
                System.out.println("command not found.\n");
                this.accessories();
            }
        }
    }

    public void manage() {
        DataManager.setCurrentPanel(Panels.SELLER_MANAGE);
        System.out.println("\n");
        for (GamePad gamePad : this.gamePads) {
            System.out.println("[" + gamePad.getId() + "]: " + gamePad.getName());
        }
        for (GamingChair gamingChair : this.gamingChairs) {
            System.out.println("[" + gamingChair.getId() + "]: " + gamingChair.getName());
        }
        for (Monitor monitor : this.monitors) {
            System.out.println("[" + monitor.getId() + "]: " + monitor.getName());
        }
        System.out.print("""
                                
                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                Enter the ID of that Accessory.
                >>\s""");
        userCommand = scanner.nextLine();
        if (userCommand.equals("back")) {
            this.accessories();
        } else {
            if (userCommand.contains("GP")) {
                DataManager.getGamePads().get(Integer.parseInt(userCommand.
                        replace("GP", ""))).panel();
            } else if (userCommand.contains("GC")) {
                DataManager.getGamingChairs().get(Integer.parseInt(userCommand.
                        replace("GC", ""))).panel();
            } else if (userCommand.contains("MA")) {
                DataManager.getMonitors().get(Integer.parseInt(userCommand.
                        replace("MA", ""))).panel();
            } else {
                System.out.println("ha?");
                this.manage();
            }
        }
    }


    private void create() {
        System.out.print(WHAT_ACCESSORY);
        userCommand = scanner.nextLine();
        switch (userCommand) {
            case "1" -> createGamePad();
            case "2" -> createGamingChair();
            case "3" -> createMonitor();
            case "back" -> this.accessories();
            default -> {
                System.out.println("command not found.");
                this.create();
            }
        }
        System.out.println("done.\n");
        this.create();
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
                this);
        DataManager.addMonitor(monitor);
        this.monitors.add(monitor);
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
                this);
        DataManager.addGamingChair(gamingChair);
        this.gamingChairs.add(gamingChair);
    }

    private void createGamePad() {
        System.out.print("\nname: ");
        String name = scanner.nextLine();
        System.out.print(WHAT_CONNECTION);
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
        System.out.print(WHAT_TYPE);
        userCommand = scanner.nextLine();
        ArrayList<DeviceType> deviceTypes = cGPHelper();
        System.out.print("price: ");
        int price = Integer.parseInt(scanner.nextLine());
        System.out.print("inventory: ");
        int inventory = Integer.parseInt(scanner.nextLine());
        GamePad gamePad = new GamePad(name, communicationPort, deviceTypes, price, inventory, this);
        DataManager.addGamePad(gamePad);
        this.gamePads.add(gamePad);
    }

    private ArrayList<DeviceType> cGPHelper() {
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

        return deviceTypes;
    }

    public void profile() {
        DataManager.setCurrentPanel(Panels.SELLER_PROFILE);
        System.out.println("sellerName: " + this.sellerName);
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
            mainMenu();
        }
    }

    private void changeInformation() {
        System.out.print("new sellerName: ");
        String sellerUserName = scanner.nextLine();
        if (!Objects.equals(sellerUserName, this.sellerName)) {
            Identification.isSellerNameValid(sellerUserName);
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

        this.password = newPassword;
        this.phoneNumber = newPhoneNumber;
        this.email = newEmail;
        scanner.nextLine();

        System.out.println("DONE.");
        this.mainMenu();
    }
}
