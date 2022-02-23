/*
 * M415 2019-2020: additional algorithmic
 */
package TD3.interval;
/**
 * @author gh002742
 */
//see : https://www.careercup.com/question?id=12743699

import java.util.*;
import java.util.stream.*;
/**
 *
 * The IntervalTreeSet Class implements greedy algorithms for finding the
 * largest subset of compatible intervals for a given set of interval requests
 * 
 * Precondition : a set of non empty intervals
 * 
 * Postcondition : the cardinality of the largest subset of non intersecting
 * intervals
 * 
 * @author m6k415
 * @version 1.16.0227
 */
public class IntervalTreeSet extends TreeSet<Interval> {
	/*
	 * https://docs.oracle.com/javase/8/docs/api/java/util/TreeSet.html
	 */

	private static final long serialVersionUID = 1L;

	/**
	 * compute the number of conflicts for a given interval inside current set
	 * 
	 * @param interval
	 *            : the given interval
	 */
	public int countConflicts(Interval interval) {
		int count = 0;
		for (Interval current : this) {
			// if (current > interval) return count;
			if (interval.getEnd() <= current.getStart())
				break;
			if (interval.intersects(current)) {
				// interval does conflict with itself
				count++;
			}
		}
		return count;
	}

	/**
	 * remove elements from current set that conflict with a given interval
	 * 
	 * @param interval
	 *            : the given interval
	 */
	public void removeConflicts(Interval interval) {
		List<Interval> conflicts = new ArrayList<>();
		for (Interval current : this) {
			if (interval.getEnd() <= current.getStart())
				break;
			if (interval.intersects(current) && (current != interval)) {
				conflicts.add(current);
			}
		}
		this.removeAll(conflicts);
	}

	/**
	 * compute the maximum conflict number for a given time in current set
	 * 
	 * @return max
	 */
	public int intersectionMax() {
		Map<Interval,Integer> map = new HashMap<>();
		for(Interval interval : this) {
			map.put(interval,this.countConflicts(interval));
		}
		List<Integer> list =  map.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.map(Map.Entry::getValue).collect(Collectors.toList());
		return list.get(list.size()-1);
	}

	/**
	 * compute the largest conflictless subset of current set using algorithm 1:
	 * take the interval that starts the earlier first.
	 * 
	 * Iterative implementation.
	 * 
	 * @return accepted : the set of compatible intervals selected
	 */
	public IntervalTreeSet getStartEarlierFirst() {
		// TODO : write as others method with removeConflicts
		IntervalTreeSet accepted = new IntervalTreeSet();
		accepted.addAll(this); // copy of this
		// iterate over original this and remove elements from the accepted copy
		Iterator<Interval> iterator = this.iterator();
		Interval current;
		if (iterator.hasNext()) {
			current = iterator.next();
			Interval next;
			while (iterator.hasNext()) {
				// remove conflicting intervals without using removeConflicts
				next = iterator.next();
				if (next.intersects(current)) {
					accepted.remove(next);
				} else {
					current = next;
				}
			}
		}
		return accepted;
	}

	/**
	 * compute the largest conflictless subset of current set using algorithm 1:
	 * take the interval that starts the earlier first.
	 * 
	 * Recursive implementation.
	 * 
	 * @param sortedSet : set of intervals sorted by starting time
	 */
	public void getStartEarlierRec(SortedSet<Interval> sortedSet) {
		if (!sortedSet.isEmpty()) {
			Interval head = sortedSet.first();
			this.add(head);
			// build an empty interval matching the end of head
			Interval next = new Interval(head.getEnd(), head.getEnd());
			// check with head instead : wrong
			// returns the subset of intervals greater or equal to next (all but
			// those intersecting head)
			this.getStartEarlierRec(sortedSet.tailSet(next));
		}
	}

	/**
	 * compute the largest conflictless subset of current set using algorithm 2:
	 * take the smallest interval first
	 * 
	 * @return accepted : the set of compatible intervals selected
	 */
	public IntervalTreeSet getSmallerFirst() {
		// use tab to sort intervals according to appropriate Comparator
		Interval[] tab = this.toArray(new Interval[0]);
		Arrays.sort(tab, Interval.IntervalSizeComparator);

		// copy from which we remove conflicting intervals
		IntervalTreeSet accepted = new IntervalTreeSet();
		accepted.addAll(this);

		// Collections.sort(accepted, Interval.IntervalSizeComparator);
		// this does not make sense for Set or SortedSet
		// One could use List, but then tailSet will be missing

		// iterate over tab and remove elements from the accepted copy
		for (Interval interval : tab) { //
			if (accepted.contains(interval)) { // same objects!!!
				accepted.removeConflicts(interval);
			}
		}
		return accepted;
	}

	/**
	 * compute the largest conflictless subset of current set using algorithm 3:
	 * take the interval with the smaller number of conflicts first
	 * 
	 * @return accepted : the set of compatible intervals selected
	 */
	public IntervalTreeSet getLessConflictsFirst() {
		// TODO Exercice 1 - question 2.d
		
		Map<Interval,Integer> map = new HashMap<>();
		for(Interval interval : this) {
			map.put(interval,this.countConflicts(interval));
		}
		List<Interval> sortedList = map.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey).collect(Collectors.toList());
		// copy from which we remove conflicting intervals
		IntervalTreeSet accepted = new IntervalTreeSet();
		accepted.addAll(sortedList);

		// Collections.sort(accepted, Interval.IntervalSizeComparator);
		// this does not make sense for Set or SortedSet
		// One could use List, but then tailSet will be missing

		// iterate over tab and remove elements from the accepted copy
		for (Interval interval : sortedList) { //
			if (accepted.contains(interval)) { // same objects!!!
				accepted.removeConflicts(interval);
			}
		}
		return accepted;
	}

	/**
	 * compute the largest conflictless subset of current set using optimal
	 * algorithm 4: take the interval that finishes the earlier first
	 * 
	 * @return accepted : the set of compatible intervals selected
	 */
	public IntervalTreeSet getFinishEarlierFirst() {
		// TODO Exercice 1 - question 2.c
		Interval[] tab = this.toArray(new Interval[0]);
		Arrays.sort(tab, Interval.IntervalFinishComparator);

		// copy from which we remove conflicting intervals
		IntervalTreeSet accepted = new IntervalTreeSet();
		accepted.addAll(this);

		// Collections.sort(accepted, Interval.IntervalSizeComparator);
		// this does not make sense for Set or SortedSet
		// One could use List, but then tailSet will be missing

		// iterate over tab and remove elements from the accepted copy
		for (Interval interval : tab) { //
			if (accepted.contains(interval)) { // same objects!!!
				accepted.removeConflicts(interval);
			}
		}
		return accepted;
	}
	
	

	/**
	 * display elements from current set with dashes [---w---]
	 * 
	 */
	public void display() {
		int i = this.size();
		System.out.println(
				"     012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
		for (Interval current : this) {
			System.out.printf("%3d: %s%n", i, current.display());
			i--;
		}
	}

	public static void main(String[] args) {

		// see also the Junit tests provided with this Class

		Interval[] tab = { new Interval(10, 15), new Interval(20, 25), new Interval(5, 35), new Interval(40, 50),
				new Interval(55, 63), new Interval(60, 65), };

		IntervalTreeSet requests = new IntervalTreeSet();
		Collections.addAll(requests, tab);

		IntervalTreeSet[] req = new IntervalTreeSet[4];
		req[0] = new IntervalTreeSet();
		Collections.addAll(req[0], tab);

		System.out.println("Requests: " + requests.toString());
		requests.display();
		IntervalTreeSet earlier = requests.getStartEarlierFirst();
				System.out.println("Earlier started first iterative: " + earlier.toString());
		earlier.display();

		Interval g = new Interval(70, 85);
		requests.add(g);
		Interval h = new Interval(40, 45);
		requests.add(h);

		System.out.println();
		System.out.println("Requests: " + requests.toString());
		requests.display();
		IntervalTreeSet earlierRec = new IntervalTreeSet();
		earlierRec.getStartEarlierRec(requests);
		System.out.println("Earlier started first recursive: " + earlierRec.toString());
		earlierRec.display();

		System.out.println();
		System.out.println("Requests: " + requests.toString());
		requests.display();
		IntervalTreeSet smaller = requests.getSmallerFirst();
		System.out.println("Smaller first: " + smaller.toString());
		smaller.display();
		
		
		System.out.println();
		System.out.println("Requests: " + requests.toString());
		requests.display();
		IntervalTreeSet lessConflits = requests.getLessConflictsFirst();
		System.out.println("lessConflits first: " + lessConflits.toString());
		lessConflits.display();
		
		
		System.out.println();
		System.out.println("Requests: " + requests.toString());
		requests.display();
		IntervalTreeSet finishEarlier = requests.getFinishEarlierFirst();
		System.out.println("FinishEarlierFirst first: " + finishEarlier.toString());
		finishEarlier.display();
		
		
		
		System.out.println("Le nombre d'intersections max est de :"+requests.intersectionMax());
	}

}
