/**
 * This class represents a train. Each train has a train number,
 * destination, arrival time and transfer time. Trains are nodes for a linked list
 * within a track.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     R01
 */
public class Train {
    private Train next;
    private Train prev;
    private int trainNumber;
    private String destination;
    private int arrivalTime;
    private int transferTime;
    private int arrivalMin;
    private int transferMin;
    public Train(){
    }

    /**
     * Constructor for Train class
     * @param number
     * the train number
     * @param d
     * the destination
     * @param arrive
     * the arrival time (24-hr format)
     * @param transfer
     * minutes until transfer
     */
    public Train(int number, String d, String arrive, int transfer){
        this.trainNumber = number;
        this.destination = d;
        this.arrivalTime = Integer.parseInt(arrive);
        this.arrivalMin = Integer.parseInt(arrive.substring(2,4)) + Integer.parseInt(arrive.substring(0,2)) * 60;
        this.transferMin = arrivalMin + transfer;
        this.transferTime = (transferMin % 60) + (transferMin / 60) * 100;
    }

    /**
     * Getter for train number
     * @return
     * returns the train number
     */
    public int getTrainNumber(){
        return this.trainNumber;
    }
    /**
     * @return
     * returns the arrival time of this train
     */
    public int getArrivalTime(){
        return this.arrivalTime;
    }
    /**
     * @return
     * returns the transfer time of this train
     */
    public int getTransferTime(){
        return this.transferTime;
    }
    /**
     * @return
     * returns the destination of this train
     */
    public String getDestination(){
        return this.destination;
    }
    /**
     * @return
     * returns the arrival time of this train in minutes
     */
    public int getArrivalMin(){
        return this.arrivalMin;
    }
    /**
     * @return
     * returns the transfer time of this train in minutes
     */
    public int getTransferMin(){
        return this.transferMin;
    }

    /**
     * Sets this train's link to the next train in the list
     * @param nextTrain
     * The train to set the link to
     */
    public void setNext(Train nextTrain){
        next = nextTrain;
    }
    /**
     * Sets this train's link to the previous train in the list
     * @param prevTrain
     * The train to set the link to
     */
    public void setPrev(Train prevTrain){
        prev = prevTrain;
    }
    /**
     * @return
     * returns the next train in the list
     */
    public Train getNext(){
        return next;
    }
    /**
     * @return
     * returns the previous train in the list
     */
    public Train getPrev(){
        return prev;
    }

    /**
     * Compares this train with another object.
     * Two trains are equal if and only if their train numbers are equal.
     * @param o
     * The object to compare.
     * @return
     * Returns a boolean indicating if the trains are equal.
     */
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Train that = (Train) o;
        return (this.trainNumber == that.trainNumber);
    }

    /**
     * @return
     * Returns a textual representation of this Train object.
     */
    public String toString(){
        return "Selected Train: \n" +
                "    Train Number: " + this.getTrainNumber() + "\n" +
                "    Train Destination: " + this.getDestination() + "\n" +
                "    Arrival Time: " + this.getArrivalTime() + "\n" +
                "    Departure Time: " + this.getTransferTime() + "\n";
    }
}