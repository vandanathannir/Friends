package friends;

import java.util.ArrayList;
import java.util.Arrays;

import structures.Queue;
import structures.Stack;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) 
	{
		
		class objValues 
		{
			int currObj;
			int prevObj;
			int level;
			objValues(int current, int parent, int lvl) 
			{
				this.currObj = current;
				this.prevObj = parent;
				this.level = lvl;
			}
		}
		objValues[] AR;
		int height = 0;
		int arrElem = 0;
		int parentEl =0;
		int p1Val = g.map.get(p1);
		int p2Val = g.map.get(p2);
		
		Queue<Integer> queue1 = new Queue<Integer>();
		AR = new objValues[g.map.size()];
		Integer intArray[] = new Integer[g.map.size()];
		
		AR[arrElem] = new objValues(p1Val,-1,height);
		intArray[arrElem] = p1Val;
		queue1.enqueue(p1Val);
		
		while(queue1.isEmpty() == false)
		{
			parentEl = queue1.dequeue();
			Friend F1;
			F1 = g.members[parentEl].first;
			height++;
			while (F1 != null) 
			{	
				if (Arrays.asList(intArray).contains(F1.fnum)==false) 
				{
					arrElem++;
					AR[arrElem] = new objValues(F1.fnum,parentEl,height);
					intArray[arrElem] = F1.fnum;
					if (F1.fnum == p2Val)
					{
						while(queue1.isEmpty() == false)
						{
							queue1.dequeue();
						}
						
						break;
					}
					else 
					{
						queue1.enqueue(F1.fnum);
					}
				}
			   F1 = F1.next;
			}
		}
		
		int s1 = intArray[arrElem];
		int parent1 = AR[arrElem].prevObj;
		Stack<Integer> stack1= new Stack<Integer>();
		stack1.push(s1);
		while(parent1 != -1)
		{
			for(int i= arrElem; i>=0; i-- )
			{
				if(intArray[i]==parent1)
				{
					stack1.push(parent1);
					parent1 = AR[i].prevObj; 
				}	
			}
		}
		ArrayList<String> shortestDis = new ArrayList<String>();
		int popped;
		if(intArray[arrElem] == p2Val) 
		{
			while(stack1.isEmpty()==false)
			{
				popped =stack1.pop();
				shortestDis.add(g.members[popped].name);
			}
		}
		//	System.out.println(shortestDis);
		return shortestDis;
	}
	
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		
		/** COMPLETE THIS METHOD **/
		int mapSize= g.map.size();
		Boolean visitStatus[] = new Boolean[mapSize];
		Arrays.fill(visitStatus, false);
		ArrayList<ArrayList<String>> finalCliques = new ArrayList<ArrayList<String>>();
		Queue<Integer> queue1 = new Queue<Integer>();
		for(int i=0; i<mapSize ;i++)
		{
			if(visitStatus[i]==false)
			{
				ArrayList<String> cliques = new ArrayList<String>();
				queue1.enqueue(i);
				while(queue1.isEmpty() == false)
				{
					int j = queue1.dequeue();
					visitStatus[j]=true;
					if(g.members[j].student==true && g.members[j].school.equals(school))//fix
					{
						cliques.add(g.members[j].name);
						
					}
					Friend neighbors = g.members[j].first;
					
					while(neighbors != null) 
					{
						int pal = neighbors.fnum;
						if(visitStatus[pal]==false)
						{
							queue1.enqueue(pal);
						}
						neighbors = neighbors.next;
					}
				}
				if(cliques.isEmpty()==false)
				{
					finalCliques.add(cliques);
				}
			}
			
		}
		System.out.print(finalCliques);
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		return finalCliques;
		
	}
	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) 
	{
		
		/** COMPLETE THIS METHOD **/
		
		int mapSize= g.map.size();
		
		Boolean visitStatus[] = new Boolean[mapSize];
		Arrays.fill(visitStatus, false);
		
		Stack<Integer> stack1 = new Stack<Integer>();
		Integer DFSNum[]      = new Integer[mapSize];
		Integer back[]        = new Integer[mapSize];
		String  connector[]   = new String[mapSize];	
		ArrayList<String> connectorFinal = new ArrayList<String>();
		int con = -1;
		int begin = 0;
		for(int i=0; i<mapSize ;i++)
		{
			int DFScount=0;
			int backCount=0;
			if(visitStatus[i]==false)
			{
				ArrayList<String> DFSFinal = new ArrayList<String>();
				DFScount++;
				backCount++;
				//System.out.println(i);
				//System.out.println(DFScount);
				DFSNum[i]=DFScount;
				back[i] = backCount;
				//System.out.println(DFSNum[i]);
				//System.out.println(back[i]);
				stack1.push(i);
				visitStatus[i]=true;
				Friend neighbors = g.members[i].first;
				
				while(stack1.isEmpty() == false)
				{
					while(neighbors != null) 
					{
						int pal = neighbors.fnum;
						if(visitStatus[pal]==false)
						{
							DFScount++;
							backCount++;
							DFSNum[pal]=DFScount;
							back[pal] = backCount;
							stack1.push(pal);
							visitStatus[pal]=true;
							neighbors = g.members[pal].first;
						}
						else
						{
							neighbors = neighbors.next;
							
						}
					}
					int stackOut = stack1.pop();
					String name = g.members[stackOut].name;
					DFSFinal.add(name);
					if(stack1.isEmpty() == false)
					{
						int stackTop = stack1.peek();
						if (stackTop == 0)
						{
							begin++;
						}
						back[stackOut] = Math.min(back[stackOut], DFSNum[stackTop]);
						if(DFSNum[stackTop] > back[stackOut])
						{
							back[stackTop] = Math.min(back[stackOut],back[stackTop]);
						}
						else
						{
						if(Arrays.asList(connector).contains(g.members[stackTop].name)==false)
				
						{
							if (stackTop != 0 || (stackTop == 0 && begin > 1))
							{
								con++;
								connector[con] = g.members[stackTop].name;
								connectorFinal.add(g.members[stackTop].name);
							}
						}
						Friend check = g.members[stackTop].first;
						while (check != null)
						{
							if (visitStatus[check.fnum]==true)
							{
								back[stackTop] = Math.min(back[stackTop], DFSNum[check.fnum]);
							}
							check = check.next;
						}
			
					}
					neighbors = g.members[stackTop].first;
				}
					
			}
			//System.out.println(DFSFinal);	
		}	
		
	}
		//	System.out.println(connectorFinal);
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
	return connectorFinal;
		
}
}

