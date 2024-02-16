package ir.ac.kntu;

class Services{
    private static final AccessoriesSeller ADMIN = new AccessoriesSeller("ADMIN",
            "********",
            "********",
            "********");

    static void start() {
        if (!DataManager.getSellers().contains(ADMIN)){
            DataManager.getSellers().add(ADMIN);
        }
        
        DataManager.setCurrentType(Type.Guest);

        switch (DataManager.getCurrentType()){
            case User -> DataManager.getCurrentUser().mainMenu();
            case Admin -> DataManager.getCurrentAdmin().mainMenu();
            case AccessorySeller -> DataManager.getCurrentSeller().mainMenu();
            case Guest -> Guest.mainMenu();
            default -> System.out.println("visit me in services default");
        }
    }
}
