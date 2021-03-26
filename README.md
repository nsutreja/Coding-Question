# PaytmLabs SDE Challenge

## Coding Question

Write an interface for a data structure that can provide the moving average of the last N elements added, add elements to the structure and get access to the elements. Provide an efficient implementation of the interface for the data structure.

### Minimum Requirements

1. Provide a separate interface (IE `interface`/`trait`) with documentation for the data structure
	
	public interface LIFOQueue {
		void addElement(double d); //add new element to the arr
		void calculateAvg(double d); //calculate the average 
		double removeFirstVal(); //remove the first value from the sum 
		double getAverage(); //return average value
	}
2. Provide an implementation for the interface

		public class MovingAverage implements LIFOQueue {
    double[] arr;
    int arrSize;
    int N;
    int head;
    double avg;
    double sum;

//define class constructor
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
        int[] arr = {1, 2, 4, 11, 9, 6};
        int N = 5;
        MovingAverage movAvg = new MovingAverage(N);
        for (int i : arr) {
            movAvg.calculateAvg(i);
            System.out.println("Element added is " + i + " MovingAvg = " + movAvg.getAverage());
        }
    }
}

3. Provide any additional explanation about the interface and implementation in a README file.
   I am using a circular buffer of size N when buffer (array) is full during adding new element, then I remove first one value from the sum. 
   Then add new value and update the sum. At last sum/arr.size gives average. Arr.size is important as it is not the same as N all the time and it can give wrong average. 
   

## Design Question

Design A Google Analytic like Backend System.
We need to provide Google Analytic like services to our customers. Please provide a high level solution design for the backend system. Feel free to choose any open source tools as you want.

### Requirements

1. Handle large write volume: Billions of write events per day.
2. Handle large read/query volume: Millions of merchants wish to gain insight into their business. Read/Query patterns are time-series related metrics.
3. Provide metrics to customers with at most one hour delay.
4. Run with minimum downtime.
5. Have the ability to reprocess historical data in case of bugs in the processing logic.

Answer:
* Basically to process data almost at real time, we need to use method called: Stream processing 
* For stream processing, the best open souce tool as per my understanding is:  Apache KafKa
* Kafka is basically used as a transportation mechanism. It moves data at very fast scale.
* It is open source tool, was created by LinkedIn. 
* It's distributed system and it can scale to millions of message/data per second.
* Due to it's high performance, latency is less than 10ms so almost real time
* Kafka Cluster is made of multiple brokers. Each broker is identified with an ID and can contain certain partitions. 
  so when a broker is down, another one can serve as leader. Thus, KafKa offers strong durability and fault tolerance guarantees.
 
 
 Flow chart: 
 
Data Sources collects log/data/messages
			||
Kafka(A distributed streaming platform) 
			||
Hadoop (Processing cluster)
			||
YARN (Resource Management)
			|| 
Driver (Spark, pig, python, etc. to execute the rules)
			||
OOZIE(Executors machines) 
			||
Dataware house/Consumers/Real-Time Dashboard/Visulization


