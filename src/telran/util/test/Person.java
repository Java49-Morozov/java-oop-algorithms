package telran.util.test;

public class Person implements Comparable<Person> {
	private long id;
	private int age;
	private String name;
	public Person(long id, int age, String name) {
		this.id = id;
		this.age = age;
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public int getAge() {
		return age;
	}
	public String getName() {
		return name;
	}
	@Override
	public int compareTo(Person person) {
		
		return Long.compare(this.id, person.id);
	}
}
