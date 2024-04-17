import java.io.Console;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // создать класс, описывающий банкомат. Набор купюр, находящ.в банкомате, должен задаваться тремя свойствами:
        //кол-вом купюр номиналом 20, 50,100. Сделать методы д/добавл.денег в банкомат. Сделать метод, снимающий деньги.
        //С клавиатуры передается сумма денег. На экран - булевское значение (операция удалась/нет). При снятии денег метод
        // должен выводить на экран каким кол-вом купюр и какого номинала выдается сумма. Созд.конструктор с тремя параметрами -
        //кол-вом купюр. Остальное - на усмотрение

        ATMContents ATM = new ATMContents(0, 0,0);
        boolean isEnd = true;


        Scanner scanner = new Scanner(System.in);


        do {
            System.out.println("\n----------------");
            System.out.println("   -= ATM =-");
            System.out.println("----------------");
            System.out.println("Choose operation: " +
                    " \n1 - Show ATM Contents; " +
                    " \n2 - Add money;" +
                    " \n3 - Withdraw money; " +
                    " \n4 - Close the program.");
            System.out.print("Введите номер операции: ");

            int choose = scanner.nextInt();

            switch (choose) {
                case 1:
                    checkATMContents(ATM);
                    break;
                case 2:
                    System.out.print("Введите сумму для пополнения: ");
                    int amountToAdd = scanner.nextInt();
                    int remainder = addAmount(ATM, amountToAdd);
                    checkATMContents(ATM);

                    if (remainder > 0) {
                        System.out.println("В банкомат внесено " + (amountToAdd - remainder) +
                                " Остаток: " + remainder + ". Банкомат не принимает купюры номиналом менее 20.");
                    } else {
                        System.out.println("В банкомат внесено " + (amountToAdd));
                    }
                    break;
                case 3:
                    System.out.print("- Снятие денег. -\nВведите желаемую сумму для снятия: ");
                    int AmountToWithdraw = scanner.nextInt();

                    if (isEnoughMoneyToWithdraw(ATM, AmountToWithdraw)) {
                        System.out.println("В банкомате достаточно денег для выдачи.");
                        boolean withdrawRemainder = withdrawAmount(ATM, AmountToWithdraw);
                        if (withdrawRemainder) {
                            System.out.println("Деньги сняты успешно!");
                        } else {
                            System.out.println("К сожалению, в банкомате нет купюр номиналом менее 20. Невозможно снять введённую сумму.");
                        }
                    } else {
                        System.out.println("К сожалению, в банкомате недостаточно денег для выдачи.");
                    }
                    break;
                case 4:
                    isEnd = false;
                    break;
                default:
                    System.out.println("Wrong operation code.");
                    break;

            }

        } while (isEnd);

        System.out.println("Завершение работы программы...");
    }

    public static class ATMContents {
        int Count20Banknote;
        int Count50Banknote;
        int Count100Banknote;
        public ATMContents (int count20Banknote, int count50Banknote, int count100Banknote) {
            Count20Banknote = count20Banknote;
            Count50Banknote = count50Banknote;
            Count100Banknote = count100Banknote;
        }
    }
    public static void checkATMContents (ATMContents ATM) {
        System.out.println("Купюры номиналом 100: " + ATM.Count100Banknote + " шт;" +
                "\nКупюры номиналом 50: " + ATM.Count50Banknote + " шт;" +
                "\nКупюры номиналом 20: " + ATM.Count20Banknote + " шт.");
    }
    public static int addAmount (ATMContents ATM, int amount) {
        int CountAdded100Banknote = amount/100;
        ATM.Count100Banknote += CountAdded100Banknote;
        int CountAdded50Banknote = (amount - CountAdded100Banknote * 100)/50;
        ATM.Count50Banknote += CountAdded50Banknote;
        int CountAdded20Banknote = (amount - (CountAdded100Banknote * 100 - CountAdded50Banknote * 50)) / 20;
        ATM.Count20Banknote += CountAdded20Banknote;
        int remainder = amount - CountAdded100Banknote * 100 - CountAdded50Banknote * 50 - CountAdded20Banknote * 20;

        return remainder;
    }

    public static boolean isEnoughMoneyToWithdraw (ATMContents ATM, int amountToWithdraw) {
        int TotalATMAmount = ATM.Count20Banknote * 20 + ATM.Count100Banknote * 100 + ATM.Count50Banknote * 50;
        System.out.println("Total: " + TotalATMAmount);
        boolean isEnough = (amountToWithdraw <= TotalATMAmount);

        return isEnough;
    }

    public static boolean withdrawAmount (ATMContents ATM, int amount) {

        boolean isMoneyWithdraw = true;
        if (amount % 20 != 0) {
            isMoneyWithdraw = false;
        } else {
            int CountW100Banknote = amount/100;
            ATM.Count100Banknote -= CountW100Banknote;
            int CountW50Banknote = (amount - CountW100Banknote * 100)/50;
            ATM.Count50Banknote -= CountW50Banknote;
            int CountW20Banknote = (amount - (CountW100Banknote * 100 - CountW50Banknote * 50)) / 20;
            ATM.Count20Banknote -= CountW20Banknote;
        }
        return isMoneyWithdraw;
    }
}