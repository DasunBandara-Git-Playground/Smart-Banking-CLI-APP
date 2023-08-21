import java.util.Scanner;

public class SmartBanking{
    private static final Scanner scanner = new Scanner(System.in);
    private static final String GREEN = "\033[32;1m";
    private static final String RED = "\033[31;1m";
    private static final String RESET = "\033[0;0m";
    private static final String ERR_MSG = String.format("\n\t%s%s%s\n",RED,"%s",RESET);
    private static final String SUC_MSG = String.format("\n\t%s%s%s\n",GREEN,"%s",RESET);
    public static void main(String[] args) {
        final String DASHBOARD = "Welcome to Smart Banking";
        final String CNA = "Create New Account";
        final String DEPO = "Deposites";
        final String WD = "Withdrawals";
        final String TRANS = "Transfer";
        final String CAB = "Check Amount Balance";
        final String DA = "Delete Account";
        final String CLEAR = "\033[H\033[2J";
        String screen = DASHBOARD;
        String[][] accountDetails = new String[0][];

        loop:
        do{
            System.out.println(CLEAR);
            System.out.printf("\n\t\t%s%s%s\n",GREEN,screen,RESET);

            switch(screen){
                case DASHBOARD:
                    System.out.println("\n\t[1] Create New Account");
                    System.out.println("\n\t[2] Deposites");
                    System.out.println("\n\t[3] Withdrawals");
                    System.out.println("\n\t[4] Transfer");
                    System.out.println("\n\t[5] Check Account Balance");
                    System.out.println("\n\t[6] Delete Account");
                    System.out.println("\n\t[7] Exit\n\n");
                    System.out.print("\tEnter the Option to Continue: ");
                    int option = scanner.nextInt();
                    scanner.nextLine();

                    switch(option){
                        case 1: screen = CNA; break;
                        case 2: screen = DEPO; break;
                        case 3: screen = WD; break;
                        case 4: screen = TRANS; break;
                        case 5: screen = CAB; break;
                        case 6: screen = DA; break;
                        case 7:
                            System.out.println(CLEAR);
                            System.exit(0);
                            break;
                    }
                    
                    continue;

                case CNA:
                    accountDetails = addAccNo(accountDetails);
                    System.out.printf("\n\tID: %s\n",generateAccNo(accountDetails));
                    String name = getValidName();
                    String deposite;
                    do{
                        System.out.print("\n\tInitial Deposite: ");
                        deposite = scanner.nextLine();
                        if(!isSufficient(deposite, 5000)){
                            System.out.printf(ERR_MSG,"Insufficient Amount");
                            continue;
                        }
                        break;
                    }while(true);

                    addElement(accountDetails, name, 1);
                    addElement(accountDetails, deposite,2);
                    System.out.printf("\n\t"+SUC_MSG,accountDetails[accountDetails.length-1][0]+" : "+accountDetails[accountDetails.length-1][1]+" has been created successfully\n");

                    System.out.print("\n\tIf you want to continue(Y/n): ");
                    if(scanner.nextLine().strip().equalsIgnoreCase("Y")){
                        continue;
                    }
                    screen = DASHBOARD;
                    continue;

                case DEPO:
                    String accNo;
                    do{
                        System.out.print("\n\tEnter A/C No: ");
                        accNo = scanner.nextLine().strip();
                        if(isValidAccNo(accNo)){
                            if(getIndex(accountDetails, accNo) == -1){
                                System.out.printf(ERR_MSG,"A/C No not found");
                                System.out.print("\n\tIf you want to continue(Y/n): ");
                                if(scanner.nextLine().strip().equalsIgnoreCase("Y")){
                                    continue;
                                }else{
                                    screen = DASHBOARD;
                                    continue loop;
                                }
                            }
                            break;
                        }else{
                            System.out.print("\n\tIf you want to continue(Y/n): ");
                            if(scanner.nextLine().strip().equalsIgnoreCase("Y")){
                                continue;
                            }else{
                                screen = DASHBOARD;
                                continue loop;
                            }
                        }
                    }while(true);
                    System.out.printf("\n\tCurrent Balance: Rs.%,.2f\n",Double.valueOf(accountDetails[getIndex(accountDetails, accNo)][2]));

                    String newDeposite;
                    do{
                        System.out.print("\n\tDeposite Amount: ");
                        newDeposite = scanner.nextLine();
                        if(!(isSufficient(newDeposite, 500))){
                            System.out.printf(ERR_MSG,"Insufficient Deposite");
                            continue;
                        }
                        break;
                    }while(true);
                    double newBlance = Double.valueOf(newDeposite)+Double.valueOf(accountDetails[getIndex(accountDetails, accNo)][2]);
                    System.out.printf("\n\tNew Balance: %,.2f\n",newBlance);
                    addElement(accountDetails, newBlance+"", getIndex(accountDetails, accNo),2);

                    System.out.print("\n\tIf you want to continue(Y/n): ");
                    if(scanner.nextLine().strip().equalsIgnoreCase("Y")){
                        continue;
                    }else{
                        screen = DASHBOARD;
                        continue loop;
                    }
                
                case WD:
                    String accNoWd;
                    do{
                        System.out.print("\n\tEnter A/C No: ");
                        accNoWd = scanner.nextLine().strip();
                        if(isValidAccNo(accNoWd)){
                            if(getIndex(accountDetails, accNoWd) == -1){
                                System.out.printf(ERR_MSG,"A/C No not found");
                                System.out.print("\n\tIf you want to continue(Y/n): ");
                                if(scanner.nextLine().strip().equalsIgnoreCase("Y")){
                                    continue;
                                }else{
                                    screen = DASHBOARD;
                                    continue loop;
                                }
                            }
                            break;
                        }else{
                            System.out.print("\n\tIf you want to continue(Y/n): ");
                            if(scanner.nextLine().strip().equalsIgnoreCase("Y")){
                                continue;
                            }else{
                                screen = DASHBOARD;
                                continue loop;
                            }
                        }
                    }while(true);
                    System.out.printf("\n\tCurrent Balance: Rs.%,.2f\n",Double.valueOf(accountDetails[getIndex(accountDetails, accNoWd)][2]));

                    String newWithdraw;
                    do{
                        System.out.print("\n\tWithdraw Amount: ");
                        newWithdraw = scanner.nextLine();
                        if(!(isLegalWithdraw(accountDetails, accNoWd,newWithdraw))){
                            System.out.printf(ERR_MSG,"Insufficient Balance");
                            continue;
                        }
                        break;
                    }while(true);
                    double newBlanceWd = Double.valueOf(accountDetails[getIndex(accountDetails, accNoWd)][2])-Double.valueOf(newWithdraw);
                    System.out.printf("\n\tNew Balance: %,.2f\n",newBlanceWd);
                    addElement(accountDetails, newBlanceWd+"", getIndex(accountDetails, accNoWd),2);

                    System.out.print("\n\tIf you want to continue(Y/n): ");
                    if(scanner.nextLine().strip().equalsIgnoreCase("Y")){
                        continue;
                    }else{
                        screen = DASHBOARD;
                        continue loop;
                    }
                
                case TRANS:
                    String fromAccNo;
                    do{
                        System.out.print("\n\tEnter from A/C NO: ");
                        fromAccNo = scanner.nextLine().strip();
                        if(!isValidAccNo(fromAccNo)){
                            continue;
                        }
                        break;
                    }while(true);

                    System.out.print("\n\tFrom A/C Name: "+getElement(accountDetails, fromAccNo, 1)+"\n");
                    System.out.printf("\n\tFrom A/C Balance: %,.2f\n",Double.valueOf(getElement(accountDetails, fromAccNo, 2)));

                    String toAccNo;
                    do{
                        System.out.print("\n\tEnter to A/C NO: ");
                        toAccNo = scanner.nextLine().strip();
                        if(!isValidAccNo(toAccNo)){
                            continue;
                        }
                        break;
                    }while(true);

                    System.out.print("\n\tFrom A/C Name: "+getElement(accountDetails, toAccNo, 1)+"\n");
                    System.out.printf("\n\tFrom A/C Balance: %,.2f\n",Double.valueOf(getElement(accountDetails, toAccNo, 2)));

                    String transAmount;
                    do{
                        System.out.print("\n\tEnter Amount: ");
                        transAmount = scanner.nextLine();
                        if(isLegalTransfer(accountDetails, fromAccNo, transAmount)){
                            break;
                        }
                        System.out.printf(ERR_MSG,"Insufficient Amount");
                    }while(true);

                    double fromAccBlance = Double.valueOf(accountDetails[getIndex(accountDetails, fromAccNo)][2]) - Double.valueOf(transAmount)*1.02;
                    double toAccBlance = Double.valueOf(accountDetails[getIndex(accountDetails, toAccNo)][2]) + Double.valueOf(transAmount);
                    addElement(accountDetails, fromAccBlance+"", getIndex(accountDetails, fromAccNo), 2);
                    addElement(accountDetails, toAccBlance+"", getIndex(accountDetails, toAccNo), 2);

                    System.out.print("\n\tIf you want to continue(Y/n): ");
                    if(scanner.nextLine().strip().equalsIgnoreCase("Y")){
                        continue;
                    }else{
                        screen = DASHBOARD;
                        continue loop;
                    }
                
                case CAB:
                    String accNoToCheckBalance;
                    do{
                        System.out.print("\n\tEnter to A/C NO: ");
                        accNoToCheckBalance = scanner.nextLine().strip();
                        if(!isValidAccNo(accNoToCheckBalance)){
                            continue;
                        }
                        break;
                    }while(true);

                    System.out.print("\n\tName: "+getElement(accountDetails, accNoToCheckBalance, 1)+"\n");
                    double accBalance = Double.valueOf(getElement(accountDetails, accNoToCheckBalance, 2));
                    System.out.printf("\n\tCurrent A/C Balance: %,.2f\n",accBalance);
                    System.out.printf("\n\tAvailable Balance for Withdraw: %,.2f\n",(accBalance-500));

                    System.out.print("\n\tIf you want to continue(Y/n): ");
                    if(scanner.nextLine().strip().equalsIgnoreCase("Y")){
                        continue;
                    }else{
                        screen = DASHBOARD;
                        continue loop;
                    }
                
                case DA:
                    String accNoToDelete;
                    do{
                        System.out.print("\n\tEnter to A/C NO: ");
                        accNoToDelete = scanner.nextLine().strip();
                        if(!isValidAccNo(accNoToDelete)){
                            continue;
                        }
                        break;
                    }while(true);
                    String nameToDelete = getElement(accountDetails, accNoToDelete, 1);
                    System.out.print("\n\tName: "+nameToDelete+"\n");
                    accBalance = Double.valueOf(getElement(accountDetails, accNoToDelete, 2));
                    System.out.printf("\n\tA/C Balance: %,.2f\n",accBalance);

                    System.out.print("\n\tAre you sure to delete(Y/n): ");
                    if(scanner.nextLine().strip().equalsIgnoreCase("Y")){
                        System.out.printf(SUC_MSG,accNoToDelete+" : "+nameToDelete+" has been deleted successfully");
                        accountDetails = deleteElements(accountDetails, getIndex(accountDetails, accNoToDelete));

                    }else{
                        screen = DASHBOARD;
                        continue loop;
                    }

                    System.out.print("\n\tIf you want to continue(Y/n): ");
                    if(scanner.nextLine().strip().equalsIgnoreCase("Y")){
                        continue;
                    }else{
                        screen = DASHBOARD;
                        continue loop;
                    }
                    
            }

        }while(true);

        
    }

    public static String generateAccNo(String[][] accDetails){
        if(accDetails.length == 0){
            return String.format("SDB-%05d",1);
        }else{
            return String.format("SDB-%05d",Integer.valueOf((accDetails[accDetails.length-1][0]).substring(4)));
        }
    }

    public static String[][] addAccNo(String[][] accDetails){
        String[][] temp = new String[accDetails.length+1][3];
        if(accDetails.length == 0){
            temp[temp.length-1][0] = String.format("SDB-%05d",1);
            return temp;
        }else{
            for (int i = 0; i < accDetails.length; i++) {
                temp[i] = accDetails[i];
            }
            temp[temp.length-1][0] = String.format("SDB-%05d",Integer.valueOf((accDetails[accDetails.length-1][0]).substring(4))+1);
            return temp;
        }
    }

    public static void addElement(String[][] accDetails,String value,int index){
        accDetails[accDetails.length-1][index] = value;
    }

    public static void addElement(String[][] accDetails,String value,int index1, int index2){
        accDetails[index1][index2] = value;
    }

    public static String getValidName(){
        loop:
        do{
            System.out.print("\n\tName: ");
            String name = scanner.nextLine().strip();
            if(name.isBlank()){
                System.out.printf(ERR_MSG,"Name is Blank");
                continue;
            }else{
                for (int i = 0; i < name.length(); i++) {
                    if(!(Character.isLetter(name.charAt(i)) || Character.isSpaceChar(name.charAt(i)))){
                        System.out.printf(ERR_MSG,"Invalid Name");
                        continue loop;
                    }
                }
            }
            return name;
        }while(true);
    }

    public static boolean isSufficient(String value, double min){
        if(Double.valueOf(value) >= min){
            return true;
        }
        return false;
    }

    public static boolean isValidAccNo(String accNo){
            
        if(accNo.isBlank()){
            System.out.printf(ERR_MSG,"A/C can't be empty");
            return false;

        }else{
            if(!(accNo.startsWith("SDB-") && accNo.length() == 9)){
                System.out.printf(ERR_MSG,"Invalid A/C Number");
                return false;
            }
            for (int i = 4; i < accNo.length(); i++) {
                if(!(Character.isDigit(accNo.charAt(i)))){
                    System.out.printf(ERR_MSG,"Invalid A/C Number");
                    return false;
                }
            }
        }

        return true;
    }

    public static int getIndex(String[][] accDetails, String accNo){
        for (int i = 0; i < accDetails.length; i++) {
            if(accDetails[i][0].equals(accNo)){
                return i;
            }
        }
        return -1;
    }

    public static boolean isLegalWithdraw(String[][] accDetails, String accNo, String amount){
        if(Double.valueOf(amount)>=100 && (Double.valueOf(accDetails[getIndex(accDetails, accNo)][2])-Double.valueOf(amount))>=500){
            return true;
        }
        return false;
    }

    public static String getElement(String[][] accDetails, String accNo, int index){
        switch(index){
            case 1:
                return accDetails[getIndex(accDetails, accNo)][index];
            default:
                return Double.valueOf(accDetails[getIndex(accDetails, accNo)][index])+"";
        }
    }

    public static boolean isLegalTransfer(String[][] accDetails, String accNo, String amount){
        if(Double.valueOf(amount)>=100 && (Double.valueOf(accDetails[getIndex(accDetails, accNo)][2])-Double.valueOf(amount)*1.02)>=500){
            return true;
        }
        return false;
    }

    public static String[][] deleteElements(String[][] accDetails, int index){
        String[][] temp = new String[accDetails.length-1][3];
        for (int i = 0; i < accDetails.length; i++) {
            if(i<index){
                temp[i] = accDetails[i];
            }else if(i>index){
                temp[i-1] = accDetails[i];
            }
        }
        return temp; 
    }

}