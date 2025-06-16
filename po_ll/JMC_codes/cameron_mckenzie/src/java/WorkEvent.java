import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;

/**
 * This is a JDK Flight Recorder event
 */
@Label("Threadwork")
@Category("02_JFR_HotMethods")
@Description("Data from one loop run in the worker thread")
public class WorkEvent extends jdk.jfr.Event {
    @Label("Intersection Size")
    @Description("The number of values that were the same in the two collections")
    private int intersectionSize;
    
    public int getIntersectionSize() {
        return intersectionSize;
    }

    public void setIntersectionSize(int intersectionSize) {
        this.intersectionSize = intersectionSize;
    }
}
