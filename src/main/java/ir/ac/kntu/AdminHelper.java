package ir.ac.kntu;

import ir.ac.kntu.accessories.*;

import java.util.ArrayList;

public class AdminHelper extends Admin{
    public static String hello = """
                    
                    
                    ╭───────────────────────╮
                    │         lords         │
                ┏━━━┷━━━━━━━━━━━━━━━━━━━━━━━┷━━━━┓
                                       
                        1. ME      2. MORE
                                          
                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                >>\s""";

    public AdminHelper(String name, String password) {
        super(name, password);
    }

    public static void createMonitor() {
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

    static void createGamingChair() {
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

    static void createGamePad() {
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
                createGamePad();
            }
        }
        System.out.print(AccessoriesSeller.WHAT_TYPE);
        userCommand = scanner.nextLine();
        ArrayList<DeviceType> deviceTypes = cGPHelper();
        System.out.print("price: ");
        int price = Integer.parseInt(scanner.nextLine());
        System.out.print("inventory: ");
        int inventory = Integer.parseInt(scanner.nextLine());

        GamePad gamePad = new GamePad(name,
                communicationPort, deviceTypes, price, inventory, DataManager.getSellers().get(0));
        DataManager.addGamePad(gamePad);
    }

    private static ArrayList<DeviceType> cGPHelper() {
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
}
