import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int current_power = 0;
        boolean notFound = true;

        while (Math.pow(3, current_power) <= N) {
            if (Math.pow(3, current_power) == N) {
                System.out.println(current_power);
                notFound = false;
                break;
            }
            current_power++;
        }

        if (notFound) {
            System.out.println(-1);
        }
    }
}
