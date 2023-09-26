import java.util.Scanner;
public class TextConverterMain {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int num1,num2, num3=1000;
        boolean object=false;

        System.out.println("Enter Num 1");
        num1 = scan.nextInt();
        System.out.println("Enter Num 2");
        num2 = scan.nextInt();

        while(object==false){
            num3=num1%num2;
            if(num3==0){
                object=true;
                break;
            }
            else{
                num1=num2;
                num2=num3;
            }
            System.out.println(num2);
        }
        System.out.println("The GCD of the two numbers is "+num2);

    }
}
