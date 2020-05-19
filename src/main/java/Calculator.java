public class Calculator {
    private int firstNum;
    private int secondNum;

    public int getFirstNum() {
        return firstNum;
    }

    public void setFirstNum(int firstNum) {
        this.firstNum = firstNum;
    }

    public int getSecondNum() {
        return secondNum;
    }

    public void setSecondNum(int secondNum) {
        this.secondNum = secondNum;
    }
    public Calculator() {

    }
    public Calculator(int firstNum, int secondNum) {
        this.firstNum = firstNum;
        this.secondNum = secondNum;
    }
    public void getSum() {
        System.out.println(firstNum + secondNum);
    }
    public void getMul() {
        System.out.println(firstNum * secondNum);
    }

}
