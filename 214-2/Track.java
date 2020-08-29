/**
 * This class represents a track, which holds a linked list of trains.
 * Tracks are also nodes for a linked list in a station class.
 * Each track has a track number and a utilization rate.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     R01
 */
public class Track {
    private Train head;
    private Train tail;
    private Train cursor;
    private Track next;
    private Track prev;
    private double utilizationRate;
    private int trackNumber;
    private int size;
    public Track(){
        head = null;
        tail = null;
        cursor = null;
        this.size = 0;
    }

    /**
     * Constructor for this track
     * @param t
     * the track number
     */
    public Track(int t){
        head = null;
        tail = null;
        cursor = null;
        this.trackNumber = t;
        this.size = 0;

    }

    /**
     * @return
     * returns the number of trains in the list
     */
    public int getSize(){
        return this.size;
    }
    /**
     * @return
     * returns the track number of this track
     */
    public int getTrackNumber(){
        return this.trackNumber;
    }
    /**
     * Sets this track's link to the next track in the list
     * @param nextTrack
     * The track to set the link to
     */
    public void setNext(Track nextTrack){
        next = nextTrack;
    }
    /**
     * Sets this track's link to the previous track in the list
     * @param prevTrack
     * The track to set the link to
     */
    public void setPrev(Track prevTrack){
        prev = prevTrack;
    }

    /**
     * @return
     * returns the next track in the list
     */
    public Track getNext(){
        return next;
    }
    /**
     * @return
     * returns the previous track in the list
     */
    public Track getPrev(){
        return prev;
    }

    /**
     * Setter for the utilization rate of this track
     * @param d
     * the new rate
     */
    public void setUtilizationRate(double d){
        this.utilizationRate = d;
    }

    /**
     * Calculation for the utilization rate for this track. This method is called after a train is
     * successfully added or removed.
     */
    public void calculateUtilizationRate(){
        if(head == null)
            this.setUtilizationRate(0.0);
        else {
            Train nodePtr = head;
            double total = 0;
            while (nodePtr != null) {
                total += nodePtr.getTransferMin() - nodePtr.getArrivalMin();
                nodePtr = nodePtr.getNext();
            }
            this.setUtilizationRate(total / 1440.0);
        }
    }

    /**
     * @return
     * returns the utilization rate of this track
     */
    public double getUtilizationRate(){
        return this.utilizationRate;
    }

    /**
     * This method takes in a new train and attempts to add it to the track.
     * @param newTrain
     * The new train to add
     * @throws InvalidTrainException
     * Thrown if the new train would create a scheduling conflict within the track
     * @throws TrainAlreadyExistsException
     * Thrown if a train already in the track has the same train number as the new train.
     */
    public void addTrain(Train newTrain) throws InvalidTrainException, TrainAlreadyExistsException {
        if(cursor == null) {
            head = newTrain;
            cursor = newTrain;
            tail = newTrain;
            this.calculateUtilizationRate();
            this.size++;
        } else {
            Train temp = cursor;
            Train nodePtr = head;
            while(nodePtr != null) {
                if (nodePtr.equals(newTrain))
                    throw new TrainAlreadyExistsException();
                nodePtr = nodePtr.getNext();
            }
            nodePtr = head;
            while(nodePtr != null) {
                if(nodePtr.getNext() == null) {
                    //New train arrives after node leaves, node is the tail as well
                    if (nodePtr.getTransferMin() < newTrain.getArrivalMin()) {
                        newTrain.setNext(nodePtr.getNext());
                        newTrain.setPrev(nodePtr);
                        newTrain.getPrev().setNext(newTrain);
                        tail = newTrain;
                        cursor = newTrain;
                        this.calculateUtilizationRate();
                        this.size++;
                        break;
                    } //New train leaves before node arrives and there was only one train in list
                    else if(nodePtr.getArrivalMin() > newTrain.getTransferMin() && nodePtr.getPrev() == null) {
                        newTrain.setNext(nodePtr);
                        nodePtr.setPrev(newTrain);
                        this.calculateUtilizationRate();
                        this.size++;
                        head = newTrain;
                        cursor = newTrain;
                        break;
                    }
                } // New train arrives after node and new train leaves before the node following would arrive
                else if ((nodePtr.getTransferMin() < newTrain.getArrivalMin() &&
                        nodePtr.getNext().getArrivalMin() > newTrain.getTransferMin())) {
                        newTrain.setNext(nodePtr.getNext());
                        newTrain.getNext().setPrev(newTrain);
                        newTrain.setPrev(nodePtr);
                        newTrain.getPrev().setNext(newTrain);
                        cursor = newTrain;
                        this.calculateUtilizationRate();
                        this.size++;
                        break;
                } // New train leaves before node arrives and either the train before the node is null
                // or the train before the node leaves before the new train arrives
                else if((newTrain.getTransferMin() < nodePtr.getArrivalMin()
                        && (nodePtr.getPrev() == null
                        || newTrain.getArrivalMin() > nodePtr.getPrev().getTransferMin()))) {
                    if(nodePtr.getPrev() == null) {
                        newTrain.setNext(nodePtr);
                        newTrain.setPrev(nodePtr.getPrev());
                        nodePtr.setPrev(newTrain);
                        head = newTrain;
                    } else {
                        nodePtr.setPrev(newTrain);
                        newTrain.getPrev().setNext(newTrain);
                    }
                    cursor = newTrain;
                    this.calculateUtilizationRate();
                    this.size++;
                    break;
                }
                nodePtr = nodePtr.getNext();
                }
            if(temp == cursor)
                throw new InvalidTrainException();
        }
    }

    /**
     * Prints the selected train of this track by calling the toString method of the train.
     */
    public void printSelectedTrain(){
        System.out.print(cursor.toString());
    }

    /**
     * Removes the selected train if possible. Moves the cursor to the next train
     * unless the train removed was the tail, then the cursor is moved to the previous train.
     * If there was only one train in the list, then the cursor will become null.
     * @return
     * Returns the train removed or null if the list is empty.
     */
    public Train removeSelectedTrain(){
        Train temp;
        if(head == null && cursor == null && tail == null)
            return null;
        else if(head != null && head == tail && head == cursor) {
            temp = head;
            head = null;
            tail = null;
            cursor = null;
            this.calculateUtilizationRate();
            this.size--;
            return temp;
        }
        else if(head != null && head == cursor) {
            temp = head;
            head = head.getNext();
            cursor = head;
            this.calculateUtilizationRate();
            this.size--;
            return temp;
        } else if(tail != null && tail == cursor) {
            temp = tail;
            tail = tail.getPrev();
            cursor = tail;
            this.calculateUtilizationRate();
            this.size--;
            return temp;
        } else if(cursor != null){
            temp = cursor;
            cursor.getPrev().setNext(cursor.getNext());
            cursor.getNext().setPrev(cursor.getPrev());
            cursor = cursor.getNext();
            this.calculateUtilizationRate();
            this.size--;
            return temp;
        }
        return null;
    }

    /**
     * Moves the cursor to the next train in the list if possible
     * @return
     * Returns a boolean indicating whether or not the cursor could be moved forward.
     * @throws NoTrainSelectedException
     * Thrown when the track is empty.
     */
    public boolean selectNextTrain() throws NoTrainSelectedException {
        if(cursor == null)
            throw new NoTrainSelectedException();
        if(cursor.getNext() == null)
            return false;
        else {
            cursor = cursor.getNext();
            return true;
        }
    }

    /**
     * Moves the cursor to the previous train in the list if possible
     * @return
     * Returns a boolean indicating whether or not the cursor could be moved backwards.
     * @throws NoTrainSelectedException
     * Thrown when the track is empty.
     */
    public boolean selectPreviousTrain() throws NoTrainSelectedException {
        if(cursor == null)
            throw new NoTrainSelectedException();
        if(cursor.getPrev() == null)
            return false;
        else {
            cursor = cursor.getPrev();
            return true;
        }
    }

    /**
     * @return
     * Returns a textual representation of this Track object.
     */
    public String toString(){
        String first = String.format("%-10s%-20s%-10s%20s%20s\n",
                "Selected", "Train Number", "Train Destination", "Arrival Time", "Departure time");
        for(int i = 0; i < 100; i++)
            first = first.concat("-");
        first = first.concat("\n");
        Train nodePtr = head;
        while(nodePtr != null) {
            if(cursor == nodePtr)
                first = first.concat(String.format("%-10s%-20s%-10s%15s%04d%14s%04d\n",
                        "*", nodePtr.getTrainNumber(), nodePtr.getDestination(),
                        "",nodePtr.getArrivalTime(), "",nodePtr.getTransferTime()));
            else
                first = first.concat(String.format("%11s%23s%21s%04d%14s%04d\n",
                        nodePtr.getTrainNumber(), nodePtr.getDestination(),
                        "",nodePtr.getArrivalTime(), "",nodePtr.getTransferTime()));
            nodePtr = nodePtr.getNext();
        }
        return first;
    }
}