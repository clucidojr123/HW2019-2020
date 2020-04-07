import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * This class represents a station that includes a the interface between the user and the
 * station to perform different tasks. The station also contains a linked list of tracks,
 * which themselves have a linked list of trains.
 * @author Cesare Lucido
 *     e-mail: cesare.lucido@stonybrook.edu
 *     sbid: 112831455
 *     R01
 */
public class Station {
    private Track head;
    private Track tail;
    private Track cursor;
    private int size;

    /**
     * Constructor for the station
     */
    public Station(){
        head = null;
        tail = null;
        cursor = null;
        this.size = 0;
    }
    /**
     * This is the main method for interacting with the user.
     * A menu is provided with multiple options for the user to select.
     * The menu will pop up after every menu interaction until the user selects q or Q,
     * which will terminate the program.
     * If the user enters invalid inputs, error messages will appear, bringing the user back to the menu.
     */
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        Station stony = new Station();
        boolean status = true;
        String menu = "|-----------------------------------------------------------------------------|\n" +
                "| Train Options                       | Track Options                         |\n" +
                "|    A. Add new Train                 |    TA. Add Track                      |\n" +
                "|    N. Select next Train             |    TR. Remove selected Track          |\n" +
                "|    V. Select previous Train         |    TS. Switch Track                   |\n" +
                "|    R. Remove selected Train         |   TPS. Print selected Track           |\n" +
                "|    P. Print selected Train          |   TPA. Print all Tracks               |\n" +
                "|-----------------------------------------------------------------------------|\n" +
                "| Station Options                                                             |\n" +
                "|   SI. Print Station Information                                             |\n" +
                "|    Q. Quit                                                                  |\n" +
                "|-----------------------------------------------------------------------------|";
        while(status) {
            System.out.println(menu + "\nEnter a selection: ");
            try {
                String pick = scan.next().toUpperCase();
                switch (pick) {
                    case ("A"):
                        //Checks if the station is empty
                        if(stony.cursor == null)
                            throw new NoTrackSelectedException();
                        System.out.println("Enter train number: ");
                        int newNumber = scan.nextInt();
                        //Checks if the input is negative or zero
                        if(newNumber <= 0)
                            throw new IllegalArgumentException();
                        scan.nextLine();
                        System.out.println("Enter train destination: ");
                        String newDescribe = scan.nextLine();
                        System.out.println("Enter train arrival time: ");
                        String newArrive = scan.nextLine();
                        //Checks if the time input is formatted correctly
                        if(newArrive.length() != 4 || Integer.parseInt(newArrive.substring(0,2)) > 24
                                || Integer.parseInt(newArrive.substring(2,4)) > 60)
                            throw new IllegalArgumentException();
                        System.out.println("Enter train transfer time: ");
                        int newTransfer = scan.nextInt();
                        //Checks if the input is negative or zero
                        if(newTransfer <= 0)
                            throw new IllegalArgumentException();
                        Train temp = new Train(newNumber, newDescribe,newArrive,newTransfer);
                        stony.cursor.addTrain(temp);
                        System.out.println("Train successfully added to the selected track.");
                        break;
                    case ("N"):
                        //Checks if the station is empty
                        if(stony.cursor == null)
                            throw new NoTrackSelectedException();
                        if(stony.cursor.selectNextTrain())
                            System.out.println("Cursor has been moved to the next train.");
                        else
                            System.out.println("Selected train not updated: Already at end of Track list.");
                        break;
                    case ("V"):
                        //Checks if the station is empty
                        if(stony.cursor == null)
                            throw new NoTrackSelectedException();
                        if(stony.cursor.selectPreviousTrain())
                            System.out.println("Cursor has been moved to the previous train.");
                        else
                            System.out.println("Selected train not updated: Already at start of Track list.");
                        break;
                    case ("R"):
                        //Checks if the station is empty
                        if(stony.cursor == null)
                            throw new NoTrackSelectedException();
                        Train removedTrain = stony.cursor.removeSelectedTrain();
                        //Checks if the selected track has zero trains
                        if(removedTrain == null)
                            throw new NoTrainSelectedException();
                        System.out.println("Train No. " + removedTrain.getTrainNumber()
                                + " to " + removedTrain.getDestination() + " has been removed from Track "
                                + stony.cursor.getTrackNumber());
                        break;
                    case ("P"):
                        //Checks if the station is empty
                        if(stony.cursor == null)
                            throw new NoTrackSelectedException();
                        stony.cursor.printSelectedTrain();
                        break;
                    case ("TA"):
                        System.out.println("Enter track number: ");
                        int newTrackNum = scan.nextInt();
                        //Checks if the input is negative or zero
                        if(newTrackNum <= 0)
                            throw new IllegalArgumentException();
                        Track tempTrack = new Track(newTrackNum);
                        stony.addTrack(tempTrack);
                        System.out.println("Track successfully added to the station.");
                        break;
                    case ("TR"):
                        //Checks if the station is empty
                        if(stony.cursor == null)
                            throw new NoTrackSelectedException();
                        Track removedTrack = stony.removeSelectedTrack();
                        System.out.println("Closed track " + removedTrack.getTrackNumber() + ".");
                        break;
                    case ("TS"):
                        //Checks if the station is empty
                        if(stony.cursor == null)
                            throw new NoTrackSelectedException();
                        System.out.println("Enter track number: ");
                        int switchNum = scan.nextInt();
                        //Checks if the input is negative or zero
                        if(switchNum <= 0)
                            throw new IllegalArgumentException();
                        if(stony.selectTrack(switchNum))
                            System.out.println("Switched to track " + switchNum + ".");
                        else
                            System.out.println("Could not switch to track " + switchNum + ": Track " +
                                    switchNum + " does not exist.");
                        break;
                    case ("TPS"):
                        //Checks if the station is empty
                        if(stony.cursor == null)
                            throw new NoTrackSelectedException();
                        stony.printSelectedTrack();
                        break;
                    case ("TPA"):
                        //Checks if the station is empty
                        if(stony.cursor == null)
                            throw new NoTrackSelectedException();
                        stony.printAllTracks();
                        break;
                    case ("SI"):
                        System.out.println(stony.toString());
                        break;
                    case ("Q"):
                        System.out.println("Program terminating normally...");
                        scan.close();
                        status = false;
                        break;
                    default:
                        System.out.println("Invalid menu option. Please try again.");
                }
            } catch (IllegalArgumentException w) {
                System.out.println("Invalid input. Please try again.");
            } catch (InputMismatchException w) {
                System.out.println("Invalid input. Please try again.");
                scan.nextLine();
            } catch (TrackAlreadyExistsException e) {
                System.out.println("There is already a track with the same number! Please try again.");
            } catch (InvalidTrainException e) {
                System.out.println("The train you are trying to add is invalid! Please try again.");
            } catch (TrainAlreadyExistsException e) {
                System.out.println("There is already a train in this track with the same number! Please try again.");
            } catch (NoTrainSelectedException e) {
                System.out.println("There are no trains within this track; no train is selected. " +
                        "Please try again.");
            } catch (NoTrackSelectedException e) {
                System.out.println("There are no tracks in this station; no track is selected. " +
                        "Please try again.");
            }
        }
    }

    /**
     * This method takes in a new track and attempts to add it to the station.
     * @param newTrack
     * The new track to add
     * @throws TrackAlreadyExistsException
     * Thrown if a track already in the station has the same track number as the new track.
     */
    public void addTrack(Track newTrack) throws TrackAlreadyExistsException {
        if(cursor == null) {
            head = newTrack;
            cursor = newTrack;
            tail = newTrack;
            this.size++;
        } else {
            Track nodePtr = head;
            while(nodePtr != null) {
                if (nodePtr.getTrackNumber() == newTrack.getTrackNumber())
                    throw new TrackAlreadyExistsException();
                nodePtr = nodePtr.getNext();
            }
            nodePtr = head;
            while(nodePtr != null) {
                if(nodePtr.getNext() == null) {
                    //New Track # is larger than node #, node is tail
                    if (nodePtr.getTrackNumber() < newTrack.getTrackNumber()) {
                        newTrack.setNext(nodePtr.getNext());
                        newTrack.setPrev(nodePtr);
                        newTrack.getPrev().setNext(newTrack);
                        tail = newTrack;
                        cursor = newTrack;
                        this.size++;
                        break;
                    } //New track # is smaller than node #, node is only track in list
                    else if(nodePtr.getTrackNumber() > newTrack.getTrackNumber() && nodePtr.getPrev() == null) {
                        newTrack.setNext(nodePtr);
                        nodePtr.setPrev(newTrack);
                        head = newTrack;
                        cursor = newTrack;
                        this.size++;
                        break;
                    }
                } // New Track # is larger than node # and
                // new track # is smaller than the node following's #
                else if ((nodePtr.getTrackNumber() < newTrack.getTrackNumber() &&
                        nodePtr.getNext().getTrackNumber() > newTrack.getTrackNumber())) {
                    newTrack.setNext(nodePtr.getNext());
                    newTrack.getNext().setPrev(newTrack);
                    newTrack.setPrev(nodePtr);
                    newTrack.getPrev().setNext(newTrack);
                    cursor = newTrack;
                    this.size++;
                    break;
                } // New Track # is smaller than node # and either the track before the node is null
                // or the track's # before the node is smaller than the new track #
                else if((newTrack.getTrackNumber() < nodePtr.getTrackNumber()
                        && (nodePtr.getPrev() == null
                        || newTrack.getTrackNumber() > nodePtr.getPrev().getTrackNumber()))) {
                    if(nodePtr.getPrev() == null) {
                        newTrack.setNext(nodePtr);
                        newTrack.setPrev(nodePtr.getPrev());
                        nodePtr.setPrev(newTrack);
                        head = newTrack;
                    } else {
                        nodePtr.setPrev(newTrack);
                        newTrack.getPrev().setNext(newTrack);
                    }
                    cursor = newTrack;
                    this.size++;
                    break;
                }
                nodePtr = nodePtr.getNext();
            }
        }
    }

    /**
     * Removes the selected track if possible. Moves the cursor to the next track
     * unless the track removed was the tail, then the cursor is moved to the previous track.
     * If there was only one track in the list, then the cursor will become null.
     * @return
     * Returns the track removed or null if the list is empty.
     */
    public Track removeSelectedTrack(){
        Track temp;
        if(head == null && cursor == null && tail == null)
            return null;
        else if(head != null && head == tail && head == cursor) {
            temp = head;
            head = null;
            tail = null;
            cursor = null;
            return temp;
        }
        else if(head != null && head == cursor) {
            temp = head;
            head = head.getNext();
            cursor = head;
            return temp;
        } else if(tail != null && tail == cursor) {
            temp = tail;
            tail = tail.getPrev();
            cursor = tail;
            return temp;
        } else if(cursor != null){
            temp = cursor;
            cursor.getPrev().setNext(cursor.getNext());
            cursor.getNext().setPrev(cursor.getPrev());
            cursor = cursor.getNext();
            return temp;
        }
        return null;
    }
    /**
     * Prints the selected track of this station by calling the toString method of the track.
     */
    public void printSelectedTrack(){
        System.out.format("Track " + cursor.getTrackNumber() + "* (%.2f%" + "%" + " Utilization Rate) :\n",
                cursor.getUtilizationRate());
        System.out.println(cursor.toString());
    }
    /**
     * Prints all of the tracks in the station by calling the toString method of each track.
     * If the station is empty then a different message is printed.
     */
    public void printAllTracks(){
        Track nodePtr = head;
        while(nodePtr != null) {
            if(cursor == nodePtr)
                System.out.format("Track " + nodePtr.getTrackNumber() + " * (%.2f%" + "%" + " Utilization Rate) :\n",
                        nodePtr.getUtilizationRate());
            else
                System.out.format("Track " + nodePtr.getTrackNumber() + " (%.2f%" + "%" + " Utilization Rate) :\n",
                        nodePtr.getUtilizationRate());
            System.out.println(nodePtr.toString());
            System.out.println();
            nodePtr = nodePtr.getNext();
        }
    }

    /**
     * Selects the track with the track number of the input.
     * @param trackSelector
     * The track number to search for.
     * @return
     * Returns a boolean indicating whether or not the track with the inputted number could be found/selected.
     */
    public boolean selectTrack(int trackSelector){
        if(cursor == null)
            return false;
        Track nodePtr = head;
        while(nodePtr != null) {
            if(nodePtr.getTrackNumber() == trackSelector) {
                cursor = nodePtr;
                return true;
            }
            nodePtr = nodePtr.getNext();
        }
        return false;
    }

    /**
     * @return
     * Returns a textual representation of this Train object.
     */
    public String toString(){
        if(this.size == 0)
            return "This station currently has 0 tracks.";
        String dab = "Station (" + this.size +" Tracks) :\n";
        Track nodePtr = head;
        while(nodePtr != null) {
            dab = dab.concat(String.format("    Track " + nodePtr.getTrackNumber() + ": "
                            + nodePtr.getSize() + " trains arriving (%.2f%" + "%" + " Utilization Rate) \n",
                    nodePtr.getUtilizationRate()));
            nodePtr = nodePtr.getNext();
        }
        return dab;
    }
}