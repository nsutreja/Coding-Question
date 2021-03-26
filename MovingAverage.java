package com.comscore.deleteme.temp;

interface LIFOQueue {
    void addElement(double d);  //add new element to the arr
    void calculateAvg(double d); //calculate the average
    double removeFirstVal(); //remove the first value from the sum
    double getAverage(); //return average value
}

public class MovingAverage implements LIFOQueue {
    double[] arr;
    int arrSize;
    int N;
    int head;
    double avg;
    double sum;

    public MovingAverage(int N) {
        this.N = N;
        arr = new double[N];
        avg = 0;
        head = 0;
        arrSize = 0;
    }

    @Override
    //calculate average function
    //remove first and then add element when arr is full
    public void calculateAvg(double num) {
        sum = avg * arrSize;
        if (arrSize == N) {
            sum = removeFirstVal();
        }
        addElement(num);
        sum += num;
        avg = sum / arrSize;
    }

    @Override
    //remove first (head) value from sum and update the size of arr
    public double removeFirstVal() {
        sum -= arr[head];
        arrSize--;
        return sum;
    }

    @Override
    //add element at the rear position in the arr and update the size of arr
    public void addElement(double num) {
        head = (head + 1) % N;
        int pos = (N + head - 1) % N;
        arr[pos] = num;
        arrSize++;
    }

    @Override
    //return avg for each element
    public double getAverage() {
        return avg;
    }
}

class Main {

    public static void main(String[] args) {
        int[] input_data = {1, 2, 4, 11, 9, 6};
        int N = 5;
        MovingAverage movAvg = new MovingAverage(N);
        for (int x : input_data) {
            movAvg.calculateAvg(x);
            System.out.println("New element added is " +
                    x + " MovingAvg = " + movAvg.getAverage());
        }
    }
}
