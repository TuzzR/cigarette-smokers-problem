# cigarette-smokers-problem
 Cigarette Smokers Problem and the Limits of Semaphores and Locks
JAVA: The Cigarette Smoker’s problem consists of one Agent and three Smokers. To smoke, one must have paper, matches, and tobacco. The Table has one of these three resources. Smoker One has an infinite supply of paper and tobacco; Smoker Two, an infinite supply of paper and matches, and Smoker Three, an infinite supply of matches and tobacco. The three smokers periodically check the Table to see if the item they need are present. If they are, they take the item (which empties the table), release the table, and signal the Agent, which will restock the table with a random item. If the required item is not there, the Smoker releases the table to try again later.

If smoking bothers you, use any problem in which there are three required components;

for example: bread, peanut butter, and jelly.

The Cigarette Smoker’s Problem involves a shared resource with one Table (containing one of the three items, which can be represented by something as simple as an integer) and four threads: one Agent and three Smokers. Access to the Table is as in the Producer-Consumer problem: only one thread can access the Table at any time. The added twist is that the Smokers must occasionally communicate with the Agent – if they remove the item they need, the table is empty and must be replenished by the Agent. We accomplish this with a second semaphore which we initialize with zero rather than one. Let’s call the two semaphores tableSemaphore and agentSemaphore. The tableSemaphore is initialized with one (there is one Table) but the agentSemaphore is initialized to zero. The Agent attempts to access the (initially empty) table and when successful places an item on the Table. The agent then releases the Table and then attempts to acquire himself through agentSemaphore. This is automatically denied, as we’ve initialized that semaphore to zero; that puts the Agent to sleep in a wait queue. Whenever a Smoker has cleared the table and needs to signal the Agent, they simply issue a release on agentSemaphore; the Agent then wakes up and can then attempt to access the Table (through tableSemaphore) and restock the table. When the Agent is through restocking the table, the Agent releases the table as before and then attempts to acquire agentSemaphore again, but its value is again zero so the Agent goes back to sleep on the wait queue until another Smoker issues a release.

Implement the Cigarette Smoker’s Problem using the techniques illustrated in this lecture (as opposed to the incredibly horrible Cigarette Smokers Problem solutions found on the ‘net). Make the Agent and the Smokers pretty verbose, so we always know what they’re trying to do, and add lots of delay loops to keep things interesting. Your output might look something like this…

Welcome to Cigarette Smokers!

How many smokers would you like (three minimum): 3

Starting the program with 3 smokers...

Starting Smoker 0...

Agent is running...

Starting Smoker 1...

Starting Smoker 2...

Agent is trying to acquire the table...

Agent has the table...

Smoker 1 trying to acquire the table...

Smoker 2 trying to acquire the table...

Agent put tobacco on the table...

Agent going to sleep...

Smoker 1 has the table!

Smoker 0 trying to acquire the table...

Smoker 1 didn't find paper...

Smoker 1 going back to sleep...

Smoker 2 has the table!

Smoker 1 trying to acquire the table...

Smoker 2 found tobacco!

Smoker 2 is smoking...

Agent is awake!

Agent is running...

Smoker 0 has the table!

Smoker 0 didn't find matches...

Smoker 0 going back to sleep...

Smoker 1 has the table!

Agent is trying to acquire the table...

Smoker 0 trying to acquire the table...

Smoker 1 didn't find paper...

Smoker 1 going back to sleep...

Agent has the table...

Agent put tobacco on the table...

Agent going to sleep...

Smoker 0 has the table!

Smoker 2 trying to acquire the table...

Smoker 1 trying to acquire the table...

Smoker 0 didn't find matches...

Smoker 0 going back to sleep...

Smoker 2 has the table!

Smoker 2 found tobacco!

Smoker 2 is smoking...

Smoker 1 has the table!

Agent is awake!

Agent is running...

Agent is trying to acquire the table...
