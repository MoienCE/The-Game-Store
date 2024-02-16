package ir.ac.kntu;

import ir.ac.kntu.accessories.GamePad;
import ir.ac.kntu.accessories.GamingChair;
import ir.ac.kntu.accessories.Monitor;

import java.util.ArrayList;

public class DataManager {

    //**********( Current Type Data )**********
    private static Type currentType;

    public static Type getCurrentType() {
        return currentType;
    }

    static void setCurrentType(Type currentType) {
        DataManager.currentType = currentType;
    }


    //**********( User Data )**********
    private static User currentUser;

    public static void setCurrentUser(User currentUser) {
        DataManager.currentUser = currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    private static final ArrayList<User> USERS = new ArrayList<>();

    public static ArrayList<User> getUsers() {
        return USERS;
    }

    public static void addUsers(User inputUser) {
        USERS.add(inputUser);
    }


    //**************( ADMIN DATA )**************
    private final static ArrayList<Admin> ADMINS = new ArrayList<>();

    private static Admin currentAdmin;

    public static ArrayList<Admin> getAdmins() {
        return ADMINS;
    }

    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public static void setCurrentAdmin(Admin admin) {
        currentAdmin = admin;
    }

    private static boolean haveAdmin = false; // 1

    public static void setHaveAdmin(boolean have) {
        haveAdmin = have;
    }

    public static boolean isHaveAdmin() {
        return haveAdmin;
    }


    private static Panels previousPanel; // 2

    public static Panels getPreviousPanel() {
        return previousPanel;
    }

    public static void setPreviousPanel(Panels previousPanel) {
        //**********( Panels Data )**********
        // 1
        DataManager.previousPanel = previousPanel;
    }

    private static Panels currentPanel;

    public static Panels getCurrentPanel() {
        return currentPanel;
    }

    public static void setCurrentPanel(Panels currentPanel) {
        DataManager.currentPanel = currentPanel;
    }


    //**********( Games Data )**********
    private static final ArrayList<Game> GAMES = new ArrayList<>();

    public static ArrayList<Game> getGAMES() {
        return GAMES;
    }

    public static void addGame(Game inputGame) {
        GAMES.add(inputGame);
    }


    //**********( developers Data )**********
    private static final ArrayList<Developer> DEVELOPERS = new ArrayList<>();

    public static ArrayList<Developer> getDevelopers() {
        return DEVELOPERS;
    }

    public static void addDeveloper(Developer developer) {
        DEVELOPERS.add(developer);
    }

    private static Developer currentDeveloper;

    public static void setCurrentDeveloper(Developer currentDeveloper) {
        DataManager.currentDeveloper = currentDeveloper;
    }

    public static Developer getCurrentDeveloper() {
        return currentDeveloper;
    }

    //**********( sellers Data )**********

    private static AccessoriesSeller currentSeller;

    public static void setCurrentSeller(AccessoriesSeller currentUser) {
        DataManager.currentSeller = currentUser;
    }

    public static AccessoriesSeller getCurrentSeller() {
        return currentSeller;
    }

    private static final ArrayList<AccessoriesSeller> SELLERS = new ArrayList<>();

    public static ArrayList<AccessoriesSeller> getSellers() {
        return SELLERS;
    }

    public static void addSeller(AccessoriesSeller inputSeller) {
        SELLERS.add(inputSeller);
    }

    //**********( Accessories Data )**********

    private static final ArrayList<GamePad> GAME_PADS = new ArrayList<>();

    private static final ArrayList<GamingChair> GAMING_CHAIRS = new ArrayList<>();

    private static final ArrayList<Monitor> MONITORS = new ArrayList<>();

    public static ArrayList<GamePad> getGamePads() {
        return GAME_PADS;
    }

    public static ArrayList<GamingChair> getGamingChairs() {
        return GAMING_CHAIRS;
    }

    public static ArrayList<Monitor> getMonitors() {
        return MONITORS;
    }

    public static void addGamePad(GamePad gamePad) {
        GAME_PADS.add(gamePad);
    }

    public static void addGamingChair(GamingChair gamingChair) {
        GAMING_CHAIRS.add(gamingChair);
    }

    public static void addMonitor(Monitor monitor) {
        MONITORS.add(monitor);
    }
}