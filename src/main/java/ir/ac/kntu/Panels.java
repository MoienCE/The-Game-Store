package ir.ac.kntu;

public enum Panels {
    ADMIN_MAIN_MENU, STORE, GUEST_MAIN_MENU,
    IDENTIFICATION_MAIN_MENU, ADMIN_IDENTIFICATION,
    USER_IDENTIFICATION, USER_MANAGEMENT,
    USER_MAIN_MENU, PROFILE, CHANGE_USER_INFORMATION,
    USER_SIGN_UP, USER_SIGN_IN,
    CREATE_ADMIN, ADMIN_PRODUCTS_PANEL,
    USER_LIBRARY, ADMIN_USER_CREATION,
    ALL_USERS, SEARCH_USER,
    USER_INCLUSIVELY_SEARCH, ADMIN_EDITING_USER,
    USER_FRIENDS,
    SELLER_SIGN_UP, SELLER_SIGN_IN, SELLER_PROFILE,
    SELLER_ACCESSORY, SELLER_SIGN, SELLER_MANAGE, DEVELOPER_GAMES,
    DEVELOPER_SIGN_UP, DEVELOPER_MAIN_MENU;

    public static void retarder(Panels previousPanel) {
        switch (previousPanel) {
            case ADMIN_MAIN_MENU -> DataManager.getCurrentAdmin().mainMenu();
            case STORE -> Utilities.store();
            case GUEST_MAIN_MENU -> Guest.mainMenu();
            case IDENTIFICATION_MAIN_MENU -> Identification.mainMenu();
            case ADMIN_IDENTIFICATION -> Identification.adminIdentification();
            case USER_IDENTIFICATION -> Identification.userIdentification();
            case USER_MANAGEMENT -> Admin.userManagement();
            case USER_MAIN_MENU -> DataManager.getCurrentUser().mainMenu();
            case PROFILE -> DataManager.getCurrentUser().profile();
            case CHANGE_USER_INFORMATION -> DataManager.getCurrentUser().changeInformation();
            case USER_SIGN_UP -> Identification.userSignUp();
            case USER_SIGN_IN -> Identification.userSignIn();
            case CREATE_ADMIN -> Identification.createAdmin();
            case ADMIN_PRODUCTS_PANEL -> DataManager.getCurrentAdmin().adminProductsPanel();
            case USER_LIBRARY -> DataManager.getCurrentUser().library();
            case ADMIN_USER_CREATION -> AdminHelper.createUser();
            case ALL_USERS -> Admin.allUsers();
            case SEARCH_USER -> AdminHelper.searchUser();
            case USER_INCLUSIVELY_SEARCH -> Admin.inclusivelySearch();
            case ADMIN_EDITING_USER -> Admin.editUser();
            case USER_FRIENDS -> DataManager.getCurrentUser().friends();
            case SELLER_SIGN_UP -> Identification.sellerSignUp();
            case SELLER_SIGN_IN -> Identification.sellerSignIn();
            case SELLER_PROFILE -> DataManager.getCurrentSeller().profile();
            case SELLER_ACCESSORY -> DataManager.getCurrentSeller().accessories();
            case SELLER_SIGN -> Identification.sellerIdentification();
            case SELLER_MANAGE -> DataManager.getCurrentSeller().manage();
            case DEVELOPER_GAMES -> DataManager.getCurrentDeveloper().games();
            case DEVELOPER_SIGN_UP -> Identification.developerSignUp();
            case DEVELOPER_MAIN_MENU -> DataManager.getCurrentDeveloper().mainMenu();
            default -> System.out.println("visit Panel.java i am there");
        }
    }
}
