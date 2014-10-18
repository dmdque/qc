/*
 * formatting of answer?
   * whitespace and '\n'
 * process is just bad
 * find max k (use pivot strategy)
 * handle edge casesi
   * question has topic which does not exist
   * Question.limit > #questions
*/

import java.util.Scanner;
import java.lang.Math;
import java.util.Arrays;
import java.util.Collections;

public class Solution {
  public static boolean DEBUG = false;

  public static void quickSortFirstKDistances(Distance[] distances, int left, int right) {
    int n = right - left;
    if(n  < 2) {
      if(distances[left].distance > distances[left + 1].distance) {
        swapDistances(distances, left, left + 1);
      }
    } else {
      int pivot = left + (int) (Math.random() * n); // random pivot. use median of medians to improve
      System.out.println("pivot: " + pivot + " val: " + distances[pivot].distance);
      int l = partitionDistances(distances, left, right - 1, pivot);
      System.out.println("l: " + l);
      quickSortFirstKDistances(distances, left, l);
      quickSortFirstKDistances(distances, l, right);
    }
  }

  public static void partitionFirstKDistances(Distance[] distances, int left, int right, int k) {
    System.out.println("left: " + left + ", right: " + right + ", k: " + k);
    System.out.println("distances[i]: ");
    for(int i = 0; i < distances.length; i++) {
      System.out.println(i + ": " + distances[i]);
    }
    if(left < right) { // check in case something went wrong
      int n = right - left;
      int pivot = left + (int) (Math.random() * n); // random pivot. use median of medians to improve
      System.out.println("pivot: " + pivot + " val: " + distances[pivot].distance);
      int l = partitionDistances(distances, left, right - 1, pivot);
      System.out.println("l: " + l);
      if(l < k) {
        // partition right of l
        partitionFirstKDistances(distances, l, right, k);
      } else if (l > k) {
        // further partition left of l
        partitionFirstKDistances(distances, left, l, k);
      } else {
        // done
      }
    } else {
      System.out.println("error");
    }
  }

  public static void swapDistances(Distance[] a, int left, int right) {
    Distance temp = a[left];
    a[left] = a[right];
    a[right] = temp;
  }

  public static boolean approxEqual(double d1, double d2) {
    if(Math.abs(d1 - d2) <= 0.001) {
      return true;
    } else {
      return false;
    }
  }

  public static int partitionDistances(Distance[] distances, int left, int right, int pivotIndex) {
    double pivotValue = distances[pivotIndex].distance;
    swapDistances(distances, right, pivotIndex);
    int storeIndex = left;

    for(int i = left; i < right; i++) {
      if(approxEqual(distances[i].distance, pivotValue)) {
        //System.out.println("approx equal" + distances[i].id + "pivot " + distances[right].id);
        //if(distances[i].id > distances[right].id) {
        swapDistances(distances, storeIndex, i);
        storeIndex++;
        //}
      } else if(distances[i].distance < pivotValue) {
        swapDistances(distances, storeIndex, i);
        storeIndex++;
      }
    }
    swapDistances(distances, right, storeIndex);
    return storeIndex;
  }
  
  public static Topic stringToTopic(String line) {
    String[] tokens = line.split(" ");
    int id = Integer.parseInt(tokens[0]);
    double x = Double.parseDouble(tokens[1]);
    double y = Double.parseDouble(tokens[2]);

    Topic t = new Topic(id, x, y);
    return t;
  }

  public static Question stringToQuestion(String line) {
    String[] tokens = line.split(" ");
    int id = Integer.parseInt(tokens[0]);
    int n = Integer.parseInt(tokens[1]);
    int[] topics = new int[n];
    for(int i = 0; i < n; i++) {
      topics[i] = Integer.parseInt(tokens[2 + i]);
    }

    Question q = new Question(id, n, topics);
    return q;
  }

  public static Query stringToQuery(String line) {
    String[] tokens = line.split(" ");
    String type = tokens[0];
    int results = Integer.parseInt(tokens[1]);
    double x = Double.parseDouble(tokens[2]);
    double y = Double.parseDouble(tokens[3]);

    Query q = new Query(type, results, x, y);
    return q;
  }

  public static void main(String[] args) {   
    int numDistances = 1000;
    Distance[] testDistances = new Distance[numDistances];
    //testDistances[0] = new Distance(0, 7.0);
    //testDistances[1] = new Distance(1, 1.0);
    //testDistances[2] = new Distance(2, 9.0);
    //testDistances[3] = new Distance(3, 6.0);
    //testDistances[4] = new Distance(4, 4.0);
    //testDistances[5] = new Distance(5, 0.0);
    //testDistances[6] = new Distance(6, 0.0);
    //testDistances[7] = new Distance(7, 9.0);
    //testDistances[8] = new Distance(8, 9.0);
    //testDistances[9] = new Distance(9, 3.0);
    //testDistances[10] = new Distance(10, 6.0);
    //testDistances[11] = new Distance(11, 5.0);
    //testDistances[12] = new Distance(12, 5.0);
    //testDistances[13] = new Distance(13, 5.0);
    //testDistances[14] = new Distance(14, 9.0);
    //testDistances[15] = new Distance(15, 6.0);
    //testDistances[16] = new Distance(16, 0.0);
    //testDistances[17] = new Distance(17, 6.0);
    //testDistances[18] = new Distance(18, 2.0);
    //testDistances[19] = new Distance(19, 9.0);
    for(int i = 0; i < numDistances; i++) {
      testDistances[i] = new Distance(i, (int) (Math.random() * 1000));
    }
    for(int i = 0; i < numDistances; i++) {
      System.out.println(testDistances[i]);
    }

    partitionFirstKDistances(testDistances, 0, numDistances, 100);
    quickSortFirstKDistances(testDistances, 0, 100);
    //partitionDistances(testDistances, 0, 19, 5);
    System.out.println("PARTITIONED:");
    for(int i = 0; i < numDistances; i++) {
      System.out.println(i + ": " + testDistances[i]);
    }

    // Input and conversion into objects
    Scanner stdin = new Scanner(System.in);
    String line = stdin.nextLine();
    String[] tokens = line.split(" ");
    int T = Integer.parseInt(tokens[0]);
    int Q = Integer.parseInt(tokens[1]);
    int N = Integer.parseInt(tokens[2]);

    Topic[] topics = new Topic[T];
    Question[] questions = new Question[Q];
    Query[] queries = new Query[N];

    if(DEBUG) { System.out.println("" + T + Q + N); }
    for(int i = 0; i < T; i++) {
      line = stdin.nextLine();
      topics[i] = stringToTopic(line);
    }
    for(int i = 0; i < Q; i++) {
      line = stdin.nextLine();
      questions[i] = stringToQuestion(line);
    }
    for(int i = 0; i < N; i++) {
      line = stdin.nextLine();
      queries[i] = stringToQuery(line);
    }
    stdin.close();

    // Print to check
    for(int i = 0; i < T; i++) {
      if(DEBUG) { System.out.println(topics[i]); }
    }
    for(int i = 0; i < Q; i++) {
      if(DEBUG) { System.out.println(questions[i]); }
    }
    for(int i = 0; i < N; i++) {
      if(DEBUG) { System.out.println(queries[i]); }
    }

    // Processing
    // process Query
    for(int i = 0; i < N; i++) {
      if(queries[i].type.equals("t")) {
        int[] ids = new int[T];
        double[] distances = new double[T];
        for(int j = 0; j < T; j++) {
          ids[j] = topics[j].id;
          distances[j] = topics[j].distance(queries[i].x, queries[i].y);
        }

        for(int j = 0; j < queries[i].limit; j++) {
          double min = 1414214;
          int minIndex = -1;
          for(int k = 0; k < T; k++) {
            if(distances[k] <= min) {
              minIndex = k;
              min = distances[k];
            }
          }
          // need this?
          if(minIndex == -1) {
            continue;
          }
          if(DEBUG) { System.out.println("id: " + ids[minIndex] + ", distance: " + distances[minIndex]); }
          System.out.print(ids[minIndex] + " ");
          ids[minIndex] = -1;
          distances[minIndex] = 1414214;
        }
      } // end if Topic
      else { // if(queries[i].type.equals("q")) {
        if(DEBUG) { System.out.println("Question Query:"); }
        int[] ids = new int[Q];
        double[] distances = new double[Q];
        for(int j = 0; j < Q; j++) {
          ids[j] = questions[j].id;
          distances[j] = questions[j].distance(queries[i].x, queries[i].y, topics);
          if(DEBUG) { System.out.println("distance: " + distances[j]); }
        }

        for(int j = 0; j < queries[i].limit; j++) {
          double min = 1414214;
          int minIndex = -1;
          for(int k = 0; k < Q; k++) {
            if(distances[k] <= min) {
              minIndex = k;
              min = distances[k];
            }
          }
          if(minIndex == -1 || min == 1414214) {
            continue;
          }
          if(DEBUG) { System.out.println("id: " + ids[minIndex]  + ", distance: " + distances[minIndex]); }
          System.out.print(ids[minIndex] + " ");
          ids[minIndex] = -1;
          distances[minIndex] = 1414214;
        }

      }
      System.out.println();
    }
    


  }
}

class Topic {
  public int id;
  public double x;
  public double y;

  public Topic(int id, double x, double y) {
    this.id = id;
    this.x = x;
    this.y = y;
  }

  public String toString() {
    return "Topic: {" + "id: " + this.id + ", x: " + this.x + ", y: " + this.y + "}";
  }

  public double distance(double x, double y) {
    return Math.sqrt(Math.pow(Math.abs(this.x - x), 2) + Math.pow(Math.abs(this.y - y), 2));
  }
}

class Question {
  public int id;
  public int n;
  public int[] topicIds;

  public Question(int id, int n, int[] topicIds) {
    this.id = id;
    this.n = n;
    this.topicIds = topicIds;
  }

  public String toString() {
    String s = "Question: {" + "id: " + this.id + ", n: " + this.n + ", topicIds: [";
    for(int i = 0; i < n; i++) {
      s = s + this.topicIds[i];
      if(i != n - 1) {
        s += ", ";
      }
    }
    s += "]";
    return s;
  }

  public double distance(double x, double y, Topic[] topics) {

    Topic[] qtopics = new Topic[n];
    int qi = 0;
    for(int i = 0; i < n; i++) {
      for(int j = 0; j < topics.length; j++) {
        if(topics[j].id == this.topicIds[i]) {
          qtopics[qi] = topics[j];
          qi++;
        }
      }
    }

    double[] distances = new double[this.n];
    for(int i = 0; i < qi; i++) {
      distances[i] = qtopics[i].distance(x, y);
      //System.out.println("distance " + i + ": " + distances[i]);
    }

    double min = 1414214;
    int minIndex = -1;
    for(int i = 0; i < qi; i++) {
      if(distances[i] < min) {
        minIndex = i;
        min = distances[i];
      }
    }
    return min;
  }
}

class Query {
  public String type;
  public int limit;
  public double x;
  public double y;

  public Query(String type, int limit, double x, double y) {
    this.type = type;
    this.limit = limit;
    this.x = x;
    this.y = y;
  }

  public String toString() {
    String s = "Query: {" + "type: " + this.type + ", limit: " + this.limit + ", x: " + this.x + ", y: " + this.y + "}";
    return s;
  }
}

class Distance {
  public int id;
  public double distance;

  public Distance(int id, double distance) {
    this.id = id;
    this.distance = distance;
  }

  public String toString() {
    return "Distance: {id: " + id + ", distance: " + distance + "}";
  }
}
