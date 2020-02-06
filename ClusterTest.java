import java.util.*;

/**
 * January 2016 Exam problem 2
 */

class Point2D {
    private  long id;
    private float x;
    private  float y;
    private float distance;

    public Point2D() {}

    public  Point2D(long id, float x, float y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public double getDistance(Point2D p2) {
        return Math.sqrt(Math.pow(this.x-p2.getX(),2) +Math.pow(this.y-p2.getY(),2));
    }

    public void setDistance(float d) {
        if(d == 0.0) {
            this.distance = Float.MAX_VALUE;
        } else {
            this.distance = d;
        }
    }

    public float getDistanceTest() {
        return distance;
    }

    public String toString() {
        return String.format("%d -> %.3f", (int) id, getDistanceTest());

    }
}

class Cluster<T extends Point2D> {
    private List<T> list;

    public Cluster() {
        this.list = new ArrayList<T>();
    }

    public void addItem(T element) {
        this.list.add(element);
    }

    public void near(long id, int top) {
        Point2D test = new Point2D();
        for(int i=0; i<list.size(); i++) {
            if(list.get(i).getId() ==  id) {
               test = list.get(i);
            }
        }
        for(int i=0; i<list.size(); i++)  {
            list.get(i).setDistance((float)list.get(i).getDistance(test));
        }
        
       list.sort(Comparator.comparing(Point2D::getDistanceTest));
            for(int i=0; i<top; i++) {
                System.out.println(i+1+". " + list.get(i));
            }
    }

}


public class ClusterTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cluster<Point2D> cluster = new Cluster<>();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            long id = Long.parseLong(parts[0]);
            float x = Float.parseFloat(parts[1]);
            float y = Float.parseFloat(parts[2]);
            cluster.addItem(new Point2D(id, x, y));
        }
        int id = scanner.nextInt();
        int top = scanner.nextInt();
        cluster.near(id, top);
        scanner.close();
    }
}

// your code here