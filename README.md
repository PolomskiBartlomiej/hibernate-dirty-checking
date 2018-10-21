# hibernate-dirty-checking

_Reference_: http://docs.jboss.org/hibernate/orm/5.3/userguide/html_single/Hibernate_User_Guide.html  
_Reference_: https://vladmihalcea.com/the-anatomy-of-hibernate-dirty-checking/  
_Reference_: https://vladmihalcea.com/how-to-customize-hibernate-dirty-checking-mechanism/

# preface
Historically Hibernate only supported diff-based dirty calculation for 
determining which entities in a persistence context have changed. This 
essentially means that Hibernate would keep track of the last known 
state of an entity in regards to the database (typically the last read 
or write). Then, as part of flushing the persistence context, Hibernate 
would walk every entity associated with the persistence context and check 
its current state against that "last known database state". This is by 
far the most thorough approach to dirty checking because it accounts for 
data-types that can change their internal state (java.util.Date is the 
prime example of this). However, in a persistence context with a large 
number of associated entities, it can also be a performance-inhibiting 
approach.

If your application does not need to care about "internal state changing 
data-type" use cases, bytecode-enhanced dirty tracking might be a 
worthwhile alternative to consider, especially in terms of performance. 
In this approach Hibernate will manipulate the bytecode of your classes 
to add "dirty tracking" directly to the entity, allowing the entity itself 
to keep track of which of its attributes have changed. During the flush 
time, Hibernate asks your entity what has changed rather than having to 
perform the state-diff calculations.

# immutability
Immutability can be specified for both entities and collections.

If a specific entity is immutable, it is good practice to mark it with the 
`@Immutable` annotation, because Internally, Hibernate is going to perform 
several optimizations, such as:
* reducing memory footprint since there is no need to retain the 
dehydrated state for the dirty checking mechanism,
* speeding-up the Persistence Context flushing phase since immutable 
entities can skip the dirty checking process.

When loading the entity and trying to change its state, Hibernate will 
skip any modification, therefore no `SQL UPDATE` statement is executed.

**While immutable entity changes are simply discarded, modifying an 
immutable collection end up in a HibernateException being thrown.**