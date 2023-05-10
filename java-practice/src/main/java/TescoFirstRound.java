import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/*
 * Problem statement Tesco has around 3200 stores and more than 10% of the stores have around 800 colleagues each. 
 * In a store, a colleague can work for multiple departments. Here are shifts of a colleague in various departments: 
 * In Bakery department: From 8 to 10
In Checkout department: From 10 to 12
In Diary department: From 14 to 19 
Given the above split of shifts, provide an API/method to return the different shifts of a colleague for the day after 
merging contiguous shifts. This will be exposed to the colleague in different UI and help them plan their day accordingly. 
His shift timings in this case are 8 to 12 and 14 to 19.
empid, start, end
empid,


 */
public class TescoFirstRound {
	private static LinkedList<Shift> findShiftsForACollegue(int EmpId, LinkedList<Shift> shifts) {
		Collections.sort(shifts, (a, b) -> a.start - b.start);
		Shift prevShift = shifts.get(0);
		Shift currShift = null;
		Iterator<Shift> itr = shifts.iterator();
		while (itr.hasNext()) {
			currShift = itr.next();
			if (prevShift.end == currShift.start) {
				prevShift.end = currShift.end;
				itr.remove();
			} else {
				prevShift = currShift;
			}
		}
		return shifts;
	}

	public static void main(String[] args) {
		int Empid = 1;
		List<Shift> shifts = List.of(new Shift(1, 8, 9), new Shift(1, 10, 12), new Shift(1, 13, 19),
				new Shift(1, 9, 10));
		LinkedList<Shift> shiftList = new LinkedList<Shift>();
		shiftList.addAll(shifts);
		System.out.println(findShiftsForACollegue(1, shiftList));
	}
}

class Shift {
	public Shift(int Empid, int start, int end) {
		super();
		this.Empid = Empid;
		this.start = start;
		this.end = end;
	}

	int Empid;
	int start;
	int end;

	@Override
	public String toString() {
		return "Shift [Empid=" + Empid + ", start=" + start + ", end=" + end + "]";
	}

}