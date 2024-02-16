package ir.ac.kntu;

public class Utilities extends Main {
    private static final String GAMES = """
                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                ♦ Games ♦
                ---------""";

    private static int from = 0;

    private static int to = 0;

    private static int counter = 0;

    public static void store() {
        System.out.println();
        DataManager.setCurrentPanel(Panels.STORE);
        printAllProducts();
        userCommand = scanner.nextLine();
        switch (userCommand) {
            case "s" -> search();
            case "f" -> filter();
            case "back" -> Panels.retarder(DataManager.getPreviousPanel());
            default -> {
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
                    DataManager.getGAMES().get(Integer.parseInt(userCommand)).panel();
                }
            }
        }
    }

    private static void filter() {
        DataManager.setCurrentPanel(Panels.STORE);
        System.out.print("Price range from: ");
        from = Integer.parseInt(scanner.nextLine());
        System.out.print("Price range to: ");
        to = Integer.parseInt(scanner.nextLine());
        System.out.println();
        filterGames();
        System.out.println();
        filterGamePads();
        filterGamingChairs();
        filterMonitors();
        if (counter == 0) {
            System.out.println("nothing found. try again...");
            filter();
        }
        System.out.print(">> ");
        userCommand = scanner.nextLine();
        if (userCommand.equals("back")) {
            store();
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
                DataManager.getGAMES().get(Integer.parseInt(userCommand)).panel();
            }
        }

    }

    private static void filterMonitors() {
        for (int i = 0; i < DataManager.getMonitors().size(); i++) {
            if (DataManager.getMonitors().get(i).getPrice() >= from &&
                    DataManager.getMonitors().get(i).getPrice() <= to) {
                System.out.println("[" + DataManager.getMonitors().get(i).getId() + "]: " +
                        DataManager.getMonitors().get(i).getName());
                counter++;
            }
        }
    }

    private static void filterGamingChairs() {
        for (int i = 0; i < DataManager.getGamingChairs().size(); i++) {
            if (DataManager.getGamingChairs().get(i).getPrice() >= from &&
                    DataManager.getGamingChairs().get(i).getPrice() <= to) {
                System.out.println("[" + DataManager.getGamingChairs().get(i).getId() + "]: " +
                        DataManager.getGamingChairs().get(i).getName());
                counter++;
            }
        }
    }

    private static void filterGamePads() {
        for (int i = 0; i < DataManager.getGamePads().size(); i++) {
            if (DataManager.getGamePads().get(i).getPrice() >= from &&
                    DataManager.getGamePads().get(i).getPrice() <= to) {
                System.out.println("[" + DataManager.getGamePads().get(i).getId() + "]: " +
                        DataManager.getGamePads().get(i).getName());
                counter++;
            }
        }
    }

    private static void filterGames() {
        for (int i = 0; i < DataManager.getGAMES().size(); i++) {
            if ((DataManager.getGAMES().get(i).getPrice() >= from) &&
                    (DataManager.getGAMES().get(i).getPrice() < to)) {
                System.out.println("[" + DataManager.getGAMES().get(i).getGameID() + "]: " +
                        DataManager.getGAMES().get(i).getName());
                counter++;
            }
        }
    }

    public static void printAllProducts() {
        System.out.println(GAMES);
        for (int i = 0; i < DataManager.getGAMES().size(); i++) {
            System.out.println("[" + DataManager.getGAMES().get(i).getGameID() + "]: " +
                    DataManager.getGAMES().get(i).getName());
        }
        System.out.println("""
                                
                        ► Accessories ◄
                        ---------------""");
        for (int j = 0; j < DataManager.getGamePads().size(); j++) {
            System.out.println("[" + DataManager.getGamePads().get(j).getId() + "]: " +
                    DataManager.getGamePads().get(j).getName());
        }
        for (int z = 0; z < DataManager.getGamingChairs().size(); z++) {
            System.out.println("[" + DataManager.getGamingChairs().get(z).getId() + "]: " +
                    DataManager.getGamingChairs().get(z).getName());
        }
        for (int x = 0; x < DataManager.getMonitors().size(); x++) {
            System.out.println("[" + DataManager.getMonitors().get(x).getId() + "]: " +
                    DataManager.getMonitors().get(x).getName());
        }
        System.out.print("""
                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                you can also search with 's' command.
                or filter products using 'f' command.
                >>\s""");
    }

    private static void search() {
        System.out.print("""
                                
                1. Existential search
                2. search with start with
                >>\s""");

        userCommand = scanner.nextLine();

        switch (userCommand) {
            case "1" -> inclusivelySearch();
            case "2" -> startWithSearch();
            case "back" -> store();
            default -> {
                System.out.println("command not found.\n");
                search();
            }
        }
    }

    private static void startWithSearch() {
        counter = 0;
        System.out.print(": ");
        userCommand = scanner.nextLine();

        searchGame();
        searchGamePad();
        searchGamingChair();
        searchMonitors();

        if (counter == 0) {
            System.out.println("nothing found. try again...");
            startWithSearch();
        }
        System.out.print(">> ");
        userCommand = scanner.nextLine();
        if (userCommand.equals("back")) {
            store();
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
                DataManager.getGAMES().get(Integer.parseInt(userCommand)).panel();
            }
        }
    }

    private static void searchMonitors() {
        for (int i = 0; i < DataManager.getMonitors().size(); i++) {
            if (DataManager.getMonitors().get(i).getName().startsWith(userCommand)) {
                System.out.println(DataManager.getMonitors().get(i).getId() +
                        ": " + DataManager.getMonitors().get(i).getName());
                counter++;
            }
        }
    }

    private static void searchGamingChair() {
        for (int i = 0; i < DataManager.getGamingChairs().size(); i++) {
            if (DataManager.getGamingChairs().get(i).getName().startsWith(userCommand)) {
                System.out.println(DataManager.getGamingChairs().get(i).getId() +
                        ": " + DataManager.getGamingChairs().get(i).getName());
                counter++;
            }
        }
    }

    private static void searchGamePad() {
        for (int i = 0; i < DataManager.getGamePads().size(); i++) {
            if (DataManager.getGamePads().get(i).getName().startsWith(userCommand)) {
                System.out.println(DataManager.getGamePads().get(i).getId() +
                        ": " + DataManager.getGamePads().get(i).getName());
                counter++;
            }
        }
    }

    private static void searchGame() {
        for (int i = 0; i < DataManager.getGAMES().size(); i++) {
            if (DataManager.getGAMES().get(i).getName().startsWith(userCommand)) {
                System.out.println(DataManager.getGAMES().get(i).getGameID() +
                        ": " + DataManager.getGAMES().get(i).getName());
                counter++;
            }
        }
    }

    private static void inclusivelySearch() {
        counter = 0;
        System.out.print(": ");
        userCommand = scanner.nextLine();

        inclusiveGame();
        inclusiveGamePad();
        inclusiveGamingChair();
        inclusiveMonitors();

        if (counter == 0) {
            System.out.println("nothing found. try again...");
            startWithSearch();
        }
        System.out.print(">> ");
        userCommand = scanner.nextLine();
        if (userCommand.equals("back")) {
            store();
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
                DataManager.getGAMES().get(Integer.parseInt(userCommand)).panel();
            }
        }
    }

    private static void inclusiveMonitors() {
        for (int i = 0; i < DataManager.getMonitors().size(); i++) {
            if (DataManager.getMonitors().get(i).getName().contains(userCommand)) {
                System.out.println(DataManager.getMonitors().get(i).getId() +
                        ": " + DataManager.getMonitors().get(i).getName());
                counter++;
            }
        }
    }

    private static void inclusiveGamingChair() {
        for (int i = 0; i < DataManager.getGamingChairs().size(); i++) {
            if (DataManager.getGamingChairs().get(i).getName().contains(userCommand)) {
                System.out.println(DataManager.getGamingChairs().get(i).getId() +
                        ": " + DataManager.getGamingChairs().get(i).getName());
                counter++;
            }
        }
    }

    private static void inclusiveGamePad() {
        for (int i = 0; i < DataManager.getGamePads().size(); i++) {
            if (DataManager.getGamePads().get(i).getName().contains(userCommand)) {
                System.out.println(DataManager.getGamePads().get(i).getId() +
                        ": " + DataManager.getGamePads().get(i).getName());
                counter++;
            }
        }
    }

    private static void inclusiveGame() {
        for (int i = 0; i < DataManager.getGAMES().size(); i++) {
            if (DataManager.getGAMES().get(i).getName().contains(userCommand)) {
                System.out.println(DataManager.getGAMES().get(i).getGameID() +
                        ": " + DataManager.getGAMES().get(i).getName());
                counter++;
            }
        }
    }
}
