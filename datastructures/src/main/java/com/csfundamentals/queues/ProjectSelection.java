/*
 Discussion at the interview time
 Maximize profit 
Input: Project Capitals=[0,1,2], Project Profits=[1,2,3], Initial Capital=1, Number of Projects=2
Output: 6
Initial capital = 1
select 2nd project. 

profit 2 

ic = 1+2 = 3

u select 3rd project
profit of 3
ic = 3+3

profit = 6

project size = 1, project size 2 



public selectProjectsToMaximizeProfit(int pc[] int pp[] , int maxNumProjects, int ic){
Queue<Project> projectQueue = new PriorityQueue<Project>();

loadAllProjectsToHeap(int pc[] int pp[],projectQueue);
int selectedProjectCount = 0;
int currentCapital = ic;
while(!projectQueue.isEmpty() && selectedProjectCount < maxNumProjects){
     Project project = projectQueue.peek();
     if(project.capital<= currentCapital){
         selectedProjectCount++;
         currentCapital = currentCapital - project.capital + project.profit;
         projectQueue.poll();
     }
    }
}

public void loadAllProjectsToHeap(int pc[] int pp[], Queue<Project> projectQueue){
for (int i=0; i< pc.length; i++){
    projectQueue.enqueue(new Project(pc[i], pp[i]))
}
}

class Project implements Comparator{
int capital;
int profit;

Project(int capital, int profit){
    this.capital=capital;
    this.profit=profit;
}

@override
public int compare(Project p1, Project p2){
if(p1.capital<p2.capital){
return -1;
}
else{
return 1;
}
else if(p1.capital>p2.captial){
return 1;
}
else if(p1.profit<p2.profit){
return 1;
}
else{
return -1;
}

}
}


[0,1,1,2]
[5,4 ,6, 4]


[0,5], [1,6], [1,4], [2,4]
1+5+6-1=11

[0,1,1,2]
[5,4,6,8]

[0,5], [1,6], [1,4], [2,8]



 */
package com.csfundamentals.queues;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class ProjectSelection {
	public static void main(String args[]) {
		int pc[] = { 3, 1, 7, 2 };
		int pp[] = { 5, 4, 6, 8 };
		int maxNumProjects = 2;
		int ic = 1;
		ProjectSelection projectSelection = new ProjectSelection();
		System.out.println(projectSelection.selectProjectsToMaximizeProfit(pc, pp, maxNumProjects, ic));
	}

	public int selectProjectsToMaximizeProfit(int pc[], int pp[], int maxNumProjects, int ic) {
		ProjectCapitalComparator projectCapitalComparator = new ProjectCapitalComparator();
		ProjectProfitComparator projectProfitComparator = new ProjectProfitComparator();
		PriorityQueue<Project> minProjectCapitalQueue = new PriorityQueue<Project>(pc.length, projectCapitalComparator);

		PriorityQueue<Project> maxProjectProfitQueue = new PriorityQueue<Project>(pp.length, projectProfitComparator);
		loadAllProjectsToHeap(pc, pp, minProjectCapitalQueue, maxProjectProfitQueue);
		int selectedProjectCount = 0;
		int currentCapital = ic;
		while (!minProjectCapitalQueue.isEmpty() && !maxProjectProfitQueue.isEmpty()
				&& selectedProjectCount < maxNumProjects) {
			boolean selectedFromMinHeap = false;
			boolean selectedFromMaxHeap = false;
			Project project1 = minProjectCapitalQueue.peek();
			Project project2 = maxProjectProfitQueue.peek();
			System.out.println(minProjectCapitalQueue);
			System.out.println(maxProjectProfitQueue);
			Project selectedProject = null;
			if (project1.equals(project2) && project1.capital <= currentCapital) {
				selectedFromMinHeap = true;
				selectedFromMaxHeap = true;
				selectedProject = project1;
			} else if (project2.capital <= currentCapital) {
				selectedProject = project2;
				selectedFromMaxHeap = true;
			} else {
				selectedProject = project1;
				selectedFromMinHeap = true;
			}
			System.out.println("selectedProject:" + selectedProject);
			selectedProjectCount++;
			currentCapital = currentCapital - selectedProject.capital + selectedProject.profit;
			if (selectedFromMinHeap) {
				minProjectCapitalQueue.poll();
			} else {
				minProjectCapitalQueue.remove(selectedProject);
			}
			if (selectedFromMaxHeap) {
				maxProjectProfitQueue.poll();
			} else {
				maxProjectProfitQueue.remove(selectedProject);
			}
		}
		return currentCapital;
	}

	public void loadAllProjectsToHeap(int pc[], int pp[], Queue<Project> minProjectCapitalQueue,
			Queue<Project> maxProjectProfitQueue) {
		for (int i = 0; i < pc.length; i++) {
			if (pc[i] < pp[i]) {
				minProjectCapitalQueue.add(new Project(pc[i], pp[i]));
				maxProjectProfitQueue.add(new Project(pc[i], pp[i]));
			}
		}
		System.out.println(minProjectCapitalQueue);
		System.out.println(maxProjectProfitQueue);
	}

}

class Project {
	public int capital;
	public int profit;

	Project(int capital, int profit) {
		this.capital = capital;
		this.profit = profit;
	}

	@Override
	public String toString() {
		return "Project [capital=" + capital + ", profit=" + profit + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + capital;
		result = prime * result + profit;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (capital != other.capital)
			return false;
		if (profit != other.profit)
			return false;
		return true;
	}

}

class ProjectCapitalComparator implements Comparator<Project> {
	@Override
	public int compare(Project p1, Project p2) {
		if (p1.capital < p2.capital) {
			return -1;
		} else if (p1.capital > p2.capital) {
			return 1;
		} else if (p1.profit < p2.profit) {
			return 1;
		} else {
			return -1;
		}

	}
}

class ProjectProfitComparator implements Comparator<Project> {
	@Override
	public int compare(Project p1, Project p2) {
		if (p1.profit < p2.profit) {
			return 1;
		} else if (p1.profit > p2.profit) {
			return -1;
		} else if (p1.capital < p2.capital) {
			return -1;
		} else {
			return 1;
		}

	}

}
