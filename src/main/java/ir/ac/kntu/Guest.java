package ir.ac.kntu;

public class Guest extends Main{
    public static void mainMenu(){
        System.out.print("""
                     
                     
                     ╭────────────────────╮
                     │       welcome      │
                ┏━━━━┷━━━━━━━━━━━━━━━━━━━━┷━━━━━┓

                  Your access is at guest level
                     1. identification panel
                     2. visit the store
                
                ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
                >>\s""");
        userCommand = scanner.nextLine();
        userCommand = userCommand.trim();

        if (userCommand.equals("1")){
            DataManager.setPreviousPanel(Panels.GUEST_MAIN_MENU);
            Identification.mainMenu();
        } else if (userCommand.equals("2")) {
            DataManager.setPreviousPanel(Panels.GUEST_MAIN_MENU);
            Utilities.store();
        } else {
            System.out.println("command not found...\n");
            mainMenu();
        }
    }
}